package com.example.restapi.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import com.example.restapi.model.Ordenador;
import com.example.restapi.repository.RepositorioOrdenadores;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import java.util.*;

class ServicioOrdenadoresTest {

    @Mock
    private RepositorioOrdenadores repositorioOrdenadores;

    @InjectMocks
    private ServicioOrdenadores servicioOrdenadores;

    private Ordenador ordenador;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        ordenador = new Ordenador("Dell", "XPS", "SN12345", true);
        ordenador.setId(1L);
    }

    @Test
    void testFindById_Found() {
        when(repositorioOrdenadores.findById(1L)).thenReturn(Optional.of(ordenador));

        Optional<Ordenador> resultado = servicioOrdenadores.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Dell", resultado.get().getMarca());
    }

    @Test
    void testFindById_NotFound() {
        when(repositorioOrdenadores.findById(2L)).thenReturn(Optional.empty());

        Optional<Ordenador> resultado = servicioOrdenadores.findById(2L);

        assertFalse(resultado.isPresent());
    }

    @Test
    void testFindByMarca() {
        List<Ordenador> lista = List.of(ordenador);
        when(repositorioOrdenadores.findByMarca("Dell")).thenReturn(lista);

        List<Ordenador> resultado = servicioOrdenadores.findByMarca("Dell");

        assertFalse(resultado.isEmpty());
        assertEquals(1, resultado.size());
        assertEquals("XPS", resultado.get(0).getModelo());
    }

    @Test
    void testAddOrdenador() {
        when(repositorioOrdenadores.save(any(Ordenador.class))).thenReturn(ordenador);

        Ordenador creado = servicioOrdenadores.addOrdenador(ordenador);

        assertNotNull(creado);
        assertEquals("SN12345", creado.getNumeroSerie());
    }

    @Test
    void testUpdateOrdenador_Success() {
        when(repositorioOrdenadores.findById(1L)).thenReturn(Optional.of(ordenador));
        when(repositorioOrdenadores.save(any(Ordenador.class))).thenAnswer(i -> i.getArgument(0));

        Ordenador actualizado = new Ordenador("HP", "EliteBook", "SN12345", false);

        Ordenador resultado = servicioOrdenadores.updateOrdenador(1L, actualizado);

        assertEquals(1L, resultado.getId());
        assertEquals("HP", resultado.getMarca());
        assertFalse(resultado.isDisponible());
    }

    @Test
    void testUpdateOrdenador_NotFound() {
        when(repositorioOrdenadores.findById(2L)).thenReturn(Optional.empty());

        Ordenador actualizado = new Ordenador("HP", "EliteBook", "SN12345", false);

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            servicioOrdenadores.updateOrdenador(2L, actualizado);
        });

        assertEquals("Ordenador no encontrado con ID: 2", thrown.getMessage());
    }

    @Test
    void testDeleteOrdenador() {
        doNothing().when(repositorioOrdenadores).deleteById(1L);

        assertDoesNotThrow(() -> servicioOrdenadores.deleteOrdenador(1L));

        verify(repositorioOrdenadores, times(1)).deleteById(1L);
    }

    @Test
    void testDeleteByNumeroSerie_Success() {
        when(repositorioOrdenadores.findByNumeroSerie("SN12345")).thenReturn(Optional.of(ordenador));
        doNothing().when(repositorioOrdenadores).delete(ordenador);

        assertDoesNotThrow(() -> servicioOrdenadores.deleteByNumeroSerie("SN12345"));

        verify(repositorioOrdenadores, times(1)).delete(ordenador);
    }

    @Test
    void testDeleteByNumeroSerie_NotFound() {
        when(repositorioOrdenadores.findByNumeroSerie("SN99999")).thenReturn(Optional.empty());

        RuntimeException thrown = assertThrows(RuntimeException.class, () -> {
            servicioOrdenadores.deleteByNumeroSerie("SN99999");
        });

        assertEquals("Ordenador no encontrado con número de serie: SN99999", thrown.getMessage());
    }
}