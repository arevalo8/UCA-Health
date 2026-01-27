package com.UCA_Health.backend.scoring;

import java.math.BigDecimal;

public class SportScoringConfigResponse {

    private Long sportId;
    private Integer basePoints;

    private BigDecimal refDistanceKm;
    private Integer refDurationMin;
    private Integer refHrAvg;
    private BigDecimal refElevationGainM;

    private BigDecimal wDistance;
    private BigDecimal wDuration;
    private BigDecimal wHrAvg;
    private BigDecimal wElevationGain;

    public static SportScoringConfigResponse fromEntity(SportScoringConfig c) {
        SportScoringConfigResponse r = new SportScoringConfigResponse();
        r.sportId = c.getSportId();
        r.basePoints = c.getBasePoints();
        r.refDistanceKm = c.getRefDistanceKm();
        r.refDurationMin = c.getRefDurationMin();
        r.refHrAvg = c.getRefHrAvg();
        r.refElevationGainM = c.getRefElevationGainM();
        r.wDistance = c.getWDistance();
        r.wDuration = c.getWDuration();
        r.wHrAvg = c.getWHrAvg();
        r.wElevationGain = c.getWElevationGain();
        return r;
    }

    // Getters

    public Long getSportId() { return sportId; }
    public Integer getBasePoints() { return basePoints; }
    public BigDecimal getRefDistanceKm() { return refDistanceKm; }
    public Integer getRefDurationMin() { return refDurationMin; }
    public Integer getRefHrAvg() { return refHrAvg; }
    public BigDecimal getRefElevationGainM() { return refElevationGainM; }
    public BigDecimal getWDistance() { return wDistance; }
    public BigDecimal getWDuration() { return wDuration; }
    public BigDecimal getWHrAvg() { return wHrAvg; }
    public BigDecimal getWElevationGain() { return wElevationGain; }
}
