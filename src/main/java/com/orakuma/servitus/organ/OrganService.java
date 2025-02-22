package com.orakuma.servitus.organ;


import java.util.Map;
import java.util.Optional;

public interface OrganService {
    Optional<OrganDto> persist(OrganDto organDto);
    Optional<OrganDto> findBy(Long id);
    Iterable<OrganDto> gets();
    Iterable<OrganDto> getAllInactive();
    int inactivate(Long organId);
    Optional<OrganDto> saveAttributes(Long id, Map<String, String> attributes);
    OrganDto updateOrganWithProperties(Long id, Map<String, String> fieldsContentMap);
}
