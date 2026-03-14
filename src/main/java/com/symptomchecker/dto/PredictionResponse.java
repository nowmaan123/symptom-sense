package com.symptomchecker.dto;

import java.util.List;

public class PredictionResponse {

    private List<DiseasePrediction> predictions;
    private List<String> matchedSymptoms;
    private List<String> unknownSymptoms;
    private String advice;

    // ─── Constructors ─────────────────────────────────────────
    public PredictionResponse() {}

    public PredictionResponse(List<DiseasePrediction> predictions,
                               List<String> matchedSymptoms,
                               List<String> unknownSymptoms,
                               String advice) {
        this.predictions = predictions;
        this.matchedSymptoms = matchedSymptoms;
        this.unknownSymptoms = unknownSymptoms;
        this.advice = advice;
    }

    // ─── Getters & Setters ────────────────────────────────────
    public List<DiseasePrediction> getPredictions() { return predictions; }
    public void setPredictions(List<DiseasePrediction> predictions) { this.predictions = predictions; }

    public List<String> getMatchedSymptoms() { return matchedSymptoms; }
    public void setMatchedSymptoms(List<String> matchedSymptoms) { this.matchedSymptoms = matchedSymptoms; }

    public List<String> getUnknownSymptoms() { return unknownSymptoms; }
    public void setUnknownSymptoms(List<String> unknownSymptoms) { this.unknownSymptoms = unknownSymptoms; }

    public String getAdvice() { return advice; }
    public void setAdvice(String advice) { this.advice = advice; }

    // ═══════════════════════════════════════════════════════════
    //  Inner class — DiseasePrediction
    // ═══════════════════════════════════════════════════════════
    public static class DiseasePrediction {

        private String diseaseName;
        private String description;
        private double confidenceScore;
        private String severity;
        private String treatment;
        private String specialist;          // CONSISTENT with Disease entity
        private List<String> matchingSymptoms;

        // ─── Constructors ─────────────────────────────────────
        public DiseasePrediction() {}

        // ─── Getters & Setters ────────────────────────────────
        public String getDiseaseName() { return diseaseName; }
        public void setDiseaseName(String diseaseName) { this.diseaseName = diseaseName; }

        public String getDescription() { return description; }
        public void setDescription(String description) { this.description = description; }

        public double getConfidenceScore() { return confidenceScore; }
        public void setConfidenceScore(double confidenceScore) { this.confidenceScore = confidenceScore; }

        public String getSeverity() { return severity; }
        public void setSeverity(String severity) { this.severity = severity; }

        public String getTreatment() { return treatment; }
        public void setTreatment(String treatment) { this.treatment = treatment; }

        public String getSpecialist() { return specialist; }
        public void setSpecialist(String specialist) { this.specialist = specialist; }

        public List<String> getMatchingSymptoms() { return matchingSymptoms; }
        public void setMatchingSymptoms(List<String> matchingSymptoms) { this.matchingSymptoms = matchingSymptoms; }

        @Override
        public String toString() {
            return "DiseasePrediction{diseaseName='" + diseaseName +
                    "', confidenceScore=" + confidenceScore +
                    ", severity='" + severity + "'}";
        }
    }
    // REMOVED: dead auto-generated builder() stub that returned null
}
