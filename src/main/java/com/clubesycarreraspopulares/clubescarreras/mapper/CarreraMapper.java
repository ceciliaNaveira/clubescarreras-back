package com.clubesycarreraspopulares.clubescarreras.mapper;

import com.clubesycarreraspopulares.clubescarreras.dto.CarreraRequest;
import com.clubesycarreraspopulares.clubescarreras.dto.CarreraResponse;
import com.clubesycarreraspopulares.clubescarreras.entities.CarreraEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface CarreraMapper {

    // DTO -> Entity
    @Mapping(source = "clubId", target = "club.clubId")
    @Mapping(source = "localizacionId", target = "localizacion.localizacionId")
    CarreraEntity fromDtoRequestToEntity(CarreraRequest request);

    // Entity -> DTO
    @Mapping(source = "carreraId", target = "carreraId")
    @Mapping(source = "club.clubId", target = "clubId")
    @Mapping(source = "club.nombre", target = "clubNombre")
    @Mapping(source = "localizacion.localizacionId", target = "localizacionId")
    @Mapping(source = "localizacion.provincia", target = "provincia")
    @Mapping(source = "localizacion.municipio", target = "municipio")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "descripcion", target = "descripcion")
    @Mapping(source = "fecha", target = "fecha")
    @Mapping(source = "distanciaKm", target = "distanciaKm")
    @Mapping(source = "webOficial", target = "webOficial")
    @Mapping(source = "posterUrl", target = "posterUrl")
    CarreraResponse fromEntityToDTO(CarreraEntity entity);
}