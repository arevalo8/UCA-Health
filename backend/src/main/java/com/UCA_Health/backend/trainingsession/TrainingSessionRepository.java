package com.UCA_Health.backend.trainingsession;

import java.time.Instant;
import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

public interface TrainingSessionRepository extends JpaRepository<TrainingSession, Long> {

    Optional<TrainingSession> findByDeviceSessionId(String deviceSessionId);

    boolean existsByDeviceSessionId(String deviceSessionId);
    
    List<TrainingSession> findByUserIdOrderByStartTimeDesc(Long userId);

    List<TrainingSession> findByUserIdAndStartTimeBetweenOrderByStartTimeDesc(
            Long userId, Instant from, Instant to
    );
}
