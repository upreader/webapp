package com.upreader.controller;

import javax.persistence.EntityManager;

import com.upreader.UpreaderApplication;
import com.upreader.model.Project;

public class ProjectDAO {
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
}
