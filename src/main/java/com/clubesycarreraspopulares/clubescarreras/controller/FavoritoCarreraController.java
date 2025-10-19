package com.clubesycarreraspopulares.clubescarreras.controller;

import com.clubesycarreraspopulares.clubescarreras.entities.CarreraEntity;
import com.clubesycarreraspopulares.clubescarreras.entities.FavoritoCarreraEntity;
import com.clubesycarreraspopulares.clubescarreras.entities.UsuarioEntity;
import com.clubesycarreraspopulares.clubescarreras.repository.CarreraRepository;
import com.clubesycarreraspopulares.clubescarreras.repository.FavoritoCarreraRepository;
import com.clubesycarreraspopulares.clubescarreras.repository.UsuarioRepository;
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

    @Operation(summary = "Listar todos los favoritos de carrera")
    @GetMapping
    public ResponseEntity<List<FavoritoCarreraEntity>> obtenerTodos() {
        List<FavoritoCarreraEntity> response = favoritoRepository.findAll();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Agregar una carrera a favoritos")
    @PostMapping
    public ResponseEntity<FavoritoCarreraEntity> agregarFavorito(@RequestParam Integer usuarioId,
                                                                 @RequestParam Integer carreraId) {
        UsuarioEntity usuario = usuarioRepository.findById(usuarioId).orElse(null);
        CarreraEntity carrera = carreraRepository.findById(carreraId).orElse(null);

        if (Objects.isNull(usuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario no existe.");
        }
        if (Objects.isNull(carrera)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "La carrera no existe.");
        }

        FavoritoCarreraEntity entity = FavoritoCarreraEntity.builder()
                .usuario(usuario)
                .carrera(carrera)
                .build();

        FavoritoCarreraEntity saved = favoritoRepository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(summary = "Eliminar un favorito de carrera")
    @DeleteMapping
    public ResponseEntity<Void> eliminarFavorito(@RequestParam Integer usuarioId,
                                                 @RequestParam Integer carreraId) {
        List<FavoritoCarreraEntity> favoritos = favoritoRepository.findByUsuario_UsuarioId(usuarioId)
                .stream()
                .filter(f -> Objects.equals(f.getCarrera().getCarreraId(), carreraId))
                .toList();

        if (favoritos.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        favoritos.forEach(favoritoRepository::delete);
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
