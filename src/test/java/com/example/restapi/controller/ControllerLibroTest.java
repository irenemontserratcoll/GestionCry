package com.example.restapi.controller;

import com.example.restapi.model.Libro;
import com.example.restapi.service.ServicioLibros;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.Optional;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(LibroController.class)
class ControllerLibroTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioLibros servicioLibros;

    @Test
    @DisplayName("Debe devolver la lista de todos los libros")
    void getAllLibros() throws Exception {
        Libro libro1 = new Libro("Libro 1", "Autor 1", "1234567890");
        Libro libro2 = new Libro("Libro 2", "Autor 2", "0987654321");

        Mockito.when(servicioLibros.findAll()).thenReturn(Arrays.asList(libro1, libro2));

        mockMvc.perform(get("/api/libros/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()").value(2));
    }

    @Test
    @DisplayName("Debe devolver un libro por ID")
    void getLibroById() throws Exception {
        Libro libro = new Libro("Libro 1", "Autor 1", "1234567890");
        Mockito.when(servicioLibros.findById(1L)).thenReturn(Optional.of(libro));

        mockMvc.perform(get("/api/libros/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.titulo").value("Libro 1"));
    }

    @Test
    @DisplayName("Debe devolver NOT FOUND si no existe el libro por ID")
    void getLibroById_NotFound() throws Exception {
        Mockito.when(servicioLibros.findById(1L)).thenReturn(Optional.empty());

        mockMvc.perform(get("/api/libros/1"))
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Debe agregar un nuevo libro")
    void addLibro() throws Exception {
        Mockito.doNothing().when(servicioLibros).addLibro(any(Libro.class));

        mockMvc.perform(post("/api/libros/add")
                .param("titulo", "Nuevo Libro")
                .param("autor", "Nuevo Autor")
                .param("isbn", "1122334455"))
                .andExpect(status().isCreated())
                .andExpect(content().string("Libro agregado correctamente"));
    }

    @Test
    @DisplayName("Debe actualizar un libro existente")
    void updateLibro() throws Exception {
        Mockito.doNothing().when(servicioLibros).updateLibro(eq(1L), any(Libro.class));

        mockMvc.perform(put("/api/libros/update/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content("""
                        {
                            "titulo": "Libro Actualizado",
                            "autor": "Autor Actualizado",
                            "isbn": "5566778899"
                        }
                        """))
                .andExpect(status().isOk())
                .andExpect(content().string("Libro actualizado correctamente"));
    }

    @Test
    @DisplayName("Debe eliminar un libro")
    void deleteLibro() throws Exception {
        Mockito.doNothing().when(servicioLibros).deleteLibro(1L);

        mockMvc.perform(delete("/api/libros/delete/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Libro eliminado correctamente"));
    }
}
