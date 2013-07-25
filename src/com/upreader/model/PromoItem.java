package com.upreader.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import java.io.Serializable;

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
 * Promotional articles added to a project.
 * They will appear on the UI like blog entries.
 */
@Entity
@Table(name = "promoitems")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class PromoItem implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

    /**
     * Title for the article
     */
	@Column(name = "name")
	private String title;

    /**
     * HTML content
     */
	@Column(name = "description")
	private String content;

    /**
     * Owner project
     */
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "projectid", nullable = false, updatable = false)
	private Project project;

	public PromoItem() {
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

	public void setTitle(String name) {
		this.title = name;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String description) {
		this.content = description;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

}
