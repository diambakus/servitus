package com.orakuma.servitus.dependency;

import java.util.LinkedHashSet;
import java.util.Optional;

public interface DependencyService {
    Optional<DependencyDto> persist(DependencyDto dependencyDto);
    Optional<DependencyDto> update(DependencyDto dependencyDto);
    LinkedHashSet<DependencyDto> fetchAll();
    Optional<DependencyDto> deactivate(Long id);
    LinkedHashSet<DependencyDto> getDependenciesToAddByServisId(Long id);
}
