package com.UCA_Health.backend.users;

import com.UCA_Health.backend.auth.UserPrincipal;
import com.UCA_Health.backend.users.dto.UpdateMeRequest;
import com.UCA_Health.backend.users.dto.UserMeResponse;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.stream.Collectors;

@Service
public class UserService {

  private final UserRepository userRepository;

  public UserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public UserMeResponse me(UserPrincipal principal) {
    User u = userRepository.findById(principal.getId())
      .orElseThrow(() -> new IllegalArgumentException("User not found"));

    return toMeResponse(u);
  }

  @Transactional
  public UserMeResponse updateMe(UserPrincipal principal, UpdateMeRequest req) {
    User u = userRepository.findById(principal.getId())
      .orElseThrow(() -> new IllegalArgumentException("User not found"));

    if (req.height() != null) {
      if (req.height() <= 0 || req.height() > 260) throw new IllegalArgumentException("Invalid height");
      u.setHeight(req.height());
    }

    if (req.weight() != null) {
      if (req.weight().signum() <= 0) throw new IllegalArgumentException("Invalid weight");
      u.setWeight(req.weight());
    }

    if (req.birthDate() != null) {
      if (req.birthDate().isAfter(LocalDate.now())) throw new IllegalArgumentException("Invalid birthDate");
      u.setBirthDate(req.birthDate());
    }

    return toMeResponse(userRepository.save(u));
  }

  private UserMeResponse toMeResponse(User u) {
    return new UserMeResponse(
      u.getId(),
      u.getUsername(),
      u.getEmail(),
      u.getRoles().stream().map(Enum::name).collect(Collectors.toSet()),
      u.getHeight(),
      u.getWeight(),
      u.getBirthDate(),
      u.getCreatedAt(),
      u.getUpdatedAt()
    );
  }
}
