package com.upreader.model.user;

import javax.persistence.*;

/**
 * User: Razvan.Ionescu
 * Date: 7/9/13
 **/
@Entity
@Table(name = "postaltype", uniqueConstraints = {
        @UniqueConstraint(name = "UK_USER_ID", columnNames = { "id" })
        })
public class PostalType {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "type")
    private String type;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
