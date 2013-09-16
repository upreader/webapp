package com.upreader.model;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table(name = "book_transactions")
@JsonIdentityInfo(generator=ObjectIdGenerators.IntSequenceGenerator.class, property="@id")
public class BookTransaction {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    /**
     * Transaction amount in dollars
     */
    @Column(name = "amount", nullable = false, updatable = false)
    private Float amount;

    /**
     * Transaction quantity
     */
    @Column(name = "books_qty", nullable = false, updatable = false)
    private Integer booksQty;

    /**
     * Transaction date
     */
    @Column(name = "transaction_date", nullable = false, updatable = false, columnDefinition = "TIMESTAMP DEFAULT CURRENT_TIMESTAMP")
    private Date transactionDate;

    /**
     * Book associated project
     */
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false, updatable = false)
    private Project project;

    /**
     * Seller
     */
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "seller", nullable = false, updatable = false)
    private User seller;

    /**
     * Buyer
     */
    @JsonBackReference
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

    public Integer getBooksQty() {
        return booksQty;
    }

    public void setBooksQty(Integer booksQty) {
        this.booksQty = booksQty;
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
