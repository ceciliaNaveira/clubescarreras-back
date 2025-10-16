package com.clubesycarreraspopulares.clubescarreras.controller;

import com.clubesycarreraspopulares.clubescarreras.entities.EntrenamientoEntity;
import com.clubesycarreraspopulares.clubescarreras.repository.EntrenamientoRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/entrenamientos")
@AllArgsConstructor
@Tag(name = "Entrenamiento", description = "Endpoints para la entidad Entrenamiento")
public class EntrenamientoController {

    private final EntrenamientoRepository entrenamientoRepository;

    @Operation(summary = "Listar todos")
    @GetMapping
    public ResponseEntity<List<EntrenamientoEntity>> obtenerTodos() {
        return ResponseEntity.ok(entrenamientoRepository.findAll());
    }

    @Operation(summary = "Obtener por id")
    @GetMapping("/{id}")
    public ResponseEntity<EntrenamientoEntity> obtenerPorId(@PathVariable Integer id) {
        EntrenamientoEntity e = entrenamientoRepository.findById(id).orElse(null);
        if (Objects.isNull(e)) return ResponseEntity.notFound().build();
        return ResponseEntity.ok(e);
    }

    @Operation(summary = "Crear nuevo")
    @PostMapping
    public ResponseEntity<EntrenamientoEntity> crear(@RequestBody EntrenamientoEntity e) {
        e.setIdEntrenamiento(null);
        EntrenamientoEntity saved = entrenamientoRepository.save(e);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(summary = "Actualizar por id")
    @PutMapping("/{id}")
    public ResponseEntity<EntrenamientoEntity> actualizar(@PathVariable Integer id,
                                                          @RequestBody EntrenamientoEntity body) {
        EntrenamientoEntity e = entrenamientoRepository.findById(id).orElse(null);
        if (Objects.isNull(e)) return ResponseEntity.notFound().build();

        e.setClub(body.getClub());
        e.setFecha(body.getFecha());
        e.setLugarEntrenamiento(body.getLugarEntrenamiento());
        e.setNivel(body.getNivel());
        e.setDescripcion(body.getDescripcion());

        EntrenamientoEntity updated = entrenamientoRepository.save(e);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Eliminar por id")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!entrenamientoRepository.existsById(id)) return ResponseEntity.notFound().build();
        entrenamientoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar por fecha")
    @GetMapping("/buscar")
    public ResponseEntity<List<EntrenamientoEntity>> buscarPorFecha(
            @RequestParam(required = false) LocalDateTime fecha) {
        if (fecha != null) return ResponseEntity.ok(entrenamientoRepository.findByFecha(fecha));
        return ResponseEntity.ok(entrenamientoRepository.findAll());
    }
}
