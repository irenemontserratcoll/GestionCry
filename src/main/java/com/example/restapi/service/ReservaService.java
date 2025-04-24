package com.example.restapi.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.restapi.model.Reserva;
import com.example.restapi.repository.RepositorioEspacioIndividual;
import com.example.restapi.repository.RepositorioLibros;
import com.example.restapi.repository.RepositorioOrdenadores;
import com.example.restapi.repository.RepositorioSalaGrupo;
import com.example.restapi.repository.ReservaRepository;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private RepositorioLibros libroRepository;

    @Autowired
    private RepositorioOrdenadores ordenadorRepository;

    @Autowired
    private RepositorioSalaGrupo salaGrupalRepository;

    @Autowired
    private RepositorioEspacioIndividual espacioIndividualRepository;

    // Obtener todas las reservas
    public List<Reserva> obtenerTodasLasReservas() {
        return reservaRepository.findAll();
    }

    // Obtener una reserva por ID
    public Reserva obtenerReservaPorId(Long id) {
        Optional<Reserva> reserva = reservaRepository.findById(id);
        return reserva.orElse(null);
    }

    // Crear una nueva reserva
    public Reserva crearReserva(Reserva reserva) {
        cargarRecursosRelacionados(reserva);
        return reservaRepository.save(reserva);
    }

    // Eliminar una reserva
    public boolean eliminarReserva(Long id) {
        if (reservaRepository.existsById(id)) {
            reservaRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Modificar una reserva
    public Reserva modificarReserva(Long id, Reserva reserva) {
        if (reservaRepository.existsById(id)) {
            reserva.setId(id);
            cargarRecursosRelacionados(reserva);
            return reservaRepository.save(reserva);
        }
        return null;
    }

    // Método auxiliar para cargar los recursos por ID desde la base de datos
    private void cargarRecursosRelacionados(Reserva reserva) {
        if (reserva.getLibro() != null && reserva.getLibro().getId() != null) {
            reserva.setLibro(libroRepository.findById(reserva.getLibro().getId()).orElse(null));
        }

        if (reserva.getOrdenador() != null && reserva.getOrdenador().getId() != null) {
            reserva.setOrdenador(ordenadorRepository.findById(reserva.getOrdenador().getId()).orElse(null));
        }

        if (reserva.getSalaGrupal() != null && reserva.getSalaGrupal().getId() != null) {
            reserva.setSalaGrupal(salaGrupalRepository.findById(reserva.getSalaGrupal().getId()).orElse(null));
        }

        if (reserva.getEspacioIndividual() != null && reserva.getEspacioIndividual().getId() != null) {
            reserva.setEspacioIndividual(espacioIndividualRepository.findById(reserva.getEspacioIndividual().getId()).orElse(null));
        }
    }
}
