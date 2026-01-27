package com.UCA_Health.backend.auth;

import com.UCA_Health.backend.auth.dto.AuthResponse;
import com.UCA_Health.backend.auth.dto.LoginRequest;
import com.UCA_Health.backend.auth.dto.RegisterRequest;
import com.UCA_Health.backend.users.Role;
import com.UCA_Health.backend.users.User;
import com.UCA_Health.backend.users.UserRepository;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.stream.Collectors;

@Service
public class AuthService {

  private final UserRepository userRepository;
  private final PasswordEncoder passwordEncoder;
  private final JwtService jwtService;
  private final AuthenticationManager authenticationManager;

  public AuthService(
    UserRepository userRepository,
    PasswordEncoder passwordEncoder,
    JwtService jwtService,
    AuthenticationManager authenticationManager
  ) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
    this.jwtService = jwtService;
    this.authenticationManager = authenticationManager;
  }

  @Transactional
  public AuthResponse register(RegisterRequest req) {
    if (userRepository.existsByEmail(req.email())) {
      throw new IllegalArgumentException("Email already exists");
    }
    if (userRepository.existsByUsername(req.username())) {
      throw new IllegalArgumentException("Username already exists");
    }

    User u = new User();
    u.setUsername(req.username());
    u.setEmail(req.email());
    u.setPasswordHash(passwordEncoder.encode(req.password()));
    u.getRoles().add(Role.ROLE_USER);

    u.setHeight(req.height());
    u.setWeight(req.weight());
    u.setBirthDate(req.birthDate());

    userRepository.save(u);

    String token = jwtService.generateToken(new UserPrincipal(u));
    return toAuthResponse(u, token);
  }

  public AuthResponse login(LoginRequest req) {
    authenticationManager.authenticate(
      new UsernamePasswordAuthenticationToken(req.email(), req.password())
    );

    User u = userRepository.findByEmail(req.email())
      .orElseThrow(() -> new IllegalArgumentException("Invalid credentials"));

    String token = jwtService.generateToken(new UserPrincipal(u));
    return toAuthResponse(u, token);
  }

  private AuthResponse toAuthResponse(User u, String token) {
    return new AuthResponse(
      token,
      u.getId(),
      u.getUsername(),
      u.getEmail(),
      u.getRoles().stream().map(Enum::name).collect(Collectors.toSet())
    );
  }
}
