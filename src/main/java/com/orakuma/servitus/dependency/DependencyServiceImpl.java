package com.orakuma.servitus.dependency;

import com.orakuma.servitus.utils.RepositoriesHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class DependencyServiceImpl implements DependencyService {
    private final DependencyRepository dependencyRepository;
    private final DependencyMapper dependencyMapper;
    private final RepositoriesHandler repositoriesHandler;

    @Override
    @Transactional
    public Optional<DependencyDto> persist(DependencyDto dependencyDto) {
        DependencyEntity dependencyEntity = dependencyMapper.toEntity(dependencyDto);
        dependencyEntity.setActive(true);
        dependencyEntity.setCreated(LocalDateTime.now());
        DependencyEntity savedDependency = dependencyRepository.save(dependencyEntity);
        return Optional.of(dependencyMapper.toDto(savedDependency));
    }

    @Override
    @Transactional
    public Optional<DependencyDto> update(DependencyDto dependencyDto) {
        DependencyEntity dependencyEntity = dependencyMapper.toEntity(dependencyDto);
        DependencyEntity savedDependency = dependencyRepository.save(dependencyEntity);
        return Optional.of(dependencyMapper.toDto(savedDependency));
    }

    @Override
    public LinkedHashSet<DependencyDto> fetchAll() {
        LinkedHashSet<DependencyEntity> entities = StreamSupport
                .stream(dependencyRepository
                        .findAll()
                        .spliterator(),
                        false)
                .collect(Collectors.toCollection(LinkedHashSet::new));
        return dependencyMapper.toDto(entities);
    }

    @Override
    @Transactional
    public Optional<DependencyDto> deactivate(Long id) {
        DependencyEntity dependency = repositoriesHandler.getDependencyById(id);
        dependency.setActive(false);
        DependencyEntity updatedDependency = dependencyRepository.save(dependency);
        return Optional.of(dependencyMapper.toDto(updatedDependency));
    }
}