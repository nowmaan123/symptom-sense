package com.symptomchecker.repository;

import com.symptomchecker.entity.CheckHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CheckHistoryRepository extends JpaRepository<CheckHistory, Long> {

    List<CheckHistory> findByUserIdOrderByCheckedAtDesc(Long userId);

    // ADDED: used by HistoryService.getRecentUserHistory()
    List<CheckHistory> findTop10ByUserIdOrderByCheckedAtDesc(Long userId);

    long countByUserId(Long userId);

    void deleteByUserId(Long userId);
}
