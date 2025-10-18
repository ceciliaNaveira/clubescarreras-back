package com.clubesycarreraspopulares.clubescarreras.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CarreraResponse {
    private Integer carreraId;
    private Integer clubId;
    private Integer localizacionId;
    private String nombre;
    private String descripcion;
    private LocalDate fecha;
    private Double distanciaKm;
    private String webOficial;
    private String posterUrl;
    private String provincia;
    private String municipio;
    private String clubNombre;
}
