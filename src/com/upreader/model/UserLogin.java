package com.upreader.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Records user logins
 */
@Entity
public class UserLogin implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    /**
     * date & time when user logged in
     */
    @Column(name = "login_date")
    private Date loginDate;

    /**
     * IP of user
     */
    @Column(name = "login_ip")
    private String loginIP;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    public UserLogin() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getLoginDate() {
        return loginDate;
    }

    public void setLoginDate(Date loginDate) {
        this.loginDate = loginDate;
    }

    public String getLoginIP() {
        return loginIP;
    }

    public void setLoginIP(String loginIP) {
        this.loginIP = loginIP;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
