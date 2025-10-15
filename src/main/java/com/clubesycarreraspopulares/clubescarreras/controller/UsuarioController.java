package com.clubesycarreraspopulares.clubescarreras.controller;

import com.clubesycarreraspopulares.clubescarreras.entities.UsuarioEntity;
import com.clubesycarreraspopulares.clubescarreras.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@RestController
@RequestMapping("/usuarios")
@Tag(name = "Usuario", description = "Endpoints para la entidad Usuario")
public class UsuarioController {

    private final UsuarioRepository usuarioRepository;

    /** Listar todos los usuarios */
    @Operation(summary = "Listar todos")
    @GetMapping
    public ResponseEntity<List<UsuarioEntity>> obtenerTodosLosUsuarios() {
        return ResponseEntity.ok(usuarioRepository.findAll());
    }

    /** Obtener un usuario por ID */
    @Operation(summary = "Obtener por id")
    @GetMapping("/{idUsuario}")
    public ResponseEntity<UsuarioEntity> obtenerUsuarioById(@PathVariable Integer idUsuario) {
        UsuarioEntity usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (Objects.isNull(usuario)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(usuario);
    }

    /** Crear un nuevo usuario */
    @Operation(summary = "Crear")
    @PostMapping
    public ResponseEntity<UsuarioEntity> añadirUsuario(@RequestBody UsuarioEntity usuario) {
        usuario.setIdUsuario(null); // Ignorar el ID que venga en el JSON
        UsuarioEntity saved = usuarioRepository.save(usuario);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /** Actualizar un usuario existente */
    @Operation(summary = "Actualizar por id")
    @PutMapping("/{idUsuario}")
    public ResponseEntity<UsuarioEntity> actualizarUsuario(@PathVariable Integer idUsuario,
                                                           @RequestBody UsuarioEntity body) {
        UsuarioEntity usuario = usuarioRepository.findById(idUsuario).orElse(null);
        if (Objects.isNull(usuario)) {
            return ResponseEntity.notFound().build();
        }

        usuario.setNombre(body.getNombre());
        usuario.setEmail(body.getEmail());
        usuario.setContraseña(body.getContraseña());
        usuario.setRol(body.getRol());

        UsuarioEntity updated = usuarioRepository.save(usuario);
        return ResponseEntity.ok(updated);
    }

    /** Eliminar un usuario por ID */
    @Operation(summary = "Eliminar por id")
    @DeleteMapping("/{idUsuario}")
    public ResponseEntity<Void> eliminarUsuario(@PathVariable Integer idUsuario) {
        if (!usuarioRepository.existsById(idUsuario)) {
            return ResponseEntity.notFound().build();
        }
        usuarioRepository.deleteById(idUsuario);
        return ResponseEntity.noContent().build();
    }

    /** Buscar usuarios por nombre o email */
    @Operation(summary = "Buscar por nombre o email")
    @GetMapping("/buscar")
    public ResponseEntity<List<UsuarioEntity>> buscarUsuarios(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String email) {

        List<UsuarioEntity> resultados = usuarioRepository.findByNombreOrEmail(nombre, email);
        return ResponseEntity.ok(resultados);
    }
}
