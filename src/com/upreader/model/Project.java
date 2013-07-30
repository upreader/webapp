package com.upreader.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

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
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;

@Entity
@Table(name = "projects")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class Project implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Location of file for book cover (Amazon S3)
     */
    @Column(name = "cover")
    private String cover;

    /**
     * title of project
     */
    @Column(name = "title", nullable = false, unique = true)
    private String title;

    /**
     * Can be one of: story, serial story
     */
    @Column(name = "format")
    private String format;

    /**
     * type of story: story,serial
     */
    @Column(name = "type")
    private String type;

    /**
     * Project genre: Drama, Comedy, Romance etc.
     */
    @Column(name = "genre")
    private String genre;

    /**
     * Novel,Novella,Short Story,Graphic Novel,Poetry
     */
    @Column(name = "category")
    private String category;

    /**
     * Subgenre for the selected project genre
     * e.g. if Genre=Fantasy then this can be one of
     * Superheroes, Vampires, Zombies, Magic
     */
    @Column(name = "subgenre")
    private String subgenre;

    /**
     * Sigle selling proposition
     */
    @Column(name = "selling_proposition")
    private String sellingProposition;

    /**
     * Comma separated list of custom tags
     */
    @Column(name = "tags")
    private String tags;

    /**
     * Description near cover under pitch (non-bold)
     */
    @Column(name = "synopsis")
    private String synopsis;

    /**
     * Location of ebook file (Amazon S3)
     */
    @Column(name = "book")
    private String book;

    /**
     * Crocodoc document UUID
     */
    @Column(name = "book_uuid")
    private String bookViewUUID;

    /**
     * Location of file for sample (Amazon S3)
     */
    @Column(name = "sample")
    private String sample;

    /**
     * Crocodoc document UUID
     */
    @Column(name = "sample")
    private String sampleViewUUID;

    /**
     * Selling rights to platform
     * How many years can the platform publish the book
     */
    @Column(name = "selling_rights")
    private Integer sellingRights;

    /**
     * Estimated sales
     */
    @Column(name = "est_unit_sales")
    private Integer estimatedUnitSales;

    /**
    * Holds the documents uploaded by the author during project creation (step 3)
    * proving the number of claimed book sales per year
    */
    @OneToMany(mappedBy = "project", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    protected List<ProofOfSales> proofsOfSales;

    /**
     * ebook price
     */
    @Column(name = "book_price")
    private Float bookPrice;

    /**
     * Percent royalties to platform
     * How many shares are for sale (%)
     */
    @Column(name = "percent_to_platform")
    private Integer percentToPlatform;

    /**
     * Percent royalties to platform
     * How many shares are for sale (%)
     */
    @Column(name = "sales_projection")
    private Integer salesProjection;

    /**
     * Comma-separated list of derivatives: audiobook, tv, movie etc.
     */
    @Column(name = "derivatives")
    private String derivatives;

    /**
     * IRS value (Calculated value)
     */
    @Column(name = "irs")
    private Integer IRS;

    /**
     * Number of shares
     * Minimum number of shares an upreader has to buy in this project
     */
    @Column(name = "min_shares_buy")
    private Integer minSharesToBuy;

    /**
     * Share value
     * Price of a single share
     */
    @Column(name = "share_value")
    private Float shareValue;

    /**
     * Total shares (Calculated value = IRS / shareValue)
     */
    @Column(name = "total_shares")
    private Integer totalShares;

    /**
     * Total shares still available for sale. This value will decrease once
     * users will start buying (Calculated value)
     */
    @Column(name = "sharesToSale")
    private Integer sharesToSale;

    /**
     * IRS sell deadline (Calculated value)
     * Formula:
     *    0 < IRS < 10000, deadline = 30 days
     *    10000 <= IRS < 20000, deadline = 45 days
     *    20000 <= IRS , deadline = 60 days
     */
    @Column(name = "deadline")
    private Date deadline;

    /**
     * If project is approved by an editor.
     * Only approved projects will be shown to the public
     */
    @Column(name = "approved")
    private Boolean approved;

    /**
     * How many times the project was viewed
     */
    @Column(name = "no_views")
    private Integer noViews;

    /**
     * status of story:
     *
     * free (only for serial story)
     *      if type=serial story then subscription is free, shares cannot be sold. Ebook does not exist
     *         Subscription is free until the number of subscribers reaches 1000. After that, it will become a
     *         waiting serial story
     * waiting
     *      if type=story then shares are being sold to raise the IRS. Ebook cannot be sold
     *      if type=serial story then shares are being sold to raise the IRS. Subscription is still free. Ebook does not exist yet.
     *
     * shelved - finished selling royalties. In this case if type=s book/subscription is for sale. if serial story is finished
     *      if type=story then all initial shares are sold, IRS is completed and ebook is for sale.
     *         Shares can be put for sale by shareholders and can be bought by upreaders
     *      if type=serial story then subscription is not free anymore. IRS is completed and now the author has to
     *         submit the remaining chapters to finish the book. Shares can be put for sale by shareholders and can be bought by upreaders.
     *         When the author submits all remaining chapters it will become a normal shelved story with an ebook for sale.
     */
    @Column(name = "status")
    private String status;

    /**
     * Users that pinned this project to their workspace
     */
    @OneToMany(mappedBy = "project", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    protected List<ProjectPromoPack> promoPacks;

    /**
     * (Serial Story) How many chapters it will have.
     */
    @Column(name = "no_chapters")
    private Integer serialStoryNoChapters;

    /**
     * (Serial Story) The author has to estimate an average chapter word count
     * between 100-500w up to 1000-2000w, 4000-5000w.
     */
    @Column(name = "avgwordsch")
    private Integer serialStoryAvgWordsPerChapter;

    /**
     * (Serial Story) A fixed amount of time between updates must be set up by
     * the author. The maximum update delay allowed by Upreader will be 4 weeks
     */
    @Column(name = "update_delay")
    private Integer serialStoryUpdateDelay;

    /**
     * project advertisements that are shown similar to blog posts on the project page
     */
    @OneToMany(mappedBy = "project", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    protected List<PromoItem> promoItems;

    /**
     * project author. This is mandatory and cannot be updated
     */
    @OneToOne(fetch = FetchType.LAZY, mappedBy = "project", cascade = CascadeType.ALL)
    @JoinColumn(name = "author_id", nullable = false, updatable = false)
    private ProjectOwnership author;

    /**
     * users that own shares in this project
     */
    @OneToMany(mappedBy = "project", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    protected List<ProjectMembership> shareholders;

    /**
     * serial story subscribers
     */
    @OneToMany(mappedBy = "project", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    protected List<ProjectSubscription> subscribers;

    /**
     * Users that pinned this project to their workspace
     */
    @OneToMany(mappedBy = "project", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    protected List<PinnedProject> interestedUsers;

    /**
     * Users that pinned this project to their workspace
     */
    @OneToMany(mappedBy = "project", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    protected List<StoryChapter> serialStoryChapters;

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

    public String getFormat() {
        return format;
    }

    public void setFormat(String format) {
        this.format = format;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getSubgenre() {
        return subgenre;
    }

    public void setSubgenre(String subgenres) {
        this.subgenre = subgenres;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getSynopsis() {
        return synopsis;
    }

    public void setSynopsis(String synopsis) {
        this.synopsis = synopsis;
    }

    public Float getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(Float bookPrice) {
        this.bookPrice = bookPrice;
    }

    public Integer getIRS() {
        return IRS;
    }

    public void setIRS(Integer iRS) {
        IRS = iRS;
    }

    public int getTotalShares() {
        return totalShares;
    }

    public void setTotalShares(Integer totalShares) {
        this.totalShares = totalShares;
    }

    public Integer getSharesToSale() {
        return sharesToSale;
    }

    public void setSharesToSale(Integer sharesToSale) {
        this.sharesToSale = sharesToSale;
    }

    public Date getDeadline() {
        return deadline;
    }

    public void setDeadline(Date deadline) {
        this.deadline = deadline;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getSerialStoryNoChapters() {
        return serialStoryNoChapters;
    }

    public void setSerialStoryNoChapters(Integer serialStoryNoChapters) {
        this.serialStoryNoChapters = serialStoryNoChapters;
    }

    public Integer getSerialStoryAvgWordsPerChapter() {
        return serialStoryAvgWordsPerChapter;
    }

    public void setSerialStoryAvgWordsPerChapter(Integer serialStoryAvgWordsPerChapter) {
        this.serialStoryAvgWordsPerChapter = serialStoryAvgWordsPerChapter;
    }

    public Integer getSerialStoryUpdateDelay() {
        return serialStoryUpdateDelay;
    }

    public void setSerialStoryUpdateDelay(Integer serialStoryUpdateFreq) {
        this.serialStoryUpdateDelay = serialStoryUpdateFreq;
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

    public Boolean getApproved() {
        return approved;
    }

    public void setApproved(Boolean approved) {
        this.approved = approved;
    }

    public String getDerivatives() {
        return derivatives;
    }

    public void setDerivatives(String derivatives) {
        this.derivatives = derivatives;
    }

    public int getNoViews() {
        return noViews;
    }

    public void setNoViews(int noViews) {
        this.noViews = noViews;
    }

    public List<PinnedProject> getInterestedUsers() {
        return interestedUsers;
    }

    public void setInterestedUsers(List<PinnedProject> interestedUsers) {
        this.interestedUsers = interestedUsers;
    }

    public List<StoryChapter> getSerialStoryChapters() {
        return serialStoryChapters;
    }

    public void setSerialStoryChapters(List<StoryChapter> serialStoryChapters) {
        this.serialStoryChapters = serialStoryChapters;
    }

    public String getSellingProposition() {
        return sellingProposition;
    }

    public void setSellingProposition(String sellingProposition) {
        this.sellingProposition = sellingProposition;
    }

    public String getSample() {
        return sample;
    }

    public void setSample(String sample) {
        this.sample = sample;
    }

    public Integer getSellingRights() {
        return sellingRights;
    }

    public void setSellingRights(Integer sellingRights) {
        this.sellingRights = sellingRights;
    }

    public Integer getEstimatedUnitSales() {
        return estimatedUnitSales;
    }

    public void setEstimatedUnitSales(Integer estimatedUnitSales) {
        this.estimatedUnitSales = estimatedUnitSales;
    }

    public List<ProofOfSales> getProofsOfSales() {
        return proofsOfSales;
    }

    public void setProofsOfSales(List<ProofOfSales> proofsOfSales) {
        this.proofsOfSales = proofsOfSales;
    }

    public Integer getPercentToPlatform() {
        return percentToPlatform;
    }

    public void setPercentToPlatform(Integer percentToPlatform) {
        this.percentToPlatform = percentToPlatform;
    }

    public Integer getSalesProjection() {
        return salesProjection;
    }

    public void setSalesProjection(Integer salesProjection) {
        this.salesProjection = salesProjection;
    }

    public Integer getMinSharesToBuy() {
        return minSharesToBuy;
    }

    public void setMinSharesToBuy(Integer minSharesToBuy) {
        this.minSharesToBuy = minSharesToBuy;
    }

    public Float getShareValue() {
        return shareValue;
    }

    public void setShareValue(Float shareValue) {
        this.shareValue = shareValue;
    }

    public void setNoViews(Integer noViews) {
        this.noViews = noViews;
    }

    public String getBookViewUUID() {
        return bookViewUUID;
    }

    public void setBookViewUUID(String bookViewUUID) {
        this.bookViewUUID = bookViewUUID;
    }

    public String getSampleViewUUID() {
        return sampleViewUUID;
    }

    public void setSampleViewUUID(String sampleViewUUID) {
        this.sampleViewUUID = sampleViewUUID;
    }

    public List<ProjectPromoPack> getPromoPacks() {
        return promoPacks;
    }

    public void setPromoPacks(List<ProjectPromoPack> promoPacks) {
        this.promoPacks = promoPacks;
    }
}
