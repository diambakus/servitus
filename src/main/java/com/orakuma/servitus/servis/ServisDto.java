package com.orakuma.servitus.servis;

import com.orakuma.servitus.unit.UnitDto;

import java.util.*;

public record ServisDto(
        Long id,
        String name,
        Double price,
        String servisType,
        String description,
        Set<UnitDto> unitsDto,
        String publicId
) {

    public ServisDto(Long id,
                     String name,
                     Double price,
                     String servisType,
                     String description,
                     Set<UnitDto> unitsDto,
                     String publicId
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.servisType = servisType;
        this.description = description;
        this.unitsDto = unitsDto == null ? Collections.emptySet() : unitsDto;
        this.publicId = publicId;
    }

    @Override
    public Set<UnitDto> unitsDto() {
        return new HashSet<>(unitsDto);
    }
}
