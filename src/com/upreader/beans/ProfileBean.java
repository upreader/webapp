package com.upreader.beans;

import com.upreader.UpreaderApplication;
import com.upreader.context.Context;
import com.upreader.dto.UserPublicDataDTO;
import com.upreader.model.User;
import org.apache.log4j.Logger;

import javax.servlet.ServletRequest;
import java.util.ArrayList;

public class ProfileBean {
    Logger log = Logger.getLogger(ProfileBean.class);
    private ServletRequest request;
    private Context context;
    private ArrayList<String> errors;

    public void setRequest(ServletRequest request) {
        try{
            this.context = (Context) request.getServletContext().getAttribute("context");
        }catch(Exception e){
            log.error(e);
            errors.add(e.getMessage() + " " + e.getCause());
        }
        this.request = request;
    }

    public String userPublicDataJson(User user){
          return UpreaderApplication.getInstance().getJavaScriptWriter().write(new UserPublicDataDTO(user));
    }
}
