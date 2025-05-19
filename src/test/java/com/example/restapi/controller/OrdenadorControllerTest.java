package com.example.restapi.controller;

import com.example.restapi.model.Ordenador;
import com.example.restapi.service.ServicioOrdenadores;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class OrdenadorControllerTest {

    @InjectMocks
    private OrdenadorController ordenadorController;

    @Mock
    private ServicioOrdenadores servicioOrdenadores;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Ordenador crearOrdenadorEjemplo() {
        Ordenador ordenador = new Ordenador("HP", "EliteBook", "ABC123", true);
        ordenador.setId(1L);
        return ordenador;
    }

    @Test
    public void testGetAllOrdenadores() {
        List<Ordenador> lista = Arrays.asList(crearOrdenadorEjemplo());
        when(servicioOrdenadores.findAll()).thenReturn(lista);

        List<Ordenador> resultado = ordenadorController.getAllOrdenadores();

        assertEquals(1, resultado.size());
        assertEquals("HP", resultado.get(0).getMarca());
        verify(servicioOrdenadores, times(1)).findAll();
    }

    @SuppressWarnings({ "null", "deprecation" })
    @Test
    public void testGetOrdenadorById_found() {
        Ordenador ordenador = crearOrdenadorEjemplo();
        when(servicioOrdenadores.findById(1L)).thenReturn(Optional.of(ordenador));

        ResponseEntity<Ordenador> response = ordenadorController.getOrdenadorById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().getMarca().equals("HP"));
    }

    @SuppressWarnings("deprecation")
    @Test
    public void testGetOrdenadorById_notFound() {
        when(servicioOrdenadores.findById(2L)).thenReturn(Optional.empty());

        ResponseEntity<Ordenador> response = ordenadorController.getOrdenadorById(2L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @SuppressWarnings({ "null", "deprecation" })
    @Test
    public void testAddOrdenador() {
        // Simulamos que addOrdenador devuelve un Ordenador
        Ordenador mockO = new Ordenador("HP", "EliteBook", "ABC123", true);
        when(servicioOrdenadores.addOrdenador(any(Ordenador.class))).thenReturn(mockO);

        ResponseEntity<String> response = ordenadorController.addOrdenador(
            "HP", "EliteBook", "ABC123", true
        );

        assertEquals(201, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Ordenador agregado correctamente"));
        verify(servicioOrdenadores, times(1)).addOrdenador(any(Ordenador.class));
    }

    @SuppressWarnings({ "null", "deprecation" })
    @Test
    public void testUpdateOrdenador() {
        // Simulamos que updateOrdenador devuelve el objeto actualizado
        Ordenador actualizado = new Ordenador("HP", "EliteBook Updated", "ABC123", false);
        when(servicioOrdenadores.updateOrdenador(eq(1L), any(Ordenador.class)))
            .thenReturn(actualizado);

        ResponseEntity<String> response = ordenadorController.updateOrdenador(
            1L, "HP", "EliteBook Updated", "ABC123", false
        );

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Ordenador actualizado correctamente"));
        verify(servicioOrdenadores, times(1))
            .updateOrdenador(eq(1L), any(Ordenador.class));
    }

    @SuppressWarnings({ "null", "deprecation" })
    @Test
    public void testDeleteOrdenador() {
        doNothing().when(servicioOrdenadores).deleteOrdenador(1L);

        ResponseEntity<String> response = ordenadorController.deleteOrdenador(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Ordenador eliminado correctamente"));
        verify(servicioOrdenadores, times(1)).deleteOrdenador(1L);
    }

    @SuppressWarnings({ "null", "deprecation" })
    @Test
    public void testDeleteOrdenadorPorNumeroSerie() {
        doNothing().when(servicioOrdenadores).deleteByNumeroSerie("ABC123");

        ResponseEntity<String> response = ordenadorController.deleteOrdenadorPorNumeroSerie("ABC123");

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Ordenador eliminado correctamente"));
        verify(servicioOrdenadores, times(1)).deleteByNumeroSerie("ABC123");
    }

}
