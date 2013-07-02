package com.upreader.context;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.security.Principal;
import java.util.Locale;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.sql.DataSource;

import org.apache.log4j.Logger;

import com.caucho.security.SecurityContext;
import com.google.common.io.BaseEncoding;
import com.upreader.Infrastructure;
import com.upreader.UpreaderApplication;
import com.upreader.UpreaderRequest;
import com.upreader.UpreaderSession;
import com.upreader.controller.ProjectDAO;
import com.upreader.controller.UserDAO;
import com.upreader.dispatcher.Dispatcher;
import com.upreader.security.TokenProvider;

/**
 * Full request context
 * 
 * @author Flavius
 */
public class Context {
	private Logger log = Logger.getLogger(Context.class);
	private static final String SO_TOKEN_PROVIDER = "_token_provider";
	public static final String SO_CONSUMABLE_REQUEST = "_consumable_prior_request";
	private static final ThreadLocal<Context> CONTEXTS_BY_THREAD = new ThreadLocal<>();
	private static final BaseEncoding TOKEN_ENCODING = BaseEncoding.base32().omitPadding();

	private final UpreaderApplication application;
	private final long processingStart;
	private final UpreaderRequest request;
	private final UpreaderRequest priorRequest;
	private final Dispatcher dispatcher;
	private final SessionNamedValues sessionNamedValues;
	private final Query query;
	private final Messages messages;
	private final Infrastructure infrastructure;
	private final EntityManagerFactory entityManagerFactory;
	private final DataSource dataSource;
	
	private Delivery delivery;
	private Cookies cookies;
	private Attachments files;
	private Headers headers;
	private UpreaderSession session;
	private UserDAO userDAO;
	private ProjectDAO projectDAO;
	
	private long requestNumber = 0L;
	
	private EntityManager entityManager;
	
	public Context(UpreaderRequest request, UpreaderApplication application, EntityManagerFactory entityManagerFactory, DataSource dataSource) {
		this.processingStart = System.currentTimeMillis();
		this.application = application;
		this.request = request;
		this.dispatcher = application.getDispatcher();
		this.infrastructure = application.getInfrastructure();
		this.sessionNamedValues = new SessionNamedValues(this);
		this.messages = new Messages(this);
		this.entityManagerFactory = entityManagerFactory;
		this.dataSource = dataSource;
		
		CONTEXTS_BY_THREAD.set(this);

		this.request.setAttribute("Context", this);

		getSession(false);

		if (session().has(SO_CONSUMABLE_REQUEST)) {
			this.priorRequest = ((UpreaderRequest) session().getObject(SO_CONSUMABLE_REQUEST));
			this.query = new Query(this.priorRequest);
			session().remove(SO_CONSUMABLE_REQUEST);
		} else {
			this.priorRequest = null;
			this.query = new Query(request);
		}

		setDefaultCharacterSets();
		
		this.entityManager = this.entityManagerFactory.createEntityManager();
		this.userDAO = new UserDAO(application, this.entityManager);
		this.projectDAO = new ProjectDAO(application, this.entityManager);
	}

	public static void complete() {
		Context current = CONTEXTS_BY_THREAD.get();
		current.entityManager.close();
		CONTEXTS_BY_THREAD.set(null);
	}

	public static Context get() {
		return (Context) CONTEXTS_BY_THREAD.get();
	}

	public UpreaderApplication getApplication() {
		return this.application;
	}

	public String getClientID() {
		return this.request.getClientID();
	}

	public String getContextPath() {
		return this.request.getContextPath();
	}

	public String getCurrentUri() {
		return this.request.getCurrentURI();
	}

	public Dispatcher getDispatcher() {
		return this.dispatcher;
	}

	public Infrastructure getInfrastructure() {
		return infrastructure;
	}

	public long getDuration() {
		return System.currentTimeMillis() - this.processingStart;
	}

	public Locale getLocale() {
		return this.application.getLocaleManager().getLocale(this);
	}

	public OutputStream getOutputStream() throws IOException {
		return this.request.getOutputStream();
	}

	public String getQueryString() {
		return this.request.getQueryString();
	}

	public String getRealPath(String path) {
		return this.request.getRealPath(path);
	}

	public UpreaderRequest getRequest() {
		return this.request;
	}

	public String getRequestMethod() {
		return this.request.getRequestMethod();
	}

	public long getRequestNumber() {
		return this.requestNumber;
	}

	public String getRequestSignature() {
		return this.request.getRequestSignature();
	}

	public String getRequestUri() {
		return this.request.getRequestURI();
	}

	public SessionNamedValues session() {
		return this.sessionNamedValues;
	}

	public Attachments files() {
		if (this.files == null) {
			this.files = new Attachments(this);
		}
		return this.files;
	}

	public Delivery delivery() {
		if (this.delivery == null) {
			this.delivery = new Delivery();
		}
		return this.delivery;
	}

	public Cookies cookies() {
		if (this.cookies == null) {
			this.cookies = new Cookies(this);
		}
		return this.cookies;
	}

	public Headers headers() {
		if (this.headers == null) {
			this.headers = new Headers(this);
		}
		return this.headers;
	}

	public Messages messages() {
		return this.messages;
	}

	public Query query() {
		return this.query;
	}

	public UpreaderSession getSession(boolean create) {
		if (this.session == null) {
			this.session = this.request.getSession(create);
		}
		return this.session;
	}
	
	public EntityManager em() {
		return this.entityManager;
	}
	
	public UserDAO userDAO() {
		return this.userDAO;
	}
	
	public ProjectDAO getProjectDAO() {
		return projectDAO;
	}
	
	public long getStartTime() {
		return this.processingStart;
	}

	public PrintWriter getWriter() throws IOException {
		return this.request.getWriter();
	}

	public boolean isCommitted() {
		return this.request.isCommitted();
	}

	public boolean isNewSession() {
		UpreaderSession currentSession = getSession(false);
		return currentSession == null ? true : currentSession.isNew();
	}

	public boolean isHead() {
		if (isPriorRequestBound()) {
			return this.priorRequest.isHead();
		}

		return this.request.isHead();
	}

	public boolean isGet() {
		if (isPriorRequestBound()) {
			return this.priorRequest.isGet();
		}

		return this.request.isGet();
	}

	public boolean isPost() {
		if (isPriorRequestBound()) {
			return this.priorRequest.isPost();
		}

		return this.request.isPost();
	}

	public boolean isPut() {
		if (isPriorRequestBound()) {
			return this.priorRequest.isPut();
		}

		return this.request.isPut();
	}

	public boolean isDelete() {
		if (isPriorRequestBound()) {
			return this.priorRequest.isDelete();
		}

		return this.request.isDelete();
	}

	public boolean isTrace() {
		if (isPriorRequestBound()) {
			return this.priorRequest.isTrace();
		}

		return this.request.isTrace();
	}

	public boolean isOptions() {
		if (isPriorRequestBound()) {
			return this.priorRequest.isOptions();
		}

		return this.request.isOptions();
	}

	public boolean isConnect() {
		if (isPriorRequestBound()) {
			return this.priorRequest.isConnect();
		}

		return this.request.isConnect();
	}

	public boolean isPatch() {
		if (isPriorRequestBound()) {
			return this.priorRequest.isPatch();
		}

		return this.request.isPatch();
	}

	public boolean isPriorRequestBound() {
		return this.priorRequest != null;
	}

	public boolean isSecure() {
		return this.request.isSecure();
	}

	public void print(String text) {
		try {
			this.request.print(text);
		} catch (IOException ioexc) {
			this.log.debug("IOException on print().");
		}
	}

	public boolean redirect(String redirectDestinationUrl) {
		return this.request.redirect(redirectDestinationUrl);
	}

	public boolean redirectPermanent(String redirectDestinationUrl) {
		return this.request.redirectPermanent(redirectDestinationUrl);
	}

	public void sendError(int error) {
		try {
			this.request.sendError(error);
		} catch (IOException localIOException) {
		}
	}

	public void sendError(int error, String message) {
		try {
			this.request.sendError(error, message);
		} catch (IOException localIOException) {
		}
	}

	public void setContentType(String contentType) {
		this.request.setContentType(contentType);
	}

	public void setDefaultCharacterSets() {
		try {
			this.request.setCharacterEncoding(this.application.getDefaultRequestCharset().displayName());
		} catch (UnsupportedEncodingException localUnsupportedEncodingException) {
		}
	}

	public void setLocale(Locale locale) {
		this.application.getLocaleManager().setLocale(this, locale);
	}

	public void setLocale(String languageID, String countryID) {
		this.application.getLocaleManager().setLocale(this, languageID, countryID);
	}

	public void setRequestNumber(long requestNumber) {
		this.requestNumber = requestNumber;
	}

	public String toString() {
		return "Context [" + getClientID() + "]";
	}

	public String getToken() {
		return TOKEN_ENCODING.encode(getTokenProvider().next());
	}

	protected TokenProvider getTokenProvider() {
		TokenProvider provider = (TokenProvider) session().getObject(SO_TOKEN_PROVIDER);
		if (provider == null) {
			provider = new TokenProvider(100, 16);
			session().putObject(SO_TOKEN_PROVIDER, provider);
		}
		return provider;
	}

	public boolean validateToken(String name) {
		String token = query().get(name);
		return (token != null) && (getTokenProvider().validate(TOKEN_ENCODING.decode(token)));
	}

	public void setStatus(int status) {
		this.request.setStatus(status);
	}

	public boolean includeFile(File file, String fileName, boolean asAttachment) {
		return includeFile(file, fileName, asAttachment, null);
	}

	public boolean includeFile(File file, String fileName, boolean asAttachment, String contentType) {
		return getRequest().includeFile(file, fileName, asAttachment, contentType);
	}

	public boolean render(String pageName) {
		return render(pageName, false);
	}

	public boolean render(String pageName, boolean fullyQualified) {
		return render(pageName, fullyQualified, null);
	}

	public boolean render(String pageName, boolean fullyQualified, String contentType) {
		getDispatcher().renderStarting(this, pageName);

		try {
			if (contentType != null) {
				setContentType(contentType);
			}

			getRequest().render(pageName, fullyQualified);
		} catch (Exception exc) {
			processRenderException(exc, pageName);
		} finally {
			getDispatcher().renderComplete(this);
		}

		return true;
	}
	
	public String getUserName() {
		Principal principal = SecurityContext.getUserPrincipal();
		return principal == null ? null : principal.getName();
	}
	
	public boolean isUserInRole(String roleName) {
		return SecurityContext.isUserInRole(roleName);
	}
	
	protected void processRenderException(Exception exc, String pageName) {
		processRenderException(exc, pageName, "Including " + pageName);
	}

	protected void processRenderException(Exception exc, String pageName, String description) {
		this.log.debug("Exception while including " + pageName + ": " + exc);

		this.dispatcher.dispatchException(this, exc, description);
	}
}
