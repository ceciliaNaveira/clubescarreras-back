package com.clubesycarreraspopulares.clubescarreras.controller;

import com.clubesycarreraspopulares.clubescarreras.dto.FavoritoClubRequest;
import com.clubesycarreraspopulares.clubescarreras.dto.FavoritoClubResponse;
import com.clubesycarreraspopulares.clubescarreras.entities.*;
import com.clubesycarreraspopulares.clubescarreras.mapper.FavoritoClubMapper;
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
@RequestMapping("/favoritos")
@Tag(name = "FavoritoClub", description = "Endpoints para gestionar favoritos de clubes")
public class FavoritoClubController {

    private final FavoritoClubRepository favoritoClubRepository;
    private final UsuarioRepository usuarioRepository;
    private final ClubRepository clubRepository;
    private final FavoritoClubMapper favoritoClubMapper;

    @Operation(summary = "Listar todos los favoritos")
    @GetMapping
    public ResponseEntity<List<FavoritoClubResponse>> obtenerTodos() {
        List<FavoritoClubResponse> response = favoritoClubRepository.findAll()
                .stream()
                .map(favoritoClubMapper::fromEntityToDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Agregar un club a favoritos")
    @PostMapping
    public ResponseEntity<FavoritoClubResponse> agregarFavorito(@RequestBody FavoritoClubRequest request) {
        UsuarioEntity usuario = usuarioRepository.findById(request.getUsuarioId()).orElse(null);
        ClubEntity club = clubRepository.findById(request.getClubId()).orElse(null);

        if (Objects.isNull(usuario)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El usuario no existe.");
        }
        if (Objects.isNull(club)) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El club no existe.");
        }

        FavoritoClubEntity entity = favoritoClubMapper.fromDtoRequestToEntity(request);
        entity.setUsuario(usuario);
        entity.setClub(club);

        FavoritoClubEntity saved = favoritoClubRepository.save(entity);
        return ResponseEntity.status(HttpStatus.CREATED).body(favoritoClubMapper.fromEntityToDTO(saved));
    }

    @Operation(summary = "Eliminar un club de favoritos")
    @DeleteMapping
    public ResponseEntity<Void> eliminarFavorito(@RequestParam Integer usuarioId,
                                                 @RequestParam Integer clubId) {
        FavoritoClubId id = new FavoritoClubId(usuarioId, clubId);
        if (!favoritoClubRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        favoritoClubRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar favoritos por usuario")
    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<FavoritoClubResponse>> buscarPorUsuario(@PathVariable Integer usuarioId) {
        List<FavoritoClubResponse> response = favoritoClubRepository.findByUsuario_UsuarioId(usuarioId)
                .stream()
                .map(favoritoClubMapper::fromEntityToDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Buscar favoritos por club")
    @GetMapping("/club/{clubId}")
    public ResponseEntity<List<FavoritoClubResponse>> buscarPorClub(@PathVariable Integer clubId) {
        List<FavoritoClubResponse> response = favoritoClubRepository.findByClub_ClubId(clubId)
                .stream()
                .map(favoritoClubMapper::fromEntityToDTO)
                .toList();
        return ResponseEntity.ok(response);
    }
}

