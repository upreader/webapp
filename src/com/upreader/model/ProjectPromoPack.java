package com.upreader.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Promo packs selected for a project in Step 4
 *
 *  ### This is work in progress ####
 */
@Entity
@Table(name = "project_promo_packs")
public class ProjectPromoPack implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    /**
     * Selected PromoPack
     */
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "pack_id", nullable = false, updatable = false)
    private PromoPack promoPack;

    /**
     * Project to which this promo pack belongs
     */
    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false, updatable = false)
    private Project project;

    public ProjectPromoPack() {
    }
}
