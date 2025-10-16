package com.clubesycarreraspopulares.clubescarreras.repository;

import com.clubesycarreraspopulares.clubescarreras.entities.ClubEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface ClubRepository extends JpaRepository<ClubEntity, Integer> {

    @Query("SELECT DISTINCT c FROM ClubEntity c " +
           "JOIN c.localizacion l " +
           "LEFT JOIN c.entrenamientos e " +
           "WHERE (:nombre IS NULL OR c.nombre LIKE %:nombre%) " +
           "AND (:provincia IS NULL OR l.provincia = :provincia) " +
           "AND (:municipio IS NULL OR l.municipio = :municipio) " +
           "AND (:codigoPostal IS NULL OR l.codigoPostal = :codigoPostal) " +
           "AND (:diaSemana IS NULL OR e.diaSemana = :diaSemana)")
    List<ClubEntity> buscarPorFiltros(
            @Param("nombre") String nombre,
            @Param("provincia") String provincia,
            @Param("municipio") String municipio,
            @Param("codigoPostal") String codigoPostal,
            @Param("diaSemana") String diaSemana
    );
}
