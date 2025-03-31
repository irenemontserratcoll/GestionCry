package com.example.restapi.repository;

import com.example.restapi.model.SalaGrupal;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

public interface RepositorioSalaGrupo extends JpaRepository<SalaGrupal, Long> {
    Optional<SalaGrupal> findByPisoAndNumeroSala(int piso, int numeroSala);
    boolean existsByPisoAndNumeroSala(int piso, int numeroSala);
}