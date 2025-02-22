package com.orakuma.servitus.unit;

import com.orakuma.servitus.organ.OrganDto;
import com.orakuma.servitus.servis.ServisDto;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public record UnitDto(
        Long id,
        String name,
        String note,
        OrganDto organDto,
        Map<String, String> attributes,
        Set<ServisDto> servisDtoSet
) {

    public UnitDto(Long id, String name, String note, OrganDto organDto, Map<String, String> attributes, Set<ServisDto> servisDtoSet) {
        this.id = id;
        this.name = name;
        this.note = note;
        this.organDto = organDto;
        this.attributes = attributes == null ? Map.of() : new LinkedHashMap<>(attributes);
        this.servisDtoSet = servisDtoSet == null ? Collections.emptySet() : servisDtoSet;
    }

    @Override
    public Map<String, String> attributes() {
        return Collections.unmodifiableMap(attributes);
    }

    @Override
    public Set<ServisDto> servisDtoSet() {
        return Collections.unmodifiableSet(servisDtoSet);
    }
}