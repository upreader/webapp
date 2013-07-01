package com.upreader.controller;

import java.io.File;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.upreader.MimeTypes;
import com.upreader.RequestFile;
import com.upreader.UpreaderApplication;
import com.upreader.dispatcher.MethodPathHandler;
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
public class UpreaderHandler extends MethodPathHandler {
	Logger log = Logger.getLogger(UpreaderHandler.class);
		
	public UpreaderHandler(UpreaderApplication application) {
		super(application);
	}
	
	@PathDefault
	public boolean homepage() {
		return context().render("home.jsp");
	}
	
	@PathSegment("login")
	public boolean loginForm() {
		return context().render("login.jsp");
	}
	
	@PathSegment("loginError")
	public boolean loginError() {
		return message("Error logging in");
	}
	
	@PathSegment("loginSuccessful")
	public boolean loginSuccessful() {
		String username = username();
		if(username != null) {
			User user = userController().findbyUsername(username);
			session().putObject("user", user);
			if(user.getRole().contains("admin"))
				session().put("isAdmin", true);
			if(user.getRole().contains("upreader"))
				session().put("isUpreader", true);
			if(user.getRole().contains("author"))
				session().put("isAuthor", true);
			if(user.getRole().contains("buyer"))
				session().put("isBuyer", true);
		}
		return homepage();
	}
	
	@PathSegment("logout")
	public boolean logout() {
		context().getSession(false).invalidate();
		return homepage();
	}
	
	@PathSegment("admin")
	public boolean gotoAdminPage() {
		return context().render("admin/status.jsp");
	}
	
	@PathSegment("admin/users")
	public boolean gotoAdminUsersPage() {
		return context().render("admin/users.jsp");
	}
	
	@PathSegment("admin/status")
	public boolean gotoAdminStatusPage() {
		return context().render("admin/status.jsp");
	}
	
	@PathSegment("admin/service/userList")
	public boolean userList() {
		List<User> users = userController().list();
		Map<String, Object> result = new HashMap<>();
		result.put("sEcho", query().get("sEcho"));
		result.put("iTotalRecords", users.size());
		result.put("iTotalDisplayRecords", users.size());
//		Object[][] array = new Object[users.size()][5];
//		for(int i=0; i<users.size(); i++) {
//			User u = users.get(i);
//			array[i] = new Object[] { u.getId(), u.getUsername(), u.getEmail(), u.getRole() };
//		}
		result.put("aaData", users);
		result.put("aoColumns", Arrays.asList(new DataTableColumn[] {
				new DataTableColumn("id"),
				new DataTableColumn("username"),
				new DataTableColumn("password"),
				new DataTableColumn("email"),
				new DataTableColumn("role")
		}));
		return json(result);
	}
	
	// TEST FEATURES ONLY BELOW HERE//
	@PathSegment("message")
	public boolean messageDemo() {
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
