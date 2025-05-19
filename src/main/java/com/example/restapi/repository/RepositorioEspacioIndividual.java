package com.example.restapi.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restapi.model.EspacioIndividual;

public interface RepositorioEspacioIndividual extends JpaRepository<EspacioIndividual, Long> {
    Optional<EspacioIndividual> findByPisoAndNumeroAsiento(int piso, int numeroAsiento);

    boolean existsByPisoAndNumeroAsiento(int piso, int numeroAsiento);
}