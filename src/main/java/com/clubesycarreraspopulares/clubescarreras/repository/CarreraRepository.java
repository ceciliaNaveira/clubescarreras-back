package com.clubesycarreraspopulares.clubescarreras.repository;

import com.clubesycarreraspopulares.clubescarreras.entities.CarreraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface CarreraRepository extends JpaRepository<CarreraEntity, Integer> {

    List<CarreraEntity> findByNombreContainingIgnoreCase(String nombre);

    List<CarreraEntity> findByFechaBetween(LocalDate inicio, LocalDate fin);

    List<CarreraEntity> findByLocalizacion_LocalizacionId(Integer localizacionId);

    List<CarreraEntity> findByClub_ClubId(Integer clubId);
}
