package com.upreader.model;

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
import javax.persistence.OneToOne;
import javax.persistence.Table;

/**
 * How an author owns a project
 */
@Entity
@Table(name = "project_ownerships")
public class ProjectOwnership implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	/**
	 * ebook price
	 */
	@Column(name = "bookPrice")
	private float bookPrice;

	/**
	 * How many years can the platform publish the ebook
	 */
	@Column(name = "publishyears")
	private int publishYears;

	/**
	 * How many shares are for sale (%)
	 */
	@Column(name = "percentToSale")
	private int percentToSale;

	/**
	 * (Serial Story) How many chapters it will have
	 */
	@Column(name = "chapters")
	private int noChapters;

	/**
	 * (Serial Story) The author has to estimate an average chapter word count
	 * between 100-500w up to 1000-2000w, 4000-5000w,
	 */
	@Column(name = "avgwordsch")
	private int avgWordsPerChapter;

	/**
	 * Contract agreed with the platform
	 */
	@Column(name = "contract")
	private String contract;

	/**
	 * Contract agreement date
	 */
	@Column(name = "signdate")
	private Date signDate;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "userid", nullable = false, updatable = false)
	private User user;

	@OneToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "projectid", nullable = false, updatable = false)
	private Project project;

	public ProjectOwnership() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public float getBookPrice() {
		return bookPrice;
	}

	public void setBookPrice(float bookPrice) {
		this.bookPrice = bookPrice;
	}

	public int getPublishYears() {
		return publishYears;
	}

	public void setPublishYears(int publishYears) {
		this.publishYears = publishYears;
	}

	public int getPercentToSale() {
		return percentToSale;
	}

	public void setPercentToSale(int percentToSale) {
		this.percentToSale = percentToSale;
	}

	public int getNoChapters() {
		return noChapters;
	}

	public void setNoChapters(int noChapters) {
		this.noChapters = noChapters;
	}

	public int getAvgWordsPerChapter() {
		return avgWordsPerChapter;
	}

	public void setAvgWordsPerChapter(int avgWordsPerChapter) {
		this.avgWordsPerChapter = avgWordsPerChapter;
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
