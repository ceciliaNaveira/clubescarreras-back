package com.clubesycarreraspopulares.clubescarreras.mapper;

import com.clubesycarreraspopulares.clubescarreras.dto.FavoritoClubRequest;
import com.clubesycarreraspopulares.clubescarreras.dto.FavoritoClubResponse;
import com.clubesycarreraspopulares.clubescarreras.entities.FavoritoClubEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface FavoritoClubMapper {

    @Mapping(source = "usuarioId", target = "usuario.usuarioId") // se asigna en controller
    @Mapping(source = "clubId", target = "club.clubId")       // se asigna en controller
    FavoritoClubEntity fromDtoRequestToEntity(FavoritoClubRequest request);

    @Mapping(source = "usuario.usuarioId", target = "usuarioId")
    @Mapping(source = "usuario.nombre", target = "usuarioNombre")
    @Mapping(source = "club.clubId", target = "clubId")
    @Mapping(source = "club.nombre", target = "clubNombre")
    FavoritoClubResponse fromEntityToDTO(FavoritoClubEntity entity);
}
