package com.example.restapi.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.restapi.model.Reserva;
import com.example.restapi.repository.RepositorioEspacioIndividual;
import com.example.restapi.repository.RepositorioLibros;
import com.example.restapi.repository.RepositorioOrdenadores;
import com.example.restapi.repository.RepositorioSalaGrupo;
import com.example.restapi.repository.ReservaRepository;

class ReservaServiceTest {

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private RepositorioLibros libroRepository;

    @Mock
    private RepositorioOrdenadores ordenadorRepository;

    @Mock
    private RepositorioSalaGrupo salaGrupalRepository;

    @Mock
    private RepositorioEspacioIndividual espacioIndividualRepository;

    @InjectMocks
    private ReservaService reservaService;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testObtenerTodasLasReservas() {
        Reserva reserva1 = new Reserva();
        Reserva reserva2 = new Reserva();
        List<Reserva> listaReservas = Arrays.asList(reserva1, reserva2);

        when(reservaRepository.findAll()).thenReturn(listaReservas);

        List<Reserva> resultado = reservaService.obtenerTodasLasReservas();

        assertEquals(2, resultado.size());
    }

    @Test
    void testObtenerReservaPorId_Existente() {
        Reserva reserva = new Reserva();
        reserva.setId(1L);

        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));

        Reserva resultado = reservaService.obtenerReservaPorId(1L);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
    }

    @Test
    void testObtenerReservaPorId_NoExistente() {
        when(reservaRepository.findById(1L)).thenReturn(Optional.empty());

        Reserva resultado = reservaService.obtenerReservaPorId(1L);

        assertNull(resultado);
    }

    @Test
    void testCrearReserva() {
        Reserva reserva = new Reserva();

        when(reservaRepository.save(reserva)).thenReturn(reserva);

        Reserva resultado = reservaService.crearReserva(reserva);

        assertNotNull(resultado);
        verify(reservaRepository, times(1)).save(reserva);
    }

    @Test
    void testEliminarReserva_Existente() {
        when(reservaRepository.existsById(1L)).thenReturn(true);
        doNothing().when(reservaRepository).deleteById(1L);

        boolean resultado = reservaService.eliminarReserva(1L);

        assertTrue(resultado);
        verify(reservaRepository, times(1)).deleteById(1L);
    }

    @Test
    void testEliminarReserva_NoExistente() {
        when(reservaRepository.existsById(1L)).thenReturn(false);

        boolean resultado = reservaService.eliminarReserva(1L);

        assertFalse(resultado);
        verify(reservaRepository, never()).deleteById(anyLong());
    }

    @Test
    void testModificarReserva_Existente() {
        Reserva reserva = new Reserva();
        reserva.setId(1L);

        when(reservaRepository.existsById(1L)).thenReturn(true);
        when(reservaRepository.save(reserva)).thenReturn(reserva);

        Reserva resultado = reservaService.modificarReserva(1L, reserva);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(reservaRepository, times(1)).save(reserva);
    }

    @Test
    void testModificarReserva_NoExistente() {
        Reserva reserva = new Reserva();

        when(reservaRepository.existsById(1L)).thenReturn(false);

        Reserva resultado = reservaService.modificarReserva(1L, reserva);

        assertNull(resultado);
        verify(reservaRepository, never()).save(any(Reserva.class));
    }
}
