package com.orakuma.servitus.dependency;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import java.util.Set;

public interface DependencyRepository extends CrudRepository<DependencyEntity, Long> {
    @Query("""
        select de
        from DependencyEntity de
        where de.id
        not in (:dependencyIds)
    """)
    Iterable<DependencyEntity> findAllNotInTheList(Set<Long> dependencyIds);
}
