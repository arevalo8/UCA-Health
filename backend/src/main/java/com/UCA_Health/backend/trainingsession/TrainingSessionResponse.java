package com.UCA_Health.backend.trainingsession;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

public class TrainingSessionResponse {

    private Long id;

    private Long userId;
    private Long sportId;
    private String deviceSessionId;

    private Instant startTime;
    private Instant endTime;

    private BigDecimal distanceKm;
    private Long durationSec;
    private Integer hrAvg;
    private Integer hrMax;
    private BigDecimal activeCaloriesKcal;
    private BigDecimal elevationGainM;

    private Integer scoreFinal;

    
    //JSON con el desglose: normas/pesos/contribuciones.
    private Map<String, Object> scoreBreakdown;

    private Instant createdAt;



    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Long getSportId() {
        return sportId;
    }

    public void setSportId(Long sportId) {
        this.sportId = sportId;
    }

    public String getDeviceSessionId() {
        return deviceSessionId;
    }

    public void setDeviceSessionId(String deviceSessionId) {
        this.deviceSessionId = deviceSessionId;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    public BigDecimal getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(BigDecimal distanceKm) {
        this.distanceKm = distanceKm;
    }

    public Long getDurationSec() {
        return durationSec;
    }

    public void setDurationSec(Long durationSec) {
        this.durationSec = durationSec;
    }

    public Integer getHrAvg() {
        return hrAvg;
    }

    public void setHrAvg(Integer hrAvg) {
        this.hrAvg = hrAvg;
    }

    public Integer getHrMax() {
        return hrMax;
    }

    public void setHrMax(Integer hrMax) {
        this.hrMax = hrMax;
    }

    public BigDecimal getActiveCaloriesKcal() {
        return activeCaloriesKcal;
    }

    public void setActiveCaloriesKcal(BigDecimal activeCaloriesKcal) {
        this.activeCaloriesKcal = activeCaloriesKcal;
    }

    public BigDecimal getElevationGainM() {
        return elevationGainM;
    }

    public void setElevationGainM(BigDecimal elevationGainM) {
        this.elevationGainM = elevationGainM;
    }

    public Integer getScoreFinal() {
        return scoreFinal;
    }

    public void setScoreFinal(Integer scoreFinal) {
        this.scoreFinal = scoreFinal;
    }

    public Map<String, Object> getScoreBreakdown() {
        return scoreBreakdown;
    }

    public void setScoreBreakdown(Map<String, Object> map) {
        this.scoreBreakdown = map;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    
    // Para mapear desde Entity
    public static TrainingSessionResponse fromEntity(TrainingSession s) {
        TrainingSessionResponse r = new TrainingSessionResponse();
        r.setId(s.getId());
        r.setUserId(s.getUserId());
        r.setSportId(s.getSportId());
        r.setDeviceSessionId(s.getDeviceSessionId());
        r.setStartTime(s.getStartTime());
        r.setEndTime(s.getEndTime());
        r.setDistanceKm(s.getDistanceKm());
        r.setDurationSec(s.getDurationSec());
        r.setHrAvg(s.getHrAvg());
        r.setHrMax(s.getHrMax());
        r.setActiveCaloriesKcal(s.getActiveCaloriesKcal());
        r.setElevationGainM(s.getElevationGainM());
        r.setScoreFinal(s.getScoreFinal());
        r.setScoreBreakdown(s.getScoreBreakdown());
        r.setCreatedAt(s.getCreatedAt());
        return r;
    }
}
