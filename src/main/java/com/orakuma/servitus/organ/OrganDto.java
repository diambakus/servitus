package com.orakuma.servitus.organ;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;

public record OrganDto(Long id, String name, String note, Map<String, String> attributes, String content) {
    public OrganDto(Long id, String name, String note, Map<String, String> attributes, String content) {
        this.id = id;
        this.name = name;
        this.note = note;
        this.content = content;
        this.attributes = attributes == null ? Map.of() : new LinkedHashMap<>(attributes);
    }

    @Override
    public Map<String, String> attributes() {
        return Collections.unmodifiableMap(attributes);
    }
}