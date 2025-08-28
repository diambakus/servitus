package com.orakuma.servitus.contact;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

public interface EntityContactRepository extends CrudRepository<EntityContact, Long> {
    @Query(value = "select ce.contact_id from contacts_entities ce " +
            "where ce.contact_id = :contactId and ce.contact_type = :contactType", nativeQuery = true)
    Iterable<Long> findContactIdsByContactIdAndContactType(Long contactId, String contactType);
}
