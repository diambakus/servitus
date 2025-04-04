package com.orakuma.servitus.servis;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Map;
import java.util.Set;

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
    @Modifying
    @Query(value = "delete from requisites where servis_id = :servisId and position in :requisitePositions", nativeQuery = true)
    void deleteRequisitesBy(@Param("servisId") Long servisId, @Param("requisitePositions") Set<Integer> requisitePositions);
    @Query(value = "insert into requisites values (:servisId, :entry.getKey(), :entry.getValue())", nativeQuery = true)
    void insertRequisites(@Param("servisId") Long servisId, @Param("entry") Map.Entry<Integer, String> entry);
}
