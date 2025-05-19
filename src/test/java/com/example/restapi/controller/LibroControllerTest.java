package com.example.restapi.controller;

import com.example.restapi.model.Libro;
import com.example.restapi.service.ServicioLibros;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

public class LibroControllerTest {

    @InjectMocks
    private LibroController libroController;

    @Mock
    private ServicioLibros servicioLibros;

    private Libro libro1;
    private Libro libro2;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        libro1 = new Libro("Titulo1", "Autor1", "ISBN1");
        libro1.setId(1L);
        libro2 = new Libro("Titulo2", "Autor2", "ISBN2");
        libro2.setId(2L);
    }

    @Test
    public void testGetAllBooks() {
        when(servicioLibros.findAll()).thenReturn(Arrays.asList(libro1, libro2));

        List<Libro> resultado = libroController.getAllBooks();

        assertEquals(2, resultado.size());
        assertEquals("Titulo1", resultado.get(0).getTitulo());
        assertEquals("Autor2", resultado.get(1).getAutor());
        verify(servicioLibros, times(1)).findAll();
    }

    @SuppressWarnings("null")
    @Test
    public void testGetLibroById_found() {
        when(servicioLibros.findById(1L)).thenReturn(Optional.of(libro1));

        ResponseEntity<Libro> response = libroController.getLibroById(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("ISBN1", response.getBody().getIsbn());
    }

    @Test
    public void testGetLibroById_notFound() {
        when(servicioLibros.findById(3L)).thenReturn(Optional.empty());

        ResponseEntity<Libro> response = libroController.getLibroById(3L);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @SuppressWarnings("null")
    @Test
    public void testAddLibro() {
        doNothing().when(servicioLibros).addLibro(any(Libro.class));

        ResponseEntity<String> response = libroController.addLibro("Nuevo Libro", "Nuevo Autor", "NuevoISBN");

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().contains("Libro agregado correctamente"));
        verify(servicioLibros, times(1)).addLibro(any(Libro.class));
    }

    @SuppressWarnings("null")
    @Test
    public void testUpdateLibro() {
        doNothing().when(servicioLibros).updateLibro(eq(1L), any(Libro.class));

        ResponseEntity<String> response = libroController.updateLibro(1L, "Libro Actualizado", "Autor Actualizado", "ISBNActualizado");

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Libro actualizado correctamente"));
        verify(servicioLibros, times(1)).updateLibro(eq(1L), any(Libro.class));
    }

    @SuppressWarnings("null")
    @Test
    public void testDeleteLibro() {
        doNothing().when(servicioLibros).deleteLibro(1L);

        ResponseEntity<String> response = libroController.deleteLibro(1L);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Libro eliminado correctamente"));
        verify(servicioLibros, times(1)).deleteLibro(1L);
    }
}
