package com.upreader.model;

import com.fasterxml.jackson.annotation.JsonBackReference;

import javax.persistence.*;
import java.io.Serializable;
import java.sql.Blob;

/**
 * Holds the documents uploaded by the author during project creation (step 3)
 * proving the number of claimed book sales per year
 */
@Entity
@Table(name = "proofs_of_sales")
public class ProofOfSales implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.TABLE)
    private Integer id;

    @JsonBackReference
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false, updatable = false)
    private Project project;

    /**
     * Location of proof file (Amazon S3)
     */
    @Column(name = "proof")
    private String proof;

    public ProofOfSales() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Project getProject() {
        return project;
    }

    public void setProject(Project project) {
        this.project = project;
    }

    public String getProof() {
        return proof;
    }

    public void setProof(String proof) {
        this.proof = proof;
    }
}
