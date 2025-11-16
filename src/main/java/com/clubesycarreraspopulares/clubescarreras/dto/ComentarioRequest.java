package com.clubesycarreraspopulares.clubescarreras.dto;

import lombok.Data;

@Data
public class ComentarioRequest {
    private Integer usuarioId;
    private Integer clubId;
    private String texto;
    private Integer valoracion; 
}
