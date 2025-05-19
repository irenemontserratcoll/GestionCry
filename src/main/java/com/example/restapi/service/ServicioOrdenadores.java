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

    public Optional<Ordenador> findById(Long id) {
        return repositorioOrdenadores.findById(id);
    }

    public List<Ordenador> findByMarca(String marca) {
        return repositorioOrdenadores.findByMarca(marca);
    }

    public List<Ordenador> findByModelo(String modelo) {
        return repositorioOrdenadores.findByModelo(modelo);
    }

    public Optional<Ordenador> findByNumeroSerie(String numeroSerie) {
        return repositorioOrdenadores.findByNumeroSerie(numeroSerie);
    }

    public List<Ordenador> findAll() {
        return repositorioOrdenadores.findAll();
    }

    public Ordenador addOrdenador(Ordenador ordenador) {
        return repositorioOrdenadores.save(ordenador);
    }

    public Ordenador updateOrdenador(Long id, Ordenador ordenador) {
        Optional<Ordenador> ordenadorExistente = repositorioOrdenadores.findById(id);
        if (ordenadorExistente.isPresent()) {
            ordenador.setId(id);
            return repositorioOrdenadores.save(ordenador);
        } else {
            throw new RuntimeException("Ordenador no encontrado con ID: " + id);
        }
    }

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