package com.clubesycarreraspopulares.clubescarreras.dto;

import lombok.Data;

@Data
public class LocalizacionResponse {
    private Integer localizacionId;
    private String provincia;
    private String municipio;
    private String codigoPostal;
    private String direccion;
    private Double latitud;
    private Double longitud;
}
