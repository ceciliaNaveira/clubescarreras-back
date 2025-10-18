package com.clubesycarreraspopulares.clubescarreras.repository;

import com.clubesycarreraspopulares.clubescarreras.entities.LocalizacionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LocalizacionRepository extends JpaRepository<LocalizacionEntity, Integer> {
    List<LocalizacionEntity> findByProvinciaContainingIgnoreCase(String provincia);
    List<LocalizacionEntity> findByMunicipioContainingIgnoreCase(String municipio);
}
