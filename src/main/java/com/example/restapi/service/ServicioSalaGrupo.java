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

    // Actualizar una sala grupal existente
    public void updateSala(SalaGrupal salaGrupal) {
        // Verificar si la sala existe antes de actualizar
        if (salaGrupalRepository.existsByPisoAndNumeroSala(salaGrupal.getPiso(), salaGrupal.getNumeroSala())) {
            salaGrupalRepository.save(salaGrupal);
        } else {
            throw new RuntimeException("La sala no existe");
        }
    }

    // Eliminar una sala grupal por piso y número de sala
    public void deleteSala(int piso, int numeroSala) {
        Optional<SalaGrupal> salaGrupal = salaGrupalRepository.findByPisoAndNumeroSala(piso, numeroSala);
        salaGrupal.ifPresent(sala -> salaGrupalRepository.delete(sala));
    }
}
