/* 
package com.example.restapi.controller;

import com.example.restapi.model.Ordenador;
import com.example.restapi.service.ServicioOrdenadores;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class ControllerOrdenadorTest {

    @InjectMocks
    private OrdenadorController ordenadorController;

    @Mock
    private ServicioOrdenadores servicioOrdenadores;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Ordenador crearOrdenadorEjemplo() {
        return new Ordenador("Dell", "Inspiron", "ABC123", true);
    }

    @Test
    public void testGetAllOrdenadores() {
        List<Ordenador> ordenadores = Collections.singletonList(crearOrdenadorEjemplo());
        when(servicioOrdenadores.findAll()).thenReturn(ordenadores);

        List<Ordenador> resultado = ordenadorController.getAllOrdenadores();

        assertEquals(1, resultado.size());
        verify(servicioOrdenadores, times(1)).findAll();
    }

    @Test
    public void testGetOrdenadorByIdExistente() {
        Ordenador ordenador = crearOrdenadorEjemplo();
        when(servicioOrdenadores.findById(1L)).thenReturn(Optional.of(ordenador));

        ResponseEntity<Ordenador> response = ordenadorController.getOrdenadorById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Dell", response.getBody().getMarca());
    }

    @Test
    public void testGetOrdenadorByIdNoExistente() {
        when(servicioOrdenadores.findById(99L)).thenReturn(Optional.empty());

        ResponseEntity<Ordenador> response = ordenadorController.getOrdenadorById(99L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testGetOrdenadorByNumeroSerieExistente() {
        Ordenador ordenador = crearOrdenadorEjemplo();
        when(servicioOrdenadores.findByNumeroSerie("ABC123")).thenReturn(Optional.of(ordenador));

        ResponseEntity<Ordenador> response = ordenadorController.getOrdenadorByNumeroSerie("ABC123");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Inspiron", response.getBody().getModelo());
    }

    @Test
    public void testGetOrdenadorByNumeroSerieNoExistente() {
        when(servicioOrdenadores.findByNumeroSerie("XYZ999")).thenReturn(Optional.empty());

        ResponseEntity<Ordenador> response = ordenadorController.getOrdenadorByNumeroSerie("XYZ999");

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testAddOrdenadorSuccess() {
        doNothing().when(servicioOrdenadores).addOrdenador(any(Ordenador.class));

        ResponseEntity<String> response = ordenadorController.addOrdenador("Dell", "Inspiron", "XYZ123", true);

        assertEquals(201, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Ordenador agregado correctamente"));
        verify(servicioOrdenadores, times(1)).addOrdenador(any(Ordenador.class));
    }

    @Test
    public void testAddOrdenadorError() {
        doThrow(new RuntimeException("Error")).when(servicioOrdenadores).addOrdenador(any(Ordenador.class));

        ResponseEntity<String> response = ordenadorController.addOrdenador("Dell", "Inspiron", "XYZ123", true);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Error al agregar el ordenador"));
    }

    @Test
    public void testUpdateOrdenadorSuccess() {
        doNothing().when(servicioOrdenadores).updateOrdenador(eq(1L), any(Ordenador.class));

        ResponseEntity<String> response = ordenadorController.updateOrdenador(1L, crearOrdenadorEjemplo());

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Ordenador actualizado correctamente"));
    }

    @Test
    public void testUpdateOrdenadorError() {
        doThrow(new RuntimeException("Error")).when(servicioOrdenadores).updateOrdenador(eq(1L), any(Ordenador.class));

        ResponseEntity<String> response = ordenadorController.updateOrdenador(1L, crearOrdenadorEjemplo());

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Error al actualizar el ordenador"));
    }

    @Test
    public void testDeleteOrdenadorSuccess() {
        doNothing().when(servicioOrdenadores).deleteOrdenador(1L);

        ResponseEntity<String> response = ordenadorController.deleteOrdenador(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Ordenador eliminado correctamente"));
    }

    @Test
    public void testDeleteOrdenadorError() {
        doThrow(new RuntimeException("Error")).when(servicioOrdenadores).deleteOrdenador(1L);

        ResponseEntity<String> response = ordenadorController.deleteOrdenador(1L);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Error al eliminar el ordenador"));
    }
}
*/