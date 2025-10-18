package com.clubesycarreraspopulares.clubescarreras.entities;

import lombok.*;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode
public class FavoritoClubId implements Serializable {
    private Integer usuario;
    private Integer club;
}
