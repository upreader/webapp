package com.upreader.model.user;

import com.upreader.model.User;

import javax.persistence.*;
import java.io.Serializable;

/**
 * User: Razvan.Ionescu
 * Date: 7/9/13
 */
@Entity
@Table(name = "user_addresses", uniqueConstraints = {
        @UniqueConstraint(name = "UK_USER_ID", columnNames = {"userId", "type"})
})
public class UserAddress implements Serializable {
    public static final String TYPE_HOME = "home";
    public static final String TYPE_BUSINESS = "business";

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type")
    private String type;

    @Column(name = "street")
    private String street;

    @Column(name = "city")
    private String city;

    @Column(name = "stateprov")
    private String stateprov;

    @Column(name = "postalcode")
    private String postalcode;

    @Column(name = "country")
    private String country;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "userid", nullable = false, updatable = false)
    private User user;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getStateprov() {
        return stateprov;
    }

    public void setStateprov(String stateprov) {
        this.stateprov = stateprov;
    }

    public String getPostalcode() {
        return postalcode;
    }

    public void setPostalcode(String postalcode) {
        this.postalcode = postalcode;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
