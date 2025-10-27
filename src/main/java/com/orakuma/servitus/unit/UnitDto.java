package com.orakuma.servitus.unit;

import com.orakuma.servitus.organ.OrganDto;

public record UnitDto(
        Long id,
        String name,
        String description,
        OrganDto organDto
) {

    public UnitDto(Long id, String name, String description, OrganDto organDto) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.organDto = organDto;
    }
}