package com.clubesycarreraspopulares.clubescarreras.controller;

import com.clubesycarreraspopulares.clubescarreras.dto.RolRequest;
import com.clubesycarreraspopulares.clubescarreras.dto.RolResponse;
import com.clubesycarreraspopulares.clubescarreras.entities.RolEntity;
import com.clubesycarreraspopulares.clubescarreras.mapper.RolMapper;
import com.clubesycarreraspopulares.clubescarreras.repository.RolRepository;
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
@RequestMapping("/roles")
@Tag(name = "Rol", description = "Endpoints necesarios para la entidad de rol")
public class RolController {

    private final RolRepository rolRepository;
    private final RolMapper rolMapper;

    @Operation(summary = "Listar todos los roles")
    @GetMapping
    public ResponseEntity<List<RolResponse>> obtenerTodosLosRoles() {
        List<RolResponse> response = rolRepository.findAll()
                .stream()
                .map(rolMapper::fromEntityToDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener un rol por ID")
    @GetMapping("/{idRol}")
    public ResponseEntity<RolResponse> obtenerRolPorId(@PathVariable Integer idRol) {
        RolEntity rol = rolRepository.findById(idRol).orElse(null);
        if (Objects.isNull(rol)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(rolMapper.fromEntityToDTO(rol));
    }

    @Operation(summary = "Crear un nuevo rol")
    @PostMapping
    public ResponseEntity<RolResponse> crearRol(@RequestBody RolRequest rolRequest) {
        if (Objects.isNull(rolRequest) || Objects.isNull(rolRequest.getNombreRol())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "El campo 'nombreRol' es obligatorio.");
        }

        RolEntity toSave = rolMapper.fromDtoRequestToEntity(rolRequest);
        RolEntity saved = rolRepository.save(toSave);
        return ResponseEntity.status(HttpStatus.CREATED).body(rolMapper.fromEntityToDTO(saved));
    }

    @Operation(summary = "Actualizar un rol existente")
    @PutMapping("/{idRol}")
    public ResponseEntity<RolResponse> actualizarRol(@PathVariable Integer idRol,
                                                     @RequestBody RolRequest rolRequest) {
        RolEntity rol = rolRepository.findById(idRol).orElse(null);
        if (Objects.isNull(rol)) {
            return ResponseEntity.notFound().build();
        }

        rol.setNombreRol(rolRequest.getNombreRol());
        RolEntity updated = rolRepository.save(rol);
        return ResponseEntity.ok(rolMapper.fromEntityToDTO(updated));
    }

    @Operation(summary = "Eliminar un rol por ID")
    @DeleteMapping("/{idRol}")
    public ResponseEntity<Void> eliminarRol(@PathVariable Integer idRol) {
        if (Boolean.FALSE.equals(rolRepository.existsById(idRol))) {
            return ResponseEntity.notFound().build();
        }
        rolRepository.deleteById(idRol);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar roles por nombre (parcial o completo)")
    @GetMapping("/buscar")
    public ResponseEntity<List<RolResponse>> buscarRolesPorNombre(@RequestParam String nombre) {
        List<RolResponse> response = rolRepository.findByNombreRolContainingIgnoreCase(nombre)
                .stream()
                .map(rolMapper::fromEntityToDTO)
                .toList();
        return ResponseEntity.ok(response);
    }
}
