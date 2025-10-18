package com.clubesycarreraspopulares.clubescarreras.dto;

import lombok.Data;

@Data
public class FavoritoCarreraRequest {
    private Integer usuarioId;
    private Integer carreraId;
}

