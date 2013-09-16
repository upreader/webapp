package com.upreader.model;


import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/**
 * Interest of an user in a project
 */
@Entity
@Table(name = "project_interests")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class PinnedProject implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    /**
     * active = true means the project is pinned and shown on the monitoring board
     * active = false means the project was unpinned. We keep this to have a history of
     *          what the user pinned.
     *          Obviously, we don't display project in monitoring board with active = false.
     */
    @Column(name = "active")
    private Boolean active;

    /**
     * when user pinned the project to his monitoring board
     */
    @Column(name = "date_pinned")
    private Date datePinned;

    /**
     * when user unpinned the project to his monitoring board
     */
    @Column(name = "date_unpinned")
    private Date dateUnpinned;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false, updatable = false)
    private Project project;

    public PinnedProject() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }

    public Date getDatePinned() {
        return datePinned;
    }

    public void setDatePinned(Date datePinned) {
        this.datePinned = datePinned;
    }

    public Date getDateUnpinned() {
        return dateUnpinned;
    }

    public void setDateUnpinned(Date dateUnpinned) {
        this.dateUnpinned = dateUnpinned;
    }
}
