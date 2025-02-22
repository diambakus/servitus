package com.orakuma.servitus.servis;

import org.springframework.stereotype.Service;

@Service
public class ServisServiceImpl implements ServisService {
    private final ServisRepository servisRepository;
    private final ServisMapper servisMapper;

    public ServisServiceImpl(final ServisRepository servisRepository, final ServisMapper servisMapper) {
        this.servisRepository = servisRepository;
        this.servisMapper = servisMapper;
    }

    @Override
    public ServisDto get(Long servisId) {
        return null;
    }

    @Override
    public Iterable<ServisDto> getAll() {
        return null;
    }

    @Override
    public ServisDto create(ServisDto servisDto) {
        return null;
    }

    @Override
    public ServisDto update(ServisDto servisDto) {
        return null;
    }

    @Override
    public void delete(Long servisId) {

    }

    @Override
    public Iterable<ServisDto> getByUnit(Long unitId) {
        return null;
    }

    @Override
    public Iterable<ServisDto> getAllActive() {
        return null;
    }
}
