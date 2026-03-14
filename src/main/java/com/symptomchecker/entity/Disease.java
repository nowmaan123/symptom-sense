package com.symptomchecker.entity;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "diseases")
public class Disease {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false, length = 120)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(columnDefinition = "TEXT")
    private String treatment;

    // CONSISTENT: named "specialist" everywhere (SymptomAnalyzer, AdminController, PredictionResponse)
    @Column(length = 80)
    private String specialist;

    // String field: LOW | MEDIUM | HIGH | CRITICAL
    // FIX: kept as String — SymptomAnalyzer uses getSeverity() directly (no .name() call needed)
    @Column(length = 20)
    private String severity;

    @Column(name = "requires_emergency")
    private Boolean requiresEmergency = false;

    @OneToMany(mappedBy = "disease", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<DiseaseSymptom> diseaseSymptoms;

    // ─── Constructors ─────────────────────────────────────────
    public Disease() {}

    // ─── Getters & Setters ────────────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public String getTreatment() { return treatment; }
    public void setTreatment(String treatment) { this.treatment = treatment; }

    public String getSpecialist() { return specialist; }
    public void setSpecialist(String specialist) { this.specialist = specialist; }

    public String getSeverity() { return severity; }
    public void setSeverity(String severity) { this.severity = severity; }

    public boolean isRequiresEmergency() { return requiresEmergency; }
    public void setRequiresEmergency(boolean requiresEmergency) { this.requiresEmergency = requiresEmergency; }

    public List<DiseaseSymptom> getDiseaseSymptoms() { return diseaseSymptoms; }
    public void setDiseaseSymptoms(List<DiseaseSymptom> diseaseSymptoms) { this.diseaseSymptoms = diseaseSymptoms; }
}
