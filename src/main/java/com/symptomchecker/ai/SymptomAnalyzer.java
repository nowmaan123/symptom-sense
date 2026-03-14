package com.symptomchecker.ai;

import com.symptomchecker.dto.PredictionResponse;
import com.symptomchecker.entity.Disease;
import com.symptomchecker.entity.DiseaseSymptom;
import com.symptomchecker.entity.Symptom;
import com.symptomchecker.repository.DiseaseRepository;
import com.symptomchecker.repository.DiseaseSymptomRepository;
import com.symptomchecker.repository.SymptomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class SymptomAnalyzer {

    private static final Logger log = LoggerFactory.getLogger(SymptomAnalyzer.class);

    private final SymptomRepository symptomRepository;
    private final DiseaseRepository diseaseRepository;
    private final DiseaseSymptomRepository diseaseSymptomRepository;

    @Autowired
    public SymptomAnalyzer(SymptomRepository symptomRepository,
                           DiseaseRepository diseaseRepository,
                           DiseaseSymptomRepository diseaseSymptomRepository) {
        this.symptomRepository = symptomRepository;
        this.diseaseRepository = diseaseRepository;
        this.diseaseSymptomRepository = diseaseSymptomRepository;
    }

    // ─── Main analyze method ──────────────────────────────────
    public PredictionResponse analyze(List<String> inputSymptoms) {
        log.info("Analyzing {} symptoms: {}", inputSymptoms.size(), inputSymptoms);

        // Step 1: Normalize input to lowercase
        List<String> normalizedInput = inputSymptoms.stream()
                .map(String::toLowerCase)
                .map(String::trim)
                .collect(Collectors.toList());

        // Step 2: Resolve input against DB — findAllByNameInIgnoreCase added to repo
        List<Symptom> matchedSymptoms = symptomRepository.findAllByNameInIgnoreCase(normalizedInput);

        List<String> matchedNames = matchedSymptoms.stream()
                .map(Symptom::getName)
                .collect(Collectors.toList());

        List<String> unknownSymptoms = normalizedInput.stream()
                .filter(s -> matchedNames.stream().noneMatch(m -> m.equalsIgnoreCase(s)))
                .collect(Collectors.toList());

        log.debug("Matched: {}, Unknown: {}", matchedNames, unknownSymptoms);

        // Step 3: Return early if nothing matched
        if (matchedSymptoms.isEmpty()) {
            PredictionResponse emptyResponse = new PredictionResponse();
            emptyResponse.setPredictions(Collections.emptyList());
            emptyResponse.setMatchedSymptoms(matchedNames);
            emptyResponse.setUnknownSymptoms(unknownSymptoms);
            emptyResponse.setAdvice("No known symptoms found. Please consult a doctor.");
            return emptyResponse;
        }

        // Step 4: Get symptom IDs and fetch all relevant disease-symptom mappings
        // findBySymptomIdIn added to DiseaseSymptomRepository
        List<Long> symptomIds = matchedSymptoms.stream()
                .map(Symptom::getId)
                .collect(Collectors.toList());

        List<DiseaseSymptom> relevantMappings = diseaseSymptomRepository.findBySymptomIdIn(symptomIds);

        // Step 5: Score each disease by summing weights of matched symptoms
        Map<Long, Double> diseaseScores = new HashMap<>();
        Map<Long, List<String>> diseaseMatchingSymptoms = new HashMap<>();

        for (DiseaseSymptom ds : relevantMappings) {
            Long diseaseId = ds.getDisease().getId();
            diseaseScores.merge(diseaseId, ds.getWeight(), Double::sum);
            diseaseMatchingSymptoms
                    .computeIfAbsent(diseaseId, k -> new ArrayList<>())
                    .add(ds.getSymptom().getName());
        }

        // Step 6: Convert to confidence percentage, filter and build predictions
        double maxPossibleScore = matchedSymptoms.size();
        List<PredictionResponse.DiseasePrediction> predictions = new ArrayList<>();

        for (Map.Entry<Long, Double> entry : diseaseScores.entrySet()) {
            Long diseaseId = entry.getKey();
            double confidence = Math.min((entry.getValue() / maxPossibleScore) * 100.0, 100.0);

            if (confidence < 10.0) continue;

            Disease disease = diseaseRepository.findById(diseaseId).orElse(null);
            if (disease == null) continue;

            PredictionResponse.DiseasePrediction prediction = new PredictionResponse.DiseasePrediction();
            prediction.setDiseaseName(disease.getName());
            prediction.setDescription(disease.getDescription());
            prediction.setConfidenceScore(Math.round(confidence * 10.0) / 10.0);
            // FIXED: getSeverity() returns String — removed incorrect .name() call
            prediction.setSeverity(disease.getSeverity() != null ? disease.getSeverity() : "UNKNOWN");
            prediction.setTreatment(disease.getTreatment());
            prediction.setSpecialist(disease.getSpecialist());
            prediction.setMatchingSymptoms(
                    diseaseMatchingSymptoms.getOrDefault(diseaseId, Collections.emptyList())
            );

            predictions.add(prediction);
        }

        // Step 7: Sort descending, take top 5
        predictions.sort(
                Comparator.comparingDouble(PredictionResponse.DiseasePrediction::getConfidenceScore).reversed()
        );
        List<PredictionResponse.DiseasePrediction> top5 = predictions.stream()
                .limit(5)
                .collect(Collectors.toList());

        // Step 8: Build final response
        PredictionResponse response = new PredictionResponse();
        response.setPredictions(top5);
        response.setMatchedSymptoms(matchedNames);
        response.setUnknownSymptoms(unknownSymptoms);
        response.setAdvice(buildAdvice(top5));
        return response;
    }

    // ─── Advice generator ─────────────────────────────────────
    private String buildAdvice(List<PredictionResponse.DiseasePrediction> predictions) {
        if (predictions.isEmpty()) {
            return "No significant disease matches found. Monitor your symptoms and consult a healthcare provider.";
        }
        PredictionResponse.DiseasePrediction top = predictions.get(0);
        String severity = top.getSeverity();

        if ("CRITICAL".equals(severity) || "SEVERE".equals(severity)) {
            return "⚠️ High severity condition detected. Please seek immediate medical attention.";
        } else if ("MODERATE".equals(severity)) {
            return "Please schedule an appointment with a " + top.getSpecialist() + " soon.";
        } else {
            return "Symptoms may indicate a mild condition. Monitor your health and consult a doctor if symptoms persist.";
        }
    }
}
