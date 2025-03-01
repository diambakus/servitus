package com.orakuma.servitus.servis;

import com.orakuma.servitus.unit.UnitDto;

import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

public record ServisDto(
        Long id,
        String name,
        Double price,
        ServisType servisType,
        String additionalDetails,
        Set<UnitDto> unitsDto
) {

    public ServisDto(Long id, String name, Double price, ServisType servisType, String additionalDetails, Set<UnitDto> unitsDto) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.servisType = servisType;
        this.additionalDetails = additionalDetails;
        this.unitsDto = unitsDto == null ? Collections.emptySet() : unitsDto;
    }

    @Override
    public Set<UnitDto> unitsDto() {
        return new HashSet<>(unitsDto);
    }
}
