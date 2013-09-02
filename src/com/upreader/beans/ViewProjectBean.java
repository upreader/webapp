package com.upreader.beans;


import com.upreader.UpreaderApplication;
import com.upreader.UpreaderConstants;
import com.upreader.UpreaderRequest;
import com.upreader.context.Context;
import com.upreader.controller.ProjectDAO;
import com.upreader.model.Project;

import javax.annotation.Resource;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.inject.Inject;
import javax.inject.Named;
import javax.naming.InitialContext;
import javax.persistence.EntityManagerFactory;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceUnit;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;
import java.util.ArrayList;
import java.util.List;

@ManagedBean(name="projectData")
@ViewScoped
public class ViewProjectBean {
    private DataSource dataSource;


    private ServletRequest request;
    private Project project;
    private ArrayList<String> errors;

    public ViewProjectBean(){
        this.errors = new ArrayList<String>();
    }

    /*
     * Getters and Setters
     */
    public void setRequest(ServletRequest request) {
        System.out.println("Setting Request");
        String projectId = request.getParameter("projectId");
        Integer projectIdValue;

        if(projectId == null){
            errors.add("Empty Project Id");
        }else{
            try{
                projectIdValue = Integer.valueOf(projectId);

                Context context = (Context) request.getServletContext().getAttribute("context");
                this.project = context.projectDAO().findProjectById(projectIdValue);
            }catch(Exception e){
                e.printStackTrace();
                errors.add(e.getMessage() + " " + e.getCause());
            }
        }
        this.request = request;
    }

    public Project getProject() {
        return project;
    }

    public ArrayList<String> getErrors() {
        return errors;
    }
}
