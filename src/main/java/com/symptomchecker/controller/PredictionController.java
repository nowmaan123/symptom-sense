package com.symptomchecker.controller;

import com.symptomchecker.dto.PredictionResponse;
import com.symptomchecker.dto.SymptomRequest;
import com.symptomchecker.service.PredictionService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/predictions")
@CrossOrigin(origins = "*")
public class PredictionController {

    private final PredictionService predictionService;

    // FIXED: was new PredictionService(null, null, null, null) — manual instantiation
    // with nulls causes NullPointerException on every call. Use Spring DI.
    @Autowired
    public PredictionController(PredictionService predictionService) {
        this.predictionService = predictionService;
    }

    /**
     * POST /api/predictions/check
     * Public endpoint — works for guests too.
     * Saves history only when the user is authenticated.
     */
    @PostMapping("/check")
    public ResponseEntity<PredictionResponse> checkSymptoms(
            @Valid @RequestBody SymptomRequest request,
            // FIXED: was @AuthenticationPrincipal String — principal is UserDetails,
            // extract email via userDetails.getUsername() which stores email
            @AuthenticationPrincipal UserDetails userDetails) {

        String userEmail = (userDetails != null) ? userDetails.getUsername() : null;
        PredictionResponse response = predictionService.predict(request, userEmail);
        return ResponseEntity.ok(response);
    }
}
