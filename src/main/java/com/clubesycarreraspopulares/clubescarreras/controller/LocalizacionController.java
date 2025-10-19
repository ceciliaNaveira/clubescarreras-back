package com.clubesycarreraspopulares.clubescarreras.controller;

import com.clubesycarreraspopulares.clubescarreras.entities.LocalizacionEntity;
import com.clubesycarreraspopulares.clubescarreras.repository.LocalizacionRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

@AllArgsConstructor
@RestController
@RequestMapping("/localizaciones")
@Tag(name = "Localizacion", description = "Endpoints para gestionar localizaciones")
public class LocalizacionController {

    private final LocalizacionRepository localizacionRepository;

    @Operation(summary = "Obtener todas las localizaciones")
    @GetMapping
    public ResponseEntity<List<LocalizacionEntity>> obtenerTodas() {
        return ResponseEntity.ok(localizacionRepository.findAll());
    }

    @Operation(summary = "Obtener una localización por ID")
    @GetMapping("/{id}")
    public ResponseEntity<LocalizacionEntity> obtenerPorId(@PathVariable Integer id) {
        Optional<LocalizacionEntity> localizacion = localizacionRepository.findById(id);
        return localizacion.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Crear una nueva localización")
    @PostMapping
    public ResponseEntity<LocalizacionEntity> crear(@RequestBody LocalizacionEntity localizacion) {
        LocalizacionEntity saved = localizacionRepository.save(localizacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    @Operation(summary = "Actualizar una localización existente")
    @PutMapping("/{id}")
    public ResponseEntity<LocalizacionEntity> actualizar(@PathVariable Integer id,
                                                         @RequestBody LocalizacionEntity localizacion) {
        Optional<LocalizacionEntity> existente = localizacionRepository.findById(id);
        if (existente.isEmpty()) {
            return ResponseEntity.notFound().build();
        }

        LocalizacionEntity entity = existente.get();
        entity.setProvincia(localizacion.getProvincia());
        entity.setMunicipio(localizacion.getMunicipio());
        entity.setCodigoPostal(localizacion.getCodigoPostal());
        entity.setDireccion(localizacion.getDireccion());
        entity.setLatitud(localizacion.getLatitud() != null ? localizacion.getLatitud() : BigDecimal.ZERO);
        entity.setLongitud(localizacion.getLongitud() != null ? localizacion.getLongitud() : BigDecimal.ZERO);

        LocalizacionEntity updated = localizacionRepository.save(entity);
        return ResponseEntity.ok(updated);
    }

    @Operation(summary = "Eliminar una localización")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminar(@PathVariable Integer id) {
        if (!localizacionRepository.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        localizacionRepository.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}
