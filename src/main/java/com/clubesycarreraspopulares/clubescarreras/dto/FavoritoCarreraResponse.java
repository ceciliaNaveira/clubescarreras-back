package com.clubesycarreraspopulares.clubescarreras.dto;

import lombok.Data;

@Data
public class FavoritoCarreraResponse {
    private Integer usuarioId;
    private String usuarioNombre;
    private Integer carreraId;
    private String carreraNombre;
}

