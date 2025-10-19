package com.clubesycarreraspopulares.clubescarreras.entities;

import java.io.Serializable;
import lombok.*;

@Data           // genera getters, setters, equals, hashCode y toString
@NoArgsConstructor
@AllArgsConstructor
public class FavoritoCarreraId implements Serializable {
    private Integer favoritoCarreraId;
    private Integer usuario;
    private Integer carrera;
}
