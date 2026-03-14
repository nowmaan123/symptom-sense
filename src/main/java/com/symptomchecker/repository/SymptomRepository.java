package com.symptomchecker.repository;

import com.symptomchecker.entity.Symptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface SymptomRepository extends JpaRepository<Symptom, Long> {

    Optional<Symptom> findByNameIgnoreCase(String name);

    // ADDED: used by SymptomAnalyzer — resolves list of symptom names from DB
    List<Symptom> findAllByNameInIgnoreCase(List<String> names);

    // ADDED: used by SymptomService.searchSymptoms()
    List<Symptom> findByNameContainingIgnoreCase(String keyword);

    List<Symptom> findByBodyPartIgnoreCase(String bodyPart);
}
