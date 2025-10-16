package com.clubesycarreraspopulares.clubescarreras.repository;

import com.clubesycarreraspopulares.clubescarreras.entities.EntrenamientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntrenamientoRepository extends JpaRepository<EntrenamientoEntity, Integer> {

    // Buscar entrenamientos por club
    List<EntrenamientoEntity> findByClubIdClub(Integer idClub);

    // Buscar entrenamientos por nivel
    List<EntrenamientoEntity> findByNivel(EntrenamientoEntity.Nivel nivel);

    // Búsqueda flexible por día de la semana, provincia, municipio o código postal
    @Query("""
        SELECT e FROM EntrenamientoEntity e
        WHERE (:diaSemana IS NULL OR e.diaSemana = :diaSemana)
          AND (:provincia IS NULL OR LOWER(e.club.localizacion.provincia) = LOWER(:provincia))
          AND (:municipio IS NULL OR LOWER(e.club.localizacion.municipio) = LOWER(:municipio))
          AND (:codigoPostal IS NULL OR e.club.localizacion.codigoPostal = :codigoPostal)
    """)
    List<EntrenamientoEntity> buscarPorFiltros(
            @Param("diaSemana") String diaSemana,
            @Param("provincia") String provincia,
            @Param("municipio") String municipio,
            @Param("codigoPostal") String codigoPostal
    );
}
