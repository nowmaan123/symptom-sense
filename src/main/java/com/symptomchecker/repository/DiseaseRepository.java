package com.symptomchecker.repository;

import com.symptomchecker.entity.Disease;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface DiseaseRepository extends JpaRepository<Disease, Long> {

    List<Disease> findBySeverityIgnoreCase(String severity);

    List<Disease> findBySpecialistIgnoreCase(String specialist);

    List<Disease> findByRequiresEmergencyTrue();

    // Returns every disease that has at least one symptom whose name
    // (case-insensitive) appears in the given list — used by SymptomAnalyzer
    @Query("""
            SELECT DISTINCT d FROM Disease d
            JOIN d.diseaseSymptoms ds
            JOIN ds.symptom s
            WHERE LOWER(s.name) IN :symptomNames
           """)
    List<Disease> findCandidatesBySymptomNames(@Param("symptomNames") List<String> symptomNames);
}
