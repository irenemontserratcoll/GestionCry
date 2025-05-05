package com.example.restapi.controller;

import com.example.restapi.model.Libro;
import com.example.restapi.service.ServicioLibros;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Collections;
import java.util.Optional;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;
import org.mockito.MockitoAnnotations;

public class LibroControllerTest {

    @InjectMocks
    private LibroController libroController;

    @Mock
    private ServicioLibros servicioLibros;

    private Libro libroEjemplo;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        libroEjemplo = new Libro("1984", "George Orwell", "1234567890");
        libroEjemplo.setId(1L);
    }

    @Test
    public void testGetAllLibros() {
        when(servicioLibros.findAll()).thenReturn(Collections.singletonList(libroEjemplo));

        List<Libro> libros = libroController.getAllLibros();

        assertEquals(1, libros.size());
        assertEquals("1984", libros.get(0).getTitulo());
        verify(servicioLibros, times(1)).findAll();
    }

    @Test
    public void testGetLibroByIdExistente() {
        when(servicioLibros.findById(1L)).thenReturn(Optional.of(libroEjemplo));

        ResponseEntity<Libro> response = libroController.getLibroById(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("George Orwell", response.getBody().getAutor());
    }

    @Test
    public void testGetLibroByIdNoExistente() {
        when(servicioLibros.findById(99L)).thenReturn(Optional.empty());

        ResponseEntity<Libro> response = libroController.getLibroById(99L);

        assertEquals(404, response.getStatusCodeValue());
        assertNull(response.getBody());
    }

    @Test
    public void testGetLibroByTitulo() {
        when(servicioLibros.findByTitulo("1984")).thenReturn(Optional.of(libroEjemplo));

        ResponseEntity<Libro> response = libroController.getLibroByTitulo("1984");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("1234567890", response.getBody().getIsbn());
    }

    @Test
    void testGetLibroByTituloNotFound() {
        String titulo = "Título No Existente";
        when(servicioLibros.findByTitulo(titulo)).thenReturn(Optional.empty());

        ResponseEntity<Libro> response = libroController.getLibroByTitulo(titulo);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetLibroByAutor() {
        when(servicioLibros.findByAutor("George Orwell")).thenReturn(Optional.of(libroEjemplo));

        ResponseEntity<Libro> response = libroController.getLibroByAutor("George Orwell");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("1984", response.getBody().getTitulo());
    }

    @Test
    void testGetLibroByAutorNotFound() {
        String autor = "Autor Inexistente";
        when(servicioLibros.findByAutor(autor)).thenReturn(Optional.empty());

        ResponseEntity<Libro> response = libroController.getLibroByAutor(autor);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testGetLibroByIsbn() {
        when(servicioLibros.findByIsbn("1234567890")).thenReturn(Optional.of(libroEjemplo));

        ResponseEntity<Libro> response = libroController.getLibroByIsbn("1234567890");

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("1984", response.getBody().getTitulo());
    }

    @Test
    void testGetLibroByIsbnNotFound() {
        String isbn = "123-456-789";
        when(servicioLibros.findByIsbn(isbn)).thenReturn(Optional.empty());

        ResponseEntity<Libro> response = libroController.getLibroByIsbn(isbn);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    public void testAddLibroExitoso() {
        // No se necesita mockear addLibro porque es void y no lanza excepción
        ResponseEntity<String> response = libroController.addLibro("1984", "George Orwell", "1234567890");

        assertEquals(201, response.getStatusCodeValue());
        assertEquals("Libro agregado correctamente", response.getBody());
        verify(servicioLibros, times(1)).addLibro(any(Libro.class));
    }

    @Test
    public void testAddLibroConExcepcion() {
        doThrow(new RuntimeException("Error")).when(servicioLibros).addLibro(any(Libro.class));

        ResponseEntity<String> response = libroController.addLibro("1984", "George Orwell", "1234567890");

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Error al agregar el libro"));
    }

    @Test
    public void testUpdateLibroExitoso() {
        ResponseEntity<String> response = libroController.updateLibro(1L, libroEjemplo);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Libro actualizado correctamente", response.getBody());
        verify(servicioLibros, times(1)).updateLibro(eq(1L), any(Libro.class));
    }

    @Test
    public void testUpdateLibroConExcepcion() {
        doThrow(new RuntimeException("Error")).when(servicioLibros).updateLibro(eq(1L), any(Libro.class));

        ResponseEntity<String> response = libroController.updateLibro(1L, libroEjemplo);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Error al actualizar el libro"));
    }

    @Test
    public void testDeleteLibroExitoso() {
        ResponseEntity<String> response = libroController.deleteLibro(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Libro eliminado correctamente", response.getBody());
        verify(servicioLibros, times(1)).deleteLibro(1L);
    }

    @Test
    public void testDeleteLibroConExcepcion() {
        doThrow(new RuntimeException("Error")).when(servicioLibros).deleteLibro(1L);

        ResponseEntity<String> response = libroController.deleteLibro(1L);

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Error al eliminar el libro"));
    }
}
