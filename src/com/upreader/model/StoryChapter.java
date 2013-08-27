package com.upreader.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.io.Serializable;
import java.util.Date;

/***
 * Chapters uploaded by users for a serial story
 *
 * ### This is work in progress ####
 */
@Entity
@Table(name = "story_chapters")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class StoryChapter implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    @Column(name = "title", nullable = false, unique = true)
    private String title;

    /**
     * Location of actual chapter file content (Amazon S3)
     */
    @Column(name = "content")
    private String content;

    /**
     * When the chapter was added
     */
    @Column(name = "date_added")
    private Date dateAdded;

    /**
     * Average word count for uploaded chapter
     */
    @Column(name = "word_count")
    private int wordCount;

    /**
     * approved by an editor. Only approved chapters will be shown to the public
     */
    @Column(name = "approved")
    private Boolean approved;

    /**
     * Serial story for which this chapter is
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false, updatable = false)
    private Project project;

    public StoryChapter() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getDateAdded() {
        return dateAdded;
    }

    public void setDateAdded(Date dateAdded) {
        this.dateAdded = dateAdded;
    }

    public int getWordCount() {
        return wordCount;
    }

    public void setWordCount(int wordCount) {
        this.wordCount = wordCount;
    }

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }
}
