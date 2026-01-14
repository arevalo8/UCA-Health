package com.UCA_Health.backend.sports;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;

@Service
@Transactional
public class SportServiceImpl implements SportService {

    private final SportRepository repo;

    // categorías permitidas a nivel de dominio
    private static final Set<String> ALLOWED_CATEGORIES = Set.of(
            "TEAM", "INDIVIDUAL", "AEROBIC", "STRENGTH", "RACKET"
    );

    public SportServiceImpl(SportRepository repo) {
        this.repo = repo;
    }

    // ---------- READ ----------

    @Override
    @Transactional(readOnly = true)
    public List<Sport> list() {
        return repo.findAll();
    }

    @Override
    @Transactional(readOnly = true)
    public Sport get(Long id) {
        return repo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Sport id=" + id + " not found"));
    }

    // ---------- CREATE ----------

    @Override
    public Sport create(Sport sport) {
        // 1) Normalizar datos de entrada
        normalize(sport);

        // 2) Validar reglas de negocio
        validateCategory(sport);
        ensureNameIsUniqueOnCreate(sport);

        // 3) Asegurar que se trata de una creación
        sport.setId(null);

        // 4) Guardar
        return repo.save(sport);
    }

    // ---------- UPDATE ----------

    @Override
    public Sport update(Long id, Sport sport) {
        Sport existing = repo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Sport id=" + id + " not found"));

        // 1) Normalizar datos de entrada
        normalize(sport);

        // 2) Validar reglas de negocio
        validateCategory(sport);
        ensureNameIsUniqueOnUpdate(id, sport);

        // 3) Aplicar cambios sobre la entidad existente
        existing.setName(sport.getName());
        existing.setDescription(sport.getDescription());
        existing.setCategory(sport.getCategory());

        // 4) Guardar cambios
        return repo.save(existing);
    }

    // ---------- DELETE ----------

    @Override
    public void delete(Long id) {
        Sport existing = repo.findById(id)
                .orElseThrow(() ->
                        new EntityNotFoundException("Sport id=" + id + " not found"));

        // Aquí iría lógica tipo:
        // - comprobar si el deporte está referenciado por otras entidades
        // - si lo está, lanzar una excepción de negocio
        //
        // de momento, simplemente borramos:
        repo.delete(existing);
    }

    // ===================================================
    // Métodos privados de lógica de negocio / utilidades
    // ===================================================

    private void normalize(Sport sport) {
        if (sport.getName() != null) {
            sport.setName(sport.getName().trim());
        }
        if (sport.getCategory() != null) {
            sport.setCategory(sport.getCategory().trim().toUpperCase());
        }
    }

    private void validateCategory(Sport sport) {
        String cat = sport.getCategory();
        if (cat == null || cat.isBlank()) {
            return;
        }
        if (!ALLOWED_CATEGORIES.contains(cat)) {
            throw new IllegalArgumentException(
                    "Invalid category: " + cat + ". Allowed: " + ALLOWED_CATEGORIES);
        }
    }

    private void ensureNameIsUniqueOnCreate(Sport sport) {
        if (sport.getName() == null) {
            return; // Bean Validation (@NotBlank) ya se encargará
        }
        if (repo.existsByNameIgnoreCase(sport.getName())) {
            throw new DataIntegrityViolationException(
                    "Sport name already exists: " + sport.getName());
        }
    }

    private void ensureNameIsUniqueOnUpdate(Long id, Sport sport) {
        if (sport.getName() == null) {
            return;
        }

        repo.findByNameIgnoreCase(sport.getName())
                .filter(other -> !other.getId().equals(id)) // distinto deporte
                .ifPresent(other -> {
                    throw new DataIntegrityViolationException(
                            "Sport name already exists: " + sport.getName());
                });
    }
}
