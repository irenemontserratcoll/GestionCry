package com.example.restapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restapi.model.SalaGrupal;

public interface RepositorioSalaGrupo extends JpaRepository<SalaGrupal, Long> {
    Optional<SalaGrupal> findByPisoAndNumeroSala(int piso, int numeroSala);
    boolean existsByPisoAndNumeroSala(int piso, int numeroSala);
}