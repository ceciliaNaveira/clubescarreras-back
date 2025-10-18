package com.clubesycarreraspopulares.clubescarreras.repository;

import com.clubesycarreraspopulares.clubescarreras.entities.EntrenamientoEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface EntrenamientoRepository extends JpaRepository<EntrenamientoEntity, Integer> {
    List<EntrenamientoEntity> findByClub_ClubId(Integer clubId);
    List<EntrenamientoEntity> findByDiaSemanaContainingIgnoreCase(String diaSemana);
    List<EntrenamientoEntity> findByNivelContainingIgnoreCase(String nivel);
}
