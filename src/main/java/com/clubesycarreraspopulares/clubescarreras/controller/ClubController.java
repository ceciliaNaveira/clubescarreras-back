package com.clubesycarreraspopulares.clubescarreras.controller;

import com.clubesycarreraspopulares.clubescarreras.entities.ClubEntity;
import com.clubesycarreraspopulares.clubescarreras.repository.ClubRepository;
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
@RequestMapping("/clubes")
@Tag(name = "Club", description = "Endpoints para la entidad Club")
public class ClubController {

    private final ClubRepository clubRepository;

    /** Listar todos los clubes */
    @Operation(summary = "Listar todos")
    @GetMapping
    public ResponseEntity<List<ClubEntity>> obtenerTodosLosClubes() {
        return ResponseEntity.ok(clubRepository.findAll());
    }

    /** Obtener un club por ID */
    @Operation(summary = "Obtener por id")
    @GetMapping("/{idClub}")
    public ResponseEntity<ClubEntity> obtenerClubById(@PathVariable Integer idClub) {
        ClubEntity club = clubRepository.findById(idClub).orElse(null);
        if (Objects.isNull(club)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(club);
    }

    /** Crear un nuevo club */
    @Operation(summary = "Crear")
    @PostMapping
    public ResponseEntity<ClubEntity> crearClub(@RequestBody ClubEntity club) {
        club.setIdClub(null); // para asegurar que se cree uno nuevo
        ClubEntity saved = clubRepository.save(club);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /** Actualizar un club existente */
    @Operation(summary = "Actualizar por id")
    @PutMapping("/{idClub}")
    public ResponseEntity<ClubEntity> actualizarClub(@PathVariable Integer idClub,
                                                     @RequestBody ClubEntity body) {
        ClubEntity club = clubRepository.findById(idClub).orElse(null);
        if (Objects.isNull(club)) {
            return ResponseEntity.notFound().build();
        }

        club.setNombre(body.getNombre());
        club.setDescripcion(body.getDescripcion());
        club.setContacto(body.getContacto());
        club.setWeb(body.getWeb());
        club.setLocalizacion(body.getLocalizacion());

        ClubEntity updated = clubRepository.save(club);
        return ResponseEntity.ok(updated);
    }

    /** Eliminar un club por ID */
    @Operation(summary = "Eliminar por id")
    @DeleteMapping("/{idClub}")
    public ResponseEntity<Void> eliminarClub(@PathVariable Integer idClub) {
        if (!clubRepository.existsById(idClub)) {
            return ResponseEntity.notFound().build();
        }
        clubRepository.deleteById(idClub);
        return ResponseEntity.noContent().build();
    }

    /** Buscar clubes por filtros opcionales */
    @Operation(summary = "Buscar por nombre, provincia, municipio, código postal o día de la semana")
    @GetMapping("/buscar")
    public ResponseEntity<List<ClubEntity>> buscarClubes(
            @RequestParam(required = false) String nombre,
            @RequestParam(required = false) String provincia,
            @RequestParam(required = false) String municipio,
            @RequestParam(required = false) String codigoPostal,
            @RequestParam(required = false) String diaSemana
    ) {
        List<ClubEntity> resultados = clubRepository.buscarPorFiltros(
                nombre, provincia, municipio, codigoPostal, diaSemana
        );
        return ResponseEntity.ok(resultados);
    }
}
