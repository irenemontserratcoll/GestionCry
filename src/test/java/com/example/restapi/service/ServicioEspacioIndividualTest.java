package com.example.restapi.service;

import com.example.restapi.model.EspacioIndividual;
import com.example.restapi.repository.RepositorioEspacioIndividual;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicioEspacioIndividualTest {

    @Mock
    private RepositorioEspacioIndividual espacioRepository;

    @InjectMocks
    private ServicioEspacioIndividual servicioEspacio;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        EspacioIndividual espacio1 = new EspacioIndividual(1, 101);
        EspacioIndividual espacio2 = new EspacioIndividual(2, 202);
        List<EspacioIndividual> espacios = Arrays.asList(espacio1, espacio2);

        when(espacioRepository.findAll()).thenReturn(espacios);

        List<EspacioIndividual> resultado = servicioEspacio.findAll();

        assertEquals(2, resultado.size());
        verify(espacioRepository, times(1)).findAll();
    }

    @Test
    void testFindByPisoAndAsiento() {
        EspacioIndividual espacio = new EspacioIndividual(1, 10);
        when(espacioRepository.findByPisoAndNumeroAsiento(1, 10)).thenReturn(Optional.of(espacio));

        Optional<EspacioIndividual> resultado = servicioEspacio.findByPisoAndAsiento(1, 10);

        assertTrue(resultado.isPresent());
        assertEquals(1, resultado.get().getPiso());
        assertEquals(10, resultado.get().getNumeroAsiento());
    }

    @Test
    void testAddEspacio() {
        EspacioIndividual espacio = new EspacioIndividual(2, 20);

        servicioEspacio.addEspacio(espacio);

        verify(espacioRepository, times(1)).save(espacio);
    }

    @Test
    void testUpdateEspacio_Success() {
        EspacioIndividual espacio = new EspacioIndividual(3, 30);
        when(espacioRepository.existsByPisoAndNumeroAsiento(3, 30)).thenReturn(true);

        servicioEspacio.updateEspacio(espacio);

        verify(espacioRepository, times(1)).save(espacio);
    }

    @Test
    void testUpdateEspacio_Failure() {
        EspacioIndividual espacio = new EspacioIndividual(4, 40);
        when(espacioRepository.existsByPisoAndNumeroAsiento(4, 40)).thenReturn(false);

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            servicioEspacio.updateEspacio(espacio);
        });

        assertEquals("El espacio no existe", ex.getMessage());
    }

    @Test
    void testDeleteEspacio_Exists() {
        EspacioIndividual espacio = new EspacioIndividual(5, 50);
        when(espacioRepository.findByPisoAndNumeroAsiento(5, 50)).thenReturn(Optional.of(espacio));

        servicioEspacio.deleteEspacio(5, 50);

        verify(espacioRepository, times(1)).delete(espacio);
    }

    @Test
    void testDeleteEspacio_NotFound() {
        when(espacioRepository.findByPisoAndNumeroAsiento(6, 60)).thenReturn(Optional.empty());

        servicioEspacio.deleteEspacio(6, 60);

        verify(espacioRepository, never()).delete(any());
    }
}
