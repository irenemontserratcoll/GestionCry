package com.example.restapi.controller;

import java.util.Collections;
import java.util.Date;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.example.restapi.model.Reserva;
import com.example.restapi.service.ReservaService;

public class ReservaControllerTest {

    @InjectMocks
    private ReservaController reservaController;

    @Mock
    private ReservaService reservaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Reserva crearReservaEjemplo() {
        Reserva reserva = new Reserva();
        reserva.setId(1L);
        reserva.setNombreCliente("Juan Pérez");
        reserva.setEmailCliente("juan@example.com");
        reserva.setFechaReserva(new Date());
        reserva.setHoraReserva("10:00");
        reserva.setNumPersonas(2);
        return reserva;
    }

    @Test
    public void testObtenerTodasLasReservas() {
        List<Reserva> reservas = Collections.singletonList(crearReservaEjemplo());
        when(reservaService.obtenerTodasLasReservas()).thenReturn(reservas);

        ResponseEntity<List<Reserva>> response = reservaController.obtenerTodasLasReservas();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(1, response.getBody().size());
        verify(reservaService, times(1)).obtenerTodasLasReservas();
    }

    @Test
    public void testObtenerReservaPorIdExistente() {
        Reserva reserva = crearReservaEjemplo();
        when(reservaService.obtenerReservaPorId(1L)).thenReturn(reserva);

        ResponseEntity<Reserva> response = reservaController.obtenerReservaPorId(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Juan Pérez", response.getBody().getNombreCliente());
        verify(reservaService, times(1)).obtenerReservaPorId(1L);
    }

    @Test
    public void testObtenerReservaPorIdNoExistente() {
        when(reservaService.obtenerReservaPorId(99L)).thenReturn(null);

        ResponseEntity<Reserva> response = reservaController.obtenerReservaPorId(99L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testCrearReserva() {
        Reserva reserva = crearReservaEjemplo();
        when(reservaService.crearReserva(any(Reserva.class))).thenReturn(reserva);

        ResponseEntity<Reserva> response = reservaController.crearReserva(reserva);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Juan Pérez", response.getBody().getNombreCliente());
        verify(reservaService, times(1)).crearReserva(any(Reserva.class));
    }

    @Test
    public void testCrearReservaNull() {
        ResponseEntity<Reserva> response = reservaController.crearReserva(null);
        assertEquals(400, response.getStatusCodeValue());
    }

    @Test
    public void testEliminarReservaExistente() {
        when(reservaService.eliminarReserva(1L)).thenReturn(true);

        ResponseEntity<Void> response = reservaController.eliminarReserva(1L);

        assertEquals(204, response.getStatusCodeValue());
        verify(reservaService, times(1)).eliminarReserva(1L);
    }

    @Test
    public void testEliminarReservaNoExistente() {
        when(reservaService.eliminarReserva(99L)).thenReturn(false);

        ResponseEntity<Void> response = reservaController.eliminarReserva(99L);

        assertEquals(404, response.getStatusCodeValue());
    }

    @Test
    public void testModificarReservaExistente() {
        Reserva reserva = crearReservaEjemplo();
        when(reservaService.modificarReserva(eq(1L), any(Reserva.class))).thenReturn(reserva);

        ResponseEntity<Reserva> response = reservaController.modificarReserva(1L, reserva);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Juan Pérez", response.getBody().getNombreCliente());
    }

    @Test
    public void testModificarReservaNoExistente() {
        when(reservaService.modificarReserva(eq(99L), any(Reserva.class))).thenReturn(null);

        ResponseEntity<Reserva> response = reservaController.modificarReserva(99L, crearReservaEjemplo());

        assertEquals(404, response.getStatusCodeValue());
    }
}
