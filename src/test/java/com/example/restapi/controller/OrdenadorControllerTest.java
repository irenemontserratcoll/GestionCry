package com.example.restapi.controller;

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

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(OrdenadorController.class)
public class OrdenadorControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioOrdenadores servicioOrdenadores;

    private Ordenador ordenador1;
    private Ordenador ordenador2;

    @BeforeEach
    public void setup() {
        ordenador1 = new Ordenador("Marca1", "Modelo1", "NS1", true);
        ordenador1.setId(1L);
        ordenador2 = new Ordenador("Marca2", "Modelo2", "NS2", false);
        ordenador2.setId(2L);
    }

    @Test
    public void testGetAllOrdenadores() throws Exception {
        Mockito.when(servicioOrdenadores.findAll()).thenReturn(Arrays.asList(ordenador1, ordenador2));

        mockMvc.perform(get("/api/ordenadores/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].marca").value("Marca1"))
                .andExpect(jsonPath("$[1].modelo").value("Modelo2"));
    }

    @Test
    public void testGetOrdenadorById_found() throws Exception {
        Mockito.when(servicioOrdenadores.findById(1L)).thenReturn(Optional.of(ordenador1));

        mockMvc.perform(get("/api/ordenadores/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.numeroSerie").value("NS1"));
    }

    @Test
    public void testGetOrdenadorById_notFound() throws Exception {
        Mockito.when(servicioOrdenadores.findById(3L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/ordenadores/3"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddOrdenador() throws Exception {
        // No devuelve nada, solo verifica que se llame el método
        Mockito.doNothing().when(servicioOrdenadores).addOrdenador(any(Ordenador.class));

        mockMvc.perform(post("/api/ordenadores/add")
                .param("marca", "MarcaNuevo")
                .param("modelo", "ModeloNuevo")
                .param("numeroSerie", "NSNuevo")
                .param("disponible", "true"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Ordenador agregado correctamente"));
    }

    @Test
    public void testUpdateOrdenador() throws Exception {
        Mockito.doNothing().when(servicioOrdenadores).updateOrdenador(eq(1L), any(Ordenador.class));

        mockMvc.perform(put("/api/ordenadores/update/1")
                .param("marca", "MarcaActualizado")
                .param("modelo", "ModeloActualizado")
                .param("numeroSerie", "NSActualizado")
                .param("disponible", "false"))
                .andExpect(status().isOk())
                .andExpect(content().string("Ordenador actualizado correctamente"));
    }

    @Test
    public void testDeleteOrdenador() throws Exception {
        Mockito.doNothing().when(servicioOrdenadores).deleteOrdenador(1L);

        mockMvc.perform(delete("/api/ordenadores/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Ordenador eliminado correctamente"));
    }

    @Test
    public void testDeleteOrdenadorPorNumeroSerie() throws Exception {
        Mockito.doNothing().when(servicioOrdenadores).deleteByNumeroSerie("NS1");

        mockMvc.perform(post("/api/ordenadores/delete-ordenador")
                .param("numeroSerie", "NS1"))
                .andExpect(status().isOk())
                .andExpect(content().string("redirect:/adminHome?success=Borrado+correcto"));
    }
}
