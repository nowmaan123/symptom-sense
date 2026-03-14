package com.symptomchecker.repository;

import com.symptomchecker.entity.DiseaseSymptom;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DiseaseSymptomRepository extends JpaRepository<DiseaseSymptom, Long> {

    List<DiseaseSymptom> findByDiseaseId(Long diseaseId);

    List<DiseaseSymptom> findBySymptomId(Long symptomId);

    // ADDED: used by SymptomAnalyzer to fetch all mappings for matched symptom IDs
    List<DiseaseSymptom> findBySymptomIdIn(List<Long> symptomIds);

    Optional<DiseaseSymptom> findByDiseaseIdAndSymptomId(Long diseaseId, Long symptomId);

    boolean existsByDiseaseIdAndSymptomId(Long diseaseId, Long symptomId);
}
