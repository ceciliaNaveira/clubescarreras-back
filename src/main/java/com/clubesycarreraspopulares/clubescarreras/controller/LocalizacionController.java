package com.clubesycarreraspopulares.clubescarreras.controller;

import com.clubesycarreraspopulares.clubescarreras.dto.LocalizacionRequest;
import com.clubesycarreraspopulares.clubescarreras.dto.LocalizacionResponse;
import com.clubesycarreraspopulares.clubescarreras.entities.LocalizacionEntity;
import com.clubesycarreraspopulares.clubescarreras.mapper.LocalizacionMapper;
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
@RequestMapping("/localizaciones")
@Tag(name = "Localizacion", description = "Endpoints para gestionar localizaciones")
public class LocalizacionController {

    private final LocalizacionRepository localizacionRepository;
    private final LocalizacionMapper localizacionMapper;

    @Operation(summary = "Listar todas las localizaciones")
    @GetMapping
    public ResponseEntity<List<LocalizacionResponse>> obtenerTodas() {
        List<LocalizacionResponse> response = localizacionRepository.findAll()
                .stream()
                .map(localizacionMapper::fromEntityToDTO)
                .toList();
        return ResponseEntity.ok(response);
    }

    @Operation(summary = "Obtener una localización por ID")
    @GetMapping("/{id}")
    public ResponseEntity<LocalizacionResponse> obtenerPorId(@PathVariable Integer id) {
        LocalizacionEntity entity = localizacionRepository.findById(id).orElse(null);
        if (Objects.isNull(entity)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(localizacionMapper.fromEntityToDTO(entity));
    }

    @Operation(summary = "Crear una nueva localización")
    @PostMapping
    public ResponseEntity<LocalizacionResponse> crear(@RequestBody LocalizacionRequest request) {
        if (Objects.isNull(request) || Objects.isNull(request.getProvincia()) || Objects.isNull(request.getMunicipio())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Provincia y municipio son obligatorios.");
        }
        LocalizacionEntity saved = localizacionRepository.save(localizacionMapper.fromDtoRequestToEntity(request));
        return ResponseEntity.status(HttpStatus.CREATED).body(localizacionMapper.fromEntityToDTO(saved));
    }

    @Operation(summary = "Actualizar una localización existente")
    @PutMapping("/{id}")
    public ResponseEntity<LocalizacionResponse> actualizar(@PathVariable Integer id,
                                                           @RequestBody LocalizacionRequest request) {
        LocalizacionEntity entity = localizacionRepository.findById(id).orElse(null);
        if (Objects.isNull(entity)) {
            return ResponseEntity.notFound().build();
        }

        entity.setProvincia(request.getProvincia());
        entity.setMunicipio(request.getMunicipio());
        entity.setCodigoPostal(request.getCodigoPostal());
        entity.setDireccion(request.getDireccion());
        entity.setLatitud(request.getLatitud());
        entity.setLongitud(request.getLongitud());

        LocalizacionEntity updated = localizacionRepository.save(entity);
        return ResponseEntity.ok(localizacionMapper.fromEntityToDTO(updated));
    }

    @Operation(summary = "Eliminar una localización por ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (Boolean.FALSE.equals(localizacionRepository.existsById(id))) {
            return ResponseEntity.notFound().build();
        }
        localizacionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @Operation(summary = "Buscar localizaciones por provincia o municipio")
    @GetMapping("/buscar")
    public ResponseEntity<List<LocalizacionResponse>> buscar(@RequestParam(required = false) String provincia,
                                                             @RequestParam(required = false) String municipio) {
        List<LocalizacionEntity> results;

        if (provincia != null) {
            results = localizacionRepository.findByProvinciaContainingIgnoreCase(provincia);
        } else if (municipio != null) {
            results = localizacionRepository.findByMunicipioContainingIgnoreCase(municipio);
        } else {
            results = localizacionRepository.findAll();
        }

        List<LocalizacionResponse> response = results.stream()
                .map(localizacionMapper::fromEntityToDTO)
                .toList();

        return ResponseEntity.ok(response);
    }
}
