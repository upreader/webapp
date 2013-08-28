package com.upreader.controller;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;

import com.github.dandelion.datatables.core.ajax.ColumnDef;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.upreader.UpreaderApplication;
import com.upreader.model.Project;
import com.upreader.model.User;
import com.upreader.util.DatatablesHelper;
import org.apache.log4j.Logger;

import java.util.*;

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
        Query query = em.createQuery("select p from Project p order by p.title asc", Project.class);
        query.setFirstResult(startPosition);
        query.setMaxResults(endPosition - startPosition);
        return query.getResultList();
    }

    public List<Project> findAllProjectsForUser(Integer userId) {
        List<Project> result = new ArrayList<>();

        // owned projects
        Query query = em.createQuery("select p from Project p where p.author.id = "+userId, Project.class);
        result.addAll(query.getResultList());

        // projects in which user is shareholder
        query = em.createQuery("select p from Project p join p.shareholders s where s.user.id = "+userId, Project.class);
        result.addAll(query.getResultList());

        // projects to which user subscribed
        query = em.createQuery("select p from Project p join p.subscribers s where s.user.id = "+userId, Project.class);
        result.addAll(query.getResultList());

        // pinned projects
        query = em.createQuery("select p from Project p join p.interestedUsers s where s.user.id = "+userId, Project.class);
        result.addAll(query.getResultList());

        return result;
    }

    public List<Project> findWithQuery(String query) {
        TypedQuery<Project> tquery = em.createQuery(query, Project.class);
        return tquery.getResultList();
    }
}
