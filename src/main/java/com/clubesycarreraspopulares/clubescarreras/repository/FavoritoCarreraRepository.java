package com.clubesycarreraspopulares.clubescarreras.repository;

import com.clubesycarreraspopulares.clubescarreras.entities.FavoritoCarreraEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritoCarreraRepository extends JpaRepository<FavoritoCarreraEntity, Integer> {

    List<FavoritoCarreraEntity> findByUsuario_UsuarioId(Integer usuarioId);
    List<FavoritoCarreraEntity> findByCarrera_CarreraId(Integer carreraId);

    boolean existsByUsuario_UsuarioIdAndCarrera_CarreraId(Integer usuarioId, Integer carreraId);
}
