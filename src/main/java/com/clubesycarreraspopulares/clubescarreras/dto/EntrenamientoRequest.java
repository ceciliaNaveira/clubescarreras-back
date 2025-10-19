package com.clubesycarreraspopulares.clubescarreras.dto;

import java.time.LocalTime;

import lombok.Data;

@Data
public class EntrenamientoRequest {
    private Integer clubId;
    private String diaSemana;
    private LocalTime hora;
    private String lugarEntrenamiento;
    private String nivel;
    private String descripcion;
}

