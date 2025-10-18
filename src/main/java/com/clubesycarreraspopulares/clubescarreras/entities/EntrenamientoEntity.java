package com.clubesycarreraspopulares.clubescarreras.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "entrenamiento", schema = "dbo")
public class EntrenamientoEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entrenamiento", nullable = false, unique = true)
    private Integer entrenamientoId;

    @ManyToOne
    @JoinColumn(name = "id_club", nullable = false)
    private ClubEntity club;

    @Column(name = "dia_semana", nullable = false, length = 20)
    private String diaSemana; // Lunes, Martes, etc.

    @Column(name = "lugar_entrenamiento", nullable = false, length = 255)
    private String lugarEntrenamiento;

    @Column(name = "nivel", nullable = false, length = 20)
    private String nivel; // Iniciación, Intermedio, Avanzado

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;
}
