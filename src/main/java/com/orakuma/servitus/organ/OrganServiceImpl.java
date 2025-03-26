package com.orakuma.servitus.organ;

import com.orakuma.servitus.utils.RepositoriesHandler;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@Transactional
public class OrganServiceImpl implements OrganService {

    private final OrganRepository     organRepository;
    private final OrganMapper         organMapper;
    private final RepositoriesHandler repositoriesHandler;

    public OrganServiceImpl(
                            final OrganRepository organRepository,
                            final OrganMapper organMapper,
                            final RepositoriesHandler repositoriesHandler
    ) {
        this.organRepository = organRepository;
        this.organMapper = organMapper;
        this.repositoriesHandler = repositoriesHandler;
    }

    @Override
    public Iterable<OrganDto> gets() {
        return organMapper.toOrgansDto(organRepository.findAllActive());
    }

    @Override
    public Iterable<OrganDto> getAllInactive() {
        return organMapper.toOrgansDto(organRepository.findAllInactive());
    }

    @Override
    public Optional<OrganDto> persist(OrganDto organDto) {
        var category = organMapper.toOrgan(organDto);
        if (organDto.id() == null || !organRepository.existsById(organDto.id())) {
            category.setCreated(LocalDate.now());
            category.setActive(true);
        } else {
            category.setModified(LocalDate.now());
        }

        OrganDto persistedCategoryDto = organMapper.toOrganDto(organRepository.save(category));
        return Optional.ofNullable(persistedCategoryDto);
    }

    @Override
    public Optional<OrganDto> findBy(Long id) {
        if (organRepository.existsById(id)) {
            return Optional.ofNullable(organMapper.toOrganDto(organRepository.findById(id).get()));
        }
        return Optional.empty();
    }

    @Override
    public int inactivate(Long organId) {
        if (!organRepository.existsById(organId)) {
            return -1;
        }
        Long units = organRepository.countByAssociatedUnits(organId);
        if (units > 0) {
            return 0;
        } else {
            makeInactive(organId);
            return 1;
        }
    }

    @Override
    public Optional<OrganDto> saveAttributes(Long id, Map<String, String> attributes) {
        Organ organ = repositoriesHandler.getOrganById(id);

        LinkedHashMap<String, String> updatedAttributes = Stream
                .concat(attributes.entrySet().stream(), organ.getAttributes().entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v2, LinkedHashMap::new));

        organ.setAttributes(updatedAttributes);
        organ.setModified(LocalDate.now());
        Organ persisted = organRepository.save(organ);
        return Optional.ofNullable(organMapper.toOrganDto(persisted));
    }

    private Organ makeInactive(Long organId) {
        Organ organ = repositoriesHandler.getOrganById(organId);
        organ.setActive(false);
        organ.setModified(LocalDate.now());
        organRepository.save(organ);
        return organ;
    }

    @Override
    public OrganDto updateOrganWithProperties(Long id, Map<String, String> fieldsContentMap) {
        Organ organ = repositoriesHandler.getOrganById(id);

        LinkedHashMap<String, String> attributesAndValues = Stream
                .concat(fieldsContentMap.entrySet().stream(), organ.getAttributes().entrySet().stream())
                .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue, (v1, v2) -> v2, LinkedHashMap::new));

        organ.setAttributes(attributesAndValues);
        Organ updatedOrgan = organRepository.save(organ);
        OrganDto updatedOrganDto = organMapper.toOrganDto(updatedOrgan);
        return updatedOrganDto;
    }
}
