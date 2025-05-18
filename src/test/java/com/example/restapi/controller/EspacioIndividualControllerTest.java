package com.example.restapi.controller;

import com.example.restapi.model.EspacioIndividual;
import com.example.restapi.service.ServicioEspacioIndividual;
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

@WebMvcTest(EspacioIndividualController.class)
public class EspacioIndividualControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioEspacioIndividual servicioEspacios;

    private EspacioIndividual espacio1;
    private EspacioIndividual espacio2;

    @BeforeEach
    public void setup() {
        espacio1 = new EspacioIndividual(1, 10);
        espacio1.setId(1L);
        espacio2 = new EspacioIndividual(2, 15);
        espacio2.setId(2L);
    }

    @Test
    public void testGetAllEspacios() throws Exception {
        Mockito.when(servicioEspacios.findAll()).thenReturn(Arrays.asList(espacio1, espacio2));

        mockMvc.perform(get("/api/Espacios-Individuales/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].piso").value(1))
                .andExpect(jsonPath("$[1].numeroAsiento").value(15));
    }

    @Test
    public void testGetEspacioByPisoAndAsiento_found() throws Exception {
        Mockito.when(servicioEspacios.findByPisoAndAsiento(1, 10)).thenReturn(Optional.of(espacio1));

        mockMvc.perform(get("/api/Espacios-Individuales/1/10"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.piso").value(1))
                .andExpect(jsonPath("$.numeroAsiento").value(10));
    }

    @Test
    public void testGetEspacioByPisoAndAsiento_notFound() throws Exception {
        Mockito.when(servicioEspacios.findByPisoAndAsiento(3, 20)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/Espacios-Individuales/3/20"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddEspacio() throws Exception {
        Mockito.doNothing().when(servicioEspacios).addEspacio(any(EspacioIndividual.class));

        mockMvc.perform(post("/api/Espacios-Individuales/add")
                .param("piso", "3")
                .param("numeroAsiento", "12"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Espacio agregado correctamente"));
    }

    @Test
    public void testUpdateEspacio() throws Exception {
        Mockito.doNothing().when(servicioEspacios).updateEspacio(eq(espacio1));

        mockMvc.perform(put("/api/Espacios-Individuales/update")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"id\":1,\"piso\":2,\"numeroAsiento\":12}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Espacio actualizado correctamente"));
    }

    @Test
    public void testDeleteEspacio() throws Exception {
        Mockito.doNothing().when(servicioEspacios).deleteEspacio(1L);

        mockMvc.perform(delete("/api/Espacios-Individuales/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Espacio eliminado correctamente"));
    }
}
