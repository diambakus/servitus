package com.orakuma.servitus.dependency;

import com.orakuma.servitus.servis.Servis;
import com.orakuma.servitus.servis.ServisRepository;
import com.orakuma.servitus.utils.RepositoriesHandler;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.atomic.AtomicReference;
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

@Service
@AllArgsConstructor
public class DependencyServiceImpl implements DependencyService {
    private final DependencyRepository dependencyRepository;
    private final ServisRepository servisRepository;
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

    @Override
    public LinkedHashSet<DependencyDto> getDependenciesToAddByServisId(Long id) {
        Optional<Servis> servis = servisRepository.findWithDependenciesById(id);
        AtomicReference<LinkedHashSet<DependencyEntity>> dependencyEntitiesAtomicReference = new AtomicReference<>();
        servis.ifPresent(presentServis -> {
            Set<Long> servisDependenciesIds;
            if (presentServis.getDependencies().isEmpty()) {
                dependencyEntitiesAtomicReference.set(toDependencyEntities(dependencyRepository.findAll()));
            } else {
                servisDependenciesIds = getDependenciesIdsByServis(presentServis);
                Iterable<DependencyEntity> servisNonRelatedDependencyEntities = dependencyRepository
                        .findAllNotInTheList(servisDependenciesIds);
                dependencyEntitiesAtomicReference.set(toDependencyEntities(servisNonRelatedDependencyEntities));
            }
        });
        return dependencyMapper.toDto(dependencyEntitiesAtomicReference.get());
    }

    private LinkedHashSet<Long> getDependenciesIdsByServis(Servis servis) {
        if (servis.getDependencies() == null || servis.getDependencies().isEmpty()) {
            return new LinkedHashSet<>();
        }
        return servis
                .getDependencies()
                .stream()
                .filter(Objects::nonNull)
                .map(DependencyEntity::getId)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }

    private LinkedHashSet<DependencyEntity> toDependencyEntities(Iterable<DependencyEntity> dependencyEntities) {
        return StreamSupport
                .stream(dependencyEntities.spliterator(), false)
                .collect(Collectors.toCollection(LinkedHashSet::new));
    }
}