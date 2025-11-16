package com.clubesycarreraspopulares.clubescarreras.mapper;

import com.clubesycarreraspopulares.clubescarreras.dto.EntrenamientoRequest;
import com.clubesycarreraspopulares.clubescarreras.dto.EntrenamientoResponse;
import com.clubesycarreraspopulares.clubescarreras.entities.EntrenamientoEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface EntrenamientoMapper {

    @Mapping(source = "clubId", target = "club.clubId")
    @Mapping(source = "hora", target = "hora") 
    EntrenamientoEntity fromDtoRequestToEntity(EntrenamientoRequest request);

    @Mapping(source = "entrenamientoId", target = "entrenamientoId")
    @Mapping(source = "club.clubId", target = "clubId")
    @Mapping(source = "club.nombre", target = "clubNombre")
    @Mapping(source = "diaSemana", target = "diaSemana")
    @Mapping(source = "hora", target = "hora") 
    @Mapping(source = "lugarEntrenamiento", target = "lugarEntrenamiento")
    @Mapping(source = "nivel", target = "nivel")
    @Mapping(source = "descripcion", target = "descripcion")
    EntrenamientoResponse fromEntityToDTO(EntrenamientoEntity entity);
}
