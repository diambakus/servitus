package com.orakuma.servitus.servis;

import java.util.List;

public interface ServisService {
    ServisDto get(Long servisId);
    Iterable<ServisDto> getAll();
    ServisDto create(ServisDto servisDto);
    ServisDto update(ServisDto servisDto);
    void delete(Long servisId);
    List<ServisDto> getByUnit(Long unitId);
    Iterable<ServisDto> getAllActive();
}
