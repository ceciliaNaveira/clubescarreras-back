package com.clubesycarreraspopulares.clubescarreras.entities;

import jakarta.persistence.*;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * Entidad que representa la tabla 'Carrera'.
 * Cada carrera puede estar asociada opcionalmente a un club
 * y debe tener una localización.
 */
@Data
@Entity
@Table(name = "Carrera")
public class CarreraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrera")
    private Integer idCarrera;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "fecha", nullable = false)
    private LocalDate fecha;

    @Column(name = "distancia_km", precision = 5, scale = 2)
    private BigDecimal distanciaKm;

    @Column(name = "web_oficial", length = 255)
    private String webOficial;

    @Column(name = "poster_url", length = 255)
    private String posterUrl;

    /** Relación opcional con Club */
    @ManyToOne
    @JoinColumn(name = "id_club")
    private ClubEntity club;

    /** Relación obligatoria con Localizacion */
    @ManyToOne(optional = false)
    @JoinColumn(name = "id_localizacion", nullable = false)
    private LocalizacionEntity localizacion;
}
