package com.clubesycarreraspopulares.clubescarreras.repository;

import com.clubesycarreraspopulares.clubescarreras.entities.RolEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Repository para la entidad RolEntity.
 * Permite realizar operaciones CRUD y consultas personalizadas sobre la tabla 'Rol'.
 */
@Repository
public interface RolRepository extends JpaRepository<RolEntity, Integer> {

    /**
     * Busca roles filtrando por nombre.
     * Si el parámetro es null, devuelve todos los roles.
     *
     * @param nombreRol Nombre del rol a filtrar (opcional)
     * @return Lista de roles que cumplen el filtro
     */
    @Query("SELECT r FROM RolEntity r " +
           "WHERE (:nombreRol IS NULL OR LOWER(r.nombreRol) LIKE LOWER(CONCAT('%', :nombreRol, '%')))")
    List<RolEntity> findByNombreRol(
            @Param("nombreRol") String nombreRol
    );
}
