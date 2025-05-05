package com.example.restapi.controller;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;

import com.example.restapi.model.SalaGrupal;
import com.example.restapi.service.ServicioSalaGrupo;

public class ControllerSalaGrupoTest {

    @Mock
    private ServicioSalaGrupo servicioSalaGrupo;

    @InjectMocks
    private SalaGrupalController salaGrupalController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private SalaGrupal crearSalaEjemplo() {
        SalaGrupal sala = new SalaGrupal();
        sala.setPiso(1);
        sala.setNumeroSala(101);
        sala.setNumeroPersonas(6);
        return sala;
    }

    @Test
    public void testGetAllSalas() {
        List<SalaGrupal> salas = Collections.singletonList(crearSalaEjemplo());
        when(servicioSalaGrupo.findAll()).thenReturn(salas);

        List<SalaGrupal> resultado = salaGrupalController.getAllSalas();

        assertEquals(1, resultado.size());
        verify(servicioSalaGrupo, times(1)).findAll();
    }

    @Test
    public void testGetSalaByPisoAndNumeroSalaExistente() {
        SalaGrupal sala = crearSalaEjemplo();
        when(servicioSalaGrupo.findByPisoAndNumeroSala(1, 101)).thenReturn(Optional.of(sala));

        ResponseEntity<SalaGrupal> response = salaGrupalController.getSalaByPisoAndNumeroSala(1, 101);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(101, response.getBody().getNumeroSala());
    }

    @Test
    public void testGetSalaByPisoAndNumeroSalaNoExistente() {
        when(servicioSalaGrupo.findByPisoAndNumeroSala(1, 999)).thenReturn(Optional.empty());

        ResponseEntity<SalaGrupal> response = salaGrupalController.getSalaByPisoAndNumeroSala(1, 999);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testCreateSala() {
        SalaGrupal sala = crearSalaEjemplo();

        doNothing().when(servicioSalaGrupo).addSala(sala);

        ResponseEntity<String> response = salaGrupalController.createSala(sala);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Sala creada exitosamente", response.getBody());
        verify(servicioSalaGrupo, times(1)).addSala(sala);
    }

    @Test
    public void testUpdateSalaExito() {
        SalaGrupal sala = crearSalaEjemplo();

        doNothing().when(servicioSalaGrupo).updateSala(sala);

        ResponseEntity<String> response = salaGrupalController.updateSala(1, 101, sala);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Sala actualizada exitosamente", response.getBody());
        verify(servicioSalaGrupo, times(1)).updateSala(sala);
    }

    @Test
    public void testUpdateSalaDatosInconsistentes() {
        SalaGrupal sala = crearSalaEjemplo();
        sala.setNumeroSala(999); // diferente del path variable

        ResponseEntity<String> response = salaGrupalController.updateSala(1, 101, sala);

        assertEquals(400, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("no coinciden"));
        verify(servicioSalaGrupo, never()).updateSala(any());
    }

    @Test
    public void testUpdateSalaLanzaExcepcion() {
        SalaGrupal sala = crearSalaEjemplo();
        doThrow(new RuntimeException("Sala no encontrada")).when(servicioSalaGrupo).updateSala(sala);

        ResponseEntity<String> response = salaGrupalController.updateSala(1, 101, sala);

        assertEquals(404, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Sala no encontrada"));
    }

    @Test
    public void testDeleteSala() {
        doNothing().when(servicioSalaGrupo).deleteSala(1, 101);

        ResponseEntity<String> response = salaGrupalController.deleteSala(1, 101);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Sala eliminada exitosamente", response.getBody());
        verify(servicioSalaGrupo, times(1)).deleteSala(1, 101);
    }
}