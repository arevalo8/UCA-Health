package com.UCA_Health.backend.users.dto;

import java.util.Set;

public record UpdateUserRolesRequest(Set<String> roles) {}
