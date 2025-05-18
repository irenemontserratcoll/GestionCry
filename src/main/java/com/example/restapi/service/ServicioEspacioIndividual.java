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

    public void updateEspacio(EspacioIndividual espacio) {
        Optional<EspacioIndividual> espacioExistente = espacioRepository.findById(espacio.getId());
        if (espacioExistente.isPresent()) {
            EspacioIndividual espacioAActualizar = espacioExistente.get();

            // Actualiza las propiedades que quieres modificar
            espacioAActualizar.setPiso(espacio.getPiso());
            espacioAActualizar.setNumeroAsiento(espacio.getNumeroAsiento());

            // Guarda el espacio actualizado
            espacioRepository.save(espacioAActualizar);
        } else {
            throw new RuntimeException("El espacio con ID " + espacio.getId() + " no existe");
        }
    }

    public void deleteEspacio(Long id) {
        if (espacioRepository.existsById(id)) {
            espacioRepository.deleteById(id);
        } else {
            throw new RuntimeException("No existe el espacio con ID: " + id);
        }
    }
}