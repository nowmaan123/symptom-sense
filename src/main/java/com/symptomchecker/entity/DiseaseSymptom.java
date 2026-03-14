package com.symptomchecker.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "disease_symptoms",
       uniqueConstraints = @UniqueConstraint(columnNames = {"disease_id", "symptom_id"}))
public class DiseaseSymptom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "disease_id", nullable = false)
    private Disease disease;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "symptom_id", nullable = false)
    private Symptom symptom;

    // Weight 0.0–1.0: how strongly this symptom indicates the disease
    @Column(nullable = false)
    private Double weight;

    // ─── Constructors ─────────────────────────────────────────
    public DiseaseSymptom() {}

    // ─── Getters & Setters ────────────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public Disease getDisease() { return disease; }
    public void setDisease(Disease disease) { this.disease = disease; }

    public Symptom getSymptom() { return symptom; }
    public void setSymptom(Symptom symptom) { this.symptom = symptom; }

    public Double getWeight() { return weight; }
    public void setWeight(Double weight) { this.weight = weight; }
}
