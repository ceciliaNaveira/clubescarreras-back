package com.clubesycarreraspopulares.clubescarreras.entities;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@IdClass(FavoritoCarreraId.class)
@Entity
@Table(name = "favorito_carrera")
public class FavoritoCarreraEntity implements Serializable {

    @Id
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioEntity usuario;

    @Id
    @ManyToOne
    @JoinColumn(name = "id_carrera", nullable = false)
    private CarreraEntity carrera;
}
