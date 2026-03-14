package com.symptomchecker.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "symptoms")
public class Symptom {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 100)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "body_part", length = 60)
    private String bodyPart;

    @Column(length = 20)
    private String severity;  // MILD | MODERATE | SEVERE

    @OneToMany(mappedBy = "symptom", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DiseaseSymptom> diseaseSymptoms;

    // ─── Constructors ─────────────────────────────────────────
    public Symptom() {}

    // ─── Getters & Setters ────────────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getBodyPart() { return bodyPart; }
    public void setBodyPart(String bodyPart) { this.bodyPart = bodyPart; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public List<DiseaseSymptom> getDiseaseSymptoms() { return diseaseSymptoms; }
    public void setDiseaseSymptoms(List<DiseaseSymptom> diseaseSymptoms) { this.diseaseSymptoms = diseaseSymptoms; }
}
