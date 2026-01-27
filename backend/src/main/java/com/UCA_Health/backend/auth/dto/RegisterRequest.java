package com.UCA_Health.backend.auth.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record RegisterRequest(
  String username,
  String email,
  String password,
  Integer height,
  BigDecimal weight,
  LocalDate birthDate
) {}
