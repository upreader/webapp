package com.upreader.security;

import com.caucho.security.AbstractLogin;
import com.caucho.security.NullAuthenticator;
import com.caucho.server.security.CachingPrincipal;
import com.upreader.UpreaderConstants;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.User;
import twitter4j.auth.AccessToken;
import twitter4j.auth.RequestToken;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.security.Principal;

public class TwitterLogin extends AbstractLogin {
    public static final String OAUTH_CODE = "oauth_verifier";
    private static final String API_KEY = "33448R6VFKpSdw2JYULtA";
    private static final String API_SECRET = "IP5f9CvfxI9Ophof0qrP01shOS9wYv5t19rSScUOOHk";
    private static final String CALLBACK = UpreaderConstants.UPREADER_HOST+UpreaderConstants.UPREADER_CONTEXT+"/i/loginWithTwitter";

    static {
        Twitter twitter = TwitterFactory.getSingleton();
        twitter.setOAuthConsumer(API_KEY, API_SECRET);
    }

    public TwitterLogin() {
        setAuthenticator(new NullAuthenticator());
    }

    @Override
    protected Principal getUserPrincipalImpl(HttpServletRequest request) {
        Twitter twitter = (Twitter) request.getSession().getAttribute("twitter");
        RequestToken requestToken = (RequestToken) request.getSession().getAttribute("twitterRequestToken");
        String verifier = request.getParameter("oauth_verifier");

        if(twitter != null && verifier != null && verifier.length() > 0) {
            try {
                twitter.getOAuthAccessToken(requestToken, verifier);
                request.getSession().removeAttribute("twitterRequestToken");
                User user = twitter.verifyCredentials();
                String username = user.getScreenName();
                return new CachingPrincipal(username);
            } catch (TwitterException e) {
                e.printStackTrace();
            }
        }

        return null;
    }

    @Override
    protected void loginChallenge(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Twitter twitter = TwitterFactory.getSingleton();
        twitter.setOAuthAccessToken(null);
        request.getSession().setAttribute("twitter", twitter);
        try {
            RequestToken requestToken = twitter.getOAuthRequestToken(CALLBACK);
            request.getSession().setAttribute("twitterRequestToken", requestToken);
            response.sendRedirect(requestToken.getAuthenticationURL());
        } catch (TwitterException e) {
            throw new IOException(e);
        }
    }
}
