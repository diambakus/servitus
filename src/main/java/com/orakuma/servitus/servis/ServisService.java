package com.orakuma.servitus.servis;

import com.orakuma.servitus.dependency.DependencyDto;

import java.util.*;

public interface ServisService {
    ServisDto get(Long servisId);
    List<ServisDto> getAll();
    List<ServisDto> getAllOpen();
    List<ServisDto> getAllOpenByRequestor();
    ServisDto create(ServisDto servisDto);
    ServisDto update(Long servisId, Map<String, Object> fields);
    void delete(Long servisId);
    List<ServisDto> getByUnit(Long unitId);
    List<ServisDto> getAllActive();
    ServisDto removeUnits(Long servisId, List<Long> unitId);
    Set<DependencyDto> getDependenciesForServis(Long id);
    Void addDependencies(Long id, LinkedHashSet<Long> dependenciesId);
    Void removeDependencies(Long id, LinkedHashSet<Long> dependenciesId);
}
