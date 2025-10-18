package com.clubesycarreraspopulares.clubescarreras.entities;

import jakarta.persistence.*;
import lombok.*;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
@Entity
@Table(name = "localizacion", schema = "dbo")
public class LocalizacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_localizacion", nullable = false, unique = true)
    private Integer localizacionId;

    @Column(name = "provincia", nullable = false, length = 100)
    private String provincia;

    @Column(name = "municipio", nullable = false, length = 100)
    private String municipio;

    @Column(name = "codigo_postal", length = 5)
    private String codigoPostal;

    @Column(name = "direccion", length = 255)
    private String direccion;

    @Column(name = "latitud", precision = 9, scale = 6)
    private Double latitud;

    @Column(name = "longitud", precision = 9, scale = 6)
    private Double longitud;
}
