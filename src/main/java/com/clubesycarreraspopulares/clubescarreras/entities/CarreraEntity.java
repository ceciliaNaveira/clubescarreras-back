package com.clubesycarreraspopulares.clubescarreras.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "carrera", schema = "dbo")
public class CarreraEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_carrera", nullable = false, unique = true)
    private Integer carreraId;

    @ManyToOne
    @JoinColumn(name = "id_club")
    private ClubEntity club; // opcional

    @ManyToOne
    @JoinColumn(name = "id_localizacion", nullable = false)
    private LocalizacionEntity localizacion;

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
}
