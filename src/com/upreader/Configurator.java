package com.upreader;

import java.io.File;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.log4j.Logger;

import com.upreader.configuration.ConfigurationProvider;
import com.upreader.configuration.FileConfigurationProvider;
import com.upreader.util.Configurable;
import com.upreader.util.ConfigurationProperties;

/**
 * Provides configuration services for the entire application
 * 
 * @author Flavius
 */
public class Configurator implements InitializationTask {
	private Logger log = Logger.getLogger(Configurator.class);

	private final UpreaderApplication application;
	private final List<Configurable> configurableComponents = new CopyOnWriteArrayList<>();
	private final List<ConfigurationProvider> providers = new ArrayList<>(2);
	private boolean configured = false;
	private ConfigurationProperties lastProps = null;

	public Configurator(UpreaderApplication application) {
		this.application = application;
		addStandardProviders();
		application.addInitializationTask(this);
	}

	public UpreaderApplication getApplication() {
		return application;
	}

	protected void addProvider(ConfigurationProvider provider) {
		this.providers.add(provider);
	}

	protected void addStandardProviders() {
		addProvider(new FileConfigurationProvider());
	}

	@Override
	public void taskInitialize(UpreaderApplication application) {
		try {
			configureIfNecessary();
		} catch (Exception exc) {
			this.log.debug("Configuration failed.", exc);
		}
	}
	
	@Override
	public void taskRetryInitialization(UpreaderApplication application, int attemptNo) {
		try {
			configureIfNecessary();
		} catch (Exception exc) {
			log.debug("Configuration failed. See earlier report.");
		}
	}
	
	@Override
	public boolean isTaskReady(UpreaderApplication application) {
		return isConfigured();
	}

	public boolean configure() {
		UpreaderVersion version = this.application.getVersion();
		this.log.debug("Configuring Upreader application.");
		ConfigurationProperties props = getConfigurationFromProviders();
		if (props != null && props.size() > 0) {
			addStandardProperties(props, this.application);
			configureWithProps(props, version);
			this.configured = true;
			this.lastProps = props;
			return true;
		}

		this.log.debug("Configuration failed; unable to read configuration file(s).");
		return false;
	}

	public void configure(Configurable configurable) {
		configurable.configure(getLastProperties());
	}

	protected ConfigurationProperties constructProperties() {
		ConfigurationProperties props = new ConfigurationProperties(this.application);
		return props;
	}

	protected ConfigurationProperties getConfigurationFromProviders() {
		ConfigurationProperties props = null;

		for (ConfigurationProvider provider : this.providers) {
			props = constructProperties();
			boolean loaded = provider.load(this.application, props);
			if (loaded) {
				return props;
			}
		}

		return null;
	}

	public void addStandardProperties(ConfigurationProperties props, UpreaderApplication app) {
		ServletInitConfig config = app.getServletConfig();
		if (config != null) {
			Enumeration<String> enumeration = config.getInitParameterNames();
			while ((enumeration != null) && (enumeration.hasMoreElements())) {
				String name = enumeration.nextElement();
				props.put("Servlet.Parameter." + name, config.getInitParameter(name));
			}

			enumeration = config.getAttributeNames();
			while ((enumeration != null) && (enumeration.hasMoreElements())) {
				String name = (String) enumeration.nextElement();
				props.put("Servlet.Attribute." + name, config.getAttribute(name).toString());
			}

			if (config.getRealPath("/") != null) {
				props.put("Servlet.DeploymentRoot", config.getRealPath("/"));
			}

			if (config.getRealPath("/WEB-INF") != null) {
				props.put("Servlet.WebInf", config.getRealPath("/WEB-INF"));
			}

			try {
				File deployRoot = new File(config.getRealPath("/"));
				String appRootPath = deployRoot.getParent();
				props.put("Servlet.ApplicationRoot", appRootPath);

				File appRoot = new File(appRootPath);
				props.put("Servlet.ApplicationRoot.LastDir", appRoot.getName());
			} catch (Exception exc) {
				if (config.getRealPath("/") != null) {
					props.put("Servlet.ApplicationRoot", config.getRealPath("/"));
				}
			}
		}

		String machineName = getMachineName();
		if (machineName != null) {
			props.put("Servlet.MachineName", machineName);
		}
	}

	public static String getMachineName() {
		String machineName = System.getenv("COMPUTERNAME");

		if (machineName == null) {
			try {
				machineName = InetAddress.getLocalHost().getHostName();
			} catch (UnknownHostException e) {
				e.printStackTrace();
			}
		}

		if (machineName != null) {
			machineName = machineName.toUpperCase();
		}

		return machineName;
	}

	public ConfigurationProperties getLastProperties() {
		return this.lastProps;
	}

	protected boolean configureIfNecessary() {
		if (!isConfigured()) {
			return configure();
		}

		return true;
	}

	public boolean isConfigured() {
		return this.configured;
	}

	protected void configureWithProps(ConfigurationProperties props, UpreaderVersion version) {
		this.application.configure(props);
		configureConfigurables(props);
	}

	public void addConfigurable(Configurable configurable) {
		this.configurableComponents.add(configurable);
	}

	public void removeConfigurable(Configurable configurable) {
		this.configurableComponents.remove(configurable);
	}

	protected void configureConfigurables(ConfigurationProperties props) {
		int size = this.configurableComponents.size();
		this.log.debug("Configuring " + size + " Configurables.");

		for (Configurable configurable : this.configurableComponents) {
			this.log.debug("Configuring " + configurable + ".");
			configurable.configure(props);
		}
		this.log.debug("Done configuring Configurables.");
	}
}
