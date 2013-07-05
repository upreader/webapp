package com.upreader.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

import com.github.dandelion.datatables.core.ajax.ColumnDef;
import com.github.dandelion.datatables.core.ajax.DataSet;
import com.github.dandelion.datatables.core.ajax.DatatablesCriterias;
import com.upreader.UpreaderApplication;
import com.upreader.model.User;
import com.upreader.util.DatatablesHelper;

public class UserDAO {
	private EntityManager em;
	private UpreaderApplication application;

	public UserDAO(UpreaderApplication application, EntityManager em) {
		this.application = application;
		this.em = em;
	}

	public User findbyUsername(String username) {
		TypedQuery<User> query = em.createNamedQuery(User.NQ_FIND_BY_USERNAME, User.class);
		return query.setParameter("username", username).getSingleResult();
	}

	public List<User> list() {
		return em.createNamedQuery(User.NQ_FIND_ALL, User.class).getResultList();
	}

	public DataSet<User> findWithDatatablesCriterias(DatatablesCriterias criterias) {
		StringBuilder queryBuilder = new StringBuilder("SELECT o FROM User o");
		queryBuilder.append(DatatablesHelper.getFilterQuery(criterias));
		if (criterias.hasOneSortedColumn()) {
			List<String> orderParams = new ArrayList<>();
			queryBuilder.append(" ORDER BY ");
			for (ColumnDef columnDef : criterias.getSortingColumnDefs()) {
				orderParams.add("o." + columnDef.getName() + " " + columnDef.getSortDirection());
			}

			Iterator<String> itr2 = orderParams.iterator();
			while (itr2.hasNext()) {
				queryBuilder.append(itr2.next());
				if (itr2.hasNext()) {
					queryBuilder.append(" , ");
				}
			}
		}
		TypedQuery<User> query = em.createQuery(queryBuilder.toString(), User.class);
		query.setFirstResult(criterias.getDisplayStart());
		query.setMaxResults(criterias.getDisplaySize());
		List<User> users = query.getResultList();
		Long count = getTotalCount();
		Long countFiltered = getFilteredCount(criterias);
		return new DataSet<User>(users, count, countFiltered);
	}

	public Long getTotalCount() {
		Query query = em.createQuery("SELECT COUNT(o) FROM User o");
		return (Long) query.getSingleResult();
	}

	public Long getFilteredCount(DatatablesCriterias criterias) {
		StringBuilder queryBuilder = new StringBuilder("SELECT o FROM User o");
		queryBuilder.append(DatatablesHelper.getFilterQuery(criterias));
		Query query = em.createQuery(queryBuilder.toString());
		return Long.parseLong(String.valueOf(query.getResultList().size()));
	}

	public void insert(User user) {
		em.getTransaction().begin();
		em.persist(user);
		em.getTransaction().commit();
	}

	public void delete(int id) {
		em.getTransaction().begin();
		User user = em.find(User.class, id);
		if(user != null)
			em.remove(user);
		em.getTransaction().commit();
	}

	public User get(int id) {
		return em.find(User.class, id);
	}

	public void update(User user) {
		em.getTransaction().begin();
		user = em.merge(user);
		em.persist(user);
		em.getTransaction().commit();
	}
}
