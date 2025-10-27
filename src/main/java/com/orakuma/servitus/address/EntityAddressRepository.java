package com.orakuma.servitus.address;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface EntityAddressRepository extends CrudRepository<EntityAddress, Long> {
    @Query("select ea.address.id from EntityAddress ea " +
            "where ea.entityId = :entityId " +
            "and ea.entityType=:entityType")
    Iterable<Long> findAddressesByEntityId(@Param("entityId") Long entityId, @Param("entityType") String entityType);
}