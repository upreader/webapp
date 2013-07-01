package com.upreader.controller;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;

import com.upreader.UpreaderApplication;
import com.upreader.model.User;

public class UserController {
	private EntityManager em;
	private UpreaderApplication application;
	
	public UserController(UpreaderApplication application, EntityManager em) {
		this.application = application;
		this.em = em;
	}
	
	public User findbyUsername(String username) {
		TypedQuery<User> query = em.createNamedQuery(User.NQ_FIND_BY_USERNAME, User.class);
		return query.setParameter("username", username).getSingleResult();
	}
}
