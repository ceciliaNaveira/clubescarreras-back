package com.clubesycarreraspopulares.clubescarreras.entities;

import com.fasterxml.jackson.annotation.JsonIgnore; // <- Import necesario
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Entrenamiento")
public class EntrenamientoEntity {

    public enum Nivel {
        Iniciación, Intermedio, Avanzado
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_entrenamiento", nullable = false)
    private Integer idEntrenamiento;

    @ManyToOne
    @JoinColumn(name = "id_club", nullable = false)
    @JsonIgnore // <- Ignoramos la referencia al club al serializar
    private ClubEntity club;

    @Column(name = "dia_semana", nullable = false)
    private String diaSemana;

    @Column(name = "lugar_entrenamiento", nullable = false)
    private String lugarEntrenamiento;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Nivel nivel;

    @Column(columnDefinition = "TEXT")
    private String descripcion;
}
