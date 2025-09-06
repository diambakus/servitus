package com.orakuma.servitus.address;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EntityAddressRepository extends CrudRepository<EntityAddress, Long> {
    @Query(value = "select ea.address_id from addresses_entities ea where ea.entity_id = :entityId " +
            "and ea.entity_type=:entityType", nativeQuery = true)
    Iterable<Long> findAddressesByEntityId(@Param("entityId") Long entityId, @Param("entityType") String entityType);
}