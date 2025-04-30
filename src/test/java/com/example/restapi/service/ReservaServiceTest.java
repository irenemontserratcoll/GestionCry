package com.example.restapi.service;

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
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.restapi.model.EspacioIndividual;
import com.example.restapi.model.Libro;
import com.example.restapi.model.Ordenador;
import com.example.restapi.model.Reserva;
import com.example.restapi.model.SalaGrupal;
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

    private Reserva reserva;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        reserva = new Reserva();
        reserva.setId(1L);
        reserva.setNombreCliente("Juan");
        reserva.setEmailCliente("juan@correo.com");

        Libro libro = new Libro();
        libro.setId(10L);
        reserva.setLibro(libro);

        Ordenador ordenador = new Ordenador();
        ordenador.setId(20L);
        reserva.setOrdenador(ordenador);

        SalaGrupal sala = new SalaGrupal();
        sala.setId(30L);
        reserva.setSalaGrupal(sala);

        EspacioIndividual espacio = new EspacioIndividual();
        espacio.setId(40L);
        reserva.setEspacioIndividual(espacio);

        when(libroRepository.findById(10L)).thenReturn(Optional.of(libro));
        when(ordenadorRepository.findById(20L)).thenReturn(Optional.of(ordenador));
        when(salaGrupalRepository.findById(30L)).thenReturn(Optional.of(sala));
        when(espacioIndividualRepository.findById(40L)).thenReturn(Optional.of(espacio));
    }

    @Test
    void testObtenerTodasLasReservas() {
        when(reservaRepository.findAll()).thenReturn(List.of(reserva));
        List<Reserva> reservas = reservaService.obtenerTodasLasReservas();
        assertEquals(1, reservas.size());
    }

    @Test
    void testObtenerReservaPorIdExistente() {
        when(reservaRepository.findById(1L)).thenReturn(Optional.of(reserva));
        Reserva resultado = reservaService.obtenerReservaPorId(1L);
        assertNotNull(resultado);
        assertEquals("Juan", resultado.getNombreCliente());
    }

    @Test
    void testObtenerReservaPorIdNoExistente() {
        when(reservaRepository.findById(2L)).thenReturn(Optional.empty());
        Reserva resultado = reservaService.obtenerReservaPorId(2L);
        assertNull(resultado);
    }

    @Test
    void testCrearReserva() {
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);
        Reserva resultado = reservaService.crearReserva(reserva);
        assertNotNull(resultado);
        verify(libroRepository).findById(10L);
        verify(ordenadorRepository).findById(20L);
        verify(salaGrupalRepository).findById(30L);
        verify(espacioIndividualRepository).findById(40L);
    }

    @Test
    void testModificarReservaExistente() {
        when(reservaRepository.existsById(1L)).thenReturn(true);
        when(reservaRepository.save(any(Reserva.class))).thenReturn(reserva);

        Reserva resultado = reservaService.modificarReserva(1L, reserva);

        assertNotNull(resultado);
        assertEquals(1L, resultado.getId());
        verify(reservaRepository).save(reserva);
    }

    @Test
    void testModificarReservaNoExiste() {
        when(reservaRepository.existsById(1L)).thenReturn(false);

        Reserva resultado = reservaService.modificarReserva(1L, reserva);

        assertNull(resultado);
        verify(reservaRepository, never()).save(any());
    }

    @Test
    void testEliminarReservaExistente() {
        when(reservaRepository.existsById(1L)).thenReturn(true);

        boolean resultado = reservaService.eliminarReserva(1L);

        assertTrue(resultado);
        verify(reservaRepository).deleteById(1L);
    }

    @Test
    void testEliminarReservaNoExiste() {
        when(reservaRepository.existsById(1L)).thenReturn(false);

        boolean resultado = reservaService.eliminarReserva(1L);

        assertFalse(resultado);
        verify(reservaRepository, never()).deleteById(any());
    }
    @Test
void testCargarRecursosRelacionadosTodosNull() {
    Reserva r = new Reserva(); // todos los recursos son null
    reservaService.crearReserva(r); // llama internamente a cargarRecursosRelacionados
    // No se debe lanzar excepción
}

@Test
void testCargarRecursosRelacionadosConIdsNull() {
    Reserva r = new Reserva();
    r.setLibro(new Libro());  // ID null
    r.setOrdenador(new Ordenador()); // ID null
    r.setSalaGrupal(new SalaGrupal()); // ID null
    r.setEspacioIndividual(new EspacioIndividual()); // ID null
    reservaService.crearReserva(r); // No debería consultar repositorios
    verifyNoInteractions(libroRepository, ordenadorRepository, salaGrupalRepository, espacioIndividualRepository);
}

@Test
void testCargarRecursosRelacionadosConDatosNoEncontrados() {
    Reserva r = new Reserva();
    Libro libro = new Libro(); libro.setId(99L);
    Ordenador ordenador = new Ordenador(); ordenador.setId(98L);
    SalaGrupal sala = new SalaGrupal(); sala.setId(97L);
    EspacioIndividual espacio = new EspacioIndividual(); espacio.setId(96L);
    r.setLibro(libro);
    r.setOrdenador(ordenador);
    r.setSalaGrupal(sala);
    r.setEspacioIndividual(espacio);

    when(libroRepository.findById(99L)).thenReturn(Optional.empty());
    when(ordenadorRepository.findById(98L)).thenReturn(Optional.empty());
    when(salaGrupalRepository.findById(97L)).thenReturn(Optional.empty());
    when(espacioIndividualRepository.findById(96L)).thenReturn(Optional.empty());

    reservaService.crearReserva(r); // Se llama a cargarRecursosRelacionados

    // No se lanzan errores aunque no se encuentren
    verify(libroRepository).findById(99L);
    verify(ordenadorRepository).findById(98L);
    verify(salaGrupalRepository).findById(97L);
    verify(espacioIndividualRepository).findById(96L);
}

}
