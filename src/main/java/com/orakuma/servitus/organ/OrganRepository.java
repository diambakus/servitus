package com.orakuma.servitus.organ;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganRepository extends CrudRepository<Organ, Long> {
    @Query(value = "select * from organs o where o.active = true", nativeQuery = true)
    List<Organ> findAllActive();
    @Query(value = "select * from organs o where o.active = false", nativeQuery = true)
    List<Organ> findAllInactive();
    @Query(value = "select count(*) from units u where u.fk_organ = :organId", nativeQuery = true)
    Long countByAssociatedUnits(Long organId);
}
