package com.upreader.controller;

import java.io.File;
import java.util.*;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.core.ajax.DatatablesResponse;
import com.upreader.UpreaderConstants;
import com.upreader.model.Project;
import com.upreader.model.User;
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
    public boolean loginWithFacebook() throws Exception {
        return loginController().loginWithFacebook();
    }

    @PathSegment("loginWithTwitter")
    public boolean loginWithTwitter() throws Exception {
        return loginController().loginWithTwitter();
    }

	@PathSegment("loginSuccessful")
	public boolean loginSuccessful() {
        return loginController().loginSuccessful();
	}
	
	@PathSegment("logout")
	public boolean logout() {
		context().getSession(false).invalidate();
        context().cookies().remove(UpreaderConstants.LOGIN_COOKIE_NAME);
		return homepage();
	}

    @PathSegment("register")
    public boolean register() {
        return loginController().register();
    }

    @PathSegment("confirmEmail")
    public boolean confirmEmail() {
        return loginController().confirmEmail();
    }

	@PathSegment("s/u")
	public boolean userService() {
		return userController().doCmd();
	}
	
	@PathSegment("s/p")
	public boolean projectService() {
		return projectController().doCmd();
	}

    @PathSegment("s/m")
    public boolean monitoringBoardService() {
        return monitoringBoardController().data();
    }


	// TEST FEATURES ONLY BELOW HERE//
    @PathSegment("s/tp")
    public boolean testProject() {
        DatatablesCriterias criterias = DatatablesCriterias.getFromRequest(context().request().getRawRequest());
        DataSet<Project> dataSet = context().projectDAO().findWithDatatablesCriterias(criterias);
        DatatablesResponse<Project> response = DatatablesResponse.build(dataSet, criterias);
        return json(response);
    }

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
