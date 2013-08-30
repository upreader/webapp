package com.upreader.dto;

import java.io.Serializable;

public class MonitorBoardDTO implements Serializable {
    private Integer id;

    // project data
    private String title;
    private Float bookPrice;
    private Integer upreaders;
    private Float shareValue;
    private Integer irsProgress;
    private Integer irsMax;
    private Integer daysToDeadline;
    private Integer noViews;
    private Integer booksSold;
    private Integer sharesToSale;
    private Integer subscribers;
    private Float serialStorySubscriptionPrice;
    private Integer income;
    private String derivatives;

    // author data
    private String authorName;
    private Integer authorRating;

    public MonitorBoardDTO() {
    }

    public Float getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(Float bookPrice) {
        this.bookPrice = bookPrice;
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

    public Integer getUpreaders() {
        return upreaders;
    }

    public void setUpreaders(Integer upreaders) {
        this.upreaders = upreaders;
    }

    public Float getShareValue() {
        return shareValue;
    }

    public void setShareValue(Float shareValue) {
        this.shareValue = shareValue;
    }

    public Integer getIrsProgress() {
        return irsProgress;
    }

    public void setIrsProgress(Integer irsProgress) {
        this.irsProgress = irsProgress;
    }

    public Integer getDaysToDeadline() {
        return daysToDeadline;
    }

    public void setDaysToDeadline(Integer daysToDeadline) {
        this.daysToDeadline = daysToDeadline;
    }

    public Integer getNoViews() {
        return noViews;
    }

    public void setNoViews(Integer noViews) {
        this.noViews = noViews;
    }

    public Integer getBooksSold() {
        return booksSold;
    }

    public void setBooksSold(Integer booksSold) {
        this.booksSold = booksSold;
    }



    public Integer getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(Integer subscribers) {
        this.subscribers = subscribers;
    }

    public Float getSerialStorySubscriptionPrice() {
        return serialStorySubscriptionPrice;
    }

    public void setSerialStorySubscriptionPrice(Float serialStorySubscriptionPrice) {
        this.serialStorySubscriptionPrice = serialStorySubscriptionPrice;
    }

    public Integer getIncome() {
        return income;
    }

    public void setIncome(Integer income) {
        this.income = income;
    }

    public String getDerivatives() {
        return derivatives;
    }

    public void setDerivatives(String derivatives) {
        this.derivatives = derivatives;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public Integer getAuthorRating() {
        return authorRating;
    }

    public void setAuthorRating(Integer authorRating) {
        this.authorRating = authorRating;
    }

    public Integer getSharesToSale() {
        return sharesToSale;
    }

    public void setSharesToSale(Integer sharesToSale) {
        this.sharesToSale = sharesToSale;
    }

    public Integer getIrsMax() {
        return irsMax;
    }

    public void setIrsMax(Integer irsMax) {
        this.irsMax = irsMax;
    }
}