package com.example.restapi.service;

import com.example.restapi.model.Ordenador;
import com.example.restapi.repository.RepositorioOrdenadores;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class ServicioOrdenadoresTest {

    @Mock
    private RepositorioOrdenadores repositorioOrdenadores;

    @InjectMocks
    private ServicioOrdenadores servicioOrdenadores;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindById() {
        Ordenador ordenador = new Ordenador();
        ordenador.setId(1L);
        when(repositorioOrdenadores.findById(1L)).thenReturn(Optional.of(ordenador));

        Optional<Ordenador> result = servicioOrdenadores.findById(1L);

        assertTrue(result.isPresent());
        assertEquals(1L, result.get().getId());
        verify(repositorioOrdenadores, times(1)).findById(1L);
    }

    @Test
    void testFindByMarca() {
        Ordenador ordenador = new Ordenador();
        ordenador.setMarca("Dell");
        when(repositorioOrdenadores.findByMarca("Dell")).thenReturn(Optional.of(ordenador));

        Optional<Ordenador> result = servicioOrdenadores.findByMarca("Dell");

        assertTrue(result.isPresent());
        assertEquals("Dell", result.get().getMarca());
        verify(repositorioOrdenadores, times(1)).findByMarca("Dell");
    }

    @Test
    void testFindByNumeroSerie() {
        Ordenador ordenador = new Ordenador();
        ordenador.setNumeroSerie("XYZ123");
        when(repositorioOrdenadores.findByNumeroSerie("XYZ123")).thenReturn(Optional.of(ordenador));

        Optional<Ordenador> result = servicioOrdenadores.findByNumeroSerie("XYZ123");

        assertTrue(result.isPresent());
        assertEquals("XYZ123", result.get().getNumeroSerie());
        verify(repositorioOrdenadores, times(1)).findByNumeroSerie("XYZ123");
    }

    @Test
    void testFindAll() {
        Ordenador ordenador1 = new Ordenador();
        Ordenador ordenador2 = new Ordenador();
        List<Ordenador> ordenadores = Arrays.asList(ordenador1, ordenador2);
        when(repositorioOrdenadores.findAll()).thenReturn(ordenadores);

        List<Ordenador> result = servicioOrdenadores.findAll();

        assertEquals(2, result.size());
        verify(repositorioOrdenadores, times(1)).findAll();
    }

    @Test
    void testAddOrdenador() {
        Ordenador ordenador = new Ordenador();
        when(repositorioOrdenadores.save(ordenador)).thenReturn(ordenador);

        Ordenador result = servicioOrdenadores.addOrdenador(ordenador);

        assertNotNull(result);
        verify(repositorioOrdenadores, times(1)).save(ordenador);
    }

    @Test
    void testUpdateOrdenador() {
        Ordenador ordenador = new Ordenador();
        ordenador.setId(1L);
        when(repositorioOrdenadores.findById(1L)).thenReturn(Optional.of(ordenador));
        when(repositorioOrdenadores.save(ordenador)).thenReturn(ordenador);

        Ordenador result = servicioOrdenadores.updateOrdenador(1L, ordenador);

        assertNotNull(result);
        assertEquals(1L, result.getId());
        verify(repositorioOrdenadores, times(1)).findById(1L);
        verify(repositorioOrdenadores, times(1)).save(ordenador);
    }

    @Test
    void testUpdateOrdenador_NotFound() {
        Ordenador ordenador = new Ordenador();
        ordenador.setId(1L);
        when(repositorioOrdenadores.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioOrdenadores.updateOrdenador(1L, ordenador);
        });

        assertEquals("Ordenador no encontrado con ID: 1", exception.getMessage());
        verify(repositorioOrdenadores, times(1)).findById(1L);
        verify(repositorioOrdenadores, never()).save(any(Ordenador.class));
    }
    
    @Test
    void testDeleteOrdenador() {
        doNothing().when(repositorioOrdenadores).deleteById(1L);

        servicioOrdenadores.deleteOrdenador(1L);

        verify(repositorioOrdenadores, times(1)).deleteById(1L);
    }
}