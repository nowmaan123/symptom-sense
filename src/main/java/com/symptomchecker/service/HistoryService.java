package com.symptomchecker.service;

import com.symptomchecker.entity.CheckHistory;
import com.symptomchecker.repository.CheckHistoryRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class HistoryService {

    private static final Logger log = LoggerFactory.getLogger(HistoryService.class);

    private final CheckHistoryRepository checkHistoryRepository;

    @Autowired
    public HistoryService(CheckHistoryRepository checkHistoryRepository) {
        this.checkHistoryRepository = checkHistoryRepository;
    }

    public List<CheckHistory> getUserHistory(Long userId) {
        return checkHistoryRepository.findByUserIdOrderByCheckedAtDesc(userId);
    }

    // findTop10ByUserIdOrderByCheckedAtDesc added to CheckHistoryRepository
    public List<CheckHistory> getRecentUserHistory(Long userId) {
        return checkHistoryRepository.findTop10ByUserIdOrderByCheckedAtDesc(userId);
    }

    public List<CheckHistory> getAllHistory() {
        return checkHistoryRepository.findAll();
    }

    public void deleteHistory(Long historyId) {
        if (!checkHistoryRepository.existsById(historyId)) {
            throw new RuntimeException("History record not found with id: " + historyId);
        }
        checkHistoryRepository.deleteById(historyId);
        log.info("History deleted: {}", historyId);
    }
}
