package com.UCA_Health.backend.trainingsession;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import com.UCA_Health.backend.auth.UserPrincipal;

import jakarta.persistence.EntityNotFoundException;

@Service
public class TrainingSessionService {

    private final TrainingSessionRepository repository;

    public TrainingSessionService(TrainingSessionRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public TrainingSession getByIdOrThrow(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("TrainingSession no encontrada: id=" + id));
    }

    @Transactional(readOnly = true)
    public List<TrainingSession> listByUser(Long userId, Instant from, Instant to) {
        if (from != null && to != null) {
            return repository.findByUserIdAndStartTimeBetweenOrderByStartTimeDesc(userId, from, to);
        }
        return repository.findByUserIdOrderByStartTimeDesc(userId);
    }

    @Transactional
    public void deleteById(Long id) {
        if (!repository.existsById(id)) {
            throw new EntityNotFoundException("TrainingSession no encontrada: id=" + id);
        }
        repository.deleteById(id);
    }
    
    public void assertCanAccess(UserPrincipal principal, TrainingSession session) {
    	  if (principal == null) {
    	    throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Unauthorized");
    	  }

    	  boolean isAdmin = principal.getAuthorities().stream()
    	    .anyMatch(a -> a.getAuthority().equals("ROLE_ADMIN"));

    	  Long ownerId = session.getUserId();

    	  if (!isAdmin && !principal.getId().equals(ownerId)) {
    	    throw new ResponseStatusException(HttpStatus.FORBIDDEN, "Forbidden");
    	  }
    	}
}
