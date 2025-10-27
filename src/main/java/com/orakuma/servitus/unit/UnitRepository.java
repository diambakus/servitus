package com.orakuma.servitus.unit;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends CrudRepository<Unit, Long>, PagingAndSortingRepository<Unit, Long> {
    @Query("SELECT u from Unit u where u.organ.id = :organId")
    List<Unit> findUnitsByOrgan(@Param("organId") Long organId);
    @Query("SELECT u from Unit u where u.organ.id = :organId and u.active")
    List<Unit> findActiveUnitsByOrgan(@Param("organId") Long organId);
    @Query("SELECT u from Unit u where u.organ.id = :organId and u.active = false")
    List<Unit> findInactiveUnitsByOrgan(@Param("organId") Long organId);
    @Query("""
        select count(s)
        from Servis s
        inner join s.units u
        where u.id = :unitId
    """)
    int countByAssociatedServices(@Param("unitId") Long unitId);
    @Query("select u from Unit u where u.active")
    List<Unit> findActiveUnits();
    @Query("select u from Unit u where u.active = false")
    List<Unit> findInactiveUnits();
}
