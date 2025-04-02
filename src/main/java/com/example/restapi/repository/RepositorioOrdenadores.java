package com.example.restapi.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

import com.example.restapi.model.Ordenador;

@Repository
public interface RepositorioOrdenadores extends JpaRepository<Ordenador, Long> {
    Optional<Ordenador> findById(Long id);
    Optional<Ordenador> findByMarca(String marca);
    Optional<Ordenador> findByNumeroSerie(String numeroSerie);
    List<Ordenador> findByDisponible(boolean disponible);
    List<Ordenador> findAll();
}