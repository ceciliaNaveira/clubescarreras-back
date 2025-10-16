package com.clubesycarreraspopulares.clubescarreras.entities;

import java.io.Serializable;
import java.util.Objects;

public class FavoritoCarreraId implements Serializable {

    private Integer usuario;
    private Integer carrera;

    public FavoritoCarreraId() {}

    public FavoritoCarreraId(Integer usuario, Integer carrera) {
        this.usuario = usuario;
        this.carrera = carrera;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FavoritoCarreraId)) return false;
        FavoritoCarreraId that = (FavoritoCarreraId) o;
        return Objects.equals(usuario, that.usuario) && Objects.equals(carrera, that.carrera);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, carrera);
    }
}
