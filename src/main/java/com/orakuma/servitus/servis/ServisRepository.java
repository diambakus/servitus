package com.orakuma.servitus.servis;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface ServisRepository extends CrudRepository<Servis, Long> {
    @Query(value = "select * from servis s where s.active = true", nativeQuery = true)
    List<Servis> findAllActive();
    @Query(value = "select * from servis s where s.active = true AND s.fk_unit = :unitId", nativeQuery = true)
    List<Servis> findAllActiveByUnit(Long unitId);
    @Query(value = "SELECT * FROM servis i INNER JOIN units u ON i.fk_unit = u.id WHERE i.active AND u.fk_organ = :organId", nativeQuery = true)
    List<Servis> findByOrgan(Long organId);
    @Query(value = "SELECT * FROM servis i INNER JOIN units u ON i.fk_unit = u.id WHERE i.active AND u.fk_organ = :organId " +
            "AND u.id = :unitId", nativeQuery = true)
    List<Servis> findByUnitAndOrgan(Long unitId, Long organId);
    @Query(value = "select * from servis i where i.active = false AND i.fk_unit = :unitId", nativeQuery = true)
    List<Servis> findAllInactiveByUnit(Long unitId);
}
