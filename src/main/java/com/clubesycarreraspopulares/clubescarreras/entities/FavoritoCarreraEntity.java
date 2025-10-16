package com.clubesycarreraspopulares.clubescarreras.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@IdClass(FavoritoCarreraId.class)
@Table(name = "Favorito_Carrera")
public class FavoritoCarreraEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioEntity usuario;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_carrera", nullable = false)
    private CarreraEntity carrera;
}
