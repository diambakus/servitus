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
    public List<ServisDto> getAll() {
        return null;
    }

    @Override
    public List<ServisDto> getAllOpen() {
        return List.of();
    }

    @Override
    public List<ServisDto> getAllOpenByRequestor() {
        return List.of();
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
    public ServisDto update(Long servisId, Map<String, Object> fields) {
        Servis servis = repositoriesHandler.getServisById(servisId);
        fields.forEach((key, value) -> {
            if (key.compareToIgnoreCase("active") == 0) {
                servis.setActive((Boolean) value);
            } else if (key.compareToIgnoreCase("price") == 0) {
                servis.setPrice((Double) value);
            } else if (key.compareToIgnoreCase("name") == 0) {
                servis.setName((String) value);
            } else if (key.compareToIgnoreCase("additionalDetails") == 0) {
                servis.setAdditionalDetails((String) value);
            }
        });

        Servis persistedServis = servisRepository.save(servis);
        return servisMapper.toServisDto(persistedServis);
    }

    @Override
    public void delete(Long servisId) {
    }

    @Override
    public List<ServisDto> getByUnit(Long unitId) {
        return servisMapper.toServisDtos(servisRepository.findAllActiveByUnit(unitId));
    }

    @Override
    public List<ServisDto> getAllActive() {
        return servisMapper.toServisDtos(servisRepository.findAllActive());
    }

    @Override
    public ServisDto addRequisites(Long servisId, Map<Integer, String> newRequisites) {
        Servis servis = repositoriesHandler.getServisById(servisId);

        newRequisites.forEach((key, value) -> {
            if (!value.equals(servis.getRequisites().get(key))) {
                servis.getRequisites().put(key, value);
            }
        });
        servis.setModified(LocalDate.now());
        Servis persistedServis = servisRepository.save(servis);
        return servisMapper.toServisDto(persistedServis);
    }

    @Override
    public ServisDto removeRequisites(Long servisId, Map<Integer, String> newRequisites) {
        return null;
    }

    @Override
    public ServisDto removeUnits(Long servisId, List<Long> unitId) {
        return null;
    }
}