package com.clubesycarreraspopulares.clubescarreras.dto;

import lombok.Data;

@Data
public class EntrenamientoResponse {
    private Integer entrenamientoId;
    private Integer clubId;
    private String clubNombre;
    private String diaSemana;
    private String lugarEntrenamiento;
    private String nivel;
    private String descripcion;
}
