package com.orakuma.servitus.unit;

import com.orakuma.servitus.organ.Organ;
import com.orakuma.servitus.organ.OrganDto;
import com.orakuma.servitus.organ.OrganMapper;
import com.orakuma.servitus.organ.OrganRepository;
import com.orakuma.servitus.utils.RepositoriesHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;


@Service
@Transactional
public class UnitServiceImpl implements UnitService {
    private final UnitRepository      repository;
    private final UnitMapper          mapper;
    private final OrganMapper         organMapper;
    private final UnitRepository      unitRepository;
    private final RepositoriesHandler repositoriesHandler;
    private static final Logger LOG = LoggerFactory.getLogger(UnitServiceImpl.class);

    public UnitServiceImpl(final UnitRepository repository, final UnitMapper mapper,
                           final OrganMapper organMapper, final OrganRepository organRepository,
                           final UnitRepository unitRepository, final RepositoriesHandler repositoriesHandler
    ) {
        this.repository = repository;
        this.mapper = mapper;
        this.organMapper = organMapper;
        this.unitRepository = unitRepository;
        this.repositoriesHandler = repositoriesHandler;
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

        if (Objects.isNull(unit.getOrgan())) {
            LOG.error("The unit organization is Null.");
            return Optional.empty();
        }

        Organ organ = repositoriesHandler.getOrganById(unitDto.organDto().id());
        unit.setOrgan(organ);
        unit.setActive(true);
        unit.setCreated(LocalDate.now());
        Unit unitPersisted = repository.save(unit);
        return Optional.ofNullable(mapper.toUnitDto(unitPersisted));
    }

    @Override
    public Optional<UnitDto> getBy(Long id) {
        Unit unit = repositoriesHandler.getUnitById(id);
        return Optional.of(mapper.toUnitDto(unit));
    }

    @Override
    public List<UnitDto> getByOrgan(Long organId) {
        List<Unit> units = repository.findActiveUnitsByOrgan(organId);
        return mapper.toUnitsDto(units);
    }

    @Override
    public OrganDto getOrgan(Long unitId) {
        Unit unit = repositoriesHandler.getUnitById(unitId);
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
        Unit unit = repositoriesHandler.getUnitById(id);

        LinkedHashMap<String, String> attributesAndValues = Stream
                .concat(fieldsContentMap.entrySet().stream(), unit.getAttributes().entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v2, LinkedHashMap::new));

        unit.setAttributes(attributesAndValues);
        Unit updatedUnit = unitRepository.save(unit);
        UnitDto updatedUnitDto = mapper.toUnitDto(updatedUnit);
        return updatedUnitDto;
    }

    private Unit makeInactive(Long unitId) {
        Unit unit = repositoriesHandler.getUnitById(unitId);
        unit.setActive(false);
        unit.setModified(LocalDate.now());
        repository.save(unit);
        return unit;
    }
}
