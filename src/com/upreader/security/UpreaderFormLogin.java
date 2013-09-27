package com.upreader.security;

import com.caucho.config.Service;
import com.caucho.security.*;
import com.caucho.server.http.CauchoRequest;
import com.caucho.server.session.SessionManager;
import com.caucho.server.webapp.WebApp;
import com.upreader.UpreaderConstants;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

/**
 * Custom Form Login
 */
@Service
public class UpreaderFormLogin extends FormLogin {
    @Override
    public Principal getUserPrincipalImpl(HttpServletRequest request) {
        Authenticator auth = getAuthenticator();
        if ((auth instanceof CookieAuthenticator)) {
            CookieAuthenticator cookieAuth = (CookieAuthenticator) auth;
            Cookie cookie = ((CauchoRequest) request).getCookie(UpreaderConstants.LOGIN_COOKIE_NAME);
            if (cookie != null) {
                Principal user = cookieAuth.authenticateByCookie(cookie.getValue());

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

    @Override
    public void loginSuccessResponse(Principal user, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getAttribute(LOGIN_CHECK) != null)
            return;

        request.setAttribute(LOGIN_CHECK, "login");
        WebApp webApp = WebApp.getCurrent();
        Authenticator auth = getAuthenticator();
        if (auth instanceof CookieAuthenticator) {
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

        if (path.endsWith("/j_security_check")) {
            RequestDispatcher disp = webApp.getNamedDispatcher("j_security_check");

            if (disp == null) {
                throw new ServletException("j_security_check servlet must be defined to use form-based login.");
            }
            disp.forward(request, response);
            return;
        }
    }

    private void generateCookie(Principal user, CookieAuthenticator auth, WebApp webApp, HttpServletRequest request, HttpServletResponse response) {
        if (webApp == null) {
            return;
        }
        SessionManager manager = webApp.getSessionManager();
        String value = manager.createCookieValue();
        Cookie cookie = new Cookie(UpreaderConstants.LOGIN_COOKIE_NAME, value);
        cookie.setVersion(1);
        // valid for 1 year
        cookie.setMaxAge(365 * 24 * 60 * 60);
        cookie.setPath("/");
        cookie.setDomain(webApp.generateCookieDomain(request));
        auth.associateCookie(user, value);
        response.addCookie(cookie);
    }
}
