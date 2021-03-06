package com.upreader.beans;


import com.crocodoc.CrocodocDocument;
import com.crocodoc.CrocodocException;
import com.crocodoc.UprCrocodocSession;
import com.upreader.UpreaderApplication;
import com.upreader.UpreaderConstants;
import com.upreader.UpreaderRequest;
import com.upreader.context.Context;
import com.upreader.controller.ProjectDAO;
import com.upreader.dto.CrocodocResultDTO;
import com.upreader.helper.AddProjectWizardHelper;
import com.upreader.model.BookTransaction;
import com.upreader.model.Project;
import com.upreader.model.ProjectMembership;
import com.upreader.model.StockTransaction;
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
import java.util.*;

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

    public String getProjectPreviewUrl(){
        CrocodocResultDTO result = new CrocodocResultDTO();
        try{
            Map<String, Object> status = CrocodocDocument.status(project.getSampleViewUUID());
            String sessionKey = UprCrocodocSession.create(project.getSampleViewUUID());
            result.setSessionKey(sessionKey);
            result.setDocStatus(status.get("status").toString());
            result.setResult(true);
            return sessionKey;
        }catch (CrocodocException ce){
            result.setResult(false);
            return "";
        }
    }

    /**
     * Method returing the current number of shares bought
     * by the shareHolders
     * @return number of bought shares
     */
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

    /**
     * From the total number of shares
     * currently on the market.
     * @return number of available shares
     */
    public Integer getAvailableShares(){
        Context context = (Context) request.getServletContext().getAttribute("context");
        return context.projectDAO().getAvailableSharesForProject(this.project.getId());
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

    public String getLocalizedTypeResource(String type){
        return UpreaderConstants.getLocalizedTypeResource(type, request.getLocale());
    }

    public String getLocalizedFormatResource(String format){
        return UpreaderConstants.getLocalizedFormatResource(format, request.getLocale());
    }

    public String getLocalizedCategoryResource(String category){
        return UpreaderConstants.getLocalizedCategoryResource(category, request.getLocale());
    }

    public String getLocalizedGenreResource(String genre){
        return UpreaderConstants.getLocalizedCategoryResource(genre, request.getLocale());
    }

    public String getLocalizedSubGenreResource(String subgenre){
        return UpreaderConstants.getLocalizedCategoryResource(subgenre, request.getLocale());
    }

    public ArrayList<String> getErrors() {
        return errors;
    }
}
