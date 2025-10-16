package com.clubesycarreraspopulares.clubescarreras.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.time.LocalDateTime;

@Data
@Entity
@Table(name = "Comentario")
public class ComentarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_comentario")
    private Integer idComentario;

    // Relación con Usuario
    @ManyToOne
    @JoinColumn(name = "id_usuario", nullable = false)
    private UsuarioEntity usuario;

    // Relación con Club
    @ManyToOne
    @JoinColumn(name = "id_club", nullable = false)
    private ClubEntity club;

    @Column(name = "texto", nullable = false, columnDefinition = "TEXT")
    private String texto;

    @Column(name = "fecha")
    private LocalDateTime fecha;

    @Column(name = "valoracion")
    private Byte valoracion;
}
