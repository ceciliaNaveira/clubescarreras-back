package com.clubesycarreraspopulares.clubescarreras.repository;

import com.clubesycarreraspopulares.clubescarreras.entities.FavoritoClubEntity;
import com.clubesycarreraspopulares.clubescarreras.entities.FavoritoClubId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface FavoritoClubRepository extends JpaRepository<FavoritoClubEntity, FavoritoClubId> {
    List<FavoritoClubEntity> findByUsuario_UsuarioId(Integer usuarioId);
    List<FavoritoClubEntity> findByClub_ClubId(Integer clubId);
}
