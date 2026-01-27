package com.UCA_Health.backend.trainingsession;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;
import java.time.Instant;

public class ImportTrainingSessionRequest {

    

    @NotNull
    private Long sportId;

    //Para evitar duplicados
    @NotBlank
    private String deviceSessionId;

    @NotNull
    private Instant startTime;

    @NotNull
    private Instant endTime;

    @NotNull
    private Long durationSec;

    private BigDecimal distanceKm;
    private Integer hrAvg;
    private Integer hrMax;
    private BigDecimal activeCaloriesKcal;
    private BigDecimal elevationGainM;



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

    public Long getDurationSec() {
        return durationSec;
    }

    public void setDurationSec(Long durationSec) {
        this.durationSec = durationSec;
    }

    public BigDecimal getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(BigDecimal distanceKm) {
        this.distanceKm = distanceKm;
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
}
