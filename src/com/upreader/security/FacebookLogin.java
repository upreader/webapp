package com.upreader.security;

import com.caucho.security.AbstractLogin;
import com.caucho.security.NullAuthenticator;
import com.caucho.server.security.CachingPrincipal;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import facebook4j.FacebookFactory;
import facebook4j.User;
import facebook4j.auth.AccessToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

public class FacebookLogin extends AbstractLogin {
    public static final String OAUTH_CODE = "code";
    public static final String OAUTH_TOKEN = "token";
    private static final String API_KEY = "676106519069423";
    private static final String API_SECRET = "2f22e41f43e2d8793cc5ce56d15d798a";
    private static final String CALLBACK = "http://www.upreader.com:8080/upreader/i/loginWithFacebook";

    public FacebookLogin() {
        setAuthenticator(new NullAuthenticator());
    }

    @Override
    protected Principal getUserPrincipalImpl(HttpServletRequest request) {
        String code = request.getParameter(OAUTH_CODE);

        Facebook facebook = (Facebook) request.getSession().getAttribute("facebook");
        if(facebook != null && code != null && code.length() > 0) {
            try {
                facebook.getOAuthAccessToken(code);
                User user = facebook.getMe();
                String email = user.getEmail();
                return new CachingPrincipal(email);
            } catch (FacebookException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void loginChallenge(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Facebook facebook = new FacebookFactory().getInstance();
        facebook.setOAuthAppId(API_KEY, API_SECRET);
        facebook.setOAuthPermissions("email, publish_actions");
        request.getSession().setAttribute("facebook", facebook);
        response.sendRedirect(facebook.getOAuthAuthorizationURL(CALLBACK));
    }
}
