package com.example.restapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.example.restapi.model.Ordenador;
import com.example.restapi.repository.RepositorioOrdenadores;

@Service
public class ServicioOrdenadores {

    private final RepositorioOrdenadores repositorioOrdenadores;

    @Autowired
    public ServicioOrdenadores(RepositorioOrdenadores repositorioOrdenadores) {
        this.repositorioOrdenadores = repositorioOrdenadores;
    }

    // Obtener un ordenador por ID
    public Optional<Ordenador> findById(Long id) {
        return repositorioOrdenadores.findById(id);
    }

    // Obtener un ordenador por marca
    public Optional<Ordenador> findByMarca(String marca) {
        return repositorioOrdenadores.findByMarca(marca);
    }

    // Obtener un ordenador por número de serie
    public Optional<Ordenador> findByNumeroSerie(String numeroSerie) {
        return repositorioOrdenadores.findByNumeroSerie(numeroSerie);
    }

    // Obtener todos los ordenadores
    public List<Ordenador> findAll() {
        return repositorioOrdenadores.findAll();
    }

    // Obtener ordenadores disponibles o no disponibles
    // public List<Ordenador> findByDisponible(boolean disponible) {
    // return repositorioOrdenadores.findByDisponible(disponible);
    // }

    // Agregar un nuevo ordenador
    public Ordenador addOrdenador(Ordenador ordenador) {
        return repositorioOrdenadores.save(ordenador);
    }

    // Actualizar un ordenador existente
    public Ordenador updateOrdenador(Long id, Ordenador ordenador) {
        Optional<Ordenador> OrdenadorExistente = repositorioOrdenadores.findById(id);
        if (OrdenadorExistente.isPresent()) {
            ordenador.setId(id);
            return repositorioOrdenadores.save(ordenador);
        } else {
            throw new RuntimeException("Ordenador no encontrado con ID: " + id);
        }
    }

    // Eliminar un ordenador por ID
    public void deleteOrdenador(Long id) {
        repositorioOrdenadores.deleteById(id);
    }

    public void deleteByNumeroSerie(String numeroSerie) {
        Optional<Ordenador> ordenadorOpt = repositorioOrdenadores.findByNumeroSerie(numeroSerie);
        if (ordenadorOpt.isPresent()) {
            repositorioOrdenadores.delete(ordenadorOpt.get());
        } else {
            throw new RuntimeException("Ordenador no encontrado con número de serie: " + numeroSerie);
        }
    }
}