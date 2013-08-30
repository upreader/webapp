package com.upreader.controller;

import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.github.dandelion.datatables.core.ajax.DatatablesResponse;
import com.upreader.UpreaderConstants;
import com.upreader.context.Context;
import com.upreader.dispatcher.BasicPathHandler;
import com.upreader.helper.StringHelper;
import com.upreader.model.User;
import com.upreader.util.PasswordUtil;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class UserController extends BasicController {
	public UserController(BasicPathHandler handler, Context context) {
		super(handler, context);
	}

    /**
     * Create a new Upreader Prospector account
     * @return
     */
    public boolean registerProspector(String firstName, String lastName, String email, String password, String countryCity, String updateMe) {
        User existingUser = context().userDAO().findbyEmail(email);
        if(existingUser != null)
            return context().render(getRegisterUserFailedURL("This email is already registered with us"));

        User newUser = new User();
        newUser.setRoles(UpreaderConstants.ROLE_PROSPECTOR);
        newUser.setFirstName(firstName);
        newUser.setLastName(lastName);
        newUser.setEmail(email);
        newUser.setPassword(PasswordUtil.encryptPassword(email, password));
        newUser.setCountry(countryCity);
        newUser.setCity(countryCity);
        newUser.setUpdateMe("checked".equals(updateMe));
        newUser.setEmailConfirmed(false);

        // email confirmation deadline is 1 day
        Calendar confirmDeadline = Calendar.getInstance();
        confirmDeadline.add(Calendar.DAY_OF_YEAR, 1);
        newUser.setEmailConfirmDeadline(confirmDeadline.getTime());
        newUser.setConfirmUUID(UUID.randomUUID().toString());

        // insert new user in DB
        context().userDAO().insert(newUser);

        // send confirmation email
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMMM yyyy");
        Map<String, String> tplData = new HashMap<>();
        tplData.put("link", UpreaderConstants.UPREADER_HOST+UpreaderConstants.UPREADER_CONTEXT+"/i/confirmEmail?email="+email+"&uuid="+newUser.getConfirmUUID());
        tplData.put("firstName", newUser.getFirstName());
        tplData.put("lastName", newUser.getLastName());
        tplData.put("email", newUser.getEmail());
        tplData.put("deadline", dateFormat.format(newUser.getEmailConfirmDeadline()));
        String emailSubject = "Please confirm your Upreader account";
        String emailBody = handler().renderMustacheTemplateToString("accountConfirmationEmailTemplate", tplData);
        System.out.println(emailBody);
        handler().getApplication().getAmazonService().sendEmail("confirm.account@upreader.com", email, emailSubject, emailBody);

        return context().render("registerSuccess.jsp");
    }

    public String getRegisterUserFailedURL(String reason) {
        String url = new StringBuilder("login.jsp?regfailed=1&reason=").append(reason).toString();
        return context().request().getRawResponse().encodeRedirectURL(url);
    }

	public boolean doCmd() {
		String cmd = context().query().get("do");
		switch (cmd) {
		case "get":
			int id = context().query().getInt("objid");
			User user = context().userDAO().get(id);
			return handler().json(user);
		case "lst":
			DatatablesCriterias criterias = DatatablesCriterias.getFromRequest(context().request().getRawRequest());
			DataSet<User> dataSet = context().userDAO().findWithDatatablesCriterias(criterias);
			DatatablesResponse<User> response = DatatablesResponse.build(dataSet, criterias);
			return handler().json(response);
		case "add":
            String email = context().query().get("email");
			String password = context().query().get("password");
			int rating = context().query().getInt("rating");
			String[] roles = context().query().getStrings("roles");
			user = new User();
			user.setEmail(email);
			user.setRating(rating);
			user.setPassword(PasswordUtil.encryptPassword(email, password));
			user.setRoles(StringHelper.join(",", roles));
			
			context().userDAO().insert(user);
			return context().render("admin/users.jsp");
		case "upd":
			id = context().query().getInt("objid");
			password = context().query().get("password");
			email = context().query().get("email");
			rating = context().query().getInt("rating");
			roles = context().query().getStrings("roles");
			user = context().userDAO().get(id);
			if(user != null) {
				user.setEmail(email);
				user.setRating(rating);
				user.setRoles(StringHelper.join(",", roles));
				if(StringHelper.isNonEmpty(password))
					user.setPassword(PasswordUtil.encryptPassword(email, password));
				
				context().userDAO().update(user);
				return context().render("admin/users.jsp");
			}
			else 
				return handler().message("NOK");
		case "del":
			id = context().query().getInt("objid");
			context().userDAO().delete(id);
			return handler().message("OK");
		default:
			break;
		}
		
		return false;
	}
}
