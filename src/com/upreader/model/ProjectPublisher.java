package com.upreader.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;

/**
 * Information about publishers that manifested interest in the
 * project
 *
 */
@Entity
@Table(name = "project_publishers")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class ProjectPublisher {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    /**
     * publisher. This is mandatory
     */
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "publisher_id", nullable = false, updatable = false)
    private User publisher;

    /**
     * project.
     */
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY )
    @JoinColumn(name = "project_id", nullable = false, updatable = false)
    private Project project;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public User getPublisher() {
        return publisher;
    }

    public void setPublisher(User publisher) {
        this.publisher = publisher;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
