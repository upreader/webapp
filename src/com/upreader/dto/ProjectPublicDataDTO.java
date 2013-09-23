package com.upreader.dto;

import com.upreader.UpreaderApplication;
import com.upreader.model.Project;
import org.joda.time.DateTime;
import org.joda.time.Days;

import java.util.Locale;
import java.util.ResourceBundle;

public class ProjectPublicDataDTO {

    private Integer projectId;
    private String projectTitle;
    private String authorName;
    private Integer authorRating;
    private Integer IRS;
    private String type;
    private String category;
    private String genre;
    private String subgenre;
    private Float bookPrice;
    private Float shareValue;
    private Integer totalShares;
    private Integer remainingShares;
    private Integer daysToDeadline;
    private Integer interestedUsers;
    private Integer authorid;
    private Integer noViews;
    private Integer interestedPublishers;

    public ProjectPublicDataDTO(Project p, Locale locale){
        this.projectId = p.getId();
        this.projectTitle = p.getTitle();
        this.authorName = p.getAuthor().getFirstName() + " " + p.getAuthor().getLastName();
        this.authorRating = p.getAuthor().getRating();
        this.IRS = p.getIRS();
        this.type = p.getType();
        this.category = p.getCategory();
        this.genre = p.getGenre();
        this.subgenre = p.getSubgenre();
        this.bookPrice = p.getBookPrice();
        this.shareValue = p.getShareValue();
        this.totalShares = p.getTotalShares();
        this.remainingShares = p.getSharesToSale();

        DateTime deadlineEnd  = new DateTime(p.getDeadline().getTime());
        DateTime now = DateTime.now();
        this.daysToDeadline = Days.daysBetween(now, deadlineEnd).getDays();
        this.authorid = p.getAuthor().getId();
        this.interestedUsers = p.getInterestedUsers() == null ? 0 : p.getInterestedUsers().size();
        this.noViews = p.getNoViews() == null ? 0 : p.getNoViews();
        this.interestedPublishers = p.getInterestedPublishers() == null ? 0 : p.getInterestedPublishers().size();

    }

    public Integer getProjectId() {
        return projectId;
    }

    public String getProjectTitle() {
        return projectTitle;
    }

    public String getAuthorName() {
        return authorName;
    }

    public Integer getAuthorRating() {
        return authorRating;
    }

    public Integer getIRS() {
        return IRS;
    }

    public String getType() {
        return type;
    }

    public String getCategory() {
        return category;
    }

    public String getGenre() {
        return genre;
    }

    public String getSubgenre() {
        return subgenre;
    }

    public Float getBookPrice() {
        return bookPrice;
    }

    public Float getShareValue() {
        return shareValue;
    }

    public Integer getTotalShares() {
        return totalShares;
    }

    public Integer getRemainingShares() {
        return remainingShares;
    }

    public Integer getAuthorid() {
        return authorid;
    }

    public Integer getDaysToDeadline() {
        return daysToDeadline;
    }

    public Integer getInterestedUsers() {
        return interestedUsers;
    }

    public Integer getNoViews() {
        return noViews;
    }

    public Integer getInterestedPublishers() {
        return interestedPublishers;
    }
}
