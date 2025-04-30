package com.example.restapi.controller;

import com.example.restapi.model.SalaGrupal;
import com.example.restapi.service.ServicioSalaGrupo;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.hamcrest.Matchers.hasSize;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(SalaGrupalController.class)
class SalaGrupalControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioSalaGrupo servicioSalaGrupo;

    private SalaGrupal sala1;
    private SalaGrupal sala2;

    @BeforeEach
    void setUp() {
        sala1 = new SalaGrupal(1, 101, 2);
        sala2 = new SalaGrupal(2, 202, 4);
    }

    @Test
    void testGetAllSalas() throws Exception {
        Mockito.when(servicioSalaGrupo.findAll()).thenReturn(Arrays.asList(sala1, sala2));

        mockMvc.perform(get("/api/sala-grupal/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testGetSalaByPisoAndNumeroSala_Found() throws Exception {
        Mockito.when(servicioSalaGrupo.findByPisoAndNumeroSala(1, 101)).thenReturn(Optional.of(sala1));

        mockMvc.perform(get("/api/sala-grupal/1/101"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.piso").value(1))
                .andExpect(jsonPath("$.numeroSala").value(101));
    }

    @Test
    void testGetSalaByPisoAndNumeroSala_NotFound() throws Exception {
        Mockito.when(servicioSalaGrupo.findByPisoAndNumeroSala(5, 505)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/sala-grupal/5/505"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testCreateSala_Success() throws Exception {
        Mockito.doNothing().when(servicioSalaGrupo).addSala(any(SalaGrupal.class));

        mockMvc.perform(post("/api/sala-grupal")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "piso": 3,
                                    "numeroSala": 303,
                                    "disponible": true
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().string("Sala creada exitosamente"));
    }

    @Test
    void testUpdateSala_Success() throws Exception {
        Mockito.doNothing().when(servicioSalaGrupo).updateSala(any(SalaGrupal.class));

        mockMvc.perform(put("/api/sala-grupal/1/101")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "piso": 1,
                                    "numeroSala": 101,
                                    "disponible": false
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().string("Sala actualizada exitosamente"));
    }

    @Test
    void testUpdateSala_BadRequest_DifferentPisoOrNumeroSala() throws Exception {
        mockMvc.perform(put("/api/sala-grupal/1/101")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "piso": 2,
                                    "numeroSala": 202,
                                    "disponible": true
                                }
                                """))
                .andExpect(status().isBadRequest())
                .andExpect(content().string("El piso y número de sala no coinciden"));
    }

    @Test
    void testUpdateSala_NotFound_WhenServiceThrowsException() throws Exception {
        Mockito.doThrow(new RuntimeException("Sala no encontrada")).when(servicioSalaGrupo).updateSala(any(SalaGrupal.class));

        mockMvc.perform(put("/api/sala-grupal/1/101")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "piso": 1,
                                    "numeroSala": 101,
                                    "disponible": false
                                }
                                """))
                .andExpect(status().isNotFound())
                .andExpect(content().string("Sala no encontrada"));
    }

    @Test
    void testDeleteSala_Success() throws Exception {
        Mockito.doNothing().when(servicioSalaGrupo).deleteSala(1, 101);

        mockMvc.perform(delete("/api/sala-grupal/1/101"))
                .andExpect(status().isOk())
                .andExpect(content().string("Sala eliminada exitosamente"));
    }
}