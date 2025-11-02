package com.clubesycarreraspopulares.clubescarreras.controller;

import com.clubesycarreraspopulares.clubescarreras.entities.CarreraEntity;
import com.clubesycarreraspopulares.clubescarreras.entities.FavoritoCarreraEntity;
import com.clubesycarreraspopulares.clubescarreras.entities.UsuarioEntity;
import com.clubesycarreraspopulares.clubescarreras.repository.CarreraRepository;
import com.clubesycarreraspopulares.clubescarreras.repository.FavoritoCarreraRepository;
import com.clubesycarreraspopulares.clubescarreras.repository.UsuarioRepository;
import com.clubesycarreraspopulares.clubescarreras.dto.FavoritoCarreraResponse;
import com.clubesycarreraspopulares.clubescarreras.mapper.FavoritoCarreraMapper;
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

    private final FavoritoCarreraRepository favoritoRepository;
    private final UsuarioRepository usuarioRepository;
    private final CarreraRepository carreraRepository;
    private final FavoritoCarreraMapper favoritoCarreraMapper;

    @Operation(summary = "Listar todos los favoritos de carrera")
    @GetMapping
    public ResponseEntity<List<FavoritoCarreraEntity>> obtenerTodos() {
        List<FavoritoCarreraEntity> response = favoritoRepository.findAll();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Agregar una carrera a favoritos")
    @PostMapping
    public ResponseEntity<FavoritoCarreraResponse> agregarFavorito(@RequestParam Integer usuarioId,
                                                                   @RequestParam Integer carreraId) {

        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Usuario no encontrado"));

        CarreraEntity carrera = carreraRepository.findById(carreraId)
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "Carrera no encontrada"));

        if (favoritoRepository.existsByUsuario_UsuarioIdAndCarrera_CarreraId(usuarioId, carreraId)) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Favorito ya existe");
        }

        FavoritoCarreraEntity favorito = FavoritoCarreraEntity.builder()
                .usuario(usuario)
                .carrera(carrera)
                .build();

        FavoritoCarreraEntity saved = favoritoRepository.save(favorito);
        FavoritoCarreraResponse response = favoritoCarreraMapper.fromEntityToDTO(saved);

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @Operation(summary = "Eliminar un favorito de carrera")
    @DeleteMapping
    public ResponseEntity<Void> eliminarFavorito(@RequestParam Integer usuarioId,
                                                 @RequestParam Integer carreraId) {

        FavoritoCarreraEntity favorito = favoritoRepository.findByUsuario_UsuarioId(usuarioId).stream()
                .filter(f -> Objects.equals(f.getCarrera().getCarreraId(), carreraId))
                .findFirst()
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Favorito no encontrado"));

        favoritoRepository.delete(favorito);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar favoritos por usuario")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<FavoritoCarreraEntity>> buscarPorUsuario(@PathVariable Integer usuarioId) {
        List<FavoritoCarreraEntity> response = favoritoRepository.findByUsuario_UsuarioId(usuarioId);
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Buscar favoritos por carrera")
    @GetMapping("/carrera/{carreraId}")
    public ResponseEntity<List<FavoritoCarreraEntity>> buscarPorCarrera(@PathVariable Integer carreraId) {
        List<FavoritoCarreraEntity> response = favoritoRepository.findByCarrera_CarreraId(carreraId);
        return ResponseEntity.ok(response);
    }
}
