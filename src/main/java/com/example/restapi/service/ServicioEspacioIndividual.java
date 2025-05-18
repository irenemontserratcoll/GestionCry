package com.example.restapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restapi.model.EspacioIndividual;
import com.example.restapi.repository.RepositorioEspacioIndividual;

@Service
public class ServicioEspacioIndividual {

    private final RepositorioEspacioIndividual espacioRepository;

    @Autowired
    public ServicioEspacioIndividual(RepositorioEspacioIndividual espacioRepository) {
        this.espacioRepository = espacioRepository;
    }

    // Obtener todos los espacios
    public List<EspacioIndividual> findAll() {
        return espacioRepository.findAll();
    }

    // Buscar un espacio por piso y número de asiento
    public Optional<EspacioIndividual> findByPisoAndAsiento(int piso, int numeroAsiento) {
        return espacioRepository.findByPisoAndNumeroAsiento(piso, numeroAsiento);
    }

    // Agregar un nuevo espacio
    public void addEspacio(EspacioIndividual espacio) {
        espacioRepository.save(espacio);
    }

    // Actualizar un espacio existente
    public void updateEspacio(EspacioIndividual espacio) {
        if (espacioRepository.existsByPisoAndNumeroAsiento(espacio.getPiso(), espacio.getNumeroAsiento())) {
            espacioRepository.save(espacio);
        } else {
            throw new RuntimeException("El espacio no existe");
        }
    }

    public void deleteEspacio(int piso, int numeroAsiento) {
        Optional<EspacioIndividual> espacio = espacioRepository.findByPisoAndNumeroAsiento(piso, numeroAsiento);
        if (espacio.isPresent()) {
            espacioRepository.delete(espacio.get());
        } else {
            System.out.println("Espacio no encontrado para eliminar: Piso " + piso + ", Asiento " + numeroAsiento);
            throw new RuntimeException("Espacio no encontrado");
        }
    }

    public void deleteById(Long id) {
        if (espacioRepository.existsById(id)) {
            espacioRepository.deleteById(id);
        } else {
            throw new RuntimeException("No existe el espacio con ID: " + id);
        }
    }
}