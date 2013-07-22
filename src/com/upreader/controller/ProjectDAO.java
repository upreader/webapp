package com.upreader.controller;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.upreader.UpreaderApplication;
import com.upreader.model.Project;
import org.apache.log4j.Logger;

import java.util.List;

public class ProjectDAO {
    Logger log = Logger.getLogger(ProjectDAO.class);
	private EntityManager em;
	private UpreaderApplication application;

	public ProjectDAO(UpreaderApplication application, EntityManager em) {
		this.application = application;
		this.em = em;
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
        CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
        log.info("StartPosition " + startPosition + " endPosition " + endPosition);
        CriteriaQuery<Project> criteriaQuery = criteriaBuilder.createQuery(Project.class);
        Root<Project> projectRoot = criteriaQuery.from(Project.class);

        TypedQuery q = em.createQuery(criteriaQuery);
        return q.getResultList();
    }
}
