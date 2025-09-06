package com.orakuma.servitus.fake;

import com.orakuma.servitus.address.Address;
import com.orakuma.servitus.address.AddressType;

import java.time.LocalDateTime;

public class AddressTestFactory {

    public static Address createFakeAddress() {
        return new Address()
                .setId(1L)
                .setStreet("123 Main Street")
                .setCity("Springfield")
                .setState("IL")
                .setCountry("USA")
                .setPostalCode("62704")
                .setAddressType(AddressType.MAIN)
                .setCreated(LocalDateTime.now());
    }

    public static Address createFakeAddress(Long id, String street, String city, String state,
                                            String country, String postalCode, AddressType type) {
        return new Address()
                .setId(id)
                .setStreet(street)
                .setCity(city)
                .setState(state)
                .setCountry(country)
                .setPostalCode(postalCode)
                .setAddressType(type)
                .setCreated(LocalDateTime.now());
    }
}
