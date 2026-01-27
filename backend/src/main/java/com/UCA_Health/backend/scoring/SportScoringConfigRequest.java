package com.UCA_Health.backend.scoring;

import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import java.math.BigDecimal;

public class SportScoringConfigRequest {

    private Long sportId;

    @NotNull @Min(1)
    private Integer basePoints;

    private BigDecimal refDistanceKm;
    private Integer refDurationMin;
    private Integer refHrAvg;
    private BigDecimal refElevationGainM;

    @NotNull
    private BigDecimal wDistance;

    @NotNull
    private BigDecimal wDuration;

    @NotNull
    @JsonProperty("wHrAvg")
    private BigDecimal wHrAvg;

    @NotNull
    private BigDecimal wElevationGain;

    public Long getSportId() { return sportId; }
    public void setSportId(Long sportId) { this.sportId = sportId; }

    public Integer getBasePoints() { return basePoints; }
    public void setBasePoints(Integer basePoints) { this.basePoints = basePoints; }

    public BigDecimal getRefDistanceKm() { return refDistanceKm; }
    public void setRefDistanceKm(BigDecimal refDistanceKm) { this.refDistanceKm = refDistanceKm; }

    public Integer getRefDurationMin() { return refDurationMin; }
    public void setRefDurationMin(Integer refDurationMin) { this.refDurationMin = refDurationMin; }

    public Integer getRefHrAvg() { return refHrAvg; }
    public void setRefHrAvg(Integer refHrAvg) { this.refHrAvg = refHrAvg; }

    public BigDecimal getRefElevationGainM() { return refElevationGainM; }
    public void setRefElevationGainM(BigDecimal refElevationGainM) { this.refElevationGainM = refElevationGainM; }

    public BigDecimal getWDistance() { return wDistance; }
    public void setWDistance(BigDecimal wDistance) { this.wDistance = wDistance; }

    public BigDecimal getWDuration() { return wDuration; }
    public void setWDuration(BigDecimal wDuration) { this.wDuration = wDuration; }

    @JsonProperty("wHrAvg")
    public BigDecimal getWHrAvg() { return wHrAvg; }

    @JsonProperty("wHrAvg")
    public void setWHrAvg(BigDecimal wHrAvg) { this.wHrAvg = wHrAvg; }

    public BigDecimal getWElevationGain() { return wElevationGain; }
    public void setWElevationGain(BigDecimal wElevationGain) { this.wElevationGain = wElevationGain; }
}
