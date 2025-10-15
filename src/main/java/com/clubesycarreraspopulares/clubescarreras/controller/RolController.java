package com.clubesycarreraspopulares.clubescarreras.controller;

import com.clubesycarreraspopulares.clubescarreras.entities.RolEntity;
import com.clubesycarreraspopulares.clubescarreras.repository.RolRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * Controller para la entidad Rol.
 * Expone los endpoints REST para CRUD y búsquedas.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/roles")
@Tag(name = "Rol", description = "Endpoints para la entidad Rol")
public class RolController {

    private final RolRepository rolRepository;

    /** Listar todos los roles */
    @Operation(summary = "Listar todos")
    @GetMapping
    public ResponseEntity<List<RolEntity>> obtenerTodosLosRoles() {
        return ResponseEntity.ok(rolRepository.findAll());
    }

    /** Obtener un rol por ID */
    @Operation(summary = "Obtener por id")
    @GetMapping("/{idRol}")
    public ResponseEntity<RolEntity> obtenerRolById(@PathVariable Integer idRol) {
        RolEntity rolEntity = rolRepository.findById(idRol).orElse(null);
        if (Objects.isNull(rolEntity)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rolEntity);
    }

    /** Crear un nuevo rol */
    @Operation(summary = "Crear")
    @PostMapping
    public ResponseEntity<RolEntity> añadirRol(@RequestBody RolEntity rolEntity) {
        rolEntity.setIdRol(null); // Ignorar el ID que venga en el JSON
        RolEntity saved = rolRepository.save(rolEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /** Actualizar un rol existente */
    @Operation(summary = "Actualizar por id")
    @PutMapping("/{idRol}")
    public ResponseEntity<RolEntity> actualizarRol(@PathVariable Integer idRol,
                                                   @RequestBody RolEntity body) {
        RolEntity rolEntity = rolRepository.findById(idRol).orElse(null);
        if (Objects.isNull(rolEntity)) {
            return ResponseEntity.notFound().build();
        }
        rolEntity.setNombreRol(body.getNombreRol());
        RolEntity updated = rolRepository.save(rolEntity);
        return ResponseEntity.ok(updated);
    }

    /** Eliminar un rol por ID */
    @Operation(summary = "Eliminar por id")
    @DeleteMapping("/{idRol}")
    public ResponseEntity<Void> eliminarRol(@PathVariable Integer idRol) {
        if (!rolRepository.existsById(idRol)) {
            return ResponseEntity.notFound().build();
        }
        rolRepository.deleteById(idRol);
        return ResponseEntity.noContent().build();
    }

    /** Buscar roles por nombre (parcial o completo) */
    @Operation(summary = "Buscar por nombre")
    @GetMapping("/buscar")
    public ResponseEntity<List<RolEntity>> buscarRolPorNombre(
            @RequestParam(required = false) String nombreRol) {
        List<RolEntity> resultados = rolRepository.findByNombreRol(nombreRol);
        return ResponseEntity.ok(resultados);
    }
}