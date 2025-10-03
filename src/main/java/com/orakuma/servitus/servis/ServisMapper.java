package com.orakuma.servitus.servis;

import com.orakuma.servitus.organ.Organ;
import com.orakuma.servitus.organ.OrganDto;
import com.orakuma.servitus.unit.Unit;
import com.orakuma.servitus.unit.UnitDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Mapper(componentModel = "spring")
public interface ServisMapper {

    @Mapping(target = "unitsDto", expression = "java(toUnitsDto(servis.getUnits()))")
    ServisDto toServisDto(Servis servis);

    @Mapping(target = "servisType", expression = "java(convertToServisType(servisDto.servisType()))")
    Servis toServis(ServisDto servisDto);

    List<ServisDto> toServisDtos(Iterable<Servis> servisList);

    @Mapping(target = "organDto", source = "organ")
    UnitDto toUnitDto(Unit unit);

    OrganDto toOrganDto(Organ organ);

    default ServisType convertToServisType(String servisType) {
        return switch (servisType) {
            case "FINE" -> ServisType.FINE;
            case "GOODS" -> ServisType.GOODS;
            case "SERVICE" -> ServisType.SERVICE;
            default -> ServisType.INVALID;
        };
    }

    default Set<UnitDto> toUnitsDto(Set<Unit> units) {
        return units == null ? Set.of() : units.stream().map(this::toUnitDto).collect(Collectors.toSet());
    }
}
