package com.upreader.model;

import javax.persistence.*;
import java.io.Serializable;

/**
 * For upreaders and authors only
 * They will receive money using Amazon or Paypal
 *
 * ### This is work in progress ####
 */
@Entity
@Table(name = "user_payment_methods")
public class UserPaymentMethod implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * Name of the payment method: Amazon, Paypal etc.
     */
    @Column(name = "name")
    private String name;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false, updatable = false)
    private User user;

    public UserPaymentMethod() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }
}
