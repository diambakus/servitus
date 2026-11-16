package com.orakuma.servitus.dependency;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

import com.orakuma.servitus.config.CacheConfig;
import com.orakuma.servitus.servis.Servis;
import com.orakuma.servitus.servis.ServisMapper;
import com.orakuma.servitus.servis.ServisRepository;
import com.orakuma.servitus.servis.ServisService;
import com.orakuma.servitus.servis.ServisServiceImpl;
import com.orakuma.servitus.utils.RepositoriesHandler;
import java.time.LocalDateTime;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

/** Exercises the services through their Spring proxies, so the cache annotations are in effect. */
@SpringJUnitConfig({CacheConfig.class, DependencyServiceImpl.class, ServisServiceImpl.class})
class ServisDependenciesCacheTest {
  private static final String PUBLIC_ID = "srv_1";
  private static final String OTHER_PUBLIC_ID = "srv_2";

  @MockitoBean private DependencyRepository dependencyRepository;
  @MockitoBean private ServisRepository servisRepository;
  @MockitoBean private DependencyMapper dependencyMapper;
  @MockitoBean private ServisMapper servisMapper;
  @MockitoBean private RepositoriesHandler repositoriesHandler;

  @Autowired private DependencyService dependencyService;
  @Autowired private ServisService servisService;
  @Autowired private CacheManager cacheManager;

  private final DependencyEntity entity =
      new DependencyEntity(7L, "Passport", 1, true, LocalDateTime.now(), DependencyType.PERSONAL);
  private final DependencyDto dto = new DependencyDto(7L, "Passport", 1, DependencyDtoType.PERSONAL);

  @BeforeEach
  void setUp() {
    cacheManager.getCache(CacheConfig.SERVIS_DEPENDENCIES).clear();
    when(dependencyRepository.findAllByServisPublicId(any())).thenReturn(List.of(entity));
    when(dependencyMapper.toDto(any(LinkedHashSet.class)))
        .thenAnswer(invocation -> new LinkedHashSet<>(List.of(dto)));
    when(dependencyMapper.toDto(any(DependencyEntity.class))).thenReturn(dto);
    when(dependencyMapper.toEntity(any())).thenReturn(entity);
    when(dependencyRepository.save(any())).thenReturn(entity);
    when(servisRepository.findWithDependenciesByPublicId(any()))
        .thenReturn(Optional.of(new Servis()));
  }

  @Test
  void repeatedReadsHitTheDatabaseOnce() {
    Set<DependencyDto> first = dependencyService.getDependenciesByServisPublicId(PUBLIC_ID);
    Set<DependencyDto> second = dependencyService.getDependenciesByServisPublicId(PUBLIC_ID);

    assertEquals(Set.of(dto), first);
    assertEquals(first, second);
    verify(dependencyRepository, times(1)).findAllByServisPublicId(PUBLIC_ID);
  }

  @Test
  void cachedResultIsReadOnly() {
    Set<DependencyDto> result = dependencyService.getDependenciesByServisPublicId(PUBLIC_ID);

    assertThrows(UnsupportedOperationException.class, result::clear);
  }

  @Test
  void addingDependenciesEvictsOnlyThatServis() {
    dependencyService.getDependenciesByServisPublicId(PUBLIC_ID);
    dependencyService.getDependenciesByServisPublicId(OTHER_PUBLIC_ID);

    servisService.addDependencies(PUBLIC_ID, new LinkedHashSet<>(List.of(7L)));
    dependencyService.getDependenciesByServisPublicId(PUBLIC_ID);
    dependencyService.getDependenciesByServisPublicId(OTHER_PUBLIC_ID);

    verify(dependencyRepository, times(2)).findAllByServisPublicId(PUBLIC_ID);
    verify(dependencyRepository, times(1)).findAllByServisPublicId(OTHER_PUBLIC_ID);
  }

  @Test
  void removingDependenciesEvictsThatServis() {
    dependencyService.getDependenciesByServisPublicId(PUBLIC_ID);

    servisService.removeDependencies(PUBLIC_ID, new LinkedHashSet<>(List.of(7L)));
    dependencyService.getDependenciesByServisPublicId(PUBLIC_ID);

    verify(dependencyRepository, times(2)).findAllByServisPublicId(PUBLIC_ID);
  }

  @Test
  void deletingServisEvictsThatServis() {
    when(servisRepository.deleteByPublicId(PUBLIC_ID)).thenReturn(1);
    dependencyService.getDependenciesByServisPublicId(PUBLIC_ID);

    servisService.deleteByPublicId(PUBLIC_ID);
    dependencyService.getDependenciesByServisPublicId(PUBLIC_ID);

    verify(dependencyRepository, times(2)).findAllByServisPublicId(PUBLIC_ID);
  }

  @Test
  void updatingADependencyEvictsEveryServis() {
    dependencyService.getDependenciesByServisPublicId(PUBLIC_ID);
    dependencyService.getDependenciesByServisPublicId(OTHER_PUBLIC_ID);

    dependencyService.update(dto);
    dependencyService.getDependenciesByServisPublicId(PUBLIC_ID);
    dependencyService.getDependenciesByServisPublicId(OTHER_PUBLIC_ID);

    verify(dependencyRepository, times(2)).findAllByServisPublicId(PUBLIC_ID);
    verify(dependencyRepository, times(2)).findAllByServisPublicId(OTHER_PUBLIC_ID);
  }

  @Test
  void deactivatingADependencyEvictsEveryServis() {
    when(repositoriesHandler.getDependencyById(7L)).thenReturn(entity);
    dependencyService.getDependenciesByServisPublicId(PUBLIC_ID);

    dependencyService.deactivate(7L);
    dependencyService.getDependenciesByServisPublicId(PUBLIC_ID);

    verify(dependencyRepository, times(2)).findAllByServisPublicId(PUBLIC_ID);
  }
}
