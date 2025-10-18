package com.clubesycarreraspopulares.clubescarreras.dto;

import lombok.Data;

@Data
public class LocalizacionRequest {
    private String provincia;
    private String municipio;
    private String codigoPostal;
    private String direccion;
    private Double latitud;
    private Double longitud;
}

