package com.orakuma.servitus.fake;

import com.orakuma.servitus.address.Address;
import com.orakuma.servitus.address.AddressType;

import java.time.LocalDateTime;

public class AddressTestFactory {

    public static Address createFakeAddress() {
        return new Address()
                .id(1L)
                .street("123 Main Street")
                .city("Springfield")
                .state("IL")
                .country("USA")
                .postalCode("62704")
                .addressType(AddressType.MAIN)
                .created(LocalDateTime.now());
    }

    public static Address createFakeAddress(Long id, String street, String city, String state,
                                            String country, String postalCode, AddressType type) {
        return new Address()
                .id(id)
                .street(street)
                .city(city)
                .state(state)
                .country(country)
                .postalCode(postalCode)
                .addressType(type)
                .created(LocalDateTime.now());
    }
}
