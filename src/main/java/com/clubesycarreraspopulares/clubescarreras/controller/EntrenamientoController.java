package com.clubesycarreraspopulares.clubescarreras.controller;

import com.clubesycarreraspopulares.clubescarreras.entities.EntrenamientoEntity;
import com.clubesycarreraspopulares.clubescarreras.repository.EntrenamientoRepository;
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
@RequestMapping("/entrenamientos")
@Tag(name = "Entrenamiento", description = "Endpoints para la entidad Entrenamiento")
public class EntrenamientoController {

    private final EntrenamientoRepository entrenamientoRepository;

    /** Listar todos los entrenamientos */
    @Operation(summary = "Listar todos")
    @GetMapping
    public ResponseEntity<List<EntrenamientoEntity>> obtenerTodosLosEntrenamientos() {
        return ResponseEntity.ok(entrenamientoRepository.findAll());
    }

    /** Obtener un entrenamiento por ID */
    @Operation(summary = "Obtener por id")
    @GetMapping("/{idEntrenamiento}")
    public ResponseEntity<EntrenamientoEntity> obtenerEntrenamientoById(@PathVariable Integer idEntrenamiento) {
        EntrenamientoEntity entrenamiento = entrenamientoRepository.findById(idEntrenamiento).orElse(null);
        if (Objects.isNull(entrenamiento)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entrenamiento);
    }

    /** Crear un nuevo entrenamiento */
    @Operation(summary = "Crear")
    @PostMapping
    public ResponseEntity<EntrenamientoEntity> añadirEntrenamiento(@RequestBody EntrenamientoEntity entrenamiento) {
        entrenamiento.setIdEntrenamiento(null);
        EntrenamientoEntity saved = entrenamientoRepository.save(entrenamiento);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /** Actualizar un entrenamiento existente */
    @Operation(summary = "Actualizar por id")
    @PutMapping("/{idEntrenamiento}")
    public ResponseEntity<EntrenamientoEntity> actualizarEntrenamiento(@PathVariable Integer idEntrenamiento,
                                                                       @RequestBody EntrenamientoEntity body) {
        EntrenamientoEntity entrenamiento = entrenamientoRepository.findById(idEntrenamiento).orElse(null);
        if (Objects.isNull(entrenamiento)) {
            return ResponseEntity.notFound().build();
        }

        entrenamiento.setClub(body.getClub());
        entrenamiento.setDiaSemana(body.getDiaSemana());
        entrenamiento.setLugarEntrenamiento(body.getLugarEntrenamiento());
        entrenamiento.setNivel(body.getNivel());
        entrenamiento.setDescripcion(body.getDescripcion());

        EntrenamientoEntity updated = entrenamientoRepository.save(entrenamiento);
        return ResponseEntity.ok(updated);
    }

    /** Eliminar un entrenamiento por ID */
    @Operation(summary = "Eliminar por id")
    @DeleteMapping("/{idEntrenamiento}")
    public ResponseEntity<Void> eliminarEntrenamiento(@PathVariable Integer idEntrenamiento) {
        if (!entrenamientoRepository.existsById(idEntrenamiento)) {
            return ResponseEntity.notFound().build();
        }
        entrenamientoRepository.deleteById(idEntrenamiento);
        return ResponseEntity.noContent().build();
    }

    /** Buscar entrenamientos por filtros opcionales */
    @Operation(summary = "Buscar por día de la semana, provincia, municipio o código postal")
    @GetMapping("/buscar")
    public ResponseEntity<List<EntrenamientoEntity>> buscarEntrenamiento(
            @RequestParam(required = false) String diaSemana,
            @RequestParam(required = false) String provincia,
            @RequestParam(required = false) String municipio,
            @RequestParam(required = false) String codigoPostal) {

        List<EntrenamientoEntity> resultados =
                entrenamientoRepository.buscarPorFiltros(diaSemana, provincia, municipio, codigoPostal);

        return ResponseEntity.ok(resultados);
    }
}
