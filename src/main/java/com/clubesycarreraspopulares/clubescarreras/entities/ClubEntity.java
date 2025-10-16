package com.clubesycarreraspopulares.clubescarreras.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Club")
public class ClubEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_club", nullable = false)
    private Integer idClub;

    @ManyToOne
    @JoinColumn(name = "id_localizacion", nullable = false)
    private LocalizacionEntity localizacion;

    @Column(name = "nombre", nullable = false, length = 150)
    private String nombre;

    @Column(name = "descripcion", columnDefinition = "TEXT")
    private String descripcion;

    @Column(name = "contacto", length = 150)
    private String contacto;

    @Column(name = "web", length = 255)
    private String web;

    @OneToMany(mappedBy = "club", cascade = CascadeType.ALL)
    private List<EntrenamientoEntity> entrenamientos;
}
