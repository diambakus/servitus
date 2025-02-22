package com.orakuma.servitus.unit;

import com.orakuma.servitus.organ.OrganDto;

import java.util.List;
import java.util.Map;
import java.util.Optional;

public interface UnitService {
    List<UnitDto> gets();
    Optional<UnitDto> create(UnitDto unitDto);
    Optional<UnitDto> getBy(Long id);
    List<UnitDto> getByOrgan(Long id);
    OrganDto getOrgan(Long unitId);
    int inactivate(Long unitId);
    UnitDto updateUnitWithProperties(Long id, Map<String, String> fieldsContentMap);
}
