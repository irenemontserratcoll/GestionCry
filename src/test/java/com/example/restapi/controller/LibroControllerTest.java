package com.example.restapi.controller;

import com.example.restapi.model.Libro;
import com.example.restapi.service.ServicioLibros;

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

@WebMvcTest(LibroController.class)
public class LibroControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioLibros servicioLibros;

    private Libro libro1;
    private Libro libro2;

    @BeforeEach
    public void setup() {
        libro1 = new Libro("Titulo1", "Autor1", "ISBN1");
        libro1.setId(1L);
        libro2 = new Libro("Titulo2", "Autor2", "ISBN2");
        libro2.setId(2L);
    }

    @Test
    public void testGetAllBooks() throws Exception {
        Mockito.when(servicioLibros.findAll()).thenReturn(Arrays.asList(libro1, libro2));

        mockMvc.perform(get("/api/libros/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].titulo").value("Titulo1"))
                .andExpect(jsonPath("$[1].autor").value("Autor2"));
    }

    @Test
    public void testGetLibroById_found() throws Exception {
        Mockito.when(servicioLibros.findById(1L)).thenReturn(Optional.of(libro1));

        mockMvc.perform(get("/api/libros/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.isbn").value("ISBN1"));
    }

    @Test
    public void testGetLibroById_notFound() throws Exception {
        Mockito.when(servicioLibros.findById(3L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/libros/3"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void testAddLibro() throws Exception {
        Mockito.doNothing().when(servicioLibros).addLibro(any(Libro.class));

        mockMvc.perform(post("/api/libros")
                .contentType(MediaType.APPLICATION_JSON)
                .content("{\"titulo\":\"Nuevo Libro\",\"autor\":\"Nuevo Autor\",\"isbn\":\"NuevoISBN\"}"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Libro agregado correctamente"));
    }

    @Test
    public void testUpdateLibro() throws Exception {
        Mockito.doNothing().when(servicioLibros).updateLibro(eq(1L), any(Libro.class));

        mockMvc.perform(put("/api/libros/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(
                        "{\"titulo\":\"Libro Actualizado\",\"autor\":\"Autor Actualizado\",\"isbn\":\"ISBNActualizado\"}"))
                .andExpect(status().isOk())
                .andExpect(content().string("Libro actualizado correctamente"));
    }

    @Test
    public void testDeleteLibro() throws Exception {
        Mockito.doNothing().when(servicioLibros).deleteLibro(1L);

        mockMvc.perform(delete("/api/libros/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Libro eliminado correctamente"));
    }
}
