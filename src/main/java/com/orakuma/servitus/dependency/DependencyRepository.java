package com.orakuma.servitus.dependency;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Set;

public interface DependencyRepository extends CrudRepository<DependencyEntity, Long> {
    @Query("""
        select de
        from DependencyEntity de
        where de.id
        not in (:dependencyIds)
    """)
    Iterable<DependencyEntity> findAllNotInTheList(Set<Long> dependencyIds);

    /**
     * Dependencies attached to a servis, in display order. Reads only dependency rows (one
     * statement, servis resolved through the unique public_id index) instead of loading the
     * Servis entity and its collection.
     */
    @Query("""
        select d
        from Servis s
        join s.dependencies d
        where s.publicId = :publicId
        order by d.position, d.id
    """)
    List<DependencyEntity> findAllByServisPublicId(@Param("publicId") String publicId);
}
