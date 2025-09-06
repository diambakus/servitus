package com.orakuma.servitus.contact;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EntityContactRepository extends CrudRepository<EntityContact, Long> {
    @Query(value = "select ce.contact_id from contacts_entities ce " +
            "where ce.entity_id = :entityId and ce.entity_type = :entityType", nativeQuery = true)
    Iterable<Long> findContactIdsByContactIdAndContactType(@Param("entityId") Long entityId, @Param("entityType") String entityType);
}
