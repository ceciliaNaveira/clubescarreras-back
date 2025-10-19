package com.clubesycarreraspopulares.clubescarreras.mapper;

import com.clubesycarreraspopulares.clubescarreras.dto.ClubRequest;
import com.clubesycarreraspopulares.clubescarreras.dto.ClubResponse;
import com.clubesycarreraspopulares.clubescarreras.entities.ClubEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;

@Mapper(componentModel = "spring")
public interface ClubMapper {

    @Mapping(source = "localizacionId", target = "localizacion.localizacionId")
    ClubEntity fromDtoRequestToEntity(ClubRequest request);

    @Mapping(source = "clubId", target = "clubId")
    @Mapping(source = "localizacion.localizacionId", target = "localizacionId")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "descripcion", target = "descripcion")
    @Mapping(source = "contacto", target = "contacto")
    @Mapping(source = "web", target = "web")
    ClubResponse fromEntityToDTO(ClubEntity entity);

}

