package com.orakuma.servitus.servis;

import java.util.List;
import java.util.Map;

public interface ServisService {
    ServisDto get(Long servisId);
    List<ServisDto> getAll();
    List<ServisDto> getAllOpen();
    List<ServisDto> getAllOpenByRequestor();
    ServisDto create(ServisDto servisDto);
    ServisDto update(Long servisId, Map<String, Object> fields);
    void delete(Long servisId);
    List<ServisDto> getByUnit(Long unitId);
    List<ServisDto> getAllActive();
    ServisDto addRequisites(Long servisId, Map<Integer, String> newRequisites);
    ServisDto removeRequisites(Long servisId, Map<Integer, String> newRequisites);
    ServisDto removeUnits(Long servisId, List<Long> unitId);
}
