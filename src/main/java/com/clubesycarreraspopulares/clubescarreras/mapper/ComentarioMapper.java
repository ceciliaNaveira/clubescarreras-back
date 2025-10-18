package com.clubesycarreraspopulares.clubescarreras.mapper;

import com.clubesycarreraspopulares.clubescarreras.dto.ComentarioRequest;
import com.clubesycarreraspopulares.clubescarreras.dto.ComentarioResponse;
import com.clubesycarreraspopulares.clubescarreras.entities.ComentarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface ComentarioMapper {

    @Mapping(source = "usuarioId", target = "usuario") // se asigna en controller
    @Mapping(source = "clubId", target = "club")       // se asigna en controller
    ComentarioEntity fromDtoRequestToEntity(ComentarioRequest request);

    @Mapping(source = "comentarioId", target = "comentarioId")
    @Mapping(source = "usuario.usuarioId", target = "usuarioId")
    @Mapping(source = "usuario.nombre", target = "usuarioNombre")
    @Mapping(source = "club.clubId", target = "clubId")
    @Mapping(source = "club.nombre", target = "clubNombre")
    @Mapping(source = "texto", target = "texto")
    @Mapping(source = "fecha", target = "fecha")
    @Mapping(source = "valoracion", target = "valoracion")
    ComentarioResponse fromEntityToDTO(ComentarioEntity entity);
}

