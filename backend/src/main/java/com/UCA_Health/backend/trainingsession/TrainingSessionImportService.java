package com.UCA_Health.backend.trainingsession;

import com.UCA_Health.backend.scoring.SportScoringConfig;
import com.UCA_Health.backend.scoring.SportScoringConfigRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.LinkedHashMap;
import java.util.Map;

@Service
public class TrainingSessionImportService {

    private final TrainingSessionRepository trainingSessionRepository;
    private final SportScoringConfigRepository scoringConfigRepository;

    public TrainingSessionImportService(
            TrainingSessionRepository trainingSessionRepository,
            SportScoringConfigRepository scoringConfigRepository
    ) {
        this.trainingSessionRepository = trainingSessionRepository;
        this.scoringConfigRepository = scoringConfigRepository;
    }

    @Transactional
    public TrainingSession importSession(Long userId, ImportTrainingSessionRequest req) {
        // 1) Idempotencia: si existe devolvemos lo ya guardado
        var existing = trainingSessionRepository.findByDeviceSessionId(req.getDeviceSessionId());
        if (existing.isPresent()) {
            return existing.get();
        }

        // 2) Creamos la entidad
        TrainingSession s = TrainingSession.builder()
                .userId(userId)
                .sportId(req.getSportId())
                .deviceSessionId(req.getDeviceSessionId())
                .startTime(req.getStartTime())
                .endTime(req.getEndTime())
                .durationSec(req.getDurationSec())
                .distanceKm(req.getDistanceKm())
                .hrAvg(req.getHrAvg())
                .hrMax(req.getHrMax())
                .activeCaloriesKcal(req.getActiveCaloriesKcal())
                .elevationGainM(req.getElevationGainM())
                .build();

        // 3) Calculamos score y breakdown
        ScoreResult score = calculateScore(req, loadConfigOrThrow(req.getSportId()));
        s.setScoreFinal(score.scoreFinal());
        s.setScoreBreakdown(score.breakdown());

        // 4) Guardamos
        try {
            return trainingSessionRepository.save(s);
        } catch (DataIntegrityViolationException e) {
            return trainingSessionRepository.findByDeviceSessionId(req.getDeviceSessionId())
                    .orElseThrow(() -> e);
        }
    }

    private SportScoringConfig loadConfigOrThrow(Long sportId) {
        return scoringConfigRepository.findById(sportId)
                .orElseThrow(() -> new IllegalStateException(
                        "No existe sport_scoring_config para sport_id=" + sportId
                ));
    }

    private ScoreResult calculateScore(ImportTrainingSessionRequest req, SportScoringConfig cfg) {
        int basePoints = cfg.getBasePoints() == null ? 100 : cfg.getBasePoints();

        BigDecimal distNorm = norm(req.getDistanceKm(), cfg.getRefDistanceKm());
        BigDecimal durNorm = normDuration(req.getDurationSec(), cfg.getRefDurationMin());
        BigDecimal hrNorm = normHr(req.getHrAvg(), cfg.getRefHrAvg());
        BigDecimal elevNorm = norm(req.getElevationGainM(), cfg.getRefElevationGainM());

        BigDecimal wDist = nz(cfg.getWDistance());
        BigDecimal wDur  = nz(cfg.getWDuration());
        BigDecimal wHr   = nz(cfg.getWHrAvg());
        BigDecimal wElev = nz(cfg.getWElevationGain());

        BigDecimal scoreRaw =
                wDist.multiply(distNorm)
                        .add(wDur.multiply(durNorm))
                        .add(wHr.multiply(hrNorm))
                        .add(wElev.multiply(elevNorm));

        scoreRaw = clamp01(scoreRaw);

        int scoreFinal = scoreRaw
                .multiply(BigDecimal.valueOf(basePoints))
                .setScale(0, RoundingMode.HALF_UP)
                .intValue();

        int cDist = wDist.multiply(distNorm).multiply(BigDecimal.valueOf(basePoints))
                .setScale(0, RoundingMode.HALF_UP).intValue();
        int cDur  = wDur.multiply(durNorm).multiply(BigDecimal.valueOf(basePoints))
                .setScale(0, RoundingMode.HALF_UP).intValue();
        int cHr   = wHr.multiply(hrNorm).multiply(BigDecimal.valueOf(basePoints))
                .setScale(0, RoundingMode.HALF_UP).intValue();
        int cElev = wElev.multiply(elevNorm).multiply(BigDecimal.valueOf(basePoints))
                .setScale(0, RoundingMode.HALF_UP).intValue();

        Map<String, Object> breakdown = new LinkedHashMap<>();
        breakdown.put("base_points", basePoints);

        Map<String, Object> norm = new LinkedHashMap<>();
        norm.put("distance", distNorm);
        norm.put("duration", durNorm);
        norm.put("hr_avg", hrNorm);
        norm.put("elevation_gain", elevNorm);
        breakdown.put("norm", norm);

        Map<String, Object> weights = new LinkedHashMap<>();
        weights.put("distance", wDist);
        weights.put("duration", wDur);
        weights.put("hr_avg", wHr);
        weights.put("elevation_gain", wElev);
        breakdown.put("weights", weights);

        Map<String, Object> contrib = new LinkedHashMap<>();
        contrib.put("distance", cDist);
        contrib.put("duration", cDur);
        contrib.put("hr_avg", cHr);
        contrib.put("elevation_gain", cElev);
        breakdown.put("contrib_points", contrib);

        breakdown.put("score_raw", scoreRaw);
        breakdown.put("score_final", scoreFinal);

        return new ScoreResult(scoreFinal, breakdown);
    }

    // --- Helpers de normalización ---

    private BigDecimal norm(BigDecimal value, BigDecimal ref) {
        if (value == null || ref == null) return BigDecimal.ZERO;
        if (ref.compareTo(BigDecimal.ZERO) <= 0) return BigDecimal.ZERO;
        return clamp01(value.divide(ref, 6, RoundingMode.HALF_UP));
    }

    private BigDecimal normDuration(Long durationSec, Integer refDurationMin) {
        if (durationSec == null || refDurationMin == null) return BigDecimal.ZERO;
        if (refDurationMin <= 0) return BigDecimal.ZERO;
        BigDecimal minutes = BigDecimal.valueOf(durationSec).divide(BigDecimal.valueOf(60), 6, RoundingMode.HALF_UP);
        return clamp01(minutes.divide(BigDecimal.valueOf(refDurationMin), 6, RoundingMode.HALF_UP));
    }

    private BigDecimal normHr(Integer hrAvg, Integer refHrAvg) {
        if (hrAvg == null || refHrAvg == null) return BigDecimal.ZERO;
        if (refHrAvg <= 0) return BigDecimal.ZERO;
        return clamp01(BigDecimal.valueOf(hrAvg).divide(BigDecimal.valueOf(refHrAvg), 6, RoundingMode.HALF_UP));
    }

    private BigDecimal clamp01(BigDecimal x) {
        if (x.compareTo(BigDecimal.ZERO) < 0) return BigDecimal.ZERO;
        if (x.compareTo(BigDecimal.ONE) > 0) return BigDecimal.ONE;
        return x;
    }

    private BigDecimal nz(BigDecimal x) {
        return x == null ? BigDecimal.ZERO : x;
    }

    private record ScoreResult(int scoreFinal, Map<String, Object> breakdown) {}
}
