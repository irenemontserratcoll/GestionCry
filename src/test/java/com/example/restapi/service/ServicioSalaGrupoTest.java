package com.example.restapi.service;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import static org.mockito.ArgumentMatchers.any;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.never;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.MockitoAnnotations;

import com.example.restapi.model.SalaGrupal;
import com.example.restapi.repository.RepositorioSalaGrupo;

class ServicioSalaGrupoTest {

    @Mock
    private RepositorioSalaGrupo salaGrupalRepository;

    @InjectMocks
    private ServicioSalaGrupo servicioSalaGrupo;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        SalaGrupal sala1 = new SalaGrupal();
        SalaGrupal sala2 = new SalaGrupal();
        List<SalaGrupal> salas = Arrays.asList(sala1, sala2);

        when(salaGrupalRepository.findAll()).thenReturn(salas);

        List<SalaGrupal> resultado = servicioSalaGrupo.findAll();

        assertEquals(2, resultado.size());
    }

    @Test
    void testFindByPisoAndNumeroSala_Existente() {
        SalaGrupal sala = new SalaGrupal();
        sala.setPiso(1);
        sala.setNumeroSala(101);

        when(salaGrupalRepository.findByPisoAndNumeroSala(1, 101)).thenReturn(Optional.of(sala));

        Optional<SalaGrupal> resultado = servicioSalaGrupo.findByPisoAndNumeroSala(1, 101);

        assertTrue(resultado.isPresent());
        assertEquals(1, resultado.get().getPiso());
        assertEquals(101, resultado.get().getNumeroSala());
    }

    @Test
    void testFindByPisoAndNumeroSala_NoExistente() {
        when(salaGrupalRepository.findByPisoAndNumeroSala(2, 202)).thenReturn(Optional.empty());

        Optional<SalaGrupal> resultado = servicioSalaGrupo.findByPisoAndNumeroSala(2, 202);

        assertFalse(resultado.isPresent());
    }

    @Test
    void testAddSala() {
        SalaGrupal sala = new SalaGrupal();
        servicioSalaGrupo.addSala(sala);

        verify(salaGrupalRepository, times(1)).save(sala);
    }

    @Test
    void testUpdateSala_Existente() {
        SalaGrupal sala = new SalaGrupal();
        sala.setPiso(1);
        sala.setNumeroSala(101);

        when(salaGrupalRepository.existsByPisoAndNumeroSala(1, 101)).thenReturn(true);
        when(salaGrupalRepository.save(sala)).thenReturn(sala);

        servicioSalaGrupo.updateSala(sala);

        verify(salaGrupalRepository, times(1)).save(sala);
    }

    @Test
    void testUpdateSala_NoExistente() {
        SalaGrupal sala = new SalaGrupal();
        sala.setPiso(2);
        sala.setNumeroSala(202);

        when(salaGrupalRepository.existsByPisoAndNumeroSala(2, 202)).thenReturn(false);

        assertThrows(RuntimeException.class, () -> servicioSalaGrupo.updateSala(sala));
        verify(salaGrupalRepository, never()).save(any(SalaGrupal.class));
    }

    @Test
    void testDeleteSala_Existente() {
        SalaGrupal sala = new SalaGrupal();
        sala.setPiso(1);
        sala.setNumeroSala(101);

        when(salaGrupalRepository.findByPisoAndNumeroSala(1, 101)).thenReturn(Optional.of(sala));
        doNothing().when(salaGrupalRepository).delete(sala);

        servicioSalaGrupo.deleteSala(1, 101);

        verify(salaGrupalRepository, times(1)).delete(sala);
    }

    @Test
    void testDeleteSala_NoExistente() {
        when(salaGrupalRepository.findByPisoAndNumeroSala(3, 303)).thenReturn(Optional.empty());

        servicioSalaGrupo.deleteSala(3, 303);

        verify(salaGrupalRepository, never()).delete(any(SalaGrupal.class));
    }
}
