package com.orakuma.servitus.organ;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.time.LocalDate;

@Mapper
public interface OrganMapper {
    @Mapping(target = "active", ignore = true)
    @Mapping(target = "modified", ignore = true)
    @Mapping(target = "id", ignore = true)
    @Mapping(target = "created", ignore = true)
    Organ toOrgan(OrganDto organDto);
    @Mapping(target = "dateToString(organ.created)")
    OrganDto toOrganDto(Organ organ);
    Iterable<OrganDto> toOrgansDto(Iterable<Organ> organs);

    default String dateToString(LocalDate date) {
        return date.toString();
    }
}
