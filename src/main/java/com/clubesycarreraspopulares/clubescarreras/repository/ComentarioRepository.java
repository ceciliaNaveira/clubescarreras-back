package com.clubesycarreraspopulares.clubescarreras.repository;

import com.clubesycarreraspopulares.clubescarreras.entities.ComentarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<ComentarioEntity, Integer> {

    // Buscar comentarios por el ID del usuario
    List<ComentarioEntity> findByUsuario_UsuarioId(Integer usuarioId);

    // Buscar comentarios por el ID del club
    List<ComentarioEntity> findByClub_ClubId(Integer clubId);

    // Buscar comentarios filtrando por usuario y club al mismo tiempo
    List<ComentarioEntity> findByUsuario_UsuarioIdAndClub_ClubId(Integer usuarioId, Integer clubId);
}
