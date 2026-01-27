package com.UCA_Health.backend.auth.dto;

public record LoginRequest(
  String email,
  String password
) {}
