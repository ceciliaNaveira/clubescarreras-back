package com.clubesycarreraspopulares.clubescarreras.mapper;

import com.clubesycarreraspopulares.clubescarreras.dto.RolRequest;
import com.clubesycarreraspopulares.clubescarreras.dto.RolResponse;
import com.clubesycarreraspopulares.clubescarreras.entities.RolEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface RolMapper {

    // DTO -> Entity
    @Mapping(source = "nombreRol", target = "nombreRol")
    RolEntity fromDtoRequestToEntity(RolRequest rolRequest);

    // Entity -> DTO
    @Mapping(source = "rolId", target = "rolId")
    @Mapping(source = "nombreRol", target = "nombreRol")
    RolResponse fromEntityToDTO(RolEntity rolEntity);
}

