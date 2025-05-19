package com.example.restapi.service;

import com.example.restapi.model.EspacioIndividual;
import com.example.restapi.repository.RepositorioEspacioIndividual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

public class ServicioEspacioIndividualTest {

    private ServicioEspacioIndividual servicioEspacioIndividual;

    private RepositorioEspacioIndividual repositorioEspacioIndividual;

    private EspacioIndividual espacio1;
    private EspacioIndividual espacio2;

    @BeforeEach
    public void setup() {
        repositorioEspacioIndividual = mock(RepositorioEspacioIndividual.class);
        servicioEspacioIndividual = new ServicioEspacioIndividual(repositorioEspacioIndividual);

        espacio1 = new EspacioIndividual(1, 10);
        espacio1.setId(1L);
        espacio2 = new EspacioIndividual(2, 15);
        espacio2.setId(2L);
    }

    @Test
    public void testFindAll() {
        // Simulamos que el repositorio devuelve una lista con los dos espacios
        when(repositorioEspacioIndividual.findAll()).thenReturn(Arrays.asList(espacio1, espacio2));

        // Llamamos al método del servicio
        var espacios = servicioEspacioIndividual.findAll();

        // Verificamos que el resultado sea el esperado
        assertEquals(2, espacios.size());
        assertTrue(espacios.contains(espacio1));
        assertTrue(espacios.contains(espacio2));

        // Verificamos que se haya llamado al repositorio
        verify(repositorioEspacioIndividual, times(1)).findAll();
    }

    @Test
    public void testFindByPisoAndAsiento_found() {
        // Simulamos que el repositorio devuelve el espacio con piso 1 y número de
        // asiento 10
        when(repositorioEspacioIndividual.findByPisoAndNumeroAsiento(1, 10)).thenReturn(Optional.of(espacio1));

        // Llamamos al método del servicio
        Optional<EspacioIndividual> resultado = servicioEspacioIndividual.findByPisoAndAsiento(1, 10);

        // Verificamos que el espacio sea encontrado
        assertTrue(resultado.isPresent());
        assertEquals(espacio1, resultado.get());

        // Verificamos que se haya llamado al repositorio
        verify(repositorioEspacioIndividual, times(1)).findByPisoAndNumeroAsiento(1, 10);
    }

    @Test
    public void testFindByPisoAndAsiento_notFound() {
        // Simulamos que el repositorio no devuelve nada
        when(repositorioEspacioIndividual.findByPisoAndNumeroAsiento(3, 20)).thenReturn(Optional.empty());

        // Llamamos al método del servicio
        Optional<EspacioIndividual> resultado = servicioEspacioIndividual.findByPisoAndAsiento(3, 20);

        // Verificamos que no se encontró el espacio
        assertFalse(resultado.isPresent());

        // Verificamos que se haya llamado al repositorio
        verify(repositorioEspacioIndividual, times(1)).findByPisoAndNumeroAsiento(3, 20);
    }

    @Test
    public void testAddEspacio() {
        when(repositorioEspacioIndividual.save(any(EspacioIndividual.class))).thenReturn(espacio1);
        servicioEspacioIndividual.addEspacio(espacio1);
        verify(repositorioEspacioIndividual, times(1)).save(espacio1);
    }


    @Test
    public void testUpdateEspacio() {
        when(repositorioEspacioIndividual.findById(espacio1.getId())).thenReturn(Optional.of(espacio1));
        when(repositorioEspacioIndividual.save(any(EspacioIndividual.class))).thenReturn(espacio1);

        servicioEspacioIndividual.updateEspacio(espacio1);

        verify(repositorioEspacioIndividual, times(1)).save(espacio1);
    }


    @Test
    public void testUpdateEspacio_notFound() {
        // Simulamos que el repositorio no encuentra el espacio con el ID proporcionado
        when(repositorioEspacioIndividual.findById(espacio1.getId())).thenReturn(Optional.empty());

        // Llamamos al método del servicio y verificamos que lance una excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioEspacioIndividual.updateEspacio(espacio1);
        });

        // Verificamos el mensaje de la excepción
        assertEquals("El espacio con ID " + espacio1.getId() + " no existe", exception.getMessage());

        // Verificamos que no se haya llamado al repositorio para guardar
        verify(repositorioEspacioIndividual, never()).save(any(EspacioIndividual.class));
    }

    @Test
    public void testDeleteEspacio() {
        // Simulamos que el repositorio existe para el espacio con el ID 1
        when(repositorioEspacioIndividual.existsById(1L)).thenReturn(true);
        doNothing().when(repositorioEspacioIndividual).deleteById(1L);

        // Llamamos al método del servicio
        servicioEspacioIndividual.deleteEspacio(1L);

        // Verificamos que se haya llamado al repositorio para eliminar el espacio
        verify(repositorioEspacioIndividual, times(1)).deleteById(1L);
    }

    @Test
    public void testDeleteEspacio_notFound() {
        // Simulamos que el repositorio no encuentra el espacio con el ID 1
        when(repositorioEspacioIndividual.existsById(1L)).thenReturn(false);

        // Llamamos al método del servicio y verificamos que lance una excepción
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            servicioEspacioIndividual.deleteEspacio(1L);
        });

        // Verificamos el mensaje de la excepción
        assertEquals("No existe el espacio con ID: 1", exception.getMessage());

        // Verificamos que no se haya llamado al repositorio para eliminar
        verify(repositorioEspacioIndividual, never()).deleteById(1L);
    }
}
