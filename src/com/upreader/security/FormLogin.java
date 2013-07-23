package com.upreader.security;

import java.io.IOException;
import java.security.Principal;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.caucho.security.BasicPrincipal;
import com.caucho.security.CookieAuthenticator;
import com.caucho.security.Credentials;
import com.caucho.security.PasswordCredentials;
import com.caucho.server.http.CauchoRequest;
import com.caucho.server.http.CauchoResponse;
import com.caucho.server.session.SessionManager;
import com.caucho.server.webapp.WebApp;
import com.caucho.util.L10N;

public class FormLogin extends AbstractLogin {
	private static final L10N L = new L10N(FormLogin.class);
	private static final Logger log = Logger.getLogger(FormLogin.class.getName());
	public static final String LOGIN_CHECK = "com.caucho.security.form.login";
	public static final String LOGIN_SAVED_PATH = "com.caucho.servlet.login.path";
	public static final String LOGIN_SAVED_QUERY = "com.caucho.servlet.login.query";
	protected String _loginPage = "/i/home";
	protected String _errorPage = "/i/loginFailed";
	protected boolean _internalForward = false;
	protected boolean _formURIPriority = true;
	private WebApp _webApp = WebApp.getCurrent();
	
	public String getFormErrorPage() {
		return _errorPage;
	}
	
	public String getAuthType() {
		return "Form";
	}
 
	public boolean isLoginUsedForRequest(HttpServletRequest request) {
		return request.getServletPath().indexOf("u_login") >= 0;
	}

	public Principal getUserPrincipalImpl(HttpServletRequest request) {
		com.caucho.security.Authenticator auth = getAuthenticator();

		if ((auth instanceof CookieAuthenticator)) {
			CookieAuthenticator cookieAuth = (CookieAuthenticator) auth;
			Cookie resinAuth = ((CauchoRequest) request).getCookie("resinauthid");
			if (resinAuth != null) {
				Principal user = cookieAuth.authenticateByCookie(resinAuth.getValue());
				if (user != null) {
					return user;
				}
			}
		}
		
		String userName = request.getParameter("j_username");
		String passwordString = request.getParameter("j_password");

		if ((userName == null) || (passwordString == null)) {
			return null;
		}
		char[] password = passwordString.toCharArray();

		BasicPrincipal basicUser = new BasicPrincipal(userName);

		Credentials credentials = new PasswordCredentials(password);

		Principal user = auth.authenticate(basicUser, credentials, request);

		return user;
	}

	protected boolean isSavedUserValid(HttpServletRequest request, Principal savedUser) {
		String userName = request.getParameter("j_username");

		return userName == null;
	}

	public void loginSuccessResponse(Principal user, HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		if (request.getAttribute(LOGIN_CHECK) != null)
			return;
		request.setAttribute(LOGIN_CHECK, "login");

		WebApp webApp = this._webApp;

		String jUseCookieAuth = request.getParameter("j_use_cookie_auth");

		com.caucho.security.Authenticator auth = getAuthenticator();

		if (((auth instanceof CookieAuthenticator)) && (((CookieAuthenticator) auth).isCookieSupported(jUseCookieAuth))) {
			CookieAuthenticator cookieAuth = (CookieAuthenticator) auth;
			generateCookie(user, cookieAuth, webApp, request, response);
		}
		
		String path = request.getServletPath();

		if (path == null)
			path = request.getPathInfo();
		else if (request.getPathInfo() != null) {
			path = path + request.getPathInfo();
		}
		if (path.equals("")) {
			path = request.getContextPath() + "/";
			response.sendRedirect(response.encodeRedirectURL(path));
			return;
		}

		if (path.endsWith("/u_login")) {
			RequestDispatcher disp = webApp.getNamedDispatcher("u_login");

			if (disp == null) {
				throw new ServletException(L.l("u_login servlet must be defined to use form-based login."));
			}
			disp.forward(request, response);
			return;
		}
	}

	public void loginChallenge(HttpServletRequest request, HttpServletResponse response) throws ServletException,
			IOException {
		String path = request.getServletPath();

		if (path == null)
			path = request.getPathInfo();
		else if (request.getPathInfo() != null) {
			path = path + request.getPathInfo();
		}
		if (path.equals("")) {
			path = request.getContextPath() + "/";
			response.sendRedirect(response.encodeRedirectURL(path));
			return;
		}

		WebApp webApp = this._webApp;

		String uri = request.getRequestURI();

		if (path.endsWith("/u_login")) {
			if ((response instanceof CauchoResponse)) {
				((CauchoResponse) response).setNoCache(true);
			} else {
				response.setHeader("Cache-Control", "no-cache");
				response.setDateHeader("Expires", 0L);
			}

			RequestDispatcher disp = webApp.getRequestDispatcher(this._errorPage);
			disp.forward(request, response);
			return;
		}
		if ((uri.equals(this._loginPage)) || (uri.equals(this._errorPage))) {
			request.getRequestDispatcher(path).forward(request, response);
			return;
		}

		HttpSession session = request.getSession();
		session.setAttribute(LOGIN_SAVED_PATH, path);
		session.setAttribute(LOGIN_SAVED_QUERY, request.getQueryString());

		if ((response instanceof CauchoResponse)) {
			((CauchoResponse) response).killCache();
			((CauchoResponse) response).setNoCache(true);
		} else {
			response.setHeader("Cache-Control", "no-cache");
		}

		if (!this._loginPage.startsWith("/")) {
			response.sendRedirect(response.encodeRedirectURL(this._loginPage));
			return;
		}

		request.setAttribute(LOGIN_CHECK, "login");

		RequestDispatcher disp = webApp.getRequestDispatcher(this._loginPage);
		disp.forward(request, response);

		if (log.isLoggable(Level.FINE))
			log.fine(this + " request '" + uri + "' has no authenticated user");
	}

	private void generateCookie(Principal user, CookieAuthenticator auth, WebApp webApp, HttpServletRequest request,
			HttpServletResponse response) {
		if (webApp == null) {
			return;
		}
		SessionManager manager = webApp.getSessionManager();
		String value = manager.createCookieValue();

		Cookie cookie = new Cookie("resinauthid", value);
		cookie.setVersion(1);

		long cookieMaxAge = 31536000000L;

		cookie.setMaxAge((int) (cookieMaxAge / 1000L));
		cookie.setPath("/");
		cookie.setDomain(webApp.generateCookieDomain(request));

		auth.associateCookie(user, value);

		response.addCookie(cookie);
	}
}