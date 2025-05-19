package com.example.restapi.controller;

import com.example.restapi.model.EspacioIndividual;
import com.example.restapi.service.ServicioEspacioIndividual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class EspacioIndividualControllerTest {

    @InjectMocks
    private EspacioIndividualController controller;

    @Mock
    private ServicioEspacioIndividual servicioEspacios;

    private EspacioIndividual espacio1;
    private EspacioIndividual espacio2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);

        espacio1 = new EspacioIndividual(1, 10);
        espacio1.setId(1L);
        espacio2 = new EspacioIndividual(2, 15);
        espacio2.setId(2L);
    }

    @Test
    public void testGetAllEspacios() {
        List<EspacioIndividual> lista = Arrays.asList(espacio1, espacio2);
        when(servicioEspacios.findAll()).thenReturn(lista);

        List<EspacioIndividual> resultado = controller.getAllEspacios();

        assertEquals(2, resultado.size());
        assertEquals(1, resultado.get(0).getPiso());
        assertEquals(15, resultado.get(1).getNumeroAsiento());
        verify(servicioEspacios, times(1)).findAll();
    }

    @Test
    public void testGetEspacioFound() {
        when(servicioEspacios.findByPisoAndAsiento(1, 10))
            .thenReturn(Optional.of(espacio1));

        ResponseEntity<EspacioIndividual> response = controller.getEspacio(1, 10);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals(10, response.getBody().getNumeroAsiento());
        verify(servicioEspacios, times(1)).findByPisoAndAsiento(1, 10);
    }

    @Test
    public void testGetEspacioNotFound() {
        when(servicioEspacios.findByPisoAndAsiento(3, 20))
            .thenReturn(Optional.empty());

        ResponseEntity<EspacioIndividual> response = controller.getEspacio(3, 20);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
        verify(servicioEspacios, times(1)).findByPisoAndAsiento(3, 20);
    }

    @Test
    public void testAddEspacio() {
        // addEspacio es void, así que doNothing() es correcto
        doNothing().when(servicioEspacios).addEspacio(any(EspacioIndividual.class));

        ResponseEntity<String> response = controller.addEspacio(3, 12);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().contains("Espacio agregado correctamente"));
        verify(servicioEspacios, times(1)).addEspacio(any(EspacioIndividual.class));
    }

    @Test
    public void testUpdateEspacio() {
        // updateEspacio es void
        doNothing().when(servicioEspacios).updateEspacio(any(EspacioIndividual.class));

        ResponseEntity<String> response = controller.updateEspacio(1L, 2, 12);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Espacio actualizado correctamente"));
        // Verificamos que el servicio reciba un EspacioIndividual con el id correcto
        ArgumentCaptor<EspacioIndividual> captor = ArgumentCaptor.forClass(EspacioIndividual.class);
        verify(servicioEspacios, times(1)).updateEspacio(captor.capture());
        assertEquals(1L, captor.getValue().getId());
        assertEquals(2, captor.getValue().getPiso());
        assertEquals(12, captor.getValue().getNumeroAsiento());
    }

    @Test
    public void testDeleteEspacio() {
        // deleteEspacio es void
        doNothing().when(servicioEspacios).deleteEspacio(1L);

        ResponseEntity<String> response = controller.deleteEspacio(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Espacio eliminado correctamente"));
        verify(servicioEspacios, times(1)).deleteEspacio(1L);
    }
}
