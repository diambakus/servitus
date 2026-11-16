package com.orakuma.servitus.servis;

import com.orakuma.servitus.config.CacheConfig;
import com.orakuma.servitus.dependency.DependencyEntity;
import com.orakuma.servitus.dependency.DependencyRepository;
import com.orakuma.servitus.servis.exceptions.ServisNotFoundException;
import com.orakuma.servitus.unit.Unit;
import com.orakuma.servitus.utils.RepositoriesHandler;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.*;

@Service
@AllArgsConstructor
@Slf4j
public class ServisServiceImpl implements ServisService {
  private final ServisRepository servisRepository;
  private final ServisMapper servisMapper;
  private final RepositoriesHandler repositoriesHandler;
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
    log.info("Create Servis: {}", servisDto);
    Servis servis = servisMapper.toServis(servisDto);

    servisDto
        .unitsDto()
        .forEach(
            unitDto -> {
              Unit unit = repositoriesHandler.getUnitById(unitDto.id());
              if (unit != null) {
                servis.addUnit(unit);
              }
            });

    servis.setCreated(LocalDate.now());
    servis.setActive(true);
    servis.setPublicId(String.format("srv_%s", UUID.randomUUID()));
    Servis persistedServis = servisRepository.save(servis);
    return servisMapper.toServisDto(persistedServis);
  }

  @Override
  @Transactional
  public ServisDto update(Long servisId, Map<String, Object> fields) {
    Servis servis = repositoriesHandler.getServisById(servisId);
    fields.forEach(
        (key, value) -> {
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
  public void delete(Long servisId) {}

  @Override
  @Transactional
  @CacheEvict(cacheNames = CacheConfig.SERVIS_DEPENDENCIES, key = "#publicId")
  public void deleteByPublicId(String publicId) {
    if (servisRepository.deleteByPublicId(publicId) == 0) {
      throw new ServisNotFoundException("Servis not found with publicId: " + publicId);
    }
    log.info("Deleted servis with publicId: {}", publicId);
  }

  @Override
  public List<ServisDto> getByUnitPublicId(String unitPublicId) {
    List<Servis> servisEntityList = servisRepository.findAllActiveByUnitPublicId(unitPublicId);
    return servisMapper.toServisDtos(servisEntityList);
  }

  @Override
  public List<Long> getServicesIdsByUnitId(Long unitId) {
    return servisRepository.findActiveServicesIdsByUnit(unitId);
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
  @Transactional
  @CacheEvict(cacheNames = CacheConfig.SERVIS_DEPENDENCIES, key = "#publicId")
  public Void addDependencies(String publicId, LinkedHashSet<Long> dependenciesId) {
    log.info("Adding dependencies for servis: {}", publicId);
    Servis servis = getWithDependenciesByPublicId(publicId);
    Iterable<DependencyEntity> chosenDependenciesEntries =
        dependencyRepository.findAllById(dependenciesId);
    for (DependencyEntity dependencyEntity : chosenDependenciesEntries) {
      servis.addDependency(dependencyEntity);
    }
    servisRepository.save(servis);

    return null;
  }

  @Override
  @Transactional
  @CacheEvict(cacheNames = CacheConfig.SERVIS_DEPENDENCIES, key = "#publicId")
  public Void removeDependencies(String publicId, LinkedHashSet<Long> dependenciesId) {
    log.info("Removing dependencies for servis: {}", publicId);
    Servis servis = getWithDependenciesByPublicId(publicId);
    Iterable<DependencyEntity> chosenDependenciesEntries =
        dependencyRepository.findAllById(dependenciesId);
    chosenDependenciesEntries.forEach(servis::removeDependency);
    servisRepository.save(servis);
    return null;
  }

  private Servis getWithDependenciesByPublicId(String publicId) {
    return servisRepository
        .findWithDependenciesByPublicId(publicId)
        .orElseThrow(
            () -> new ServisNotFoundException("Servis not found with publicId: " + publicId));
  }

  @Override
  public ServisDto getServisByPublicId(String publicId) {
    try {
      Servis servis = servisRepository.findByPublicId(publicId);
      return servisMapper.toServisDto(servis);
    } catch (Exception e) {
      log.error("Failed to find service with publicId: {}", publicId);
      throw new RuntimeException(e);
    }
  }
}
