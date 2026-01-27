package com.UCA_Health.backend.auth;


import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.UCA_Health.backend.auth.dto.AuthResponse;
import com.UCA_Health.backend.auth.dto.LoginRequest;
import com.UCA_Health.backend.auth.dto.RegisterRequest;


@RestController
@RequestMapping("/auth")
public class AuthController {

  private final AuthService authService;

  public AuthController(AuthService authService) {
    this.authService = authService;
  }

  @PostMapping("/register")
  public AuthResponse register(@RequestBody RegisterRequest req) {
    return authService.register(req);
  }

  @PostMapping("/login")
  public AuthResponse login(@RequestBody LoginRequest req) {
    return authService.login(req);
  }
}

  

