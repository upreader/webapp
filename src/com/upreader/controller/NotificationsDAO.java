package com.upreader.controller;

import com.upreader.UpreaderApplication;
import com.upreader.model.BookTransaction;
import com.upreader.model.Notification;
import org.apache.log4j.Logger;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class NotificationsDAO {
    Logger log = Logger.getLogger(NotificationsDAO.class);
    private EntityManager em;
    private UpreaderApplication application;

    public NotificationsDAO(UpreaderApplication application, EntityManager em) {
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

    public Notification get(int id) {
        return em.find(Notification.class, id);
    }

    public List<Notification> getNotificationsSendByUser(Integer senderId) {
        List<Notification> result = new ArrayList<>();

        Query query = em.createQuery("select n from Notification n where n.sender.id = :senderId", Notification.class);
        query.setParameter("senderId", senderId);
        result.addAll(query.getResultList());

        return result;
    }

    public ArrayList<Map> getNotificationsReceivedByUser(Integer receiverId) {

        ArrayList<Map> groupedNotifications = new ArrayList();



        List<String> groups = getNotificationGroupsForUser(receiverId);

        for(String group : groups){
            HashMap item = new HashMap();
            List<Notification> groupNotifications = getNotificationsReceivedByUserAndGroup(receiverId, group);
            item.put("group", group);
            item.put("notifications", groupNotifications);

            groupedNotifications.add(item);

        }

        return groupedNotifications;
    }

    public List<String> getNotificationGroupsForUser(Integer receiverId) {
        List<String> result = new ArrayList<>();

        Query query = em.createQuery("select distinct(n.notificationGroup) from Notification n where n.receiver.id = :receiverId", Notification.class);
        query.setParameter("receiverId", receiverId);
        result.addAll(query.getResultList());

        return result;
    }

    public List<Notification> getNotificationsReceivedByUserAndGroup(Integer receiverId, String notificationGroup) {
        List<Notification> result = new ArrayList<>();

        Query query = em.createQuery("select n from Notification n where n.receiver.id = :receiverId and n.notificationGroup = :notificationGroup", Notification.class);
        query.setParameter("receiverId", receiverId);
        query.setParameter("notificationGroup", notificationGroup);
        result.addAll(query.getResultList());

        return result;
    }
}
