package com.example.restapi.controller;

import com.example.restapi.model.EspacioIndividual;
import com.example.restapi.service.ServicioEspacioIndividual;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.springframework.http.ResponseEntity;

import java.util.Optional;
import java.util.List;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

public class EspacioIndividualControllerTest {

    @InjectMocks
    private EspacioIndividualController espacioController;

    @Mock
    private ServicioEspacioIndividual servicioEspacio;

    private EspacioIndividual espacioEjemplo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        espacioEjemplo = new EspacioIndividual();
        espacioEjemplo.setPiso(2);
        espacioEjemplo.setNumeroAsiento(5);
    }

    @Test
    public void testGetAllEspacios() {
        when(servicioEspacio.findAll()).thenReturn(Collections.singletonList(espacioEjemplo));

        List<EspacioIndividual> espacios = espacioController.getAllEspacios();

        assertEquals(1, espacios.size());
        assertEquals(2, espacios.get(0).getPiso());
        verify(servicioEspacio, times(1)).findAll();
    }

    @Test
    public void testGetEspacioExistente() {
        when(servicioEspacio.findByPisoAndAsiento(2, 5)).thenReturn(Optional.of(espacioEjemplo));

        ResponseEntity<EspacioIndividual> response = espacioController.getEspacio(2, 5);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(5, response.getBody().getNumeroAsiento());
    }

    @Test
    public void testGetEspacioNoExistente() {
        when(servicioEspacio.findByPisoAndAsiento(3, 1)).thenReturn(Optional.empty());

        ResponseEntity<EspacioIndividual> response = espacioController.getEspacio(3, 1);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testAddEspacioExitoso() {
        ResponseEntity<String> response = espacioController.addEspacio(2, 5);

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Espacio agregado correctamente", response.getBody());
        verify(servicioEspacio, times(1)).addEspacio(any(EspacioIndividual.class));
    }

    @Test
    public void testAddEspacioConExcepcion() {
        doThrow(new RuntimeException("Error")).when(servicioEspacio).addEspacio(any(EspacioIndividual.class));

        ResponseEntity<String> response = espacioController.addEspacio(2, 5);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Error al agregar el espacio"));
    }

    @Test
    public void testUpdateEspacioExitoso() {
        ResponseEntity<String> response = espacioController.updateEspacio(espacioEjemplo);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Espacio actualizado correctamente", response.getBody());
        verify(servicioEspacio, times(1)).updateEspacio(espacioEjemplo);
    }

    @Test
    public void testUpdateEspacioConExcepcion() {
        doThrow(new RuntimeException("Error")).when(servicioEspacio).updateEspacio(any(EspacioIndividual.class));

        ResponseEntity<String> response = espacioController.updateEspacio(espacioEjemplo);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Error al actualizar el espacio"));
    }

    @Test
    public void testDeleteEspacioExitoso() {
        ResponseEntity<String> response = espacioController.deleteEspacio(2, 5);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Espacio eliminado correctamente", response.getBody());
        verify(servicioEspacio, times(1)).deleteEspacio(2, 5);
    }

    @Test
    public void testDeleteEspacioConExcepcion() {
        doThrow(new RuntimeException("Error")).when(servicioEspacio).deleteEspacio(2, 5);

        ResponseEntity<String> response = espacioController.deleteEspacio(2, 5);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Error al eliminar el espacio"));
    }
}
