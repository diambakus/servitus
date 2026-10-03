package com.orakuma.servitus.dependency;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.LinkedHashSet;

@Mapper
public interface DependencyMapper {
  @Mapping(target = "type", expression = "java(toDependencyDtoType(dependencyEntity.getType()))")
  DependencyDto toDto(DependencyEntity dependencyEntity);
  @Mapping(target = "created", ignore = true)
  @Mapping(target = "type", expression = "java(toDependencyType(dependencyDto.type()))")
  DependencyEntity toEntity(DependencyDto dependencyDto);
  LinkedHashSet<DependencyDto> toDto(LinkedHashSet<DependencyEntity> dependencyEntities);

  default DependencyType toDependencyType(DependencyDtoType type) {
    return switch (type) {
      case PERSONAL -> DependencyType.PERSONAL;
      case CONTACT -> DependencyType.CONTACT;
      case PARENTAL -> DependencyType.PARENTAL;
      case CIVIL_FINANCIAL -> DependencyType.CIVIL_FINANCIAL;
    };
  }

  default DependencyDtoType toDependencyDtoType(DependencyType type) {
    return switch (type) {
      case PERSONAL -> DependencyDtoType.PERSONAL;
      case CONTACT -> DependencyDtoType.CONTACT;
      case PARENTAL -> DependencyDtoType.PARENTAL;
      case CIVIL_FINANCIAL -> DependencyDtoType.CIVIL_FINANCIAL;
    };
  }
}
