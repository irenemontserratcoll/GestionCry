package com.example.restapi.controller;

import com.example.restapi.model.Libro;
import com.example.restapi.service.ServicioLibros;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

class LibroControllerTest {

    @Mock
    private ServicioLibros servicioLibros;

    @InjectMocks
    private LibroController libroController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testGetAllBooks() {
        List<Libro> libros = List.of(new Libro("Libro1", "Autor1", "123"));
        when(servicioLibros.findAll()).thenReturn(libros);

        List<Libro> result = libroController.getAllBooks();

        assertEquals(1, result.size());
        assertEquals("Libro1", result.get(0).getTitulo());
    }

    @Test
    void testGetLibroById_Existente() {
        Libro libro = new Libro("Libro2", "Autor2", "456");
        when(servicioLibros.findById(1L)).thenReturn(Optional.of(libro));

        ResponseEntity<Libro> response = libroController.getLibroById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Libro2", response.getBody().getTitulo());
    }

    @Test
    void testGetLibroById_NoExistente() {
        when(servicioLibros.findById(1L)).thenReturn(Optional.empty());

        ResponseEntity<Libro> response = libroController.getLibroById(1L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
    }

    @Test
    void testAddLibro_Success() {
        ResponseEntity<String> response = libroController.addLibro("Nuevo Libro", "AutorX", "999");

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().contains("Libro agregado"));
        verify(servicioLibros, times(1)).addLibro(any(Libro.class));
    }

    @Test
    void testAddLibro_Error() {
        doThrow(new RuntimeException("Error")).when(servicioLibros).addLibro(any(Libro.class));

        ResponseEntity<String> response = libroController.addLibro("ErrorLibro", "AutorError", "000");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Error"));
    }

    @Test
    void testDeleteLibro_Success() {
        doNothing().when(servicioLibros).deleteLibro(1L);

        ResponseEntity<String> response = libroController.deleteLibro(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("eliminado"));
    }

    @Test
    void testDeleteLibro_Error() {
        doThrow(new RuntimeException("No se pudo borrar")).when(servicioLibros).deleteLibro(1L);

        ResponseEntity<String> response = libroController.deleteLibro(1L);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Error"));
    }

    @Test
    void testUpdateLibro_Success() {
        Libro libroActualizado = new Libro("Actualizado", "AutorA", "777");
        libroActualizado.setId(1L); // aseguramos que tenga el ID correcto

        when(servicioLibros.updateLibro(eq(1L), any(Libro.class))).thenReturn(libroActualizado);

        ResponseEntity<String> response = libroController.updateLibro(1L, "Actualizado", "AutorA", "777");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("actualizado"));
    }


    @Test
    void testUpdateLibro_Error() {
        doThrow(new RuntimeException("Fallo al actualizar")).when(servicioLibros).updateLibro(eq(1L), any(Libro.class));

        ResponseEntity<String> response = libroController.updateLibro(1L, "Fail", "AutorF", "888");

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertTrue(response.getBody().contains("Error"));
    }
}
