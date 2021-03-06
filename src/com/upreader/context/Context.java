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

import com.upreader.controller.*;
import org.apache.log4j.Logger;

import com.caucho.security.SecurityContext;
import com.caucho.security.SecurityContextException;
import com.google.common.io.BaseEncoding;
import com.upreader.Infrastructure;
import com.upreader.UpreaderApplication;
import com.upreader.UpreaderRequest;
import com.upreader.UpreaderSession;
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
	private static final ThreadLocal<Context> CONTEXTS_BY_THREAD = new ThreadLocal<>();
	private static final BaseEncoding TOKEN_ENCODING = BaseEncoding.base32().omitPadding();

	private final UpreaderApplication application;
	private final long processingStart;
	private final UpreaderRequest request;
	private final Dispatcher dispatcher;
	private final SessionNamedValues sessionNamedValues;
	private final Query query;
	private final Messages messages;
	private final Infrastructure infrastructure;
	private final EntityManagerFactory entityManagerFactory;
	private final DataSource dataSource;
	
	private Cookies cookies;
	private Attachments files;
	private Headers headers;
	private UpreaderSession session;
	private UserDAO userDAO;
	private ProjectDAO projectDAO;
    private BooksDAO booksDAO;
    private StocksDAO stocksDAO;
    private NotificationsDAO notificationsDAO;
	
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

		this.query = new Query(request);
		
		setDefaultCharacterSets();
		
		this.entityManager = this.entityManagerFactory.createEntityManager();
		this.userDAO = new UserDAO(application, this.entityManager);
		this.projectDAO = new ProjectDAO(application, this.entityManager);
        this.booksDAO = new BooksDAO(application, this.entityManager);
        this.stocksDAO = new StocksDAO(application, this.entityManager);
        this.notificationsDAO = new NotificationsDAO(application, this.entityManager);
	}

	public static void complete() {
		Context current = CONTEXTS_BY_THREAD.get();
        if(current != null) {
		    current.entityManager.close();
		    CONTEXTS_BY_THREAD.set(null);
        }
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

	public UpreaderRequest request() {
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
	
	public ProjectDAO projectDAO() {
		return projectDAO;
	}

    public BooksDAO booksDAO() {
        return booksDAO;
    }

    public StocksDAO stocksDAO() {
        return stocksDAO;
    }

    public NotificationsDAO notificationsDAO() {
        return notificationsDAO;
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
		return this.request.isHead();
	}

	public boolean isGet() {
		return this.request.isGet();
	}

	public boolean isPost() {
		return this.request.isPost();
	}

	public boolean isPut() {
		return this.request.isPut();
	}

	public boolean isDelete() {
		return this.request.isDelete();
	}

	public boolean isTrace() {
		return this.request.isTrace();
	}

	public boolean isOptions() {
		return this.request.isOptions();
	}

	public boolean isConnect() {
		return this.request.isConnect();
	}

	public boolean isPatch() {
		return this.request.isPatch();
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

    /**
     * Send a normal redirect using the HttpServletResponse
     *
     * @param url url to redirect to
     * @return
     */
	public boolean redirect(String url) {
		return this.request.redirect(url);
	}

    /**
     * Redirect using a 301 Moved Permanently status
     *
     * @param url the absolute URL to redirect to
     * @return
     */
	public boolean redirectPermanent(String url) {
		return this.request.redirectPermanent(url);
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
	
	/**
	 * Returns an encrypted token for this application instance
	 * 
	 * @return
	 */
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
	
	/**
	 * Checks that an issued token is valid for this application instance
	 * 
	 * @param name
	 * @return
	 */
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
		return request().includeFile(file, fileName, asAttachment, contentType);
	}

    /**
     * Forwards to the specified page name inside WEB-INF/jsp
     *
     * @param pageName jsp page name, including extension
     * @return
     */
	public boolean forward(String pageName) {
		return forward(pageName, false);
	}

    /**
     * Forwards to the specified jsp page
     *
     * @param pageName jsp page name, including extension
     * @param fullyQualified true if the page name contains the path to WEB-INF/jsp or not
     * @return
     */
	public boolean forward(String pageName, boolean fullyQualified) {
		return forward(pageName, fullyQualified, null);
	}

    /**
     * Forwards to the specified jsp jage
     * @param pageName  jsp page name, including extension
     * @param fullyQualified true if the page name contains the path to WEB-INF/jsp or not
     * @param contentType the content type to set on the response or null
     * @return
     */
	public boolean forward(String pageName, boolean fullyQualified, String contentType) {
		getDispatcher().renderStarting(this, pageName);

		try {
			if (contentType != null) {
				setContentType(contentType);
			}

			request().forwardToJsp(pageName, fullyQualified);
		} catch (Exception exc) {
			processRenderException(exc, pageName);
		} finally {
			getDispatcher().renderComplete(this);
		}

		return true;
	}

    /**
     * Returns the currently logged-in user name
     *
     * @return user name
     * @throws SecurityContextException
     */
	public String username() throws SecurityContextException {
		Principal principal = SecurityContext.getUserPrincipal();
		return principal == null ? null : principal.getName();
	}

    /**
     * Checks if the current user is in the specified role
     * @param roleName role to check
     * @return
     */
	public boolean isUserInRole(String roleName) {
		return SecurityContext.isUserInRole(roleName);
	}
	
	public DataSource getDataSource() {
		return dataSource;
	}

	protected void processRenderException(Exception exc, String pageName) {
		this.log.debug("Exception while including " + pageName + ": " + exc);
		this.dispatcher.dispatchException(this, exc);
	}
}
