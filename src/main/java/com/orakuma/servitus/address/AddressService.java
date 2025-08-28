package com.orakuma.servitus.address;

import java.util.Map;
import java.util.Optional;

public interface AddressService {
    Iterable<Address> getAddresses();
    Iterable<AddressDto> getAddressesByEntityIdAndType(Long entityId, String entityType);
    Optional<AddressDto> addAddress(Long entityId, String entityType, AddressDto addressDto);
    Optional<AddressDto> update(Long addressId, Map<String, Object> updatingFieldsValue);
}
