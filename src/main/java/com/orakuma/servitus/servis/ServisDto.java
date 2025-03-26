package com.orakuma.servitus.servis;

import com.orakuma.servitus.unit.UnitDto;

import java.util.*;

public record ServisDto(
        Long id,
        String name,
        Double price,
        ServisType servisType,
        String additionalDetails,
        Set<UnitDto> unitsDto,
        Map<Integer, String> requisites
) {

    public ServisDto(Long id, String name, Double price, ServisType servisType, String additionalDetails,
                     Set<UnitDto> unitsDto, Map<Integer, String> requisites
    ) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.servisType = servisType;
        this.additionalDetails = additionalDetails;
        this.unitsDto = unitsDto == null ? Collections.emptySet() : unitsDto;
        this.requisites = requisites == null ? Collections.emptyMap() : new LinkedHashMap<>(requisites);
    }

    @Override
    public Set<UnitDto> unitsDto() {
        return new HashSet<>(unitsDto);
    }

    @Override
    public Map<Integer, String> requisites() {
        return Collections.unmodifiableMap(requisites);
    }
}
