package com.clubesycarreraspopulares.clubescarreras.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Localizacion")
public class LocalizacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_localizacion", nullable = false)
    private Integer idLocalizacion;

    @Column(name = "provincia", nullable = false, length = 100)
    private String provincia;

    @Column(name = "municipio", nullable = false, length = 100)
    private String municipio;

    @Column(name = "codigo_postal", length = 10)
    private String codigoPostal;

    @Column(name = "direccion", length = 255)
    private String direccion;

    @Column(name = "latitud")
    private Double latitud;

    @Column(name = "longitud")
    private Double longitud;

    // Evitamos referencia circular
    @OneToMany(mappedBy = "localizacion")
    @JsonIgnore
    private List<ClubEntity> clubes;
}
