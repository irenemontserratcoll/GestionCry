/*package com.example.restapi.controller;

import com.example.restapi.model.Ordenador;
import com.example.restapi.service.ServicioOrdenadores;
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

@WebMvcTest(OrdenadorController.class)
class OrdenadorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioOrdenadores servicioOrdenadores;

    private Ordenador ordenador1;
    private Ordenador ordenador2;

    @BeforeEach
    void setUp() {
        ordenador1 = new Ordenador("Dell", "Inspiron", "12345", true);
        ordenador1.setId(1L);
        ordenador2 = new Ordenador("HP", "Pavilion", "67890", false);
        ordenador2.setId(2L);
    }

    @Test
    void testGetAllOrdenadores() throws Exception {
        Mockito.when(servicioOrdenadores.findAll()).thenReturn(Arrays.asList(ordenador1, ordenador2));

        mockMvc.perform(get("/api/ordenadores/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)));
    }

    @Test
    void testGetOrdenadorById_Found() throws Exception {
        Mockito.when(servicioOrdenadores.findById(1L)).thenReturn(Optional.of(ordenador1));

        mockMvc.perform(get("/api/ordenadores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marca").value("Dell"))
                .andExpect(jsonPath("$.modelo").value("Inspiron"));
    }

    @Test
    void testGetOrdenadorById_NotFound() throws Exception {
        Mockito.when(servicioOrdenadores.findById(99L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/ordenadores/99"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testGetOrdenadorByNumeroSerie_Found() throws Exception {
        Mockito.when(servicioOrdenadores.findByNumeroSerie("12345")).thenReturn(Optional.of(ordenador1));

        mockMvc.perform(get("/api/ordenadores/numeroSerie/12345"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.marca").value("Dell"));
    }

    @Test
    void testGetOrdenadorByNumeroSerie_NotFound() throws Exception {
        Mockito.when(servicioOrdenadores.findByNumeroSerie("99999")).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/ordenadores/numeroSerie/99999"))
                .andExpect(status().isNotFound());
    }

    @Test
    void testAddOrdenador_Success() throws Exception {
        Mockito.doNothing().when(servicioOrdenadores).addOrdenador(any(Ordenador.class));

        mockMvc.perform(post("/api/ordenadores/add")
                        .param("marca", "Lenovo")
                        .param("modelo", "ThinkPad")
                        .param("numeroSerie", "ABC123")
                        .param("disponible", "true"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Ordenador agregado correctamente"));
    }

    @Test
    void testAddOrdenador_Failure() throws Exception {
        Mockito.doThrow(new RuntimeException("Error interno")).when(servicioOrdenadores).addOrdenador(any(Ordenador.class));

        mockMvc.perform(post("/api/ordenadores/add")
                        .param("marca", "Lenovo")
                        .param("modelo", "ThinkPad")
                        .param("numeroSerie", "ABC123")
                        .param("disponible", "true"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Error al agregar el ordenador")));
    }

    @Test
    void testUpdateOrdenador_Success() throws Exception {
        Mockito.doNothing().when(servicioOrdenadores).updateOrdenador(eq(1L), any(Ordenador.class));

        mockMvc.perform(put("/api/ordenadores/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "marca": "Dell",
                                    "modelo": "XPS",
                                    "numeroSerie": "12345",
                                    "disponible": true
                                }
                                """))
                .andExpect(status().isOk())
                .andExpect(content().string("Ordenador actualizado correctamente"));
    }

    @Test
    void testUpdateOrdenador_Failure() throws Exception {
        Mockito.doThrow(new RuntimeException("Error interno")).when(servicioOrdenadores).updateOrdenador(eq(1L), any(Ordenador.class));

        mockMvc.perform(put("/api/ordenadores/update/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("""
                                {
                                    "marca": "Dell",
                                    "modelo": "XPS",
                                    "numeroSerie": "12345",
                                    "disponible": true
                                }
                                """))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Error al actualizar el ordenador")));
    }

    @Test
    void testDeleteOrdenador_Success() throws Exception {
        Mockito.doNothing().when(servicioOrdenadores).deleteOrdenador(1L);

        mockMvc.perform(delete("/api/ordenadores/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Ordenador eliminado correctamente"));
    }

    @Test
    void testDeleteOrdenador_Failure() throws Exception {
        Mockito.doThrow(new RuntimeException("Error interno")).when(servicioOrdenadores).deleteOrdenador(1L);

        mockMvc.perform(delete("/api/ordenadores/delete/1"))
                .andExpect(status().isInternalServerError())
                .andExpect(content().string(org.hamcrest.Matchers.containsString("Error al eliminar el ordenador")));
    }
}*/