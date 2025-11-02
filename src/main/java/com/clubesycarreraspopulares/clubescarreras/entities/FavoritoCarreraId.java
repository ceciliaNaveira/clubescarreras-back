package com.clubesycarreraspopulares.clubescarreras.entities;

import java.io.Serializable;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class FavoritoCarreraId implements Serializable {
    private Integer usuario;
    private Integer carrera;
}
