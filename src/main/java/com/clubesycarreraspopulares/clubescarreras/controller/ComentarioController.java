package com.clubesycarreraspopulares.clubescarreras.controller;

import com.clubesycarreraspopulares.clubescarreras.entities.ComentarioEntity;
import com.clubesycarreraspopulares.clubescarreras.repository.ComentarioRepository;
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
@RequestMapping("/comentarios")
@Tag(name = "Comentario", description = "Endpoints para la entidad Comentario")
public class ComentarioController {

    private final ComentarioRepository comentarioRepository;

    /** Listar todos los comentarios */
    @Operation(summary = "Listar todos")
    @GetMapping
    public ResponseEntity<List<ComentarioEntity>> obtenerTodosLosComentarios() {
        return ResponseEntity.ok(comentarioRepository.findAll());
    }

    /** Obtener un comentario por ID */
    @Operation(summary = "Obtener por id")
    @GetMapping("/{idComentario}")
    public ResponseEntity<ComentarioEntity> obtenerComentarioById(@PathVariable Integer idComentario) {
        ComentarioEntity comentario = comentarioRepository.findById(idComentario).orElse(null);
        if (Objects.isNull(comentario)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comentario);
    }

    /** Crear un nuevo comentario */
    @Operation(summary = "Crear")
    @PostMapping
    public ResponseEntity<ComentarioEntity> añadirComentario(@RequestBody ComentarioEntity comentario) {
        comentario.setIdComentario(null);
        ComentarioEntity saved = comentarioRepository.save(comentario);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /** Actualizar un comentario existente */
    @Operation(summary = "Actualizar por id")
    @PutMapping("/{idComentario}")
    public ResponseEntity<ComentarioEntity> actualizarComentario(@PathVariable Integer idComentario,
                                                                 @RequestBody ComentarioEntity body) {
        ComentarioEntity comentario = comentarioRepository.findById(idComentario).orElse(null);
        if (Objects.isNull(comentario)) {
            return ResponseEntity.notFound().build();
        }

        comentario.setTexto(body.getTexto());
        comentario.setValoracion(body.getValoracion());
        comentario.setFecha(body.getFecha());
        comentario.setUsuario(body.getUsuario());
        comentario.setClub(body.getClub());

        ComentarioEntity updated = comentarioRepository.save(comentario);
        return ResponseEntity.ok(updated);
    }

    /** Eliminar un comentario por ID */
    @Operation(summary = "Eliminar por id")
    @DeleteMapping("/{idComentario}")
    public ResponseEntity<Void> eliminarComentario(@PathVariable Integer idComentario) {
        if (Boolean.FALSE.equals(comentarioRepository.existsById(idComentario))) {
            return ResponseEntity.notFound().build();
        }
        comentarioRepository.deleteById(idComentario);
        return ResponseEntity.noContent().build();
    }

    /** Buscar comentarios por filtros opcionales */
    @Operation(summary = "Buscar por club, usuario o valoración")
    @GetMapping("/buscar")
    public ResponseEntity<List<ComentarioEntity>> buscarComentarios(
            @RequestParam(required = false) Integer idClub,
            @RequestParam(required = false) Integer idUsuario,
            @RequestParam(required = false) Byte valoracion) {

        List<ComentarioEntity> resultados;

        if (idClub != null) {
            resultados = comentarioRepository.findByClubIdClub(idClub);
        } else if (idUsuario != null) {
            resultados = comentarioRepository.findByUsuarioIdUsuario(idUsuario);
        } else if (valoracion != null) {
            resultados = comentarioRepository.findByValoracion(valoracion);
        } else {
            resultados = comentarioRepository.findAll();
        }

        return ResponseEntity.ok(resultados);
    }
}
