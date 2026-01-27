package com.UCA_Health.backend.users.dto;

import java.math.BigDecimal;
import java.time.LocalDate;

public record UpdateMeRequest(
  Integer height,
  BigDecimal weight,
  LocalDate birthDate
) {}
