package com.orakuma.servitus.unit;

import com.orakuma.servitus.organ.OrganDto;

public record UnitDto(
    Long id, String name, String description, OrganDto organDto, String publicId) {}
