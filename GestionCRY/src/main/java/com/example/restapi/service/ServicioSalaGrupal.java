package com.example.restapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.example.restapi.model.ReservarSalaGrupal;
import com.example.restapi.repository.RepositorioSalaGrupo;

@Service
public class ServicioSalaGrupo {

    private final RepositorioSalaGrupo repositorioSalaGrupo;

    @Autowired
    public ServicioSalaGrupo(RepositorioSalaGrupo repositorioSalaGrupo) {
        this.repositorioSalaGrupo = repositorioSalaGrupo;
    }

    // Método para obtener todas las reservas de salas grupales
    public List<ReservarSalaGrupal> findAll() {
        return repositorioSalaGrupo.findAll();
    }

    // Método para buscar una reserva por ID
    public Optional<ReservarSalaGrupal> findById(Integer id) {
        return repositorioSalaGrupo.findById(id);
    }

    // Método para agregar una nueva reserva
    public ReservarSalaGrupal addReserva(ReservarSalaGrupal reserva) {
        return repositorioSalaGrupo.save(reserva);
    }

    // Método para eliminar una reserva por ID
    public void deleteReserva(Integer id) {
        repositorioSalaGrupo.deleteById(id);
    }
}