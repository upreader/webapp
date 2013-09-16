package com.upreader.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

/**
 * Information about membership information for a User that bought shares in a
 * project
 *
 * @author burcafl
 */
@Entity
@Table(name = "project_memberships")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class ProjectMembership implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    /**
     * Number of shares bought (will be deducted from the project shares transactionally)
     */
    @Column(name = "shares")
    private int shares;

    /**
     * How much the user paid for the shares
     */
    @Column(name = "pricepaid")
    private float pricePaid;

    /**
     * Contract agreed with the platform
     */
    @Column(name = "contract")
    private String contract;

    /**
     * Date when user became shareholder
     */
    @Column(name = "mdate")
    private Date signDate;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", nullable = false, updatable = false)
    private User user;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "projectid", nullable = false, updatable = false)
    private Project project;

    public ProjectMembership() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getShares() {
        return shares;
    }

    public void setShares(int shares) {
        this.shares = shares;
    }

    public float getPricePaid() {
        return pricePaid;
    }

    public void setPricePaid(float pricePaid) {
        this.pricePaid = pricePaid;
    }

    public String getContract() {
        return contract;
    }

    public void setContract(String contract) {
        this.contract = contract;
    }

    public Date getSignDate() {
        return signDate;
    }

    public void setSignDate(Date signDate) {
        this.signDate = signDate;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

}
