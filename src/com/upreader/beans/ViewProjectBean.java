package com.upreader.beans;


import com.upreader.UpreaderApplication;
import com.upreader.UpreaderConstants;
import com.upreader.UpreaderRequest;
import com.upreader.context.Context;
import com.upreader.controller.ProjectDAO;
import com.upreader.model.BookTransaction;
import com.upreader.model.Project;
import com.upreader.model.ProjectMembership;
import org.apache.log4j.Logger;
import org.joda.time.DateTime;
import org.joda.time.Days;

import javax.annotation.Resource;
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

public class ViewProjectBean {
    Logger log = Logger.getLogger(ViewProjectBean.class);
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
        String projectId = request.getParameter("projectId");
        Integer projectIdValue;

        if(projectId == null){
            errors.add("Empty Project Id");
        }else{
            try{
                projectIdValue = Integer.valueOf(projectId);

                Context context = (Context) request.getServletContext().getAttribute("context");
                this.project = context.projectDAO().get(projectIdValue);
            }catch(Exception e){
                log.error(e);
                errors.add(e.getMessage() + " " + e.getCause());
            }
        }
        this.request = request;
    }

    public Project getProject() {
        return project;
    }

    public Integer getInitialDeadlineDays(){
        DateTime contractDate = new DateTime(project.getContractDate().getTime());
        DateTime deadlineEnd  = new DateTime(project.getDeadline().getTime());
        return Days.daysBetween(contractDate, deadlineEnd).getDays();
    }

    public Integer getBoughtShares(){
        Integer result = 0;
        List<ProjectMembership> shareHolders = project.getShareholders();
        if(shareHolders == null){return 0;}
        else{
            for(ProjectMembership holder : shareHolders){
                result += holder.getShares();
            }
        }
        return result;
    }

    public Integer getBoughtBooks(){
        Integer result = 0;
        List<BookTransaction> bookTransactions = project.getBookTransactions();
        if(bookTransactions == null){return 0;}
        else{
            for(BookTransaction transaction : bookTransactions){
                result += transaction.getBooksQty();
            }
        }
        return result;
    }

    public Integer getUsedDaysFromDeadlineDays(){
        DateTime contractDate = new DateTime(project.getContractDate().getTime());
        DateTime now = DateTime.now();
        return Days.daysBetween(contractDate, now).getDays();
    }

    public Integer getInterestedUsersCount(){
        return project.getInterestedUsers() == null ? 0 : project.getInterestedUsers().size();
    }

    public ArrayList<String> getErrors() {
        return errors;
    }
}
