package com.example.restapi.service;

import com.example.restapi.model.Reserva;
import com.example.restapi.repository.ReservaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ReservaService {

    @Autowired
    private ReservaRepository reservaRepository;

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
            reserva.setId(id); // Mantener el ID original
            return reservaRepository.save(reserva);
        }
        return null;
    }
}
