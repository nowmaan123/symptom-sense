package com.symptomchecker.service;

import com.symptomchecker.entity.Symptom;
import com.symptomchecker.repository.SymptomRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SymptomService {

    private static final Logger log = LoggerFactory.getLogger(SymptomService.class);

    private final SymptomRepository symptomRepository;

    @Autowired
    public SymptomService(SymptomRepository symptomRepository) {
        this.symptomRepository = symptomRepository;
    }

    public List<Symptom> getAllSymptoms() {
        return symptomRepository.findAll();
    }

    public List<Symptom> searchSymptoms(String keyword) {
        // findByNameContainingIgnoreCase added to SymptomRepository
        return symptomRepository.findByNameContainingIgnoreCase(keyword);
    }

    public Symptom getSymptomById(Long id) {
        return symptomRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Symptom not found with id: " + id));
    }

    public Symptom createSymptom(Symptom symptom) {
        if (symptomRepository.findByNameIgnoreCase(symptom.getName()).isPresent()) {
            throw new RuntimeException("Symptom already exists: " + symptom.getName());
        }
        Symptom saved = symptomRepository.save(symptom);
        log.info("Symptom created: {}", saved.getName());
        return saved;
    }

    public Symptom updateSymptom(Long id, Symptom updated) {
        Symptom existing = getSymptomById(id);
        existing.setName(updated.getName());
        existing.setDescription(updated.getDescription());
        existing.setBodyPart(updated.getBodyPart());
        existing.setSeverity(updated.getSeverity());
        return symptomRepository.save(existing);
    }

    public void deleteSymptom(Long id) {
        if (!symptomRepository.existsById(id)) {
            throw new RuntimeException("Symptom not found with id: " + id);
        }
        symptomRepository.deleteById(id);
        log.info("Symptom deleted: {}", id);
    }
}
