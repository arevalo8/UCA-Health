package com.UCA_Health.backend.trainingsession;

import java.time.Instant;
import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import com.UCA_Health.backend.auth.UserPrincipal;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/api/training-sessions")
public class TrainingSessionController {

  private final TrainingSessionImportService importService;
  private final TrainingSessionService sessionService;

  public TrainingSessionController(
    TrainingSessionImportService importService,
    TrainingSessionService sessionService
  ) {
    this.importService = importService;
    this.sessionService = sessionService;
  }

  @PostMapping("/import")
  @ResponseStatus(HttpStatus.OK)
  public TrainingSessionResponse importTrainingSession(
    @AuthenticationPrincipal UserPrincipal principal,
    @Valid @RequestBody ImportTrainingSessionRequest request
  ) {
    TrainingSession saved = importService.importSession(principal.getId(), request);
    return TrainingSessionResponse.fromEntity(saved);
  }

  @GetMapping
  public List<TrainingSessionResponse> list(
    @AuthenticationPrincipal UserPrincipal principal,
    @RequestParam(required = false) Instant from,
    @RequestParam(required = false) Instant to
  ) {
    return sessionService.listByUser(principal.getId(), from, to)
      .stream()
      .map(TrainingSessionResponse::fromEntity)
      .toList();
  }

  @GetMapping("/{id}")
  public TrainingSessionResponse getById(
    @AuthenticationPrincipal UserPrincipal principal,
    @PathVariable Long id
  ) {
    TrainingSession s = sessionService.getByIdOrThrow(id);

    
    sessionService.assertCanAccess(principal, s);

    return TrainingSessionResponse.fromEntity(s);
  }

  @DeleteMapping("/{id}")
  @ResponseStatus(HttpStatus.NO_CONTENT)
  public void delete(
    @AuthenticationPrincipal UserPrincipal principal,
    @PathVariable Long id
  ) {
    TrainingSession s = sessionService.getByIdOrThrow(id);
    sessionService.assertCanAccess(principal, s);
    sessionService.deleteById(id);
  }
}
