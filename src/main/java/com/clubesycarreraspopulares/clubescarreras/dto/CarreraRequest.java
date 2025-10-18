package com.clubesycarreraspopulares.clubescarreras.dto;

import lombok.Data;

import java.time.LocalDate;

@Data
public class CarreraRequest {
    private Integer clubId; // opcional
    private Integer localizacionId; // obligatorio
    private String nombre;
    private String descripcion;
    private LocalDate fecha;
    private Double distanciaKm;
    private String webOficial;
    private String posterUrl;
}
