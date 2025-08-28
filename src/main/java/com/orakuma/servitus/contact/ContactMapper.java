package com.orakuma.servitus.contact;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ContactMapper {
    ContactDto toDto(Contact contact);
    @Mapping(target = "contactType",
            expression = "java(" +
                    "com.orakuma.servitus.utils.EntityTypeConverter.toContactType(contactDto.contactType())" +
                    ")"
    )
    Contact toEntity(ContactDto contactDto);
    Iterable<ContactDto> toDto(Iterable<Contact> contacts);
    Iterable<Contact> toEntity(Iterable<ContactDto> contactDtos);
}
