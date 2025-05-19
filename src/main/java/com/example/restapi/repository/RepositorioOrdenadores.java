package com.example.restapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restapi.model.Ordenador;

public interface RepositorioOrdenadores extends JpaRepository<Ordenador, Long> {
    Optional<Ordenador> findById(Long id);

    List<Ordenador> findByMarca(String marca);

    List<Ordenador> findByModelo(String modelo);

    Optional<Ordenador> findByNumeroSerie(String numeroSerie);

    List<Ordenador> findByDisponible(boolean disponible);

    List<Ordenador> findAll();
}
