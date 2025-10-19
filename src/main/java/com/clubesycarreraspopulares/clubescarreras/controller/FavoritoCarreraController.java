package com.clubesycarreraspopulares.clubescarreras.controller;

import com.clubesycarreraspopulares.clubescarreras.entities.*;
import com.clubesycarreraspopulares.clubescarreras.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/favoritos")
public class FavoritoCarreraController {

    @Autowired
    private FavoritoCarreraRepository favoritoCarreraRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private CarreraRepository carreraRepository;

    @PostMapping("/add")
    public String addFavorito(@RequestParam Integer usuarioId, @RequestParam Integer carreraId) {
        UsuarioEntity usuario = usuarioRepository.findById(usuarioId)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));
        CarreraEntity carrera = carreraRepository.findById(carreraId)
                .orElseThrow(() -> new RuntimeException("Carrera no encontrada"));

        FavoritoCarreraEntity favorito = FavoritoCarreraEntity.builder()
                .usuario(usuario)
                .carrera(carrera)
                .build();

        favoritoCarreraRepository.save(favorito);
        return "Favorito agregado correctamente";
    }

    @GetMapping("/all")
    public List<FavoritoCarreraEntity> getAll() {
        return favoritoCarreraRepository.findAll();
    }

    @DeleteMapping("/delete")
    public String deleteFavorito(@RequestParam Integer favoritoCarreraId) {
        favoritoCarreraRepository.deleteById(favoritoCarreraId);
        return "Favorito eliminado correctamente";
    }
}
