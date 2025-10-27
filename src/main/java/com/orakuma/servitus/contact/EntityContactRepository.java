package com.orakuma.servitus.contact;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EntityContactRepository extends CrudRepository<EntityContact, Long> {
    @Query("select ce.contact.id " +
            "from EntityContact ce " +
            "where ce.entityId = :entityId " +
            "and ce.entityType = :entityType")
    Iterable<Long> findContactIdsByContactIdAndContactType(@Param("entityId") Long entityId, @Param("entityType") String entityType);
}
