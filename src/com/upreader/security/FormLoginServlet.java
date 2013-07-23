package com.upreader.security;

import java.io.IOException;
import java.security.Principal;
import java.util.logging.Logger;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.caucho.server.http.CauchoResponse;
import com.caucho.server.webapp.RequestDispatcherImpl;
import com.caucho.server.webapp.WebApp;
import com.caucho.util.L10N;
import com.upreader.util.EncryptHelper;

@WebServlet(name = "u_login", urlPatterns = "/u_login")
public class FormLoginServlet extends GenericServlet {
	private final Logger log = Logger.getLogger(FormLoginServlet.class.getName());
	private static final L10N L = new L10N(FormLoginServlet.class);
	
	@Override
	public void init() throws ServletException {
		super.init();
		FormLogin login = new FormLogin();
		login.setAuthenticator(WebApp.getCurrent().getAuthenticator());
		WebApp.getCurrent().setLogin(login);
	}
	
	public void service(ServletRequest request, ServletResponse response) throws ServletException, IOException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		res.setHeader("Access-Control-Allow-Origin", "*");
		
		WebApp webApp = (WebApp) req.getServletContext();
		FormLogin login = (FormLogin) webApp.getLogin();

		Principal user = login.login(req, res, true);

		if (res.isCommitted()) {
			return;
		}
		if (user == null) {
			String errorPage = login.getFormErrorPage();
			RequestDispatcherImpl disp = webApp.getRequestDispatcher(errorPage);
			if ((res instanceof CauchoResponse)) {
				((CauchoResponse) res).killCache();
				((CauchoResponse) res).setNoCache(true);
			} else {
				res.setDateHeader("Expires", 0L);
				res.setHeader("Cache-Control", "no-cache");
			}

			disp.error(req, res);
			return;
		}
		
		
		String uri = "http://www.upreader.com:8080/upreader/i/loginSuccessful?id="+EncryptHelper.encrypt(user.getName());
		
		String uriPwd = req.getRequestURI();
		int p = uriPwd.indexOf("/u_login");
		if (p >= 0) {
			uriPwd = uriPwd.substring(0, p + 1);
		}
		if (uri.length() != 0) {
			if (uri.charAt(0) == '/')
				uri = req.getContextPath() + uri;
			else if ((uri.indexOf(':') < 0) || ((uri.indexOf(':') >= uri.indexOf('/')) && (uri.indexOf('/') >= 0))) {
				uri = uriPwd + uri;
			}

		}

		res.sendRedirect(res.encodeRedirectURL(uri));
	}
}