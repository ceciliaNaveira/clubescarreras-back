package com.clubesycarreraspopulares.clubescarreras.repository;

import com.clubesycarreraspopulares.clubescarreras.entities.LocalizacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository para la entidad LocalizacionEntity.
 * Permite realizar operaciones CRUD y consultas personalizadas sobre la tabla 'Localizacion'.
 */
@Repository
public interface LocalizacionRepository extends JpaRepository<LocalizacionEntity, Integer> {

    /**
     * Busca localizaciones filtrando por provincia y/o municipio.
     * Si los parámetros son null, no filtra por ese campo.
     *
     * @param provincia Provincia a filtrar (opcional)
     * @param municipio Municipio a filtrar (opcional)
     * @return Lista de localizaciones que cumplen los filtros
     */
    @Query("SELECT l FROM LocalizacionEntity l " +
           "WHERE (:provincia IS NULL OR LOWER(l.provincia) = LOWER(:provincia)) " +
           "AND (:municipio IS NULL OR LOWER(l.municipio) = LOWER(:municipio))")
    List<LocalizacionEntity> findByProvinciaAndMunicipio(
            @Param("provincia") String provincia,
            @Param("municipio") String municipio
    );
}