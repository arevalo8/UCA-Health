package com.UCA_Health.backend.users;

import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.UCA_Health.backend.users.dto.AdminUserResponse;
import com.UCA_Health.backend.users.dto.UpdateUserRolesRequest;

@RestController
@RequestMapping("/admin/users")
public class AdminUsersController {

  private final AdminUserService adminUserService;

  public AdminUsersController(AdminUserService adminUserService) { this.adminUserService = adminUserService; }

  @GetMapping
  public List<AdminUserResponse> list() {
    return adminUserService.listUsers();
  }

  @GetMapping("/{id}")
  public AdminUserResponse get(@PathVariable Long id) {
    return adminUserService.getUser(id);
  }

  @PatchMapping("/{id}/roles")
  public AdminUserResponse updateRoles(@PathVariable Long id, @RequestBody UpdateUserRolesRequest req) {
    return adminUserService.updateRoles(id, req);
  }

  @DeleteMapping("/{id}")
  public void delete(@PathVariable Long id) {
    adminUserService.deleteUser(id);
  }
}
