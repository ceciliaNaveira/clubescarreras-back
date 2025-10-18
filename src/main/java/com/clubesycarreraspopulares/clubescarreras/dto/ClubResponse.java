package com.clubesycarreraspopulares.clubescarreras.dto;

import lombok.Data;

@Data
public class ClubResponse {
    private Integer clubId;
    private Integer localizacionId;
    private String nombre;
    private String descripcion;
    private String contacto;
    private String web;
    private String provincia;
    private String municipio;
}
