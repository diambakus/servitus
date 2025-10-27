package com.orakuma.servitus.organ;

import java.util.Optional;

public interface OrganService {
    Optional<OrganDto> persist(OrganDto organDto);
    Optional<OrganDto> findBy(Long id);
    Iterable<OrganDto> gets();
    Iterable<OrganDto> getAllInactive();
    int inactivate(Long organId);
}
