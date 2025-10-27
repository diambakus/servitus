package com.orakuma.servitus.fake;

import com.orakuma.servitus.unit.Unit;
import com.orakuma.servitus.unit.UnitDto;

import java.time.LocalDate;

public class UnitFK {
    private UnitFK() {
    }

    public static Unit getEntity() {
        Unit unit = new Unit();
        unit.setId(1L);
        unit.setName("Unit1");
        unit.setDescription("Sample unit");
        unit.setActive(true);
        unit.setCreated(LocalDate.now());

        return unit;
    }

    public static UnitDto getDto() {
        return new UnitDto(1L, "Unit1", "Sample unit", null);
    }
}
