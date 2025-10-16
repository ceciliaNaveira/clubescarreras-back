package com.clubesycarreraspopulares.clubescarreras.controller;

import com.clubesycarreraspopulares.clubescarreras.entities.CarreraEntity;
import com.clubesycarreraspopulares.clubescarreras.repository.CarreraRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@RestController
@RequestMapping("/carreras")
@Tag(name = "Carrera", description = "Endpoints para la entidad Carrera")
public class CarreraController {

    private final CarreraRepository carreraRepository;

    /** Listar todas las carreras */
    @Operation(summary = "Listar todas")
    @GetMapping
    public ResponseEntity<List<CarreraEntity>> obtenerTodasLasCarreras() {
        return ResponseEntity.ok(carreraRepository.findAll());
    }

    /** Obtener una carrera por ID */
    @Operation(summary = "Obtener por id")
    @GetMapping("/{idCarrera}")
    public ResponseEntity<CarreraEntity> obtenerCarreraById(@PathVariable Integer idCarrera) {
        CarreraEntity carrera = carreraRepository.findById(idCarrera).orElse(null);
        if (Objects.isNull(carrera)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carrera);
    }

    /** Crear una nueva carrera */
    @Operation(summary = "Crear")
    @PostMapping
    public ResponseEntity<CarreraEntity> añadirCarrera(@RequestBody CarreraEntity carrera) {
        carrera.setIdCarrera(null); // Ignorar el ID si viene en el JSON
        CarreraEntity saved = carreraRepository.save(carrera);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /** Actualizar una carrera existente */
    @Operation(summary = "Actualizar por id")
    @PutMapping("/{idCarrera}")
    public ResponseEntity<CarreraEntity> actualizarCarrera(@PathVariable Integer idCarrera,
                                                           @RequestBody CarreraEntity body) {
        CarreraEntity carrera = carreraRepository.findById(idCarrera).orElse(null);
        if (Objects.isNull(carrera)) {
            return ResponseEntity.notFound().build();
        }

        // Actualizar campos
        carrera.setNombre(body.getNombre());
        carrera.setDescripcion(body.getDescripcion());
        carrera.setFecha(body.getFecha());
        carrera.setDistanciaKm(body.getDistanciaKm());
        carrera.setWebOficial(body.getWebOficial());
        carrera.setPosterUrl(body.getPosterUrl());
        carrera.setClub(body.getClub());
        carrera.setLocalizacion(body.getLocalizacion());

        CarreraEntity updated = carreraRepository.save(carrera);
        return ResponseEntity.ok(updated);
    }

    /** Eliminar una carrera por ID */
    @Operation(summary = "Eliminar por id")
    @DeleteMapping("/{idCarrera}")
    public ResponseEntity<Void> eliminarCarrera(@PathVariable Integer idCarrera) {
        if (!carreraRepository.existsById(idCarrera)) {
            return ResponseEntity.notFound().build();
        }
        carreraRepository.deleteById(idCarrera);
        return ResponseEntity.noContent().build();
    }

    /** Buscar carreras por varios filtros */
    @Operation(summary = "Buscar por filtros")
    @GetMapping("/buscar")
    public ResponseEntity<List<CarreraEntity>> buscarCarreras(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) LocalDate fecha,
            @RequestParam(required = false) Double distancia,
            @RequestParam(required = false) Double distanciaMin,
            @RequestParam(required = false) Double distanciaMax,
            @RequestParam(required = false) String codigoPostal,
            @RequestParam(required = false) String municipio,
            @RequestParam(required = false) String provincia,
            @RequestParam(required = false) Integer idClub
    ) {
        // Puedes crear lógicas combinadas o llamar a métodos específicos según el filtro que venga
        // Aquí un ejemplo simple llamando al método por nombre si se pasa nombre
        List<CarreraEntity> resultados;

        if (nombre != null) {
            resultados = carreraRepository.findByNombre(nombre);
        } else if (fecha != null) {
            resultados = carreraRepository.findByFecha(fecha);
        } else if (distancia != null) {
            resultados = carreraRepository.findByDistancia(distancia);
        } else if (distanciaMin != null && distanciaMax != null) {
            resultados = carreraRepository.findByRangoDistancia(distanciaMin, distanciaMax);
        } else if (codigoPostal != null) {
            resultados = carreraRepository.findByCodigoPostal(codigoPostal);
        } else if (municipio != null) {
            resultados = carreraRepository.findByMunicipio(municipio);
        } else if (provincia != null) {
            resultados = carreraRepository.findByProvincia(provincia);
        } else if (idClub != null) {
            resultados = carreraRepository.findByClub(idClub);
        } else {
            resultados = carreraRepository.findAll();
        }

        return ResponseEntity.ok(resultados);
    }
}
