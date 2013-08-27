package com.upreader.model;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * Represnts a promo pack in UpReader
 *
 *  ### This is work in progress ####
 */
@Entity
@Table(name = "promo_packs")
public class PromoPack implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    @Column(name = "name", nullable = false, unique = true)
    private String name;

    @Column(name = "price", nullable = false, unique = true)
    private Float price;

    /**
     * Projects that selected this PromoPack
     */
    @OneToMany(mappedBy = "promoPack", orphanRemoval = true, fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    protected List<ProjectPromoPack> projects;

    public PromoPack() {
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

    public Float getPrice() {
        return price;
    }

    public void setPrice(Float price) {
        this.price = price;
    }

    public List<ProjectPromoPack> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectPromoPack> projects) {
        this.projects = projects;
    }
}
