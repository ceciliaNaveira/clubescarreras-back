package com.clubesycarreraspopulares.clubescarreras.mapper;

import com.clubesycarreraspopulares.clubescarreras.dto.UsuarioRequest;
import com.clubesycarreraspopulares.clubescarreras.dto.UsuarioResponse;
import com.clubesycarreraspopulares.clubescarreras.entities.UsuarioEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UsuarioMapper {

    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "contraseña", target = "contraseña")
    UsuarioEntity fromDtoRequestToEntity(UsuarioRequest usuarioRequest);

    @Mapping(source = "usuarioId", target = "usuarioId")
    @Mapping(source = "nombre", target = "nombre")
    @Mapping(source = "email", target = "email")
    @Mapping(source = "rol.rolId", target = "rolId")
    @Mapping(source = "rol.nombreRol", target = "nombreRol")
    UsuarioResponse fromEntityToDTO(UsuarioEntity usuarioEntity);
}
