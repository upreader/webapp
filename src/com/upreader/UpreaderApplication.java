package com.upreader;

import java.io.InputStream;
import java.nio.charset.Charset;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.upreader.aws.AmazonService;
import com.upreader.context.Context;
import com.upreader.controller.UpreaderHandler;
import com.upreader.dispatcher.Dispatcher;
import com.upreader.helper.JsonWriter;
import com.upreader.locale.LocaleManager;
import com.upreader.mustache.MustacheManager;
import com.upreader.thread.DeferredAsyncResource;
import com.upreader.thread.DeferredAsyncResourceStarter;
import com.upreader.thread.EndableThread;
import com.upreader.util.ConfigurationProperties;

/**
 * Main application class
 * 
 * @author Flavius
 */
public class UpreaderApplication {
	private Logger log = Logger.getLogger(UpreaderApplication.class);
	private static final UpreaderApplication INSTANCE = new UpreaderApplication();

	private final UpreaderVersion version;
	private final List<AsyncResource> asyncResources;
	private final List<DeferredAsyncResource> deferredAsyncResources;
	private final Configurator configurator;
	private final Dispatcher dispatcher;
	private final LocaleManager localeManager;
	private final Infrastructure infrastructure;
	private final JsonWriter standardJsw;
	private final MustacheManager mustacheManager;
	private final AmazonService amazonService;

	private List<InitializationTask> initializationTasks = null;
	private UpreaderServlet servlet;
	private OperationalState state = OperationalState.NEW;
	private ServletInitConfig servletConfig;
	private InitializationWaitThread iwt;
	private String testQuery = "SELECT 1 AS Result";
	private String testColumn = "Result";
	private String testValue = "1";
	private Charset defaultRequestCharset = null;
	private long startTime = 0L;
	private long endTime = 0L;

	public static enum OperationalState {
		NEW, INITIALIZED, RUNNING, STOPPED;
	}

	public UpreaderApplication() {
        System.setProperty("http.proxyHost", "localhost");
        System.setProperty("http.proxyPort", "8888");

		this.version = new UpreaderVersion();
		this.asyncResources = new ArrayList<>();
		this.deferredAsyncResources = new ArrayList<>();
		this.localeManager = new LocaleManager(this);
		this.configurator = new Configurator(this);
		this.infrastructure = new Infrastructure(this);
		this.mustacheManager = new MustacheManager(this);
		this.dispatcher = new Dispatcher(this, new UpreaderHandler(this), new BasicExceptionHandler(this));
		this.standardJsw = new JsonWriter();
		this.amazonService = new AmazonService(this, this.standardJsw);
	}

	/**
	 * Configure the object
	 * 
	 * @param configurationProperties
	 *            - loaded configuration properties
	 */
	public void configure(ConfigurationProperties configurationProperties) {

	}

	protected void initialize(ServletInitConfig config) {
		this.servletConfig = config;
		displayBanner();
		this.log.debug("Upreader initializing.");
		runInitializationTasks();

		Runnable toRunWhenGood = new Runnable() {
			public void run() {
				UpreaderApplication.this.postInitialize();
				UpreaderApplication.this.begin();
			}
		};

		if (evaluateInitialization()) {
			toRunWhenGood.run();
		} else {
			waitForInitialization(toRunWhenGood);
		}
	}

	protected void postInitialize() {
		this.state = OperationalState.INITIALIZED;

		if (this.iwt != null) {
			this.iwt.setKeepRunning(false);
		}

		if (getConfigurator().isConfigured()) {
			log.debug("Upreader post-initializing.");
			
			// add here post-initialization things
		}
	}

	/**
	 * Last method called at application startup. Handles also starting
	 * asynchronous resources.
	 */
	public void begin() {
		if (getConfigurator().isConfigured()) {
			log.debug("Upreader starting asynchronous resources.");
			this.state = OperationalState.RUNNING;

			this.endTime = 0L;
			this.startTime = System.currentTimeMillis();

			startAsynchronousResources();
		}
	}

	/**
	 * Shuts down the application and its aync resources
	 */
	public void shutdown() {
		log.debug("Upreader stopping asynchronous resources.");
		this.state = OperationalState.STOPPED;

		if (this.iwt != null) {
			this.iwt.setKeepRunning(false);
		}

		this.endTime = System.currentTimeMillis();

		stopAsynchronousResources();
        log.debug("Upreader asynchronous resources stopped.");
	}

	/**
	 * Starts all registered async resources
	 */
	public void startAsynchronousResources() {
		for (AsyncResource asynch : this.asyncResources) {
			asynch.begin();
		}

		if (this.deferredAsyncResources.size() > 0) {
			DeferredAsyncResourceStarter thread = new DeferredAsyncResourceStarter(this.deferredAsyncResources);
			thread.start();
		}
	}

	/**
	 * Stops all registered async resources
	 */
	public void stopAsynchronousResources() {
		for (AsyncResource asynch : this.asyncResources) {
			asynch.end();
		}

		for (DeferredAsyncResource asynch : this.deferredAsyncResources) {
			asynch.end();
		}
	}

	protected void displayBanner() {
		String javaVm = System.getProperty("java.vm.name", "Unknown");
		String javaVmVersion = System.getProperty("java.vm.version", "?");
		String osName = System.getProperty("os.name", "Unknown");
		String osVersion = System.getProperty("os.version", "?");
		String osArch = System.getProperty("os.arch", "Unknown");

		log.debug(javaVm + " (v" + javaVmVersion + ")");
		log.debug(osName + " (v" + osVersion + "; " + osArch + ")");

		if (getServletConfig() != null) {
			this.log.debug("Servlet Container: " + getServletConfig().getServerInfo());
		}

		log.debug("JVM memory: " + Runtime.getRuntime().totalMemory() / 1048576L + "Mb; free: " + Runtime.getRuntime().freeMemory()
				/ 1048576L + "Mb");
	}

	protected boolean evaluateInitialization() {
		boolean goodState = true;
		Iterator<InitializationTask> iter = getInitializationTasks();
		while ((goodState) && (iter != null) && (iter.hasNext())) {
			goodState = goodState && iter.next().isTaskReady(this);
		}
		return goodState;
	}

	protected void waitForInitialization(Runnable toRunWhenGood) {
		log.info("Initialization is ongoing.  Starting initialization-wait thread.");
		if (this.iwt != null) {
			this.iwt.setKeepRunning(false);
		}
		this.iwt = new InitializationWaitThread(toRunWhenGood);
		this.iwt.start();
	}

	/**
	 * @return application version
	 */
	public UpreaderVersion getVersion() {
		return version;
	}

	/**
	 * @return the singleton instance of the application
	 */
	public static UpreaderApplication getInstance() {
		return INSTANCE;
	}

	/**
	 * @return the application Configurator
	 */
	public Configurator getConfigurator() {
		return configurator;
	}

	public MustacheManager getMustacheManager() {
		return mustacheManager;
	}

	public JsonWriter getJavaScriptWriter() {
		return standardJsw;
	}

	public Infrastructure getInfrastructure() {
		return infrastructure;
	}

	public LocaleManager getLocaleManager() {
		return localeManager;
	}

	public Dispatcher getDispatcher() {
		return dispatcher;
	}

	public Context getContext(UpreaderRequest request, EntityManagerFactory entityManagerFactory, DataSource dataSource) {
		return new Context(request, this, entityManagerFactory, dataSource);
	}

	protected void setServlet(UpreaderServlet servlet) {
		this.servlet = servlet;
	}

	public UpreaderServlet getServlet() {
		return servlet;
	}

    public boolean isRunning() {
		return this.state == OperationalState.RUNNING;
	}

	public OperationalState getState() {
		return this.state;
	}

	public ServletInitConfig getServletConfig() {
		return this.servletConfig;
	}
	
	public AmazonService getAmazonService() {
		return amazonService;
	}

	/**
	 * Returns the value of an init-param configuration parameter from the
	 * Upreader servlet
	 * 
	 * @param name
	 *            name of the parameter
	 * @return parameter value
	 */
	public String getServletConfigParameter(String name) {
		if (getServletConfig() != null) {
			return getServletConfig().getInitParameter(name);
		}
		return null;
	}

	public InputStream getResourceAsStream(String filename) {
		if (getServletConfig() != null) {
			return getServletConfig().getResourceAsStream("WEB-INF/" + filename);
		}

		return null;
	}

	public Charset getDefaultRequestCharset() {
		return this.defaultRequestCharset == null ? Charset.defaultCharset() : this.defaultRequestCharset;
	}

	/**
	 * Register a resource to start after the application started (in the same
	 * thread)
	 * 
	 * @param asyncResource
	 */
	public void addAsyncResource(AsyncResource asyncResource) {
		if (!this.asyncResources.contains(asyncResource)) {
			this.asyncResources.add(asyncResource);
		}
	}

	/**
	 * Register a resource to start after the application started (in another
	 * thread)
	 * 
	 * @param asyncResource
	 */
	public void addAsyncResource(DeferredAsyncResource asyncResource) {
		if (!this.deferredAsyncResources.contains(asyncResource)) {
			this.deferredAsyncResources.add(asyncResource);
		}
	}

	/**
	 * @return the number of ms Upreader has been running
	 */
	public long getUptime() {
		if (this.startTime > 0L) {
			if (this.endTime > 0L) {
				return this.endTime - this.startTime;
			}

			return System.currentTimeMillis() - this.startTime;
		}

		return 0L;
	}

	/**
	 * Register an initialization task to at application startup
	 * 
	 * @param task
	 */
	public void addInitializationTask(InitializationTask task) {
		if (this.initializationTasks == null) {
			this.initializationTasks = new ArrayList<>();
		}

		this.initializationTasks.add(task);
	}

	/**
	 * @return registered initialization tasks
	 */
	public Iterator<InitializationTask> getInitializationTasks() {
		if (this.initializationTasks != null) {
			return this.initializationTasks.iterator();
		}
		return null;
	}

	protected void runInitializationTasks() {
		Iterator<InitializationTask> iter = getInitializationTasks();
		while ((iter != null) && (iter.hasNext())) {
			iter.next().taskInitialize(this);
		}
	}

	protected void retryInitialization(int attemptNumber) {
		Iterator<InitializationTask> iter = getInitializationTasks();
		while ((iter != null) && (iter.hasNext())) {
			((InitializationTask) iter.next()).taskRetryInitialization(this, attemptNumber);
		}
	}

	class InitializationWaitThread extends EndableThread {
		public static final int INITIALIZATION_WAIT_TIME = 10000;
		private Runnable toRunWhenGood;
		private int attempts = 1;

		public InitializationWaitThread(Runnable toRunWhenGood) {
			super("Initialization Wait Thread", INITIALIZATION_WAIT_TIME);
			this.toRunWhenGood = toRunWhenGood;
		}

		@Override
		public void run() {
			while (isRunning()) {
				try {
					if (attempts == 1 || attempts % 10 == 0)
						log.info("Waiting for initialization state to be 'good'.  Attempt #" + this.attempts + ".");

					simpleSleep();

					UpreaderApplication.this.retryInitialization(this.attempts++);

					if (UpreaderApplication.this.evaluateInitialization()) {
						log.debug("Initialization state has switched to 'good'.");
						setKeepRunning(false);
						this.toRunWhenGood.run();
					}
				} catch (Exception exc) {
					log.debug("Exception in InitializationWaitThread:" + exc);
				}

			}
		}
	}
}
