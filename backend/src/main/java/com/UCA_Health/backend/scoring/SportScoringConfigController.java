package com.UCA_Health.backend.scoring;

import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/scoring-config")
public class SportScoringConfigController {

    private final SportScoringConfigService service;

    public SportScoringConfigController(SportScoringConfigService service) {
        this.service = service;
    }

    @GetMapping
    public List<SportScoringConfigResponse> listAll() {
        return service.listAll().stream()
                .map(SportScoringConfigResponse::fromEntity)
                .toList();
    }

    @GetMapping("/{sportId}")
    public SportScoringConfigResponse getOne(@PathVariable Long sportId) {
        return SportScoringConfigResponse.fromEntity(service.getBySportId(sportId));
    }
    
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SportScoringConfigResponse create(@Valid @RequestBody SportScoringConfigRequest req) {
        return SportScoringConfigResponse.fromEntity(service.create(req));
    }

    @PutMapping("/{sportId}")
    public SportScoringConfigResponse update(@PathVariable Long sportId, @Valid @RequestBody SportScoringConfigRequest req) {
        return SportScoringConfigResponse.fromEntity(service.update(sportId, req));
    }
    
    @DeleteMapping("/{sportId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long sportId) {
        service.delete(sportId);
    }
}
