package com.upreader.controller;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.log4j.Logger;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.core.ajax.DatatablesResponse;
import com.upreader.MimeTypes;
import com.upreader.RequestFile;
import com.upreader.UpreaderApplication;
import com.upreader.dispatcher.MethodPathHandler;
import com.upreader.dispatcher.PathDefault;
import com.upreader.dispatcher.PathSegment;
import com.upreader.helper.CollectionHelper;
import com.upreader.helper.StringHelper;
import com.upreader.model.User;
import com.upreader.util.PasswordUtil;

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
	
	@PathSegment("loginSuccessful")
	public boolean loginSuccessful() {
		return homepage();
	}
	
	@PathSegment("logout")
	public boolean logout() {
		context().getSession(false).invalidate();
		return homepage();
	}
	
	
	@PathSegment("service/userList")
	public boolean userList() {
		DatatablesCriterias criterias = DatatablesCriterias.getFromRequest(context().getRequest().getRawRequest());
		DataSet<User> dataSet = userController().findWithDatatablesCriterias(criterias);
		DatatablesResponse<User> response = DatatablesResponse.build(dataSet, criterias);
		return json(response);
	}
	
	@PathSegment("service/addUser")
	public boolean addUser() {
		String username = query().get("username");
		String password = query().get("password");
		String email = query().get("email");
		String role = query().get("role");
		
		User user = new User();
		user.setUsername(username);
		user.setEmail(email);
		user.setPassword(PasswordUtil.encryptPassword(username, password));
		user.setRole(role);
		
		userController().insert(user);
		return context().render("admin/users.jsp");
	}
	
	@PathSegment("service/deleteUser")
	public boolean deleteUser() {
		int id = query().getInt("objid");
		userController().delete(id);
		return message("OK");
	}
	
	@PathSegment("service/updateUser")
	public boolean updateUser() {
		int id = query().getInt("objid");
		String username = query().get("username");
		String password = query().get("password");
		String email = query().get("email");
		String role = query().get("role");
		
		User user = userController().get(id);
		if(user != null) {
			user.setUsername(username);
			user.setEmail(email);
			user.setRole(role);
			if(StringHelper.isNonEmpty(password))
				user.setPassword(PasswordUtil.encryptPassword(username, password));
			
			userController().update(user);
			return context().render("admin/users.jsp");
		}
		
		return message("NOK");
	}
	
	@PathSegment("service/getUser")
	public boolean getUser() {
		int id = query().getInt("objid");
		User user = userController().get(id);
		return json(user);
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
