package com.clubesycarreraspopulares.clubescarreras.repository;

import com.clubesycarreraspopulares.clubescarreras.entities.EntrenamientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface EntrenamientoRepository extends JpaRepository<EntrenamientoEntity, Integer> {

    // Buscar entrenamientos por club
    List<EntrenamientoEntity> findByClubIdClub(Integer idClub);

    // Buscar entrenamientos por fecha exacta
    List<EntrenamientoEntity> findByFecha(LocalDateTime fecha);

    // Buscar entrenamientos por nivel
    List<EntrenamientoEntity> findByNivel(EntrenamientoEntity.Nivel nivel);

    // Búsqueda flexible por varios filtros (fecha, provincia, municipio, código postal)
    @Query("""
        SELECT e FROM EntrenamientoEntity e
        WHERE (:fecha IS NULL OR DATE(e.fecha) = DATE(:fecha))
          AND (:provincia IS NULL OR LOWER(e.club.localizacion.provincia) = LOWER(:provincia))
          AND (:municipio IS NULL OR LOWER(e.club.localizacion.municipio) = LOWER(:municipio))
          AND (:codigoPostal IS NULL OR e.club.localizacion.codigoPostal = :codigoPostal)
    """)
    List<EntrenamientoEntity> buscarPorFiltros(
            @Param("fecha") LocalDateTime fecha,
            @Param("provincia") String provincia,
            @Param("municipio") String municipio,
            @Param("codigoPostal") String codigoPostal
    );
}
