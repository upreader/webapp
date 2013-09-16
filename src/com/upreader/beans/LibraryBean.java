package com.upreader.beans;

import com.upreader.UpreaderApplication;
import com.upreader.UpreaderConstants;
import com.upreader.context.Context;
import com.upreader.dto.ProjectPublicDataDTO;
import com.upreader.dto.SelectItem;
import com.upreader.helper.JsonWriter;
import com.upreader.model.Project;
import org.apache.log4j.Logger;

import javax.servlet.ServletRequest;
import java.util.*;

public class LibraryBean {
    Logger log = Logger.getLogger(LibraryBean.class);
    private ServletRequest request;
    private Context context;
    private ArrayList<String> errors;

    /*
     * Getters and Setters
     */
    public void setRequest(ServletRequest request) {
        try{
            this.context = (Context) request.getServletContext().getAttribute("context");
        }catch(Exception e){
            log.error(e);
            errors.add(e.getMessage() + " " + e.getCause());
        }
        this.request = request;
    }

    public Integer getNoOfActiveProject(){
        return this.context.projectDAO().countActiveProjects();
    }

    public Map<String, Long> getProjectCountByCategory(){
        Map<String, Long> results = this.context.projectDAO().projectsCountByCategory();
        return results;
    }

    public Map<String, String> getLocalizedCategories(){
       List<String> results = this.context.projectDAO().projectsCategories();
       Map<String, String> resultMap = new HashMap<String, String>();
       for(String cat : results){
          resultMap.put(cat, getLocalizedCategoryResource(cat));
       }
       return resultMap;
    }

    public Map<String, String> getLocalizedGenres(){
        List<String> results = this.context.projectDAO().projectsGenres();
        Map<String, String> resultMap = new HashMap<String, String>();
        for(String g : results){
            resultMap.put(g, getLocalizedGenreResource(g));
        }
        return resultMap;
    }

    public Map<String, String> getLocalizedSubGenres(){
        List<String> result = this.context.projectDAO().projectsGenres();
        Map<String, String> resultMap = new HashMap<String, String>();
        for(String g : result){
            List<SelectItem> subgenres = retreiveSubGenres(g);
            for(SelectItem sg : subgenres){
                resultMap.put(sg.getValue(), sg.getLabel());
            }
        }
        return resultMap;
    }

    public Map<Integer, String> getAuthors(){
        return this.context.projectDAO().projectsAuthors();
    }

    public List<String> getTags(){
        return this.context.projectDAO().projectsTags();
    }

    public Map<String, List> getProjectsByGenre(){
        Map<String, List> results = this.context.projectDAO().projectsByGenre();
        return results;
    }

    public String getLocalizedCategoryResource(String category){
        return UpreaderConstants.getLocalizedCategoryResource(category, context.getLocale());
    }

    public String getLocalizedGenreResource(String genre){
        return UpreaderConstants.getLocalizedGenreResource(genre, context.getLocale());
    }

    public List<SelectItem> retreiveSubGenres(String genre){
        return UpreaderConstants.getLocalizedSubGenresResource(genre, context.getLocale());
    }

    public String projectsData(List projectList){
        ArrayList<ProjectPublicDataDTO> tmpList = new ArrayList<ProjectPublicDataDTO>();
        for(int i=0;i<projectList.size();i++){
            tmpList.add(new ProjectPublicDataDTO( (Project)projectList.get(i), context.getLocale() ) );
        }
        return UpreaderApplication.getInstance().getJavaScriptWriter().write(tmpList);
    }

    public String genresData(Set genresSet){
        return UpreaderApplication.getInstance().getJavaScriptWriter().write(genresSet);
    }
}
