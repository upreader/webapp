package com.upreader.controller;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.common.collect.Maps;
import com.upreader.UpreaderApplication;
import com.upreader.UpreaderConstants;
import com.upreader.dto.MonitorBoardDTO;
import com.upreader.helper.JsonWriter;
import com.upreader.helper.NumberHelper;
import com.upreader.model.Project;
import com.upreader.model.StockTransaction;
import org.apache.log4j.Logger;
import org.bouncycastle.util.Strings;
import org.joda.time.DateMidnight;
import org.joda.time.Days;

import java.math.BigDecimal;
import java.util.*;

public class ProjectDAO {
    Logger log = Logger.getLogger(ProjectDAO.class);
    private EntityManager em;
    private UpreaderApplication application;

    public ProjectDAO(UpreaderApplication application, EntityManager em) {
        this.application = application;
        this.em = em;
    }

    public void update(Project project) {
        em.getTransaction().begin();
        project = em.merge(project);
        em.persist(project);
        em.getTransaction().commit();
    }

    public void insert(Project project) {
        em.getTransaction().begin();
        em.persist(project);
        em.getTransaction().commit();
    }

    public void delete(int id) {
        em.getTransaction().begin();
        Project project = em.find(Project.class, id);
        if(project != null)
            em.remove(project);
        em.getTransaction().commit();
    }

    public Project get(int id) {
        return em.find(Project.class, id);
    }

    public Integer countActiveProjects() {
        Query query = em.createQuery("select p from Project p", Project.class);
        return query.getResultList().size();
    }

    public Map<String, List> projectsByGenre() {
        Query query = em.createQuery("select distinct(p.genre) from Project p order by p.genre", String.class);
        List<String> genres = (List<String>)query.getResultList();

        Map<String, List> resultMap = new HashMap<String, List>();
        for(String g : genres){
            Query pQuery = em.createQuery("select p from Project p where p.genre = :genre", Project.class);
            pQuery.setParameter("genre", g);

            List pQueryResult = pQuery.getResultList();
            if(pQueryResult!=null && pQueryResult.size() > 0 )
                resultMap.put(g, pQueryResult);
        }

        return resultMap;
    }

    public List<String> projectsCategories(){
        Query query = em.createQuery("select distinct(p.category) from Project p order by p.category", String.class);
        return query.getResultList();
    }

    public List<String> projectsGenres(){
        Query query = em.createQuery("select distinct(p.genre) from Project p order by p.genre", String.class);
        return query.getResultList();
    }

    public List<String> projectsTags(){
        Query query = em.createQuery("select p.tags from Project p order by p.tags", String.class);
        List<String> queryResult = query.getResultList();
        Set<String> result = new TreeSet<String>();
        for(String _tags : queryResult){
            if(_tags!=null && !_tags.isEmpty()){
                String[] tags = Strings.split(_tags, ';');
                result.addAll(Arrays.asList(tags));
            }
        }
        String[] tmp = new String[result.size()];
        return Arrays.asList(result.toArray(tmp));
    }

    public Map<Integer, String> projectsAuthors(){
        Query query = em.createQuery("select distinct(p.author.email), p.author.firstName, p.author.lastName, p.author.id from Project p", String.class);
        List<Object[]> resultList = query.getResultList();
        Map<Integer, String> resultMap = new HashMap(resultList.size());
        for (Object[] result : resultList){
              resultMap.put((Integer)result[3], (String)result[1] + " " + (String)result[2]);
        }
        return resultMap;
    }

    public Map<String, Long> projectsCountByCategory() {
        Query query = em.createQuery("select p.category, count(p) from Project p group by p.category");
        List<Object[]> resultList = query.getResultList();
        Map<String, Long> resultMap = new HashMap<String, Long>(resultList.size());
        for (Object[] result : resultList)
            resultMap.put((String)result[0], (Long)result[1]);
        return resultMap;
    }

    public List<Project> findAllProjectsInRange(int startPosition, int endPosition){
        Query query = em.createQuery("select p from Project p order by p.title asc", Project.class);
        query.setFirstResult(startPosition);
        query.setMaxResults(endPosition - startPosition);
        return query.getResultList();
    }

    public Project findProjectById(Integer projectId) {
        Query query = em.createQuery("select p from Project p where p.id = "+projectId, Project.class);
        return (Project) query.getResultList().get(0);
    }

    public List<Project> findAllProjectsWithIncomeForUser(Integer userId) {
        List<Project> result = new ArrayList<>();

        // owned projects
        Query query = em.createQuery("select p from Project p where p.author.id = :userId", Project.class);
        query.setParameter("userId", userId);
        result.addAll(query.getResultList());

        // projects in which user is shareholder
        query = em.createQuery("select p from Project p join p.shareholders s where s.user.id = :userId", Project.class);
        query.setParameter("userId", userId);
        result.addAll(query.getResultList());

        return result;
    }

    public List<Project> findAllProjectsForUser(Integer userId) {
        List<Project> result = new ArrayList<>();

        // owned projects
        Query query = em.createQuery("select p from Project p where p.author.id = :userId", Project.class);
        query.setParameter("userId", userId);
        result.addAll(query.getResultList());

        // projects in which user is shareholder
        query = em.createQuery("select p from Project p join p.shareholders s where s.user.id = :userId", Project.class);
        query.setParameter("userId", userId);
        result.addAll(query.getResultList());

        // projects to which user subscribed
        query = em.createQuery("select p from Project p join p.subscribers s where s.user.id = :userId", Project.class);
        query.setParameter("userId", userId);
        result.addAll(query.getResultList());

        // pinned projects
        query = em.createQuery("select p from Project p join p.interestedUsers s where s.user.id = :userId", Project.class);
        query.setParameter("userId", userId);
        result.addAll(query.getResultList());

        return result;
    }

    public List<Project> getProjectsIncomeForUser(Integer userId){
          List<Project> incomeProjects = findAllProjectsWithIncomeForUser(userId);
          return incomeProjects;
    }

    public MonitorBoardDTO toMonitorBoardDTO(Project project) {
        MonitorBoardDTO dto = new MonitorBoardDTO();

        dto.setId(project.getId());

        dto.setTitle(project.getTitle());
        dto.setBookPrice(NumberHelper.safeNumber(project.getBookPrice(), 0f));
        dto.setUpreaders(project.getShareholders() == null ? 0 : project.getShareholders().size());
        dto.setShareValue(NumberHelper.safeNumber(project.getShareValue(),0f));
        dto.setIrsProgress(project.getSharesToSale() / project.getTotalShares() * 100);

        if(project.getDeadline() != null) {
            DateMidnight now = new DateMidnight(new Date().getTime());
            DateMidnight deadline = new DateMidnight(project.getDeadline().getTime());

            int days = Days.daysBetween(now, deadline).getDays();
            if(days > 0) {
                // project is still in deadline
                dto.setDaysToDeadline(days);
            } else {
                dto.setDaysToDeadline(0); // project is passed deadline
            }
        } else {
            // not set (possible for first release)
            dto.setDaysToDeadline(-1);
        }

        dto.setNoViews(NumberHelper.safeNumber(project.getNoViews(), 0));
        dto.setBooksSold(NumberHelper.safeNumber(project.getBooksSold(), 0));
        dto.setSharesToSale(NumberHelper.safeNumber(project.getSharesToSale(),0));
        dto.setSubscribers(project.getSubscribers() == null ? 0 : project.getSubscribers().size());
        dto.setSerialStorySubscriptionPrice(NumberHelper.safeNumber(project.getSerialStorySubscriptionPrice(), 0f));
        dto.setDerivatives(project.getDerivatives());

        dto.setIncome(-1); // this needs to be calculated
        dto.setAuthorName(project.getAuthor().getFirstName() + " " + project.getAuthor().getLastName());
        dto.setAuthorRating(project.getAuthor().getRating());

        return dto;
    }

    public List<Project> findWithQuery(String query) {
        TypedQuery<Project> tquery = em.createQuery(query, Project.class);
        return tquery.getResultList();
    }

    /**
     * Financial Transactions Queries
     */
    public Integer getAvailableSharesForProject(Integer projectId){
        Query query = em.createQuery("select sum(s.sharesNo) from StockTransaction s where s.project.id = :projectId and s.buyer IS NULL", Project.class);
        query.setParameter("projectId", projectId);
        BigDecimal result = (BigDecimal)query.getSingleResult();
        return result == null ? 0 : result.intValue();
    }
}