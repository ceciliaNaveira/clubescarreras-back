package com.clubesycarreraspopulares.clubescarreras.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "club", schema = "dbo")
public class ClubEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_club", nullable = false, unique = true)
    private Integer clubId;

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
}
