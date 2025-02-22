package com.orakuma.servitus.servis;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface ServisMapper {

    ServisDto toServisDto(Servis servis);
    @Mapping(target="servisType", expression = "java(convertToServisType(servisDto.servisType()))")
    Servis toServis(ServisDto servisDto);
    List<ServisDto> toServisDtos(Iterable<Servis> servisList);

    default ServisType convertToServisType(String servisType) {
        return switch (servisType) {
            case "FINE" -> ServisType.FINE;
            case "GOODS" -> ServisType.GOODS;
            case "SERVICE" -> ServisType.SERVICE;
            default -> {
                yield ServisType.INVALID;
            }
        };
    }
}
