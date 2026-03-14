package com.symptomchecker.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.symptomchecker.ai.SymptomAnalyzer;
import com.symptomchecker.dto.PredictionResponse;
import com.symptomchecker.dto.SymptomRequest;
import com.symptomchecker.entity.CheckHistory;
import com.symptomchecker.entity.User;
import com.symptomchecker.repository.CheckHistoryRepository;
import com.symptomchecker.repository.UserRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PredictionService {

    private static final Logger log = LoggerFactory.getLogger(PredictionService.class);

    private final SymptomAnalyzer symptomAnalyzer;
    private final CheckHistoryRepository checkHistoryRepository;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Autowired
    public PredictionService(SymptomAnalyzer symptomAnalyzer,
                             CheckHistoryRepository checkHistoryRepository,
                             UserRepository userRepository,
                             ObjectMapper objectMapper) {
        this.symptomAnalyzer = symptomAnalyzer;
        this.checkHistoryRepository = checkHistoryRepository;
        this.userRepository = userRepository;
        this.objectMapper = objectMapper;
    }

    public PredictionResponse predict(SymptomRequest request, String userEmail) {
        log.info("Prediction requested by '{}' for symptoms: {}", userEmail, request.getSymptoms());

        PredictionResponse response = symptomAnalyzer.analyze(request.getSymptoms());

        // Save history only for authenticated users
        if (userEmail != null) {
            saveHistory(request, response, userEmail);
        }

        return response;
    }

    private void saveHistory(SymptomRequest request, PredictionResponse response, String email) {
        try {
            User user = userRepository.findByEmail(email).orElse(null);
            if (user == null) {
                log.warn("Could not find user with email: {}", email);
                return;
            }

            // Resolve top prediction name and confidence
            String topPrediction = "Unknown";
            Double confidenceScore = 0.0;

            if (response.getPredictions() != null && !response.getPredictions().isEmpty()) {
                PredictionResponse.DiseasePrediction top = response.getPredictions().get(0);
                topPrediction = top.getDiseaseName();
                confidenceScore = top.getConfidenceScore();
            }

            CheckHistory history = new CheckHistory();
            history.setUser(user);
            history.setSymptomsInput(objectMapper.writeValueAsString(request.getSymptoms()));
            // FIXED: was setTopPrediction + setConfidenceScore never set → DB constraint violation
            history.setTopPrediction(topPrediction);
            history.setConfidenceScore(confidenceScore);
            // FIXED: was setPredictionResult — field is fullResult in CheckHistory entity
            history.setFullResult(objectMapper.writeValueAsString(response));

            checkHistoryRepository.save(history);
            log.debug("History saved for user: {}", email);

        } catch (JsonProcessingException e) {
            log.error("Failed to serialize prediction for history: {}", e.getMessage());
        }
    }
}
