package com.clubesycarreraspopulares.clubescarreras.controller;

import com.clubesycarreraspopulares.clubescarreras.dto.ClubRequest;
import com.clubesycarreraspopulares.clubescarreras.dto.ClubResponse;
import com.clubesycarreraspopulares.clubescarreras.entities.ClubEntity;
import com.clubesycarreraspopulares.clubescarreras.entities.LocalizacionEntity;
import com.clubesycarreraspopulares.clubescarreras.mapper.ClubMapper;
import com.clubesycarreraspopulares.clubescarreras.repository.ClubRepository;
import com.clubesycarreraspopulares.clubescarreras.repository.LocalizacionRepository;
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
@RequestMapping("/clubes")
@Tag(name = "Club", description = "Endpoints para gestionar clubes")
public class ClubController {

    private final ClubRepository clubRepository;
    private final LocalizacionRepository localizacionRepository;
    private final ClubMapper clubMapper;

    @Operation(summary = "Listar todos los clubes")
    @GetMapping
    public ResponseEntity<List<ClubResponse>> obtenerTodos() {
        List<ClubResponse> response = clubRepository.findAll()
                .stream()
                .map(clubMapper::fromEntityToDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener un club por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ClubResponse> obtenerPorId(@PathVariable Integer id) {
        ClubEntity club = clubRepository.findById(id).orElse(null);
        if (Objects.isNull(club)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(clubMapper.fromEntityToDTO(club));
    }

    @Operation(summary = "Crear un nuevo club")
    @PostMapping
    public ResponseEntity<ClubResponse> crear(@RequestBody ClubRequest request) {
        LocalizacionEntity localizacion = localizacionRepository.findById(request.getLocalizacionId()).orElse(null);
        if (Objects.isNull(localizacion)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La localización no existe.");
        }

        ClubEntity toSave = clubMapper.fromDtoRequestToEntity(request);
        toSave.setLocalizacion(localizacion);

        ClubEntity saved = clubRepository.save(toSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(clubMapper.fromEntityToDTO(saved));
    }

    @Operation(summary = "Actualizar un club existente")
    @PutMapping("/{id}")
    public ResponseEntity<ClubResponse> actualizar(@PathVariable Integer id,
                                                   @RequestBody ClubRequest request) {
        ClubEntity club = clubRepository.findById(id).orElse(null);
        if (Objects.isNull(club)) {
            return ResponseEntity.notFound().build();
        }

        if (request.getLocalizacionId() != null) {
            LocalizacionEntity localizacion = localizacionRepository.findById(request.getLocalizacionId()).orElse(null);
            if (Objects.isNull(localizacion)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La localización no existe.");
            }
            club.setLocalizacion(localizacion);
        }

        club.setNombre(request.getNombre());
        club.setDescripcion(request.getDescripcion());
        club.setContacto(request.getContacto());
        club.setWeb(request.getWeb());

        ClubEntity updated = clubRepository.save(club);
        return ResponseEntity.ok(clubMapper.fromEntityToDTO(updated));
    }

    @Operation(summary = "Eliminar un club por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (Boolean.FALSE.equals(clubRepository.existsById(id))) {
            return ResponseEntity.notFound().build();
        }
        clubRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar clubes por nombre")
    @GetMapping("/buscar")
    public ResponseEntity<List<ClubResponse>> buscar(@RequestParam String nombre) {
        List<ClubResponse> response = clubRepository.findByNombreContainingIgnoreCase(nombre)
                .stream()
                .map(clubMapper::fromEntityToDTO)
                .toList();
        return ResponseEntity.ok(response);
    }
}
