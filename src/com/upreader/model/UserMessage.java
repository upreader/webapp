package com.upreader.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Collection;
import java.util.Date;

/**
 * User notifications
 */
@Entity
@Table(name = "user_messages")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class UserMessage implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    @Column(name = "subject", nullable = false)
    private String subject;

    /**
     * type of message (projects, platform, subscriptions, etc.)
     * messages can be grouped in the UI by this category
     */
    @Column(name = "category", nullable = false)
    private String category;

    @Column(name = "body")
    private String body;

    @Column(name = "sentDate", nullable = false)
    private Date sentDate;

    @Column(name = "read", nullable = false)
    private Boolean read;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    public UserMessage() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Date getSentDate() {
        return sentDate;
    }

    public void setSentDate(Date sentDate) {
        this.sentDate = sentDate;
    }

    public Boolean getRead() {
        return read;
    }

    public void setRead(Boolean read) {
        this.read = read;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
