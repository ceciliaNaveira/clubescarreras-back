package com.clubesycarreraspopulares.clubescarreras.repository;

import com.clubesycarreraspopulares.clubescarreras.entities.FavoritoClubEntity;
import com.clubesycarreraspopulares.clubescarreras.entities.FavoritoClubId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritoClubRepository extends JpaRepository<FavoritoClubEntity, FavoritoClubId> {

    // Buscar favoritos de un usuario
    List<FavoritoClubEntity> findByUsuarioIdUsuario(Integer idUsuario);

    // Buscar usuarios que tienen un club favorito
    List<FavoritoClubEntity> findByClubIdClub(Integer idClub);
}
