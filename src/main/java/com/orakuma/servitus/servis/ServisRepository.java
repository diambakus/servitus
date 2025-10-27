package com.orakuma.servitus.servis;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ServisRepository extends CrudRepository<Servis, Long> {
    @Query("select s " +
            "from Servis s " +
            "where s.active = true")
    List<Servis> findAllActive();
    @Query("""
        select s
        from Servis s
        inner join s.units u
        where u.id = :unitId
          and s.active
    """)
    List<Servis> findAllActiveByUnit(@Param("unitId") Long unitId);

    @Query("""
        select s
        from Servis s
        inner join s.units u
        where u.organ.id = :organId
            and s.active
    """)
    List<Servis> findByOrgan(@Param("organId") Long organId);
    @Query("""
        select s
        from Servis s
        inner join s.units u
        where u.id = :unitId 
            and u.organ.id = :organId
            and s.active
    """)
    List<Servis> findByUnitAndOrgan(@Param("unitId") Long unitId, @Param("organId") Long organId);
    @Query("""
        select s
        from Servis s
        inner join s.units u
        where u.id = :unitId
            and s.active = false
    """)
    List<Servis> findAllInactiveByUnit(@Param("unitId") Long unitId);

    @Query("""
        select s
        from Servis s
        left join fetch s.dependencies
        where s.id = :id
    """)
    Optional<Servis> findWithDependenciesById(@Param("id") Long id);
}
