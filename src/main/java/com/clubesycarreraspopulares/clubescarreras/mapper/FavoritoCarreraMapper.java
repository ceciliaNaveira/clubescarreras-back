package com.clubesycarreraspopulares.clubescarreras.mapper;

import com.clubesycarreraspopulares.clubescarreras.dto.FavoritoCarreraRequest;
import com.clubesycarreraspopulares.clubescarreras.dto.FavoritoCarreraResponse;
import com.clubesycarreraspopulares.clubescarreras.entities.FavoritoCarreraEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FavoritoCarreraMapper {

    @Mapping(source = "usuarioId", target = "usuario.usuarioId") // se asigna en controller
    @Mapping(source = "carreraId", target = "carrera.carreraId") // se asigna en controller
    FavoritoCarreraEntity fromDtoRequestToEntity(FavoritoCarreraRequest request);

    @Mapping(source = "usuario.usuarioId", target = "usuarioId")
    @Mapping(source = "usuario.nombre", target = "usuarioNombre")
    @Mapping(source = "carrera.carreraId", target = "carreraId")
    @Mapping(source = "carrera.nombre", target = "carreraNombre")
    FavoritoCarreraResponse fromEntityToDTO(FavoritoCarreraEntity entity);
}
