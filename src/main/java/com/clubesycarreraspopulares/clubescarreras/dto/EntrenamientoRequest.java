package com.clubesycarreraspopulares.clubescarreras.dto;

import lombok.Data;

@Data
public class EntrenamientoRequest {
    private Integer clubId;
    private String diaSemana;
    private String lugarEntrenamiento;
    private String nivel;
    private String descripcion;
}

