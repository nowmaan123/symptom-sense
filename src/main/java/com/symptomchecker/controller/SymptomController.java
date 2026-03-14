package com.symptomchecker.controller;

import com.symptomchecker.entity.Symptom;
import com.symptomchecker.service.SymptomService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/symptoms")
public class SymptomController {

    private final SymptomService symptomService;

    // FIXED: was new SymptomService(null) — manual instantiation causes NPE.
    // Use Spring DI via constructor injection.
    @Autowired
    public SymptomController(SymptomService symptomService) {
        this.symptomService = symptomService;
    }

    // ── Public endpoints ──────────────────────────────────────

    @GetMapping
    public ResponseEntity<List<Symptom>> getAllSymptoms() {
        return ResponseEntity.ok(symptomService.getAllSymptoms());
    }

    @GetMapping("/search")
    public ResponseEntity<List<Symptom>> searchSymptoms(@RequestParam String keyword) {
        return ResponseEntity.ok(symptomService.searchSymptoms(keyword));
    }

    @GetMapping("/{id}")
    public ResponseEntity<Symptom> getSymptomById(@PathVariable Long id) {
        return ResponseEntity.ok(symptomService.getSymptomById(id));
    }

    // ── Admin-only endpoints ──────────────────────────────────

    @PostMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Symptom> createSymptom(@Valid @RequestBody Symptom symptom) {
        return ResponseEntity.status(HttpStatus.CREATED)
                .body(symptomService.createSymptom(symptom));
    }

    @PutMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Symptom> updateSymptom(@PathVariable Long id,
                                                  @Valid @RequestBody Symptom symptom) {
        return ResponseEntity.ok(symptomService.updateSymptom(id, symptom));
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Void> deleteSymptom(@PathVariable Long id) {
        symptomService.deleteSymptom(id);
        return ResponseEntity.noContent().build();
    }
}
