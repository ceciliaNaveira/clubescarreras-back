package com.clubesycarreraspopulares.clubescarreras.repository;

import com.clubesycarreraspopulares.clubescarreras.entities.ComentarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<ComentarioEntity, Integer> {

    List<ComentarioEntity> findByUsuario_UsuarioId(Integer usuarioId);
    List<ComentarioEntity> findByClub_ClubId(Integer clubId);
    List<ComentarioEntity> findByUsuario_UsuarioIdAndClub_ClubId(Integer usuarioId, Integer clubId);
}
