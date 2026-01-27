package com.UCA_Health.backend.auth.dto;

import java.util.Set;

public record AuthResponse(
  String accessToken,
  Long userId,
  String username,
  String email,
  Set<String> roles
) {}
