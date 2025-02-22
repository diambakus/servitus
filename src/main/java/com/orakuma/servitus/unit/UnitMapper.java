package com.orakuma.servitus.unit;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UnitMapper {
    List<UnitDto> toUnitsDto(List<Unit> units);
    @Mapping(target = "organDto", source = "unit.organ")
    UnitDto toUnitDto(Unit unit);
    @Mapping(target = "organ", source = "unitDto.organDto")
    @Mapping(target = "active", ignore = true)
    Unit toUnit(UnitDto unitDto);
}
