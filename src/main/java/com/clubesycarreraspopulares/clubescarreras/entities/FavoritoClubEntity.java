package com.clubesycarreraspopulares.clubescarreras.entities;

import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "Favorito_Club")
@IdClass(FavoritoClubId.class) // Clase que representa la clave compuesta
public class FavoritoClubEntity {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioEntity usuario;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_club", nullable = false)
    private ClubEntity club;
}
