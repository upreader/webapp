package com.upreader.security;

import com.upreader.UpreaderRequest;
import org.scribe.builder.ServiceBuilder;
import org.scribe.builder.api.TwitterApi;
import org.scribe.model.*;
import org.scribe.oauth.OAuthService;

/**
 * Created
 * User: Razvan.Ionescu
 * Date: 7/20/13
 */
public class TwitterAuthenticationProvider {
    /*
 * Facebook Constants
 */
    public static final String OAUTH_CODE = "oauth_verifier";
    public static final String OAUTH_TOKEN = "oauth_token";
    public static final String NETWORK_NAME = "Twitter";
    private static final Token EMPTY_TOKEN = null;
    private static final String PROTECTED_RESOURCE_URL = "https://api.twitter.com/1.1/account/verify_credentials.json";


    private String apiKey = "SDE8lUbgJZ7gnll4rmgRQ";
    private String apiSecret = "Amh24eCHZtUf0fxXVtRA88voRCzDaUpUpqeLn7bXg";
    private String upreaderCallback = "http://dev.upreader.com:8080/upreader/i/loginWithTwitter";
    private OAuthService twtService;

    public TwitterAuthenticationProvider(String apiKey, String apiSecret, String upreaderCallback){
        this.apiKey  = apiKey;
        this.apiSecret = apiSecret;
        this.upreaderCallback = upreaderCallback;

        twtService = new ServiceBuilder()
                    .provider(TwitterApi.class)
                    .apiKey(apiKey)
                    .apiSecret(apiSecret)
                    .callback(upreaderCallback)
                    .build();
    }

    public TwitterAuthenticationProvider(){
        twtService = new ServiceBuilder()
                    .provider(TwitterApi.class)
                    .apiKey(apiKey)
                    .apiSecret(apiSecret)
                    .callback(upreaderCallback)
                    .build();
    }

    public String goToTwitterAuthorization(){
        Token requestToken = twtService.getRequestToken();
        return twtService.getAuthorizationUrl(requestToken);
    }

    public String performLogin(String token, String verifierCode){
        Verifier verifier = new Verifier(verifierCode);
        Token accessToken = twtService.getAccessToken(new Token(token, apiSecret), verifier);
        return readTwtUserDetails(accessToken);
    }

    public String readTwtUserDetails(Token accessToken){
        OAuthRequest userDetailsReq = new OAuthRequest(Verb.GET, PROTECTED_RESOURCE_URL);
        twtService.signRequest(accessToken, userDetailsReq);
        Response response = userDetailsReq.send();
        return response.getBody();
    }

    public OAuthService getTwtService() {
        return twtService;
    }
}
