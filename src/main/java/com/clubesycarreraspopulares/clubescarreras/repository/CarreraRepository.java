package com.clubesycarreraspopulares.clubescarreras.repository;

import com.clubesycarreraspopulares.clubescarreras.entities.CarreraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CarreraRepository extends JpaRepository<CarreraEntity, Integer> {

    // Buscar por nombre parcial
    @Query("SELECT c FROM CarreraEntity c WHERE (:nombre IS NULL OR LOWER(c.nombre) LIKE LOWER(CONCAT('%', :nombre, '%')))")
    List<CarreraEntity> findByNombre(@Param("nombre") String nombre);

    // Buscar por fecha
    @Query("SELECT c FROM CarreraEntity c WHERE (:fecha IS NULL OR c.fecha = :fecha)")
    List<CarreraEntity> findByFecha(@Param("fecha") LocalDate fecha);

    // Buscar por distancia exacta
    @Query("SELECT c FROM CarreraEntity c WHERE (:distancia IS NULL OR c.distanciaKm = :distancia)")
    List<CarreraEntity> findByDistancia(@Param("distancia") Double distancia);

    // Buscar por rango de distancia
    @Query("SELECT c FROM CarreraEntity c WHERE (:min IS NULL OR :max IS NULL OR c.distanciaKm BETWEEN :min AND :max)")
    List<CarreraEntity> findByRangoDistancia(@Param("min") Double min, @Param("max") Double max);

    // Buscar por código postal de la localización
    @Query("SELECT c FROM CarreraEntity c WHERE (:codigoPostal IS NULL OR c.localizacion.codigoPostal = :codigoPostal)")
    List<CarreraEntity> findByCodigoPostal(@Param("codigoPostal") String codigoPostal);

    // Buscar por municipio
    @Query("SELECT c FROM CarreraEntity c WHERE (:municipio IS NULL OR LOWER(c.localizacion.municipio) = LOWER(:municipio))")
    List<CarreraEntity> findByMunicipio(@Param("municipio") String municipio);

    // Buscar por provincia
    @Query("SELECT c FROM CarreraEntity c WHERE (:provincia IS NULL OR LOWER(c.localizacion.provincia) = LOWER(:provincia))")
    List<CarreraEntity> findByProvincia(@Param("provincia") String provincia);

    // Buscar por club
    @Query("SELECT c FROM CarreraEntity c WHERE (:idClub IS NULL OR c.club.idClub = :idClub)")
    List<CarreraEntity> findByClub(@Param("idClub") Integer idClub);
}
