package com.upreader.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.upreader.security.FacebookAuthenticationProvider;
import org.apache.log4j.Logger;

import com.upreader.BusinessException;
import com.upreader.MimeTypes;
import com.upreader.RequestFile;
import com.upreader.UpreaderApplication;
import com.upreader.dispatcher.BasicPathHandler;
import com.upreader.dispatcher.PathDefault;
import com.upreader.dispatcher.PathSegment;
import com.upreader.helper.CollectionHelper;
import com.upreader.model.User;

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
        String token = context().request().getParameter(FacebookAuthenticationProvider.OAUTH_TOKEN);
        String code = context().request().getParameter(FacebookAuthenticationProvider.OAUTH_CODE);

//        if(token != null && token.length() > 0){
//            //user previously authenticated
//            return homepage();
//        }
        if(code != null && code.length() > 0){
            //exchange code for token
            String result = getApplication().getFbauthProvider().performLogin(context().request(), code);
            log.debug("facebook login result " + result);
            return context().redirect("http://dev.upreader.com:8080/upreader");
        }else{
            return context().redirect(getApplication().getFbauthProvider().getAuthorizationURL());
        }
    }

	@PathSegment("loginSuccessful")
	public boolean loginSuccessful() {
		User user = context().userDAO().findbyUsername(context().username());
		context().session().putObject("_user_", user);
		return homepage();
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
