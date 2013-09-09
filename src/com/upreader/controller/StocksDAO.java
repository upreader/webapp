package com.upreader.controller;

import com.upreader.UpreaderApplication;
import com.upreader.model.StockTransaction;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.List;

public class StocksDAO {
    Logger log = Logger.getLogger(StocksDAO.class);
    private EntityManager em;
    private UpreaderApplication application;

    public StocksDAO(UpreaderApplication application, EntityManager em) {
        this.application = application;
        this.em = em;
    }

    public void update(StockTransaction transaction) {
        em.getTransaction().begin();
        transaction = em.merge(transaction);
        em.persist(transaction);
        em.getTransaction().commit();
    }

    public void insert(StockTransaction transaction) {
        em.getTransaction().begin();
        em.persist(transaction);
        em.getTransaction().commit();
    }

    public void delete(int id) {
        em.getTransaction().begin();
        StockTransaction transaction = em.find(StockTransaction.class, id);
        if(transaction != null)
            em.remove(transaction);
        em.getTransaction().commit();
    }

    public StockTransaction get(int id) {
        return em.find(StockTransaction.class, id);
    }


    public List<StockTransaction> findTransactionsForProject(Integer projectId) {
        List<StockTransaction> result = new ArrayList<>();

        Query query = em.createQuery("select a from StockTransaction a where a.project.id = :projectId", StockTransaction.class);
        query.setParameter("projectId", projectId);
        result.addAll(query.getResultList());

        return result;
    }

    public List<StockTransaction> findSellTransactionsForUser(Integer sellerUserId) {
        List<StockTransaction> result = new ArrayList<>();

        Query query = em.createQuery("select a from StockTransaction a where a.seller.id = :sellerUserId", StockTransaction.class);
        query.setParameter("sellerUserId", sellerUserId);
        result.addAll(query.getResultList());

        return result;
    }

    public List<StockTransaction> findBuyTransactionsForUser(Integer sellerUserId) {
        List<StockTransaction> result = new ArrayList<>();

        Query query = em.createQuery("select a from StockTransaction a where a.buyer.id = :buyerUserId", StockTransaction.class);
        query.setParameter("buyerUserId", sellerUserId);
        result.addAll(query.getResultList());

        return result;
    }
}
