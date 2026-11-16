package com.orakuma.servitus.dependency;

import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.Set;

public interface DependencyService {
    Optional<DependencyDto> persist(DependencyDto dependencyDto);
    Optional<DependencyDto> update(DependencyDto dependencyDto);
    LinkedHashSet<DependencyDto> fetchAll();
    Optional<DependencyDto> deactivate(Long id);
    LinkedHashSet<DependencyDto> getDependenciesToAddByServisId(Long id);
    LinkedHashSet<DependencyDto> getDependenciesToAddByServisPublicId(String publicId);
    /** Dependencies attached to the servis, ordered by position. Cached; the result is read-only. */
    Set<DependencyDto> getDependenciesByServisPublicId(String publicId);
}
