package com.orakuma.servitus.utils;

import com.orakuma.servitus.address.AddressType;
import com.orakuma.servitus.contact.ContactType;

public final class EntityTypeConverter {
    private EntityTypeConverter(){}
    public static final String ORGAN_ENTITY_TYPE = "ORGAN";
    public static final String UNIT_ENTITY_TYPE = "UNIT";

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

    public static String toContactTypeView(ContactType contactType) {
        return contactType.name();
    }

    public static String toAddressTypeView(AddressType addressType) {
        return addressType.name();
    }
}
