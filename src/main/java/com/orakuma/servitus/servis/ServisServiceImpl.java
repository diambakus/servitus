package com.orakuma.servitus.servis;

import com.orakuma.servitus.unit.Unit;
import com.orakuma.servitus.utils.RepositoriesHandler;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.*;

@Service
@Transactional
public class ServisServiceImpl implements ServisService {
    private final ServisRepository    servisRepository;
    private final ServisMapper        servisMapper;
    private final RepositoriesHandler repositoriesHandler;

    public ServisServiceImpl(
            final ServisRepository servisRepository,
            final ServisMapper servisMapper,
            final RepositoriesHandler repositoriesHandler
    ) {
        this.servisRepository = servisRepository;
        this.servisMapper = servisMapper;
        this.repositoriesHandler = repositoriesHandler;
    }

    @Override
    public ServisDto get(Long servisId) {
        Servis servis = repositoriesHandler.getServisById(servisId);
        return servisMapper.toServisDto(servis);
    }

    @Override
    public Iterable<ServisDto> getAll() {
        return null;
    }

    @Override
    public ServisDto create(ServisDto servisDto) {
        Servis servis = servisMapper.toServis(servisDto);

        servisDto.unitsDto().forEach(unitDto -> {
            Unit unit = repositoriesHandler.getUnitById(unitDto.id());
            if (unit != null) {
                servis.addUnit(unit);
            }
        });

        servis.setCreated(LocalDate.now());
        servis.setActive(true);

        Servis persistedServis = servisRepository.save(servis);
        return servisMapper.toServisDto(persistedServis);
    }

    @Override
    public ServisDto update(ServisDto servisDto) {
        return null;
    }

    @Override
    public void delete(Long servisId) {
    }

    @Override
    public List<ServisDto> getByUnit(Long unitId) {
        return null;
    }

    @Override
    public Iterable<ServisDto> getAllActive() {
        return null;
    }
}
