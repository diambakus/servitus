package com.orakuma.servitus.organ;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OrganRepository extends CrudRepository<Organ, Long> {
    @Query("SELECT o FROM Organ o WHERE o.active = true")
    List<Organ> findAllActive();
    @Query("SELECT o FROM Organ o WHERE o.active = false")
    List<Organ> findAllInactive();
    @Query("select count(*) from Unit u where u.organ.id = :organId")
    Long countByAssociatedUnits(@Param("organId") Long organId);
}
