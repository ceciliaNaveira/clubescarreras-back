package com.clubesycarreraspopulares.clubescarreras.controller;

import com.clubesycarreraspopulares.clubescarreras.dto.ComentarioRequest;
import com.clubesycarreraspopulares.clubescarreras.dto.ComentarioResponse;
import com.clubesycarreraspopulares.clubescarreras.entities.ComentarioEntity;
import com.clubesycarreraspopulares.clubescarreras.entities.ClubEntity;
import com.clubesycarreraspopulares.clubescarreras.entities.UsuarioEntity;
import com.clubesycarreraspopulares.clubescarreras.mapper.ComentarioMapper;
import com.clubesycarreraspopulares.clubescarreras.repository.ComentarioRepository;
import com.clubesycarreraspopulares.clubescarreras.repository.ClubRepository;
import com.clubesycarreraspopulares.clubescarreras.repository.UsuarioRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;

@AllArgsConstructor
@RestController
@RequestMapping("/comentarios")
@Tag(name = "Comentario", description = "Endpoints para gestionar comentarios de clubes")
public class ComentarioController {

    private final ComentarioRepository comentarioRepository;
    private final UsuarioRepository usuarioRepository;
    private final ClubRepository clubRepository;
    private final ComentarioMapper comentarioMapper;

    @Operation(summary = "Listar todos los comentarios")
    @GetMapping
    public ResponseEntity<List<ComentarioResponse>> obtenerTodos() {
        List<ComentarioResponse> response = comentarioRepository.findAll()
                .stream()
                .map(comentarioMapper::fromEntityToDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener un comentario por ID")
    @GetMapping("/{id}")
    public ResponseEntity<ComentarioResponse> obtenerPorId(@PathVariable Integer id) {
        ComentarioEntity comentario = comentarioRepository.findById(id).orElse(null);
        if (Objects.isNull(comentario)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comentarioMapper.fromEntityToDTO(comentario));
    }

    @Operation(summary = "Crear un nuevo comentario")
    @PostMapping
    public ResponseEntity<ComentarioResponse> crear(@RequestBody ComentarioRequest request) {
        UsuarioEntity usuario = usuarioRepository.findById(request.getUsuarioId()).orElse(null);
        if (Objects.isNull(usuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario no existe.");
        }

        ClubEntity club = clubRepository.findById(request.getClubId()).orElse(null);
        if (Objects.isNull(club)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El club no existe.");
        }

        if (request.getValoracion() == null || request.getValoracion() < 1 || request.getValoracion() > 5) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La valoración debe estar entre 1 y 5.");
        }

        ComentarioEntity toSave = comentarioMapper.fromDtoRequestToEntity(request);
        toSave.setUsuario(usuario);
        toSave.setClub(club);
        toSave.setFecha(LocalDateTime.now());

        ComentarioEntity saved = comentarioRepository.save(toSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(comentarioMapper.fromEntityToDTO(saved));
    }

    @Operation(summary = "Actualizar un comentario existente")
    @PutMapping("/{id}")
    public ResponseEntity<ComentarioResponse> actualizar(@PathVariable Integer id,
                                                         @RequestBody ComentarioRequest request) {
        ComentarioEntity comentario = comentarioRepository.findById(id).orElse(null);
        if (Objects.isNull(comentario)) {
            return ResponseEntity.notFound().build();
        }

        if (request.getUsuarioId() != null) {
            UsuarioEntity usuario = usuarioRepository.findById(request.getUsuarioId()).orElse(null);
            if (Objects.isNull(usuario)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario no existe.");
            }
            comentario.setUsuario(usuario);
        }

        if (request.getClubId() != null) {
            ClubEntity club = clubRepository.findById(request.getClubId()).orElse(null);
            if (Objects.isNull(club)) {
                throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El club no existe.");
            }
            comentario.setClub(club);
        }

        if (request.getValoracion() != null && (request.getValoracion() < 1 || request.getValoracion() > 5)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La valoración debe estar entre 1 y 5.");
        }

        comentario.setTexto(request.getTexto());
        comentario.setValoracion(request.getValoracion());

        ComentarioEntity updated = comentarioRepository.save(comentario);
        return ResponseEntity.ok(comentarioMapper.fromEntityToDTO(updated));
    }

    @Operation(summary = "Eliminar un comentario por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (Boolean.FALSE.equals(comentarioRepository.existsById(id))) {
            return ResponseEntity.notFound().build();
        }
        comentarioRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar comentarios por filtros opcionales")
    @GetMapping("/buscar")
public ResponseEntity<List<ComentarioResponse>> buscar(
        @RequestParam(required = false) Integer usuarioId,
        @RequestParam(required = false) Integer clubId) {

    List<ComentarioEntity> results;

    if (usuarioId != null && clubId != null) {
        results = comentarioRepository.findByUsuario_UsuarioIdAndClub_ClubId(usuarioId, clubId);
    } else if (usuarioId != null) {
        results = comentarioRepository.findByUsuario_UsuarioId(usuarioId);
    } else if (clubId != null) {
        results = comentarioRepository.findByClub_ClubId(clubId);
    } else {
        results = comentarioRepository.findAll();
    }

    List<ComentarioResponse> response = results.stream()
            .map(comentarioMapper::fromEntityToDTO)
            .toList();

    return ResponseEntity.ok(response);
}
}
