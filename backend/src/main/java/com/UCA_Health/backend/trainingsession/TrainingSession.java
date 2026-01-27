package com.UCA_Health.backend.trainingsession;

import java.math.BigDecimal;
import java.time.Instant;
import java.util.Map;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.type.SqlTypes;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

@Entity
@Table(
    name = "training_sessions"
)

@AllArgsConstructor
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Builder 
public class TrainingSession {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // --- Identificación y relación lógica ---
    @Column(name = "user_id", nullable = false)
    private Long userId;

    @Column(name = "sport_id", nullable = false)
    private Long sportId;

    
    //Identificador de sesión de Health Connect 
    //endpoint idempotente: 
    @Column(name = "device_session_id", unique = true)
    private String deviceSessionId;

    // --- Tiempos de la sesión ---
    @Column(name = "start_time", nullable = false)
    private Instant startTime;

    @Column(name = "end_time", nullable = false)
    private Instant endTime;

    //Métricas resumen 
    @Column(name = "distance_km", precision = 10, scale = 3)
    private BigDecimal distanceKm;

    @Column(name = "duration_sec", nullable = false)
    private Long durationSec;

    @Column(name = "hr_avg")
    private Integer hrAvg;

    @Column(name = "hr_max")
    private Integer hrMax;

    @Column(name = "active_calories_kcal", precision = 10, scale = 2)
    private BigDecimal activeCaloriesKcal;

    // Desnivel positivo 
    @Column(name = "elevation_gain_m", precision = 10, scale = 2)
    private BigDecimal elevationGainM;

    //Puntuacion del entrenamiento
    @Column(name = "score_final")
    private Integer scoreFinal;

    // Breakdown en JSON (por ejemplo: dist_norm, dur_norm, hr_norm, elev_norm, pesos, etc.)
    //para devolver a la app puntuaciones por apartado y para logs y depuración.
    @JdbcTypeCode(SqlTypes.JSON)
    @Column(name = "score_breakdown", columnDefinition = "jsonb")
    private Map<String, Object> scoreBreakdown;


    @Column(name = "created_at", nullable = false, updatable = false)
    @CreationTimestamp
    private Instant createdAt;

    


    public Long getId() {
        return id;
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

	public void setScoreBreakdown(Map<String, Object> scoreBreakdown) {
	  this.scoreBreakdown = scoreBreakdown;
	}


    public Instant getCreatedAt() {
        return createdAt;
    }
}
