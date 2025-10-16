package com.clubesycarreraspopulares.clubescarreras.entities;

import java.io.Serializable;
import java.util.Objects;

public class FavoritoClubId implements Serializable {

    private Integer usuario;
    private Integer club;

    public FavoritoClubId() {}

    public FavoritoClubId(Integer usuario, Integer club) {
        this.usuario = usuario;
        this.club = club;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof FavoritoClubId)) return false;
        FavoritoClubId that = (FavoritoClubId) o;
        return Objects.equals(usuario, that.usuario) && Objects.equals(club, that.club);
    }

    @Override
    public int hashCode() {
        return Objects.hash(usuario, club);
    }
}

