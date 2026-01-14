package com.UCA_Health.backend.sports;

import java.util.List;

public interface SportService {
    List<Sport> list();
    Sport get(Long id);
    Sport create(Sport sport);
    Sport update(Long id, Sport sport);
    void delete(Long id);
}
