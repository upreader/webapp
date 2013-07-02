package com.upreader.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "projects")
public class Project implements Serializable {
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;

	@Column(name = "title", nullable = false, unique = true)
	private String title;

	/**
	 * Location of actual book file
	 */
	@Column(name = "book")
	private String book;

	@Column(name = "genre")
	private String genre;

	@Column(name = "subgenres")
	private String subgenres;

	/**
	 * Location of file for book cover
	 */
	@Column(name = "cover")
	private String cover;

	/**
	 * Location of file for book back cover (optional)
	 */
	@Column(name = "backcover")
	private String backCover;

	@Column(name = "pitch")
	private String pitch;

	@Column(name = "synopsis")
	private String synopsis;

	/**
	 * Location of file for sample book content
	 */
	@Column(name = "sample")
	private String sample;

	@Column(name = "references")
	private String references;

	@Column(name = "backstory")
	private String backstory;

	/**
	 * ebook price (not updateable, from ProjectOwnership)
	 */
	@Column(name = "bookPrice")
	private float bookPrice;

	/**
	 * How many years can the platform publish the ebook (not updateable, from
	 * ProjectOwnership)
	 */
	@Column(name = "publishyears")
	private int publishYears;

	/**
	 * How many shares are for sale (%) (not updateable, from ProjectOwnership)
	 */
	@Column(name = "percentToSale")
	private int percentToSale;

	/**
	 * IRS value (Calculated value)
	 */
	@Column(name = "irs")
	private float IRS;

	/**
	 * Total shares (Calculated value)
	 */
	@Column(name = "totshares")
	private int totalShares;

	/**
	 * Total shares still available for sale. This value will decrease once
	 * users will start buying (Calculated value)
	 */
	@Column(name = "sharesToSale")
	private int sharesToSale;

	/**
	 * Share price (Calculated value)
	 */
	@Column(name = "sharePrice")
	private float sharePrice;

	/**
	 * IRS sell deadline (Calculated value)
	 */
	@Column(name = "deadline")
	private Date deadline;

	/**
	 * if a serial story
	 */
	@Column(name = "serial")
	private Boolean serial;

	/**
	 * (Serial Story) How many chapters it will have. (not updateable, from
	 * ProjectOwnership)
	 */
	@Column(name = "chapters")
	private int noChapters;

	/**
	 * (Serial Story) The author has to estimate an average chapter word count
	 * between 100-500w up to 1000-2000w, 4000-5000w. (not updateable, from
	 * ProjectOwnership)
	 */
	@Column(name = "avgwordsch")
	private int avgWordsPerChapter;

	/**
	 * (Serial Story) First chapter. It is mandatory for the author to upload a
	 * first chapter/pilot.
	 */
	@Column(name = "pilot")
	private Boolean pilot;

	/**
	 * (Serial Story) A fixed amount of time between updates must be set up by
	 * the author. The maximum update delay allowed by Upreader will be 4 weeks
	 */
	@Column(name = "updatefreq")
	private int updateFreq;

	@OneToMany(mappedBy = "project", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected List<PromoItem> promoItems;

	// author is mandatory and cannot be updated
	@OneToOne(fetch = FetchType.LAZY, mappedBy = "project")
	@JoinColumn(name = "ownerid", nullable = false, updatable = false)
	private ProjectOwnership author;

	@OneToMany(mappedBy = "project", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected List<ProjectMembership> shareholders;
	
	@OneToMany(mappedBy = "project", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
	protected List<ProjectSubscription> subscribers;
	

	public Project() {
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

	public String getBook() {
		return book;
	}

	public void setBook(String book) {
		this.book = book;
	}

	public String getGenre() {
		return genre;
	}

	public void setGenre(String genre) {
		this.genre = genre;
	}

	public String getSubgenres() {
		return subgenres;
	}

	public void setSubgenres(String subgenres) {
		this.subgenres = subgenres;
	}

	public String getCover() {
		return cover;
	}

	public void setCover(String cover) {
		this.cover = cover;
	}

	public String getBackCover() {
		return backCover;
	}

	public void setBackCover(String backCover) {
		this.backCover = backCover;
	}

	public String getPitch() {
		return pitch;
	}

	public void setPitch(String pitch) {
		this.pitch = pitch;
	}

	public String getSynopsis() {
		return synopsis;
	}

	public void setSynopsis(String synopsis) {
		this.synopsis = synopsis;
	}

	public String getSample() {
		return sample;
	}

	public void setSample(String sample) {
		this.sample = sample;
	}

	public String getReferences() {
		return references;
	}

	public void setReferences(String references) {
		this.references = references;
	}

	public String getBackstory() {
		return backstory;
	}

	public void setBackstory(String backstory) {
		this.backstory = backstory;
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

	public float getIRS() {
		return IRS;
	}

	public void setIRS(float iRS) {
		IRS = iRS;
	}

	public int getTotalShares() {
		return totalShares;
	}

	public void setTotalShares(int totalShares) {
		this.totalShares = totalShares;
	}

	public int getSharesToSale() {
		return sharesToSale;
	}

	public void setSharesToSale(int sharesToSale) {
		this.sharesToSale = sharesToSale;
	}

	public float getSharePrice() {
		return sharePrice;
	}

	public void setSharePrice(float sharePrice) {
		this.sharePrice = sharePrice;
	}

	public Date getDeadline() {
		return deadline;
	}

	public void setDeadline(Date deadline) {
		this.deadline = deadline;
	}

	public Boolean getSerial() {
		return serial;
	}

	public void setSerial(Boolean serial) {
		this.serial = serial;
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

	public Boolean getPilot() {
		return pilot;
	}

	public void setPilot(Boolean pilot) {
		this.pilot = pilot;
	}

	public int getUpdateFreq() {
		return updateFreq;
	}

	public void setUpdateFreq(int updateFreq) {
		this.updateFreq = updateFreq;
	}

	public List<PromoItem> getPromoItems() {
		return promoItems;
	}

	public void setPromoItems(List<PromoItem> promoItems) {
		this.promoItems = promoItems;
	}

	public ProjectOwnership getAuthor() {
		return author;
	}

	public void setAuthor(ProjectOwnership author) {
		this.author = author;
	}

	public List<ProjectMembership> getShareholders() {
		return shareholders;
	}

	public void setShareholders(List<ProjectMembership> shareholders) {
		this.shareholders = shareholders;
	}

	public List<ProjectSubscription> getSubscribers() {
		return subscribers;
	}
	
	public void setSubscribers(List<ProjectSubscription> subscribers) {
		this.subscribers = subscribers;
	}
}
