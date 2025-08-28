package com.orakuma.servitus.utils;

import com.orakuma.servitus.address.AddressType;
import com.orakuma.servitus.contact.ContactType;

public final class EntityTypeConverter {
    private EntityTypeConverter(){}

    public static ContactType toContactType(String contactType) {
        return switch (contactType) {
            case "MAIN" -> ContactType.MAIN;
            default -> ContactType.INVALID;
        };
    }

    public static AddressType toAddressType(String addressType) {
        return switch (addressType) {
            case "MAIN" -> AddressType.MAIN;
            default -> AddressType.INVALID;
        };
    }
}
