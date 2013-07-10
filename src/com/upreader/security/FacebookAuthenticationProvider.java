package com.upreader.security;

import com.upreader.UpreaderRequest;
import org.scribe.builder.*;
import org.scribe.builder.api.*;
import org.scribe.model.*;
import org.scribe.oauth.*;

/**
 * Created
 * User: Razvan.Ionescu
 * Date: 7/10/13
 */
public class FacebookAuthenticationProvider implements AuthenticationProvider {
    /*
     * Facebook Constants
     */
    public static final String OAUTH_CODE = "code";
    public static final String OAUTH_TOKEN = "token";
    public static final String NETWORK_NAME = "Facebook";
    private static final Token  EMPTY_TOKEN = null;
    private static final String PROTECTED_RESOURCE_URL = "https://graph.facebook.com/me";


    private String apiKey = "547932105271250";
    private String apiSecret = "5f1245f9a6b0a3c132930ecc48a43fd0";
    private String upreaderCallback = "http://dev.upreader.com:8080/upreader/i/loginWithFacebook";
    private OAuthService fbService;

    public FacebookAuthenticationProvider(String apiKey, String apiSecret, String upreaderCallback){
        this.apiKey  = apiKey;
        this.apiSecret = apiSecret;
        this.upreaderCallback = upreaderCallback;

        fbService = new ServiceBuilder()
                        .provider(FacebookApi.class)
                        .apiKey(apiKey)
                        .apiSecret(apiSecret)
                        .callback(upreaderCallback)
                        .build();
    }

    public FacebookAuthenticationProvider(){
        fbService = new ServiceBuilder()
                        .provider(FacebookApi.class)
                        .apiKey(apiKey)
                        .apiSecret(apiSecret)
                        .callback(upreaderCallback)
                        .build();
    }

    public String performLogin(UpreaderRequest request, String code){
        Verifier verifier = new Verifier(code);
        Token accessToken = fbService.getAccessToken(EMPTY_TOKEN, verifier);
        return readFbUserDetails(accessToken);
    }

    public String readFbUserDetails(String token){
        Token accessToken = new Token(token, apiSecret);
        OAuthRequest userDetailsReq = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        fbService.signRequest(accessToken, userDetailsReq);
        Response response = userDetailsReq.send();
        return response.getBody();
    }

    public String readFbUserDetails(Token accessToken){
        OAuthRequest userDetailsReq = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        fbService.signRequest(accessToken, userDetailsReq);
        Response response = userDetailsReq.send();
        return response.getBody();
    }

    public String getAuthorizationURL(){
        return fbService.getAuthorizationUrl(EMPTY_TOKEN);
    }

    public OAuthService getFbService() {
        return fbService;
    }
}
