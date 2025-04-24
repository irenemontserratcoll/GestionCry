package com.example.restapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.restapi.model.Reserva;
import com.example.restapi.service.ReservaService;

@RestController
@RequestMapping("/api/reservas")
public class ReservaController {

    @Autowired
    private ReservaService reservaService;

    // Obtener todas las reservas
    @GetMapping("/all")
    public ResponseEntity<List<Reserva>> obtenerTodasLasReservas() {
        List<Reserva> reservas = reservaService.obtenerTodasLasReservas();
        return new ResponseEntity<>(reservas, HttpStatus.OK);
    }

    // Obtener una reserva por ID
    @GetMapping("/{id}")
    public ResponseEntity<Reserva> obtenerReservaPorId(@PathVariable Long id) {
        Reserva reserva = reservaService.obtenerReservaPorId(id);
        return reserva != null ? new ResponseEntity<>(reserva, HttpStatus.OK)
                               : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Crear una nueva reserva
    @PostMapping("/add")
    public ResponseEntity<Reserva> crearReserva(@RequestBody Reserva reserva) {
        if (reserva != null) {
            Reserva nuevaReserva = reservaService.crearReserva(reserva);
            return new ResponseEntity<>(nuevaReserva, HttpStatus.CREATED);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    // Eliminar una reserva
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarReserva(@PathVariable Long id) {
        boolean eliminado = reservaService.eliminarReserva(id);
        return eliminado ? new ResponseEntity<>(HttpStatus.NO_CONTENT)
                         : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    // Modificar una reserva
    @PutMapping("/{id}")
    public ResponseEntity<Reserva> modificarReserva(@PathVariable Long id, @RequestBody Reserva reserva) {
        Reserva reservaModificada = reservaService.modificarReserva(id, reserva);
        return reservaModificada != null ? new ResponseEntity<>(reservaModificada, HttpStatus.OK)
                                         : new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
}
