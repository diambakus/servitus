package com.orakuma.servitus.address;

public record AddressDto(
        Long          id,
        String        street,
        String        city,
        String        state,
        String        country,
        String        postalCode,
        String        addressNumber,
        Double        latitude,
        Double        longitude,
        String        addressType
) { }
