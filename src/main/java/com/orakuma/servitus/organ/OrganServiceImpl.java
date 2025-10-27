package com.orakuma.servitus.organ;

import com.orakuma.servitus.utils.RepositoriesHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;


@Service
@AllArgsConstructor
public class OrganServiceImpl implements OrganService {

    private final OrganRepository     organRepository;
    private final OrganMapper         organMapper;
    private final RepositoriesHandler repositoriesHandler;

    @Override
    public Iterable<OrganDto> gets() {
        return organMapper.toOrgansDto(organRepository.findAllActive());
    }

    @Override
    public Iterable<OrganDto> getAllInactive() {
        return organMapper.toOrgansDto(organRepository.findAllInactive());
    }

    @Override
    @Transactional
    public Optional<OrganDto> persist(OrganDto organDto) {
        Organ organ = organMapper.toOrgan(organDto);
        if (organDto.id() == null || !organRepository.existsById(organDto.id())) {
            organ.setCreated(LocalDate.now());
            organ.setActive(true);
        } else {
            organ.setModified(LocalDate.now());
        }

        Organ persistedOrgan = organRepository.save(organ);
        OrganDto persistedCategoryDto = organMapper.toOrganDto(persistedOrgan);
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
    @Transactional
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

    private void makeInactive(Long organId) {
        Organ organ = repositoriesHandler.getOrganById(organId);
        organ.setActive(false);
        organ.setModified(LocalDate.now());
        organRepository.save(organ);
    }
}