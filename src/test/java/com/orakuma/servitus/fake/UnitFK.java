package com.orakuma.servitus.fake;

import com.orakuma.servitus.unit.Unit;
import com.orakuma.servitus.unit.UnitDto;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

public class UnitFK {
    private UnitFK() {
    }

    public static Unit getEntity() {
        Map<String, String> attributes = new LinkedHashMap<>();
        attributes.put("color", "red");
        Unit unit = new Unit();
        unit.setId(1L);
        unit.setName("Unit1");
        unit.setDescription("Sample unit");
        unit.setActive(true);
        unit.setAttributes(attributes);
        unit.setCreated(LocalDate.now());

        return unit;
    }

    public static UnitDto getDto() {
        return new UnitDto(1L, "Unit1", "Sample unit", null, Map.of());
    }
}
