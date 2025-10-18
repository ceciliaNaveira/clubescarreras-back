package com.clubesycarreraspopulares.clubescarreras.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComentarioResponse {
    private Integer comentarioId;
    private Integer usuarioId;
    private String usuarioNombre;
    private Integer clubId;
    private String clubNombre;
    private String texto;
    private LocalDateTime fecha;
    private Integer valoracion;
}

