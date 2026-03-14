package com.symptomchecker.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "check_history")
public class CheckHistory {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    // Comma-separated / JSON list of symptoms submitted
    @Column(name = "symptoms_input", columnDefinition = "TEXT", nullable = false)
    private String symptomsInput;

    // Disease name with highest confidence
    @Column(name = "top_prediction", length = 120, nullable = false)
    private String topPrediction;

    // Confidence of top prediction (0.0–100.0)
    @Column(name = "confidence_score")
    private Double confidenceScore;

    // FIXED: field is fullResult — PredictionService calls setFullResult()
    @Column(name = "full_result", columnDefinition = "TEXT")
    private String fullResult;

    @Column(name = "checked_at", updatable = false)
    private LocalDateTime checkedAt;

    @PrePersist
    protected void onCreate() {
        checkedAt = LocalDateTime.now();
    }

    // ─── Constructors ─────────────────────────────────────────
    public CheckHistory() {}

    // ─── Getters & Setters ────────────────────────────────────
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public User getUser() { return user; }
    public void setUser(User user) { this.user = user; }

    public String getSymptomsInput() { return symptomsInput; }
    public void setSymptomsInput(String symptomsInput) { this.symptomsInput = symptomsInput; }

    public String getTopPrediction() { return topPrediction; }
    public void setTopPrediction(String topPrediction) { this.topPrediction = topPrediction; }

    public Double getConfidenceScore() { return confidenceScore; }
    public void setConfidenceScore(Double confidenceScore) { this.confidenceScore = confidenceScore; }

    public String getFullResult() { return fullResult; }
    public void setFullResult(String fullResult) { this.fullResult = fullResult; }

    public LocalDateTime getCheckedAt() { return checkedAt; }
}
