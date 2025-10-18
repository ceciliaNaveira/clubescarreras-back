package com.clubesycarreraspopulares.clubescarreras.dto;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class ComentarioRequest {
    private Integer usuarioId;
    private Integer clubId;
    private String texto;
    private Integer valoracion; // 1 a 5
}
