package com.clubesycarreraspopulares.clubescarreras.dto;

import lombok.Data;

@Data
public class ClubRequest {
    private Integer localizacionId;
    private String nombre;
    private String descripcion;
    private String contacto;
    private String web;
}
