package com.orakuma.servitus.unit;

import com.orakuma.servitus.organ.OrganDto;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public record UnitDto(
        Long id,
        String name,
        String description,
        OrganDto organDto,
        Map<String, String> attributes
) {

    public UnitDto(Long id, String name, String description, OrganDto organDto, Map<String, String> attributes) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.organDto = organDto;
        this.attributes = attributes == null ? Map.of() : new LinkedHashMap<>(attributes);
    }

    @Override
    public Map<String, String> attributes() {
        return Collections.unmodifiableMap(attributes);
    }

}