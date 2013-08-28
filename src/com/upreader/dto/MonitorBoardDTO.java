package com.upreader.dto;

public class MonitorBoardDTO {
    private int id;

    // project data
    private String title;
    private float bookPrice;
    private int upreaders;
    private float shareValue;
    private int irsProgress;
    private int daysToDeadline;
    private int noViews;
    private int booksSold;
    private int sharesToSale;
    private int subscribers;
    private int serialStorySubscriptionPrice;
    private int income;
    private String derivatives;

    // author data
    private String authorName;
    private int authorRating;

    public MonitorBoardDTO() {
    }

    public float getBookPrice() {
        return bookPrice;
    }

    public void setBookPrice(float bookPrice) {
        this.bookPrice = bookPrice;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getUpreaders() {
        return upreaders;
    }

    public void setUpreaders(int upreaders) {
        this.upreaders = upreaders;
    }

    public float getShareValue() {
        return shareValue;
    }

    public void setShareValue(float shareValue) {
        this.shareValue = shareValue;
    }

    public int getIrsProgress() {
        return irsProgress;
    }

    public void setIrsProgress(int irsProgress) {
        this.irsProgress = irsProgress;
    }

    public int getDaysToDeadline() {
        return daysToDeadline;
    }

    public void setDaysToDeadline(int daysToDeadline) {
        this.daysToDeadline = daysToDeadline;
    }

    public int getNoViews() {
        return noViews;
    }

    public void setNoViews(int noViews) {
        this.noViews = noViews;
    }

    public int getBooksSold() {
        return booksSold;
    }

    public void setBooksSold(int booksSold) {
        this.booksSold = booksSold;
    }

    public int getSharesToSale() {
        return sharesToSale;
    }

    public void setSharesToSale(int sharesToSale) {
        this.sharesToSale = sharesToSale;
    }

    public int getSubscribers() {
        return subscribers;
    }

    public void setSubscribers(int subscribers) {
        this.subscribers = subscribers;
    }

    public int getSerialStorySubscriptionPrice() {
        return serialStorySubscriptionPrice;
    }

    public void setSerialStorySubscriptionPrice(int serialStorySubscriptionPrice) {
        this.serialStorySubscriptionPrice = serialStorySubscriptionPrice;
    }

    public int getIncome() {
        return income;
    }

    public void setIncome(int income) {
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

    public int getAuthorRating() {
        return authorRating;
    }

    public void setAuthorRating(int authorRating) {
        this.authorRating = authorRating;
    }
}
