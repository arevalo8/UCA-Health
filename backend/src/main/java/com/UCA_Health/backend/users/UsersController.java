package com.UCA_Health.backend.users;

import com.UCA_Health.backend.auth.UserPrincipal;
import com.UCA_Health.backend.users.dto.UpdateMeRequest;
import com.UCA_Health.backend.users.dto.UserMeResponse;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UsersController {

  private final UserService userService;

  public UsersController(UserService userService) {
    this.userService = userService;
  }

  @GetMapping("/me")
  public UserMeResponse me(@AuthenticationPrincipal UserPrincipal principal) {
    return userService.me(principal);
  }

  @PatchMapping("/me")
  public UserMeResponse updateMe(
    @AuthenticationPrincipal UserPrincipal principal,
    @RequestBody UpdateMeRequest req
  ) {
    return userService.updateMe(principal, req);
  }
}
