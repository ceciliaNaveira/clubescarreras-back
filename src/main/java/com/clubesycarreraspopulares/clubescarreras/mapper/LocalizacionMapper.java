package com.clubesycarreraspopulares.clubescarreras.mapper;

import com.clubesycarreraspopulares.clubescarreras.dto.LocalizacionRequest;
import com.clubesycarreraspopulares.clubescarreras.dto.LocalizacionResponse;
import com.clubesycarreraspopulares.clubescarreras.entities.LocalizacionEntity;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface LocalizacionMapper {

    // DTO -> Entity
    @Mapping(source = "provincia", target = "provincia")
    @Mapping(source = "municipio", target = "municipio")
    @Mapping(source = "codigoPostal", target = "codigoPostal")
    @Mapping(source = "direccion", target = "direccion")
    @Mapping(source = "latitud", target = "latitud")
    @Mapping(source = "longitud", target = "longitud")
    LocalizacionEntity fromDtoRequestToEntity(LocalizacionRequest request);

    // Entity -> DTO
    @Mapping(source = "localizacionId", target = "localizacionId")
    @Mapping(source = "provincia", target = "provincia")
    @Mapping(source = "municipio", target = "municipio")
    @Mapping(source = "codigoPostal", target = "codigoPostal")
    @Mapping(source = "direccion", target = "direccion")
    @Mapping(source = "latitud", target = "latitud")
    @Mapping(source = "longitud", target = "longitud")
    LocalizacionResponse fromEntityToDTO(LocalizacionEntity entity);
}
