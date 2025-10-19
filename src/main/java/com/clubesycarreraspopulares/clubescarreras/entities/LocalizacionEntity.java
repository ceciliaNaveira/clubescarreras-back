package com.clubesycarreraspopulares.clubescarreras.entities;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "localizacion")
public class LocalizacionEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_localizacion", nullable = false)
    private Integer localizacionId;

    @Column(name = "provincia", nullable = false)
    private String provincia;

    @Column(name = "municipio", nullable = false)
    private String municipio;

    @Column(name = "codigo_postal", length = 5)
    private String codigoPostal;

    @Column(name = "direccion")
    private String direccion;

    @Column(name = "latitud", precision = 9, scale = 6)
    private BigDecimal latitud;

    @Column(name = "longitud", precision = 9, scale = 6)
    private BigDecimal longitud;
}
