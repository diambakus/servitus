package com.orakuma.servitus.servis;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ServisMapper {

    ServisDto toServisDto(Servis servis);
    @Mapping(target="convertToServisType(servisDto.servisType)")
    Servis toServis(ServisDto servisDto);
    Iterable<ServisDto> toServisDto(Iterable<Servis> servisList);

    default ServisType convertToServisType(short servisType) {
        return switch (servisType) {
            case 1-> ServisType.FINE;
            case 2-> ServisType.GOODS;
            case 3-> ServisType.SERVICE;
            default -> {
                yield ServisType.INVALID;
            }
        };
    }
}
