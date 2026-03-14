package com.symptomchecker.controller;

import com.symptomchecker.entity.CheckHistory;
import com.symptomchecker.entity.Disease;
import com.symptomchecker.entity.DiseaseSymptom;
import com.symptomchecker.entity.User;
import com.symptomchecker.repository.DiseaseRepository;
import com.symptomchecker.repository.DiseaseSymptomRepository;
import com.symptomchecker.service.HistoryService;
import com.symptomchecker.service.UserService;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
@PreAuthorize("hasRole('ADMIN')")
public class AdminController {

    private final UserService userService;
    private final HistoryService historyService;
    private final DiseaseRepository diseaseRepository;
    private final DiseaseSymptomRepository diseaseSymptomRepository;

    public AdminController(UserService userService,
                           HistoryService historyService,
                           DiseaseRepository diseaseRepository,
                           DiseaseSymptomRepository diseaseSymptomRepository) {

        this.userService = userService;
        this.historyService = historyService;
        this.diseaseRepository = diseaseRepository;
        this.diseaseSymptomRepository = diseaseSymptomRepository;
    }

    // ─────────────────────────────
    // User Management
    // ─────────────────────────────

    @GetMapping("/users")
    public ResponseEntity<List<User>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/users/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        return ResponseEntity.ok(userService.getUserById(id));
    }

    @DeleteMapping("/users/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long id) {

        userService.deleteUser(id);

        return ResponseEntity.noContent().build();
    }

    // ─────────────────────────────
    // History Management
    // ─────────────────────────────

    @GetMapping("/history")
    public ResponseEntity<List<CheckHistory>> getAllHistory() {

        return ResponseEntity.ok(historyService.getAllHistory());
    }

    @GetMapping("/history/user/{userId}")
    public ResponseEntity<List<CheckHistory>> getUserHistory(@PathVariable Long userId) {

        return ResponseEntity.ok(historyService.getUserHistory(userId));
    }

    @DeleteMapping("/history/{id}")
    public ResponseEntity<Void> deleteHistory(@PathVariable Long id) {

        historyService.deleteHistory(id);

        return ResponseEntity.noContent().build();
    }

    // ─────────────────────────────
    // Disease Management
    // ─────────────────────────────

    @GetMapping("/diseases")
    public ResponseEntity<List<Disease>> getAllDiseases() {

        return ResponseEntity.ok(diseaseRepository.findAll());
    }

    @PostMapping("/diseases")
    public ResponseEntity<Disease> createDisease(@RequestBody Disease disease) {

        Disease savedDisease = diseaseRepository.save(disease);

        return ResponseEntity.status(HttpStatus.CREATED).body(savedDisease);
    }

    @PutMapping("/diseases/{id}")
    public ResponseEntity<Disease> updateDisease(@PathVariable Long id,
                                                 @RequestBody Disease updatedDisease) {

        Disease disease = diseaseRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Disease not found with id: " + id));

        disease.setName(updatedDisease.getName());
        disease.setDescription(updatedDisease.getDescription());
        disease.setTreatment(updatedDisease.getTreatment());
        disease.setSpecialist(updatedDisease.getSpecialist());
        disease.setSeverity(updatedDisease.getSeverity());
        disease.setRequiresEmergency(updatedDisease.isRequiresEmergency());

        Disease saved = diseaseRepository.save(disease);

        return ResponseEntity.ok(saved);
    }

    @DeleteMapping("/diseases/{id}")
    public ResponseEntity<Void> deleteDisease(@PathVariable Long id) {

        diseaseRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }

    // ─────────────────────────────
    // Disease-Symptom Mapping
    // ─────────────────────────────

    @GetMapping("/disease-symptoms")
    public ResponseEntity<List<DiseaseSymptom>> getAllMappings() {

        return ResponseEntity.ok(diseaseSymptomRepository.findAll());
    }

    @PostMapping("/disease-symptoms")
    public ResponseEntity<DiseaseSymptom> createMapping(
            @RequestBody DiseaseSymptom mapping) {

        DiseaseSymptom saved = diseaseSymptomRepository.save(mapping);

        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @DeleteMapping("/disease-symptoms/{id}")
    public ResponseEntity<Void> deleteMapping(@PathVariable Long id) {

        diseaseSymptomRepository.deleteById(id);

        return ResponseEntity.noContent().build();
    }
}