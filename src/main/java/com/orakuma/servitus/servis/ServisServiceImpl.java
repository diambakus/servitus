package com.orakuma.servitus.servis;

import com.orakuma.servitus.dependency.DependencyDto;
import com.orakuma.servitus.dependency.DependencyEntity;
import com.orakuma.servitus.dependency.DependencyMapper;
import com.orakuma.servitus.dependency.DependencyRepository;
import com.orakuma.servitus.unit.Unit;
import com.orakuma.servitus.utils.RepositoriesHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
public class ServisServiceImpl implements ServisService {
    private final ServisRepository    servisRepository;
    private final ServisMapper        servisMapper;
    private final RepositoriesHandler repositoriesHandler;
    private final DependencyMapper dependencyMapper;
    private final DependencyRepository dependencyRepository;

    @Override
    public ServisDto get(Long servisId) {
        Servis servis = repositoriesHandler.getServisById(servisId);
        return servisMapper.toServisDto(servis);
    }

    @Override
    public List<ServisDto> getAll() {
        Iterable<Servis> servisEntityList = servisRepository.findAll();
        return servisMapper.toServisDtos(servisEntityList);
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
    @Transactional
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
    @Transactional
    public ServisDto update(Long servisId, Map<String, Object> fields) {
        Servis servis = repositoriesHandler.getServisById(servisId);
        fields.forEach((key, value) -> {
            if (key.compareToIgnoreCase("active") == 0) {
                servis.setActive((Boolean) value);
            } else if (key.compareToIgnoreCase("price") == 0) {
                servis.setPrice((Double) value);
            } else if (key.compareToIgnoreCase("name") == 0) {
                servis.setName((String) value);
            } else if (key.compareToIgnoreCase("description") == 0) {
                servis.setDescription((String) value);
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
        List<Servis> servisEntityList = servisRepository.findAllActiveByUnit(unitId);
        return servisMapper.toServisDtos(servisEntityList);
    }

    @Override
    public List<ServisDto> getAllActive() {
        return servisMapper.toServisDtos(servisRepository.findAllActive());
    }

    @Override
    public ServisDto removeUnits(Long servisId, List<Long> unitId) {
        return null;
    }

    @Override
    public Set<DependencyDto> getDependenciesForServis(Long id) {
        Optional<Servis> servis = servisRepository.findWithDependenciesById(id);
        LinkedHashSet<DependencyEntity> dependencyEntities = new LinkedHashSet<>();
        servis.ifPresent(servisEntity -> dependencyEntities.addAll(servisEntity.getDependencies()));
        return dependencyMapper.toDto(dependencyEntities);
    }

    @Override
    @Transactional
    public Void addDependencies(Long id, LinkedHashSet<Long> dependenciesId) {
        Servis servis = repositoriesHandler.getServisById(id);
        Iterable<DependencyEntity> chosenDependenciesEntries = dependencyRepository.findAllById(dependenciesId);
        for (DependencyEntity dependencyEntity : chosenDependenciesEntries) {
           servis.addDependency(dependencyEntity);
        }
        servisRepository.save(servis);

        return null;
    }
}