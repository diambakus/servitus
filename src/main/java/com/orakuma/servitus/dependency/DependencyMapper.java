package com.orakuma.servitus.dependency;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.LinkedHashSet;

@Mapper
public interface DependencyMapper {
    DependencyDto toDto(DependencyEntity dependencyEntity);
    @Mapping(target = "created", ignore = true)
    DependencyEntity toEntity(DependencyDto dependencyDto);
    LinkedHashSet<DependencyDto> toDto(LinkedHashSet<DependencyEntity> dependencyEntities);
}
