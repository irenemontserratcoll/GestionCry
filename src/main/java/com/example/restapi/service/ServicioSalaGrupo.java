package com.example.restapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restapi.model.SalaGrupal;
import com.example.restapi.repository.RepositorioSalaGrupo;

@Service
public class ServicioSalaGrupo {

    private final RepositorioSalaGrupo salaGrupalRepository;

    @Autowired
    public ServicioSalaGrupo(RepositorioSalaGrupo salaGrupalRepository) {
        this.salaGrupalRepository = salaGrupalRepository;
    }

    // Obtener todas las salas grupales
    public List<SalaGrupal> findAll() {
        return salaGrupalRepository.findAll();
    }

    // Buscar una sala grupal por piso y número de sala
    public Optional<SalaGrupal> findByPisoAndNumeroSala(int piso, int numeroSala) {
        return salaGrupalRepository.findByPisoAndNumeroSala(piso, numeroSala);
    }

    // Agregar una nueva sala grupal
    public void addSala(SalaGrupal salaGrupal) {
        salaGrupalRepository.save(salaGrupal);
    }

    public void updateSala(SalaGrupal salaGrupal) {
        if (salaGrupal.getId() != null && salaGrupalRepository.existsById(salaGrupal.getId())) {
            salaGrupalRepository.save(salaGrupal);
        } else {
            throw new RuntimeException("La sala no existe");
        }
    }

    public void deleteSala(int piso, int numeroSala) {
        Optional<SalaGrupal> salaGrupal = salaGrupalRepository.findByPisoAndNumeroSala(piso, numeroSala);
        if (salaGrupal.isPresent()) {
            salaGrupalRepository.delete(salaGrupal.get());
        } else {
            throw new RuntimeException("La sala no existe.");
        }
    }

    public Optional<SalaGrupal> findById(Long id) {
        return salaGrupalRepository.findById(id);
    }

}
