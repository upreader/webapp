package com.upreader.beans;


import com.upreader.context.Context;
import com.upreader.model.Notification;
import org.apache.log4j.Logger;

import javax.servlet.ServletRequest;
import java.util.ArrayList;
import java.util.List;

public class WorkspaceBean {
    Logger log = Logger.getLogger(WorkspaceBean.class);
    private ServletRequest request;
    private ArrayList<String> errors;

    /*
     * Getters and Setters
     */
    public void setRequest(ServletRequest request) {
        try{
            Context context = (Context) request.getServletContext().getAttribute("context");

        }catch(Exception e){
            log.error(e);
            errors.add(e.getMessage() + " " + e.getCause());
        }
        this.request = request;
    }

}
