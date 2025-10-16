package com.clubesycarreraspopulares.clubescarreras.repository;

import com.clubesycarreraspopulares.clubescarreras.entities.ComentarioEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ComentarioRepository extends JpaRepository<ComentarioEntity, Integer> {

    // Buscar comentarios por club
    List<ComentarioEntity> findByClubIdClub(Integer idClub);

    // Buscar comentarios por usuario
    List<ComentarioEntity> findByUsuarioIdUsuario(Integer idUsuario);

    // Buscar comentarios por valoración
    List<ComentarioEntity> findByValoracion(Byte valoracion);
}
