package com.UCA_Health.backend.scoring;

import java.math.BigDecimal;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Builder;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Entity
@Table(name = "sport_scoring_config")
public class SportScoringConfig {

    @Id
    @Column(name = "sport_id")
    private Long sportId;

    @Column(name = "base_points", nullable = false)
    private Integer basePoints;

    @Column(name = "ref_distance_km", precision = 10, scale = 3)
    private BigDecimal refDistanceKm;

    @Column(name = "ref_duration_min")
    private Integer refDurationMin;

    @Column(name = "ref_hr_avg")
    private Integer refHrAvg;

    @Column(name = "ref_elevation_gain_m", precision = 10, scale = 2)
    private BigDecimal refElevationGainM;

    @Column(name = "w_distance", precision = 6, scale = 3, nullable = false)
    private BigDecimal wDistance;

    @Column(name = "w_duration", precision = 6, scale = 3, nullable = false)
    private BigDecimal wDuration;

    @Column(name = "w_hr_avg", precision = 6, scale = 3, nullable = false)
    private BigDecimal wHrAvg;

    @Column(name = "w_elevation_gain", precision = 6, scale = 3, nullable = false)
    private BigDecimal wElevationGain;

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
	
	public void setWDistance(BigDecimal wDistance) { this.wDistance = wDistance; }
	public void setWDuration(BigDecimal wDuration) { this.wDuration = wDuration; }
	public void setWHrAvg(BigDecimal wHrAvg) { this.wHrAvg = wHrAvg; }
	public void setWElevationGain(BigDecimal wElevationGain) { this.wElevationGain = wElevationGain; }
	public void setSportId(Long sportId) { this.sportId = sportId;}
	public void setBasePoints(Integer basePoints) { this.basePoints = basePoints; }
	public void setRefDistanceKm(BigDecimal refDistanceKm) { this.refDistanceKm = refDistanceKm; }
	public void setRefDurationMin(Integer refDurationMin) { this.refDurationMin = refDurationMin; }
	public void setRefHrAvg(Integer refHrAvg) { this.refHrAvg = refHrAvg; }
	public void setRefElevationGainM(BigDecimal refElevationGainM) { this.refElevationGainM = refElevationGainM; }
}
