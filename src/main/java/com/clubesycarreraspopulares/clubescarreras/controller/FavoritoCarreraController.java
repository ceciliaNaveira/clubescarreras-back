package com.clubesycarreraspopulares.clubescarreras.controller;

import com.clubesycarreraspopulares.clubescarreras.dto.FavoritoCarreraRequest;
import com.clubesycarreraspopulares.clubescarreras.dto.FavoritoCarreraResponse;
import com.clubesycarreraspopulares.clubescarreras.entities.*;
import com.clubesycarreraspopulares.clubescarreras.mapper.FavoritoCarreraMapper;
import com.clubesycarreraspopulares.clubescarreras.repository.*;
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
@RequestMapping("/favoritos-carrera")
@Tag(name = "FavoritoCarrera", description = "Endpoints para gestionar favoritos de carreras")
public class FavoritoCarreraController {

    private final FavoritoCarreraRepository favoritoCarreraRepository;
    private final UsuarioRepository usuarioRepository;
    private final CarreraRepository carreraRepository;
    private final FavoritoCarreraMapper favoritoCarreraMapper;

    @Operation(summary = "Listar todos los favoritos de carrera")
    @GetMapping
    public ResponseEntity<List<FavoritoCarreraResponse>> obtenerTodos() {
        List<FavoritoCarreraResponse> response = favoritoCarreraRepository.findAll()
                .stream()
                .map(favoritoCarreraMapper::fromEntityToDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Agregar una carrera a favoritos")
    @PostMapping
    public ResponseEntity<FavoritoCarreraResponse> agregarFavorito(@RequestBody FavoritoCarreraRequest request) {
        UsuarioEntity usuario = usuarioRepository.findById(request.getUsuarioId()).orElse(null);
        CarreraEntity carrera = carreraRepository.findById(request.getCarreraId()).orElse(null);

        if (Objects.isNull(usuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario no existe.");
        }
        if (Objects.isNull(carrera)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La carrera no existe.");
        }

        FavoritoCarreraEntity entity = favoritoCarreraMapper.fromDtoRequestToEntity(request);
        entity.setUsuario(usuario);
        entity.setCarrera(carrera);

        FavoritoCarreraEntity saved = favoritoCarreraRepository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(favoritoCarreraMapper.fromEntityToDTO(saved));
    }

    @Operation(summary = "Eliminar una carrera de favoritos")
    @DeleteMapping
    public ResponseEntity<Void> eliminarFavorito(@RequestParam Integer usuarioId,
                                                 @RequestParam Integer carreraId) {
        FavoritoCarreraId id = new FavoritoCarreraId(usuarioId, carreraId);
        if (!favoritoCarreraRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        favoritoCarreraRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar favoritos por usuario")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<FavoritoCarreraResponse>> buscarPorUsuario(@PathVariable Integer usuarioId) {
        List<FavoritoCarreraResponse> response = favoritoCarreraRepository.findByUsuario_UsuarioId(usuarioId)
                .stream()
                .map(favoritoCarreraMapper::fromEntityToDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Buscar favoritos por carrera")
    @GetMapping("/carrera/{carreraId}")
    public ResponseEntity<List<FavoritoCarreraResponse>> buscarPorCarrera(@PathVariable Integer carreraId) {
        List<FavoritoCarreraResponse> response = favoritoCarreraRepository.findByCarrera_Id(carreraId)
                .stream()
                .map(favoritoCarreraMapper::fromEntityToDTO)
                .toList();
        return ResponseEntity.ok(response);
    }
}
