package com.UCA_Health.backend.sports;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;


public interface SportRepository extends JpaRepository<Sport, Long> {
    Optional<Sport> findByNameIgnoreCase(String name);
    boolean existsByNameIgnoreCase(String name);
}