package com.UCA_Health.backend.users.dto;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.Instant;
import java.util.Set;

public record AdminUserResponse(
  Long id,
  String username,
  String email,
  Set<String> roles,
  Integer height,
  BigDecimal weight,
  LocalDate birthDate,
  Instant createdAt,
  Instant updatedAt
) {}
