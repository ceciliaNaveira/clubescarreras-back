package com.clubesycarreraspopulares.clubescarreras.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Entity que representa la tabla 'Usuario' en la base de datos.
 * Cada instancia de esta clase corresponde a un registro en la tabla.
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "Usuario") // nombre exacto de la tabla en MySQL
public class UsuarioEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id_usuario", nullable = false)
    private Integer idUsuario;

    @ManyToOne
    @JoinColumn(name = "id_rol", nullable = false)
    private RolEntity rol;

    @Column(name = "nombre", nullable = false, length = 100)
    private String nombre;

    @Column(name = "email", nullable = false, length = 100, unique = true)
    private String email;

    @Column(name = "contraseña", nullable = false, length = 255)
    private String contraseña;
}
