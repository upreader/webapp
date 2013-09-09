package com.upreader.model;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "stock_transactions")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class StockTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    /**
     * Transaction amount in dollars
     */
    @Column(name = "amount", nullable = false, updatable = false)
    private Float amount;

    /**
     * Transaction noOfShares
     */
    @Column(name = "shares_no", nullable = false, updatable = false)
    private Integer sharesNo;

    /**
     * Transaction date
     */
    @Column(name = "transaction_date", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date transactionDate;

    /**
     * Stock associated project
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false, updatable = false)
    private Project project;

    /**
     * Seller
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller", nullable = false, updatable = false)
    private User seller;

    /**
     * Buyer
     */
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "buyer", nullable = false, updatable = false)
    private User buyer;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Float getAmount() {
        return amount;
    }

    public void setAmount(Float amount) {
        this.amount = amount;
    }

    public Integer getSharesNo() {
        return sharesNo;
    }

    public void setSharesNo(Integer sharesNo) {
        this.sharesNo = sharesNo;
    }

    public Date getTransactionDate() {
        return transactionDate;
    }

    public void setTransactionDate(Date transactionDate) {
        this.transactionDate = transactionDate;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public User getSeller() {
        return seller;
    }

    public void setSeller(User seller) {
        this.seller = seller;
    }

    public User getBuyer() {
        return buyer;
    }

    public void setBuyer(User buyer) {
        this.buyer = buyer;
    }
}
