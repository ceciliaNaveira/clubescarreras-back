package com.clubesycarreraspopulares.clubescarreras.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity que representa la tabla 'Rol' en la base de datos.
 * Cada instancia de esta clase corresponde a un registro en la tabla.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Rol") // nombre exacto de la tabla en MySQL
public class RolEntity {

        // Clave primaria autogenerada de la tabla
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_rol", nullable = false)
    private Integer idRol;

    // Nombre del rol, obligatorio y único, hasta 50 caracteres
    @Column(name = "nombre_rol", nullable = false, unique = true, length = 50)
    private String nombreRol;
    
}
