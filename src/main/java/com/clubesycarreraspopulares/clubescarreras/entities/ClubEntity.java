package com.clubesycarreraspopulares.clubescarreras.entities;

import jakarta.persistence.*;
import lombok.Data;

/**
 * Entidad que representa la tabla 'Club'.
 */
@Data
@Entity
@Table(name = "Club")
public class ClubEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_club")
    private Integer idClub;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "contacto", length = 150)
    private String contacto;

    @Column(name = "web", length = 255)
    private String web;

    // Relación con Localizacion
    @ManyToOne
    @JoinColumn(name = "id_localizacion", nullable = false)
    private LocalizacionEntity localizacion;
}

