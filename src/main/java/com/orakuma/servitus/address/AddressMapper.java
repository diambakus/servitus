package com.orakuma.servitus.address;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface AddressMapper {
    @Mapping(target="addressType",
            expression = "java(" +
                    "com.orakuma.servitus.utils.EntityTypeConverter.toAddressType(addressDto.addressType())" +
                    ")"
    )
    @Mapping(target = "created", ignore = true)
    Address toAddress(AddressDto addressDto);
    AddressDto toAddressDto(Address address);
    Iterable<AddressDto> toAddressDto(Iterable<Address> addresses);
    Iterable<Address> toAddress(Iterable<AddressDto> addressDtos);
}
