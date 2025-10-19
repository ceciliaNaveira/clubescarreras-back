package com.clubesycarreraspopulares.clubescarreras.controller;

import com.clubesycarreraspopulares.clubescarreras.dto.CarreraRequest;
import com.clubesycarreraspopulares.clubescarreras.dto.CarreraResponse;
import com.clubesycarreraspopulares.clubescarreras.entities.CarreraEntity;
import com.clubesycarreraspopulares.clubescarreras.entities.ClubEntity;
import com.clubesycarreraspopulares.clubescarreras.entities.LocalizacionEntity;
import com.clubesycarreraspopulares.clubescarreras.mapper.CarreraMapper;
import com.clubesycarreraspopulares.clubescarreras.repository.CarreraRepository;
import com.clubesycarreraspopulares.clubescarreras.repository.ClubRepository;
import com.clubesycarreraspopulares.clubescarreras.repository.LocalizacionRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@RestController
@RequestMapping("/carreras")
@Tag(name = "Carrera", description = "Endpoints para gestionar carreras")
public class CarreraController {

    private final CarreraRepository carreraRepository;
    private final LocalizacionRepository localizacionRepository;
    private final ClubRepository clubRepository;
    private final CarreraMapper carreraMapper;

    @Operation(summary = "Listar todas las carreras")
    @GetMapping
    public ResponseEntity<List<CarreraResponse>> obtenerTodas() {
        List<CarreraResponse> response = carreraRepository.findAll()
                .stream()
                .map(carreraMapper::fromEntityToDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener una carrera por ID")
    @GetMapping("/{id}")
    public ResponseEntity<CarreraResponse> obtenerPorId(@PathVariable Integer id) {
        CarreraEntity carrera = carreraRepository.findById(id).orElse(null);
        if (Objects.isNull(carrera)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(carreraMapper.fromEntityToDTO(carrera));
    }

    @Operation(summary = "Crear una nueva carrera")
    @PostMapping
    public ResponseEntity<CarreraResponse> crear(@RequestBody CarreraRequest request) {
        LocalizacionEntity localizacion = localizacionRepository.findById(request.getLocalizacionId()).orElse(null);
        if (Objects.isNull(localizacion)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La localización no existe.");
        }

        ClubEntity club = null;
        if (request.getClubId() != null) {
            club = clubRepository.findById(request.getClubId()).orElse(null);
            if (Objects.isNull(club)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El club no existe.");
            }
        }

        CarreraEntity toSave = carreraMapper.fromDtoRequestToEntity(request);
        toSave.setLocalizacion(localizacion);
        toSave.setClub(club);

        if (request.getDistanciaKm() != null)
            toSave.setDistanciaKm(BigDecimal.valueOf(request.getDistanciaKm()));

        CarreraEntity saved = carreraRepository.save(toSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(carreraMapper.fromEntityToDTO(saved));
    }

    @Operation(summary = "Actualizar una carrera existente")
    @PutMapping("/{id}")
    public ResponseEntity<CarreraResponse> actualizar(@PathVariable Integer id,
                                                      @RequestBody CarreraRequest request) {
        CarreraEntity carrera = carreraRepository.findById(id).orElse(null);
        if (Objects.isNull(carrera)) {
            return ResponseEntity.notFound().build();
        }

        if (request.getLocalizacionId() != null) {
            LocalizacionEntity localizacion = localizacionRepository.findById(request.getLocalizacionId()).orElse(null);
            if (Objects.isNull(localizacion)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La localización no existe.");
            }
            carrera.setLocalizacion(localizacion);
        }

        if (request.getClubId() != null) {
            ClubEntity club = clubRepository.findById(request.getClubId()).orElse(null);
            if (Objects.isNull(club)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El club no existe.");
            }
            carrera.setClub(club);
        } else {
            carrera.setClub(null);
        }

        carrera.setNombre(request.getNombre());
        carrera.setDescripcion(request.getDescripcion());
        carrera.setFecha(request.getFecha());
        carrera.setDistanciaKm(request.getDistanciaKm() != null ? BigDecimal.valueOf(request.getDistanciaKm()) : null);
        carrera.setWebOficial(request.getWebOficial());
        carrera.setPosterUrl(request.getPosterUrl());

        CarreraEntity updated = carreraRepository.save(carrera);
        return ResponseEntity.ok(carreraMapper.fromEntityToDTO(updated));
    }

    @Operation(summary = "Eliminar una carrera por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (Boolean.FALSE.equals(carreraRepository.existsById(id))) {
            return ResponseEntity.notFound().build();
        }
        carreraRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar carreras por filtros opcionales")
    @GetMapping("/buscar")
    public ResponseEntity<List<CarreraResponse>> buscar(@RequestParam(required = false) String nombre,
                                                        @RequestParam(required = false) Integer clubId,
                                                        @RequestParam(required = false) Integer localizacionId,
                                                        @RequestParam(required = false) LocalDate fechaInicio,
                                                        @RequestParam(required = false) LocalDate fechaFin) {
        List<CarreraEntity> results = carreraRepository.findAll();

        if (nombre != null) {
            results = carreraRepository.findByNombreContainingIgnoreCase(nombre);
        } else if (clubId != null) {
            results = carreraRepository.findByClub_ClubId(clubId);
        } else if (localizacionId != null) {
            results = carreraRepository.findByLocalizacion_LocalizacionId(localizacionId);
        } else if (fechaInicio != null && fechaFin != null) {
            results = carreraRepository.findByFechaBetween(fechaInicio, fechaFin);
        }

        List<CarreraResponse> response = results.stream()
                .map(carreraMapper::fromEntityToDTO)
                .toList();

        return ResponseEntity.ok(response);
    }
}
