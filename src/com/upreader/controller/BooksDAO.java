package com.upreader.controller;

import com.upreader.UpreaderApplication;
import com.upreader.model.BookTransaction;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class BooksDAO {
    Logger log = Logger.getLogger(ProjectDAO.class);
    private EntityManager em;
    private UpreaderApplication application;

    public BooksDAO(UpreaderApplication application, EntityManager em) {
        this.application = application;
        this.em = em;
    }

    public void update(BookTransaction transaction) {
        em.getTransaction().begin();
        em.persist(transaction);
        em.getTransaction().commit();
    }

    public void insert(BookTransaction transaction) {
        em.getTransaction().begin();
        em.persist(transaction);
        em.getTransaction().commit();
    }

    public void delete(int id) {
        em.getTransaction().begin();
        BookTransaction transaction = em.find(BookTransaction.class, id);
        if(transaction != null)
            em.remove(transaction);
        em.getTransaction().commit();
    }

    public BookTransaction get(int id) {
        return em.find(BookTransaction.class, id);
    }


    public List<BookTransaction> findTransactionsForProject(Integer projectId) {
        List<BookTransaction> result = new ArrayList<>();

        Query query = em.createQuery("select a from BookTransaction a where a.project.id = :projectId", BookTransaction.class);
        query.setParameter("projectId", projectId);
        result.addAll(query.getResultList());

        return result;
    }

    public List<BookTransaction> findSellTransactionsForUser(Integer sellerUserId) {
        List<BookTransaction> result = new ArrayList<>();

        Query query = em.createQuery("select a from BookTransaction a where a.seller.id = :sellerUserId", BookTransaction.class);
        query.setParameter("sellerUserId", sellerUserId);
        result.addAll(query.getResultList());

        return result;
    }

    public List<BookTransaction> findBuyTransactionsForUser(Integer sellerUserId) {
        List<BookTransaction> result = new ArrayList<>();

        Query query = em.createQuery("select a from BookTransaction a where a.buyer.id = :buyerUserId", BookTransaction.class);
        query.setParameter("buyerUserId", sellerUserId);
        result.addAll(query.getResultList());

        return result;
    }
}
