package com.orakuma.servitus.unit;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface UnitRepository extends CrudRepository<Unit, Long>, PagingAndSortingRepository<Unit, Long> {
    @Query(value = "SELECT * from units u where u.fk_organ = :organId", nativeQuery = true)
    List<Unit> findUnitsByOrgan(Long organId);
    @Query(value = "SELECT * from units u where u.active = true and u.fk_organ = :organId", nativeQuery = true)
    List<Unit> findActiveUnitsByOrgan(Long organId);
    @Query(value = "SELECT * from units u where u.active = false and u.fk_organ = :organId", nativeQuery = true)
    List<Unit> findInactiveUnitsByOrgan(Long organId);
    @Query(value = "select count(*) from items item where item.fk_unit = :unitId", nativeQuery = true)
    int countByAssociatedServices(Long unitId);
    @Query(value = "select * from units u where u.active = true", nativeQuery = true)
    List<Unit> findActiveUnits();
    @Query(value = "select * from units u where u.active = false", nativeQuery = true)
    List<Unit> findInactiveUnits();
}
