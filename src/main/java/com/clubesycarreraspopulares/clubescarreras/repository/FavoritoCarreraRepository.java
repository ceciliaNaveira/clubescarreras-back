package com.clubesycarreraspopulares.clubescarreras.repository;

import com.clubesycarreraspopulares.clubescarreras.entities.FavoritoCarreraEntity;
import com.clubesycarreraspopulares.clubescarreras.entities.FavoritoCarreraId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritoCarreraRepository extends JpaRepository<FavoritoCarreraEntity, FavoritoCarreraId> {

    List<FavoritoCarreraEntity> findByUsuarioIdUsuario(Integer idUsuario);

    List<FavoritoCarreraEntity> findByCarreraIdCarrera(Integer idCarrera);
}
