package com.UCA_Health.backend.users;

import com.UCA_Health.backend.users.dto.AdminUserResponse;
import com.UCA_Health.backend.users.dto.UpdateUserRolesRequest;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminUserService {

  private final UserRepository userRepository;

  public AdminUserService(UserRepository userRepository) {
    this.userRepository = userRepository;
  }

  public List<AdminUserResponse> listUsers() {
    return userRepository.findAll().stream()
      .map(this::toAdminResponse)
      .toList();
  }

  public AdminUserResponse getUser(Long id) {
    User u = userRepository.findById(id)
      .orElseThrow(() -> new IllegalArgumentException("User not found"));
    return toAdminResponse(u);
  }

  @Transactional
  public AdminUserResponse updateRoles(Long id, UpdateUserRolesRequest req) {
    User u = userRepository.findById(id)
      .orElseThrow(() -> new IllegalArgumentException("User not found"));

    if (req.roles() == null || req.roles().isEmpty()) {
      throw new IllegalArgumentException("roles is required");
    }

    Set<Role> newRoles = req.roles().stream()
      .map(String::trim)
      .map(r -> {
        try { return Role.valueOf(r); }
        catch (Exception e) { throw new IllegalArgumentException("Invalid role: " + r); }
      })
      .collect(Collectors.toSet());

    u.setRoles(newRoles);
    return toAdminResponse(userRepository.save(u));
  }

  @Transactional
  public void deleteUser(Long id) {
    if (!userRepository.existsById(id)) {
      throw new IllegalArgumentException("User not found");
    }
    userRepository.deleteById(id);
  }

  private AdminUserResponse toAdminResponse(User u) {
    return new AdminUserResponse(
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
