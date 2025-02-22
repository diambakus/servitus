package com.orakuma.servitus.servis;

public interface ServisService {
    ServisDto get(Long servisId);
    Iterable<ServisDto> getAll();
    ServisDto create(ServisDto servisDto);
    ServisDto update(ServisDto servisDto);
    void delete(Long servisId);
    Iterable<ServisDto> getByUnit(Long unitId);
    Iterable<ServisDto> getAllActive();
}
