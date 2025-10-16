package com.clubesycarreraspopulares.clubescarreras.repository;

import com.clubesycarreraspopulares.clubescarreras.entities.ClubEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface ClubRepository extends JpaRepository<ClubEntity, Integer> {

    /**
     * Buscar clubes por filtros opcionales:
     * nombre, provincia, municipio, código postal y fecha de entrenamiento.
     * Si algún parámetro es nulo, no se aplica ese filtro.
     */
    @Query("""
        SELECT DISTINCT c 
        FROM ClubEntity c
        LEFT JOIN EntrenamientoEntity e ON e.club.idClub = c.idClub
        WHERE (:nombre IS NULL OR LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))
          AND (:provincia IS NULL OR LOWER(c.localizacion.provincia) = LOWER(:provincia))
          AND (:municipio IS NULL OR LOWER(c.localizacion.municipio) = LOWER(:municipio))
          AND (:codigoPostal IS NULL OR c.localizacion.codigoPostal = :codigoPostal)
          AND (:fechaEntrenamiento IS NULL OR DATE(e.fecha) = DATE(:fechaEntrenamiento))
    """)
    List<ClubEntity> buscarPorFiltros(
            @Param("nombre") String nombre,
            @Param("provincia") String provincia,
            @Param("municipio") String municipio,
            @Param("codigoPostal") String codigoPostal,
            @Param("fechaEntrenamiento") LocalDateTime fechaEntrenamiento
    );
}
