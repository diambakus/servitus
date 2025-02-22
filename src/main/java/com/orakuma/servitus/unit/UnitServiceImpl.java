package com.orakuma.servitus.unit;

import com.orakuma.servitus.organ.Organ;
import com.orakuma.servitus.organ.OrganDto;
import com.orakuma.servitus.organ.OrganMapper;
import com.orakuma.servitus.organ.OrganRepository;
import com.orakuma.servitus.organ.exceptions.OrganNotFoundException;
import com.orakuma.servitus.unit.exceptions.UnitNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@Transactional
public class UnitServiceImpl implements UnitService {
    private final UnitRepository  repository;
    private final UnitMapper      mapper;
    private final OrganRepository organRepository;
    private final OrganMapper     organMapper;
    private final UnitRepository unitRepository;

    public UnitServiceImpl(final UnitRepository repository, final UnitMapper mapper,
                           final OrganMapper organMapper, final OrganRepository organRepository, UnitRepository unitRepository) {
        this.repository = repository;
        this.organRepository = organRepository;
        this.mapper = mapper;
        this.organMapper = organMapper;
        this.unitRepository = unitRepository;
    }

    @Override
    public List<UnitDto> gets() {
        return mapper.toUnitsDto(repository.findActiveUnits());
    }

    public List<UnitDto> getAllInactive() {
        return mapper.toUnitsDto(repository.findInactiveUnits());
    }

    @Override
    public Optional<UnitDto> create(UnitDto unitDto) {
        Unit unit = mapper.toUnit(unitDto);
        Organ organ = organRepository.findById(unitDto.organDto().id()).orElseThrow(OrganNotFoundException::new);
        unit.setOrgan(organ);
        unit.setActive(true);
        unit.setCreated(LocalDate.now());
        Unit unitPersisted = repository.save(unit);
        return Optional.ofNullable(mapper.toUnitDto(unitPersisted));
    }

    @Override
    public Optional<UnitDto> getBy(Long id) {
        if (id == null || !repository.existsById(id)) {
            return Optional.empty();
        }

        Optional<Unit> unit = repository.findById(id);
        if (unit.isEmpty()) {
            return Optional.empty();
        }
        return Optional.of(mapper.toUnitDto(unit.get()));
    }

    @Override
    public List<UnitDto> getByOrgan(Long organId) {
        var units = repository.findActiveUnitsByOrgan(organId);
        return mapper.toUnitsDto(units);
    }

    @Override
    public OrganDto getOrgan(Long unitId) {
        Unit unit = fetchUnitById(unitId);
        return organMapper.toOrganDto(unit.getOrgan());
    }

    @Override
    public int inactivate(Long unitId) {
        if (!repository.existsById(unitId)) {
            return -1;
        }

        int items = repository.countByAssociatedServices(unitId);
        if (items > 0) {
            return 0;
        } else {
            makeInactive(unitId);
            return 1;
        }
    }

    @Override
    public UnitDto updateUnitWithProperties(Long id, Map<String, String> fieldsContentMap) {
        Unit unit = fetchUnitById(id);

        LinkedHashMap<String, String> attributesAndValues = Stream
                .concat(fieldsContentMap.entrySet().stream(), unit.getAttributes().entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v2, LinkedHashMap::new));

        unit.setAttributes(attributesAndValues);
        Unit updatedUnit = unitRepository.save(unit);
        UnitDto updatedUnitDto = mapper.toUnitDto(updatedUnit);
        return updatedUnitDto;
    }

    private Unit makeInactive(Long unitId) {
        Unit unit = fetchUnitById(unitId);
        unit.setActive(false);
        unit.setModified(LocalDate.now());
        repository.save(unit);
        return unit;
    }

    private Unit fetchUnitById(Long unitId) {
        Unit unit = unitRepository.findById(unitId).orElseThrow(() -> {
            String errorMessage = String.format("Unit with id %s not found", unitId);
            return new UnitNotFoundException(errorMessage);
        });
        return unit;
    }
}
