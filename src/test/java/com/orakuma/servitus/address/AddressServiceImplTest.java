package com.orakuma.servitus.address;

import java.util.Map;
import java.util.Optional;

public class AddressServiceImplTest implements AddressService {
    @Override
    public Iterable<Address> getAddresses() {
        return null;
    }

    @Override
    public Iterable<AddressDto> getAddressesByEntityIdAndType(Long entityId, String entityType) {
        return null;
    }

    @Override
    public Optional<AddressDto> addAddress(Long entityId, String EntityType, AddressDto addressDto) {
        return Optional.empty();
    }

    @Override
    public Optional<AddressDto> update(Long addressId, Map<String, Object> updatingFieldsValue) {
        return Optional.empty();
    }
}
