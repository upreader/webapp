package com.upreader.model;

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
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "project_id", nullable = false, updatable = false)
    private Project project;

    @Column(name = "proof")
    private Blob proof;

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

    public Blob getProof() {
        return proof;
    }

    public void setProof(Blob proof) {
        this.proof = proof;
    }
}
