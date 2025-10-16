package com.clubesycarreraspopulares.clubescarreras.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Table(name = "Entrenamiento")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EntrenamientoEntity {

    public enum Nivel {
        Iniciación,
        Intermedio,
        Avanzado
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entrenamiento")
    private Integer idEntrenamiento;

    @ManyToOne
    @JoinColumn(name = "id_club", nullable = false)
    private ClubEntity club;

    @Column(name = "fecha", nullable = false)
    private LocalDateTime fecha;

    @Column(name = "lugar_entrenamiento", nullable = false, length = 255)
    private String lugarEntrenamiento;

    @Enumerated(EnumType.STRING)
    @Column(name = "nivel", nullable = false)
    private Nivel nivel;

    @Column(name = "descripcion")
    private String descripcion;
}
