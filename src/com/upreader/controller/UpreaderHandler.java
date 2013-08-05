package com.upreader.controller;

import java.io.File;
import java.security.Principal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upreader.security.FacebookLogin;
import org.apache.log4j.Logger;

import com.upreader.MimeTypes;
import com.upreader.RequestFile;
import com.upreader.UpreaderApplication;
import com.upreader.dispatcher.BasicPathHandler;
import com.upreader.dispatcher.PathDefault;
import com.upreader.dispatcher.PathSegment;
import com.upreader.helper.CollectionHelper;

/**
 * Test file
 * 
 * @author Flavius
 *
 */
public class UpreaderHandler extends BasicPathHandler {
	Logger log = Logger.getLogger(UpreaderHandler.class);
		
	public UpreaderHandler(UpreaderApplication application) {
		super(application);
	}
	
	@PathDefault
	public boolean homepage() {
		return context().render("home.jsp");
	}

    @PathSegment("loginWithFacebook")
    public boolean loginWithFacebook() {
        FacebookLogin login = new FacebookLogin();
        Principal user = login.login(context().request().getRawRequest(), context().request().getRawResponse(), true);
        if(user == null)
            return false;

        String uri = session().get("com.caucho.servlet.login.path");
        String query = session().get("com.caucho.servlet.login.query");
        session().remove("com.caucho.servlet.login.path");
        session().remove("com.caucho.servlet.login.query");

        if ((uri != null) && (query != null)) {
            uri = uri + "?" + query;
            return context().request().redirect(context().request().getRawResponse().encodeRedirectURL(uri));
        }
        else {
            return homepage();
        }
    }

    @PathSegment("loginWithTwitter")
    public boolean loginWithTwitter() {
//        String token        = context().request().getParameter(TwitterAuthenticationProvider.OAUTH_TOKEN);
//        String verifierCode = context().request().getParameter(TwitterAuthenticationProvider.OAUTH_CODE);
//
//        if(token != null && token.length() > 0 && verifierCode != null && verifierCode.length() > 0){
//            String result =  getApplication().getTwtauthProvider().performLogin(token, verifierCode);
//            log.debug("twitter login result " + result);
//            return context().redirect("http://www.upreader.com:8080/upreader");
//        }
//        else{
//            return context().redirect(getApplication().getTwtauthProvider().goToTwitterAuthorization());
//        }
        return false;
    }

	@PathSegment("loginSuccessful")
	public boolean loginSuccessful() {
        String prevPath = session().get("com.upreader.previous.path");
        String prevQuery = session().get("com.upreader.previous.query");
        if(prevPath != null && !prevPath.isEmpty()) {
            if(prevQuery != null && !prevQuery.isEmpty())
                prevPath += "?"+prevQuery;

            session().remove("com.upreader.previous.path");
            session().remove("com.upreader.previous.query");
            if(!prevPath.startsWith("/"))
                prevPath = "/" + prevPath;

            if(prevPath.startsWith(context().getContextPath()))
                return context().redirect(prevPath);
            else
                return context().redirect(context().getContextPath()+prevPath);
        }
        return context().redirect("http://www.upreader.com:8080/upreader");
	}
	
	@PathSegment("logout")
	public boolean logout() {
		context().getSession(false).invalidate();
		return homepage();
	}
	
	
	@PathSegment("s/u")
	public boolean userService() {
		return userController().doCmd(context());
	}
	
	@PathSegment("s/p")
	public boolean projectService() {
		return projectController().doCmd(context());
	}

	// TEST FEATURES ONLY BELOW HERE//
	@PathSegment("error")
	public boolean error() throws Exception {
		throw new Exception("ERRRRRORRR");
	}
	
	@PathSegment("message")
	public boolean messageDemo() {
		String signedURL = getApplication().getAmazonService().getSignedURL("c2e1b504-ef24-4d32-9ff3-a7d44913035b.pfx");
		
		return message("this is a message");
	}
	
	@PathSegment("json")
	public boolean jsonDemo() {		
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("str1", "v1");
		map.put("int1", 1);
		map.put("boolean1", true);
		map.put("float1", 1.3f);
		map.put("double1", 1.345634646346d);
		map.put("strArray1", new String[] { "s1", "s2", "s3" });
		map.put("strList1", CollectionHelper.toList("s1", "s2", "s3"));
		
		return json(map);
	}
	
	@PathSegment("redirect")
	public boolean redirectToJsp() {
		return redirectRelative("/json");
	}
	
	@PathSegment("upload")
	public boolean upload() {
		List<RequestFile> uploadedFiles = files().list();
		// do logic
		return true;
	}
	
	@PathSegment("download/inline")
	public boolean download() {
		return context().includeFile(new File("c:\\doc.pdf"), "doc.pdf", false, MimeTypes.PDF);
	}
	
	@PathSegment("download/attachment")
	public boolean attachment() {
		return context().includeFile(new File("c:\\doc.pdf"), "doc.pdf", true, MimeTypes.PDF);
	}
	
	@PathSegment("secure/message")
	public boolean secureMessage() {
		return message("this is secure");
	}
}
