package com.clubesycarreraspopulares.clubescarreras.controller;

import com.clubesycarreraspopulares.clubescarreras.dto.EntrenamientoRequest;
import com.clubesycarreraspopulares.clubescarreras.dto.EntrenamientoResponse;
import com.clubesycarreraspopulares.clubescarreras.entities.EntrenamientoEntity;
import com.clubesycarreraspopulares.clubescarreras.entities.ClubEntity;
import com.clubesycarreraspopulares.clubescarreras.mapper.EntrenamientoMapper;
import com.clubesycarreraspopulares.clubescarreras.repository.EntrenamientoRepository;
import com.clubesycarreraspopulares.clubescarreras.repository.ClubRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@RestController
@RequestMapping("/entrenamientos")
@Tag(name = "Entrenamiento", description = "Endpoints para gestionar entrenamientos")
public class EntrenamientoController {

    private final EntrenamientoRepository entrenamientoRepository;
    private final ClubRepository clubRepository;
    private final EntrenamientoMapper entrenamientoMapper;

    @Operation(summary = "Listar todos los entrenamientos")
    @GetMapping
    public ResponseEntity<List<EntrenamientoResponse>> obtenerTodos() {
        List<EntrenamientoResponse> response = entrenamientoRepository.findAll()
                .stream()
                .map(entrenamientoMapper::fromEntityToDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener un entrenamiento por ID")
    @GetMapping("/{id}")
    public ResponseEntity<EntrenamientoResponse> obtenerPorId(@PathVariable Integer id) {
        EntrenamientoEntity ent = entrenamientoRepository.findById(id).orElse(null);
        if (Objects.isNull(ent)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(entrenamientoMapper.fromEntityToDTO(ent));
    }

    @Operation(summary = "Crear un nuevo entrenamiento")
    @PostMapping
    public ResponseEntity<EntrenamientoResponse> crear(@RequestBody EntrenamientoRequest request) {
        ClubEntity club = clubRepository.findById(request.getClubId()).orElse(null);
        if (Objects.isNull(club)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El club no existe.");
        }

        EntrenamientoEntity toSave = entrenamientoMapper.fromDtoRequestToEntity(request);
        toSave.setClub(club);

        EntrenamientoEntity saved = entrenamientoRepository.save(toSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(entrenamientoMapper.fromEntityToDTO(saved));
    }

    @Operation(summary = "Actualizar un entrenamiento existente")
    @PutMapping("/{id}")
    public ResponseEntity<EntrenamientoResponse> actualizar(@PathVariable Integer id,
                                                            @RequestBody EntrenamientoRequest request) {
        EntrenamientoEntity ent = entrenamientoRepository.findById(id).orElse(null);
        if (Objects.isNull(ent)) {
            return ResponseEntity.notFound().build();
        }

        if (request.getClubId() != null) {
            ClubEntity club = clubRepository.findById(request.getClubId()).orElse(null);
            if (Objects.isNull(club)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El club no existe.");
            }
            ent.setClub(club);
        }

        ent.setDiaSemana(request.getDiaSemana());
        ent.setHora(request.getHora());
        ent.setLugarEntrenamiento(request.getLugarEntrenamiento());
        ent.setNivel(request.getNivel());
        ent.setDescripcion(request.getDescripcion());

        EntrenamientoEntity updated = entrenamientoRepository.save(ent);
        return ResponseEntity.ok(entrenamientoMapper.fromEntityToDTO(updated));
    }

    @Operation(summary = "Eliminar un entrenamiento por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (Boolean.FALSE.equals(entrenamientoRepository.existsById(id))) {
            return ResponseEntity.notFound().build();
        }
        entrenamientoRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar entrenamientos por filtros opcionales")
    @GetMapping("/buscar")
    public ResponseEntity<List<EntrenamientoResponse>> buscar(@RequestParam(required = false) Integer clubId,
                                                               @RequestParam(required = false) String diaSemana,
                                                               @RequestParam(required = false) String nivel) {
        List<EntrenamientoEntity> results = entrenamientoRepository.findAll();

        if (clubId != null) {
            results = entrenamientoRepository.findByClub_ClubId(clubId);
        } else if (diaSemana != null) {
            results = entrenamientoRepository.findByDiaSemanaContainingIgnoreCase(diaSemana);
        } else if (nivel != null) {
            results = entrenamientoRepository.findByNivelContainingIgnoreCase(nivel);
        }

        List<EntrenamientoResponse> response = results.stream()
                .map(entrenamientoMapper::fromEntityToDTO)
                .toList();

        return ResponseEntity.ok(response);
    }
}
