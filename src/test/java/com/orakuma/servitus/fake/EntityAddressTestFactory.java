package com.orakuma.servitus.fake;

import com.orakuma.servitus.address.Address;
import com.orakuma.servitus.address.EntityAddress;

public class EntityAddressTestFactory {

    public static EntityAddress createFakeEntityAddress() {
        return new EntityAddress()
                .id(1L)
                .entityType("CUSTOMER")
                .entityId(2001L)
                .address(AddressTestFactory.createFakeAddress());
    }

    public static EntityAddress createFakeEntityAddress(Long id, String entityType, Long entityId, Address address) {
        return new EntityAddress()
                .id(id)
                .entityType(entityType)
                .entityId(entityId)
                .address(address);
    }
}

