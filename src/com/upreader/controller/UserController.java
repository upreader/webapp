package com.upreader.controller;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.core.ajax.DatatablesResponse;
import com.upreader.context.Context;
import com.upreader.dispatcher.BasicPathHandler;
import com.upreader.helper.StringHelper;
import com.upreader.model.User;
import com.upreader.util.PasswordUtil;

public class UserController {
	private final UpreaderHandler handler;
	
	public UserController(BasicPathHandler handler) {
		this.handler = (UpreaderHandler) handler;
	}
	
	public boolean doCmd(Context context) {
		String cmd = context.query().get("do");
		switch (cmd) {
		case "get":
			int id = context.query().getInt("objid");
			User user = context.userDAO().get(id);
			return handler.json(user);
		case "lst":
			DatatablesCriterias criterias = DatatablesCriterias.getFromRequest(context.request().getRawRequest());
			DataSet<User> dataSet = context.userDAO().findWithDatatablesCriterias(criterias);
			DatatablesResponse<User> response = DatatablesResponse.build(dataSet, criterias);
			return handler.json(response);
		case "add":
			String username = context.query().get("username");
			String password = context.query().get("password");
			String email = context.query().get("email");
			int rating = context.query().getInt("rating");
			String[] roles = context.query().getStrings("roles");
			user = new User();
			user.setUsername(username);
			user.setEmail(email);
			user.setRating(rating);
			user.setPassword(PasswordUtil.encryptPassword(username, password));
			user.setRoles(StringHelper.join(",", roles));
			
			context.userDAO().insert(user);
			return context.render("admin/users.jsp");
		case "upd":
			id = context.query().getInt("objid");
			username = context.query().get("username");
			password = context.query().get("password");
			email = context.query().get("email");
			rating = context.query().getInt("rating");
			roles = context.query().getStrings("roles");
			user = context.userDAO().get(id);
			if(user != null) {
				user.setUsername(username);
				user.setEmail(email);
				user.setRating(rating);
				user.setRoles(StringHelper.join(",", roles));
				if(StringHelper.isNonEmpty(password))
					user.setPassword(PasswordUtil.encryptPassword(username, password));
				
				context.userDAO().update(user);
				return context.render("admin/users.jsp");
			}
			else 
				return handler.message("NOK");
		case "del":
			id = context.query().getInt("objid");
			context.userDAO().delete(id);
			return handler.message("OK");
		default:
			break;
		}
		
		return false;
	}
}
