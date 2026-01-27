package com.UCA_Health.backend.auth;

import com.UCA_Health.backend.users.User;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.stream.Collectors;

public class UserPrincipal implements UserDetails {

	private final User user;

	public UserPrincipal(User user) { this.user = user; }

	public Long getId() { return user.getId(); }
	public String getEmail() { return user.getEmail(); }

	@Override
	public Collection<? extends GrantedAuthority> getAuthorities() {
		return user.getRoles().stream()
				.map(r -> new SimpleGrantedAuthority(r.name()))
				.collect(Collectors.toSet());
	}

	@Override public String getPassword() { return user.getPasswordHash(); }
	@Override public String getUsername() { return user.getEmail(); } 
	@Override public boolean isAccountNonExpired() { return true; }
	@Override public boolean isAccountNonLocked() { return true; }
	@Override public boolean isCredentialsNonExpired() { return true; }
	@Override public boolean isEnabled() { return true; }
}
