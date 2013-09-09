package com.upreader.controller;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.upreader.UpreaderApplication;
import com.upreader.dto.MonitorBoardDTO;
import com.upreader.helper.NumberHelper;
import com.upreader.model.Project;
import org.apache.log4j.Logger;
import org.joda.time.DateMidnight;
import org.joda.time.Days;

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
}