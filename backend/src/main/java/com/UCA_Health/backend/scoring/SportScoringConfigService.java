package com.UCA_Health.backend.scoring;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

@Service
public class SportScoringConfigService {

    private final SportScoringConfigRepository repository;

    public SportScoringConfigService(SportScoringConfigRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true)
    public List<SportScoringConfig> listAll() {
        return repository.findAll();
    }

    @Transactional(readOnly = true)
    public SportScoringConfig getBySportId(Long sportId) {
        return repository.findById(sportId)
                .orElseThrow(() -> new EntityNotFoundException("No existe sport_scoring_config para sport_id=" + sportId));
    }

    @Transactional
    public SportScoringConfig create(SportScoringConfigRequest req) {
        if (req.getSportId() == null) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "sportId es obligatorio en create");
        }
        if (repository.existsById(req.getSportId())) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Ya existe sport_scoring_config para sport_id=" + req.getSportId());
        }

        validateWeightsAndRefs(req);

        SportScoringConfig c = new SportScoringConfig();
        apply(c, req, req.getSportId());

        try {
            return repository.save(c);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Violación de integridad al crear config", e);
        }
    }

    @Transactional
    public SportScoringConfig update(Long sportId, SportScoringConfigRequest req) {
        SportScoringConfig c = getBySportId(sportId);

        validateWeightsAndRefs(req);

        apply(c, req, sportId);

        try {
            return repository.save(c);
        } catch (DataIntegrityViolationException e) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Violación de integridad al actualizar config", e);
        }
    }
    
    @Transactional
    public void delete(Long sportId) {
        if (!repository.existsById(sportId)) {
            throw new EntityNotFoundException("No existe sport_scoring_config para sport_id=" + sportId);
        }
        repository.deleteById(sportId);
    }

    private void apply(SportScoringConfig c, SportScoringConfigRequest req, Long sportId) {
        // sportId es PK (no debe cambiar)
        c.setSportId(sportId);

        c.setBasePoints(req.getBasePoints());

        c.setRefDistanceKm(req.getRefDistanceKm());
        c.setRefDurationMin(req.getRefDurationMin());
        c.setRefHrAvg(req.getRefHrAvg());
        c.setRefElevationGainM(req.getRefElevationGainM());

        c.setWDistance(req.getWDistance());
        c.setWDuration(req.getWDuration());
        c.setWHrAvg(req.getWHrAvg());
        c.setWElevationGain(req.getWElevationGain());
    }

    /**
     * Reglas:
     * - pesos no null
     * - pesos >= 0
     * - suma ~ 1 (tolerancia)
     * - si un peso > 0 => su ref debe existir y ser > 0
     */
    private void validateWeightsAndRefs(SportScoringConfigRequest req) {
        BigDecimal wDist = nz(req.getWDistance());
        BigDecimal wDur  = nz(req.getWDuration());
        BigDecimal wHr   = nz(req.getWHrAvg());
        BigDecimal wElev = nz(req.getWElevationGain());

        if (wDist.signum() < 0 || wDur.signum() < 0 || wHr.signum() < 0 || wElev.signum() < 0) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Los pesos no pueden ser negativos");
        }

        BigDecimal sum = wDist.add(wDur).add(wHr).add(wElev);
        // tolerancia +/- 0.001 (como constraint)
        if (sum.compareTo(new BigDecimal("0.999")) < 0 || sum.compareTo(new BigDecimal("1.001")) > 0) {
            throw new ResponseStatusException(
                    HttpStatus.BAD_REQUEST,
                    "La suma de pesos debe ser 1.0 (tolerancia 0.001). Suma actual=" + sum.setScale(3, RoundingMode.HALF_UP)
            );
        }

        if (wDist.signum() > 0 && !isPositive(req.getRefDistanceKm())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "refDistanceKm debe ser > 0 si wDistance > 0");
        }
        if (wDur.signum() > 0 && (req.getRefDurationMin() == null || req.getRefDurationMin() <= 0)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "refDurationMin debe ser > 0 si wDuration > 0");
        }
        if (wHr.signum() > 0 && (req.getRefHrAvg() == null || req.getRefHrAvg() <= 0)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "refHrAvg debe ser > 0 si wHrAvg > 0");
        }
        if (wElev.signum() > 0 && !isPositive(req.getRefElevationGainM())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "refElevationGainM debe ser > 0 si wElevationGain > 0");
        }
    }

    private BigDecimal nz(BigDecimal x) {
        return x == null ? BigDecimal.ZERO : x;
    }

    private boolean isPositive(BigDecimal x) {
        return x != null && x.compareTo(BigDecimal.ZERO) > 0;
    }
}
