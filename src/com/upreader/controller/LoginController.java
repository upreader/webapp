package com.upreader.controller;

import com.upreader.UpreaderConstants;
import com.upreader.context.Context;
import com.upreader.dispatcher.BasicPathHandler;
import com.upreader.model.User;
import com.upreader.security.FacebookLogin;
import com.upreader.security.TwitterLogin;
import facebook4j.Facebook;
import facebook4j.FacebookException;
import org.apache.commons.lang.StringUtils;

import java.security.Principal;
import java.util.Date;

public class LoginController extends BasicController {
    public LoginController(BasicPathHandler handler, Context context) {
        super(handler, context);
    }

    public boolean loginWithFacebook() throws Exception {
        FacebookLogin login = new FacebookLogin();
        Principal user = login.login(context().request().getRawRequest(), context().request().getRawResponse(), true);
        if(user == null)
            return false;

        Facebook facebook = context().session().getObject("facebook");
        try {
            facebook4j.User fbUser = facebook.getMe();

        } catch (FacebookException e) {
            throw e;
        }


        // redirect to last known url
        String uri = context().session().get("com.caucho.servlet.login.path");
        String query = context().session().get("com.caucho.servlet.login.query");
        context().session().remove("com.caucho.servlet.login.path");
        context().session().remove("com.caucho.servlet.login.query");

        if ((uri != null) && (query != null)) {
            uri = uri + "?" + query;
            return context().request().redirect(context().request().getRawResponse().encodeRedirectURL(uri));
        }
        else {
            return loginSuccessful();
        }
    }

    public boolean loginWithTwitter() throws Exception {
        TwitterLogin login = new TwitterLogin();
        Principal user = login.login(context().request().getRawRequest(), context().request().getRawResponse(), true);
        if(user == null)
            return false;

        String uri = context().session().get("com.caucho.servlet.login.path");
        String query = context().session().get("com.caucho.servlet.login.query");
        context().session().remove("com.caucho.servlet.login.path");
        context().session().remove("com.caucho.servlet.login.query");

        if ((uri != null) && (query != null)) {
            uri = uri + "?" + query;
            return context().request().redirect(context().request().getRawResponse().encodeRedirectURL(uri));
        }
        else {
            return loginSuccessful();
        }
    }

    public boolean loginSuccessful() {
        String prevPath = context().session().get("com.upreader.previous.path");
        String prevQuery = context().session().get("com.upreader.previous.query");
        if(prevPath != null && !prevPath.isEmpty()) {
            if(prevQuery != null && !prevQuery.isEmpty())
                prevPath += "?"+prevQuery;

            context().session().remove("com.upreader.previous.path");
            context().session().remove("com.upreader.previous.query");
            if(!prevPath.startsWith("/"))
                prevPath = "/" + prevPath;

            if(prevPath.startsWith(context().getContextPath()))
                return context().redirect(prevPath);
            else
                return context().redirect(context().getContextPath()+prevPath);
        }

        String loggedUser = context().username();

        // set session variables
        User user = context().userDAO().findbyEmail(loggedUser);
        context().session().putObject(UpreaderConstants.SESSION_USER, user);

        return context().redirect("http://www.upreader.com:8080/upreader");
    }

    public boolean register() {
        String firstName = context().query().get("firstName");
        String lastName = context().query().get("lastName");
        String email = context().query().get("email");
        String password = context().query().get("password");
        String countryCity = context().query().get("countryCity");
        String updateMe = context().query().get("updateMe");

        if(!StringUtils.isEmpty(firstName)
                && !StringUtils.isEmpty(lastName)
                && !StringUtils.isEmpty(email)
                && !StringUtils.isEmpty(password)
                && !StringUtils.isEmpty(countryCity)
                && !StringUtils.isEmpty(updateMe)) {

            return handler().userController().registerProspector(firstName, lastName, email, password, countryCity, updateMe);
        }
        else {
            return context().render(handler().userController().getRegisterUserFailedURL("One of the fields is empty"));
        }
    }

    public boolean confirmEmail() {
        String email = context().query().get("email");
        String uuid = context().query().get("uuid");

        if(!StringUtils.isEmpty(email) && !StringUtils.isEmpty(uuid)) {
            User user = context().userDAO().findbyEmail(email);

            // if no user found with this email goto homepage
            if(user == null)
                return handler().homepage();

            Date deadline = user.getEmailConfirmDeadline();
            if(new Date().before(deadline)) {
                if(user.getConfirmUUID().equals(uuid)) {
                    // user is good to be confirmed
                    user.setEmailConfirmed(true);
                    context().userDAO().update(user);
                    return context().render("confirmSuccess.jsp");
                } else {
                    return context().render("confirmError.jsp?uuid=1");
                }
            } else {
                return context().render("confirmError.jsp?expired=1");
            }
        }

        return handler().homepage();
    }
}
