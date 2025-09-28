package com.clubesycarreraspopulares.clubescarreras.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

/**
Entity que representa la tabla 'Localizacion' en la base de datos.
Cada instancia de esta clase corresponde a un registro en la tabla.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Localizacion") // nombre exacto de la tabla en MySQL

public class LocalizacionEntity {
    //Clave primaria autogenerada de la tabla 
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_localizacion", nullable = false)
    private Integer idLocalizacion;

    //Provincia de la localización, obligatorio, hasta 100 caracteres
    @Column(name = "provincia", nullable = false, length = 100)
    private String provincia;

    //Municipio de la localización, obligatorio, hasta 100 caracteres
    @Column(name = "municipio", nullable = false, length = 100)
    private String municipio;

    //Código postal de la localización, hasta 5 caracteres
    @Column(name = "codigo_postal", length = 5)
    private String codigoPostal;

    //Dirección completa, hasta 255 caracteres
    @Column(name = "direccion", length = 255)
    private String direccion;

    //Latitud geográfica con 6 decimales
    @Column(name = "latitud", precision = 9, scale = 6)
    private BigDecimal latitud;

    //Longitud geográfica con 6 decimales
    @Column(name = "longitud", precision = 9, scale = 6)
    private BigDecimal longitud;   
}
