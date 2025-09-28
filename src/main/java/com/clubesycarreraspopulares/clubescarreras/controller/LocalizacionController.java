package com.clubesycarreraspopulares.clubescarreras.controller;

import com.clubesycarreraspopulares.clubescarreras.entities.LocalizacionEntity;
import com.clubesycarreraspopulares.clubescarreras.repository.LocalizacionRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;

/**
 * Controller para la entidad Localizacion.
 * Expone los endpoints REST para CRUD y búsquedas.
 */
@AllArgsConstructor
@RestController
@RequestMapping("/localizaciones")
@Tag(name = "Localizacion", description = "Endpoints para la entidad Localizacion")
public class LocalizacionController {

    private final LocalizacionRepository localizacionRepository;

    /** Listar todas las localizaciones */
    @Operation(summary = "Listar todas")
    @GetMapping
    public ResponseEntity<List<LocalizacionEntity>> obtenerTodasLasLocalizaciones() {
        return ResponseEntity.ok(localizacionRepository.findAll());
    }

    /** Obtener una localización por ID */
    @Operation(summary = "Obtener por id")
    @GetMapping("/{idLocalizacion}")
    public ResponseEntity<LocalizacionEntity> obtenerLocalizacionById(@PathVariable Integer idLocalizacion) {
        LocalizacionEntity localizacionEntity = localizacionRepository.findById(idLocalizacion).orElse(null);
        if (Objects.isNull(localizacionEntity)) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(localizacionEntity);
    }

    /** Crear una nueva localización */
    @Operation(summary = "Crear")
    @PostMapping
    public ResponseEntity<LocalizacionEntity> añadirLocalizacion(@RequestBody LocalizacionEntity localizacionEntity) {
        // Ignorar el ID que venga en el JSON, ya que es autogenerado
        localizacionEntity.setIdLocalizacion(null);
        LocalizacionEntity saved = localizacionRepository.save(localizacionEntity);
        return ResponseEntity.status(HttpStatus.CREATED).body(saved);
    }

    /** Actualizar una localización existente */
    @Operation(summary = "Actualizar por id")
    @PutMapping("/{idLocalizacion}")
    public ResponseEntity<LocalizacionEntity> actualizarLocalizacion(@PathVariable Integer idLocalizacion,
                                                                     @RequestBody LocalizacionEntity body) {
        LocalizacionEntity localizacionEntity = localizacionRepository.findById(idLocalizacion).orElse(null);
        if (Objects.isNull(localizacionEntity)) {
            return ResponseEntity.notFound().build();
        }

        // Actualizar campos
        localizacionEntity.setProvincia(body.getProvincia());
        localizacionEntity.setMunicipio(body.getMunicipio());
        localizacionEntity.setCodigoPostal(body.getCodigoPostal());
        localizacionEntity.setDireccion(body.getDireccion());
        localizacionEntity.setLatitud(body.getLatitud());
        localizacionEntity.setLongitud(body.getLongitud());

        LocalizacionEntity updated = localizacionRepository.save(localizacionEntity);
        return ResponseEntity.ok(updated);
    }

    /** Eliminar una localización por ID */
    @Operation(summary = "Eliminar por id")
    @DeleteMapping("/{idLocalizacion}")
    public ResponseEntity<Void> eliminarLocalizacion(@PathVariable Integer idLocalizacion) {
        if (Boolean.FALSE.equals(localizacionRepository.existsById(idLocalizacion))) {
            return ResponseEntity.notFound().build();
        }
        localizacionRepository.deleteById(idLocalizacion);
        return ResponseEntity.noContent().build();
    }

    /** Buscar localizaciones por provincia y/o municipio */
    @Operation(summary = "Buscar por provincia y/o municipio")
    @GetMapping("/buscar")
    public ResponseEntity<List<LocalizacionEntity>> buscarLocalizacionPorProvinciaMunicipio(
            @RequestParam(required = false) String provincia,
            @RequestParam(required = false) String municipio) {

        List<LocalizacionEntity> resultados =
                localizacionRepository.findByProvinciaAndMunicipio(provincia, municipio);
        return ResponseEntity.ok(resultados);
    }
}

