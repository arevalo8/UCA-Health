package com.UCA_Health.backend.common;

import com.UCA_Health.backend.users.Role;
import com.UCA_Health.backend.users.User;
import com.UCA_Health.backend.users.UserRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

@Configuration
public class SeedConfig {

  @Bean
  CommandLineRunner seedAdmin(UserRepository repo, PasswordEncoder encoder) {
    return args -> {
      String email = "admin@ucahealth.com";

      if (repo.findByEmail(email).isEmpty()) {
        User u = new User();
        u.setUsername("admin");
        u.setEmail(email);
        u.setPasswordHash(encoder.encode("Admin123!"));
        u.getRoles().add(Role.ROLE_ADMIN);
        u.getRoles().add(Role.ROLE_USER);

        repo.save(u);

        System.out.println("✅ Admin creado: " + email + " / Admin123!");
      }
    };
  }
}
