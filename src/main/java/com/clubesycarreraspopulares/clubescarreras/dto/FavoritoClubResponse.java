package com.clubesycarreraspopulares.clubescarreras.dto;

import lombok.Data;

@Data
public class FavoritoClubResponse {
    private Integer usuarioId;
    private String usuarioNombre;
    private Integer clubId;
    private String clubNombre;
}

