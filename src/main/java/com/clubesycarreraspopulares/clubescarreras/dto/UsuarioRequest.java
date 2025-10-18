package com.clubesycarreraspopulares.clubescarreras.dto;

import lombok.Data;

@Data
public class UsuarioRequest {
    private String nombre;
    private String email;
    private String contraseña;
    private Integer rolId;
}
