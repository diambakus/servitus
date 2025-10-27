package com.orakuma.servitus.unit;

import com.orakuma.servitus.organ.Organ;
import com.orakuma.servitus.organ.OrganDto;
import com.orakuma.servitus.organ.OrganMapper;
import com.orakuma.servitus.utils.RepositoriesHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;


@Service
@AllArgsConstructor
@Slf4j
public class UnitServiceImpl implements UnitService {
    private final UnitMapper          mapper;
    private final OrganMapper         organMapper;
    private final UnitRepository      unitRepository;
    private final RepositoriesHandler repositoriesHandler;

    @Override
    public List<UnitDto> gets() {
        return mapper.toUnitsDto(unitRepository.findActiveUnits());
    }

    public List<UnitDto> getAllInactive() {
        return mapper.toUnitsDto(unitRepository.findInactiveUnits());
    }

    @Override
    @Transactional
    public Optional<UnitDto> create(UnitDto unitDto) {
        Unit unit = mapper.toUnit(unitDto);

        if (Objects.isNull(unit.getOrgan())) {
            log.error("The unit organization is Null.");
            return Optional.empty();
        }

        Organ organ = repositoriesHandler.getOrganById(unitDto.organDto().id());
        unit.setOrgan(organ);
        unit.setActive(true);
        unit.setCreated(LocalDate.now());
        Unit unitPersisted = unitRepository.save(unit);
        return Optional.ofNullable(mapper.toUnitDto(unitPersisted));
    }

    @Override
    public Optional<UnitDto> getBy(Long id) {
        Unit unit = repositoriesHandler.getUnitById(id);
        return Optional.of(mapper.toUnitDto(unit));
    }

    @Override
    public List<UnitDto> getByOrgan(Long organId) {
        List<Unit> units = unitRepository.findActiveUnitsByOrgan(organId);
        return mapper.toUnitsDto(units);
    }

    @Override
    public OrganDto getOrgan(Long unitId) {
        Unit unit = repositoriesHandler.getUnitById(unitId);
        return organMapper.toOrganDto(unit.getOrgan());
    }

    @Override
    @Transactional
    public int inactivate(Long unitId) {
        if (!unitRepository.existsById(unitId)) {
            return -1;
        }

        int items = unitRepository.countByAssociatedServices(unitId);
        if (items > 0) {
            return 0;
        } else {
            makeInactive(unitId);
            return 1;
        }
    }

    private void makeInactive(Long unitId) {
        Unit unit = repositoriesHandler.getUnitById(unitId);
        unit.setActive(false);
        unit.setModified(LocalDate.now());
        unitRepository.save(unit);
    }
}
