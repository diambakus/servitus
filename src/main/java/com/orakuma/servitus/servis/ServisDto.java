package com.orakuma.servitus.servis;

import com.orakuma.servitus.unit.UnitDto;

import java.util.*;

public record ServisDto(
        Long id,
        String name,
        Double price,
        String servisType,
        String description,
        Set<UnitDto> unitsDto
) {

    public ServisDto(Long id,
                     String name,
                     Double price,
                     String servisType,
                     String description,
                     Set<UnitDto> unitsDto
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.servisType = servisType;
        this.description = description;
        this.unitsDto = unitsDto == null ? Collections.emptySet() : unitsDto;
    }

    @Override
    public Set<UnitDto> unitsDto() {
        return new HashSet<>(unitsDto);
    }
}
