package com.example.restapi.service;

import com.example.restapi.model.Libro;
import com.example.restapi.repository.RepositorioLibros;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class ServicioLibrosTest {

    @Mock
    private RepositorioLibros repositorioLibros;

    @InjectMocks
    private ServicioLibros servicioLibros;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindAll() {
        Libro libro1 = new Libro("Título A", "Autor A", "12345");
        Libro libro2 = new Libro("Título B", "Autor B", "67890");
        List<Libro> libros = Arrays.asList(libro1, libro2);

        when(repositorioLibros.findAll()).thenReturn(libros);

        List<Libro> resultado = servicioLibros.findAll();

        assertEquals(2, resultado.size());
        verify(repositorioLibros, times(1)).findAll();
    }

    @Test
    void testFindById() {
        Libro libro = new Libro("Título X", "Autor X", "11111");
        when(repositorioLibros.findById(1L)).thenReturn(Optional.of(libro));

        Optional<Libro> resultado = servicioLibros.findById(1L);

        assertTrue(resultado.isPresent());
        assertEquals("Título X", resultado.get().getTitulo());
    }

    @Test
    void testFindByTitulo() {
        Libro libro = new Libro("Mi Libro", "Autor", "12345");
        when(repositorioLibros.findByTitulo("Mi Libro")).thenReturn(Optional.of(libro));

        Optional<Libro> resultado = servicioLibros.findByTitulo("Mi Libro");

        assertTrue(resultado.isPresent());
        assertEquals("Autor", resultado.get().getAutor());
    }

    @Test
    void testFindByAutor() {
        Libro libro = new Libro("Título", "Autor Famoso", "12345");
        when(repositorioLibros.findByAutor("Autor Famoso")).thenReturn(Optional.of(libro));

        Optional<Libro> resultado = servicioLibros.findByAutor("Autor Famoso");

        assertTrue(resultado.isPresent());
        assertEquals("Título", resultado.get().getTitulo());
    }

    @Test
    void testFindByIsbn() {
        Libro libro = new Libro("Título", "Autor", "99999");
        when(repositorioLibros.findByIsbn("99999")).thenReturn(Optional.of(libro));

        Optional<Libro> resultado = servicioLibros.findByIsbn("99999");

        assertTrue(resultado.isPresent());
        assertEquals("99999", resultado.get().getIsbn());
    }

    @Test
    void testAddLibro() {
        Libro libro = new Libro("Nuevo Libro", "Nuevo Autor", "00000");
        when(repositorioLibros.save(libro)).thenReturn(libro);

        Libro resultado = servicioLibros.addLibro(libro);

        assertNotNull(resultado);
        assertEquals("Nuevo Libro", resultado.getTitulo());
    }

    @Test
    void testUpdateLibro_Success() {
        Libro libroExistente = new Libro("Viejo Título", "Viejo Autor", "12345");
        Libro libroActualizado = new Libro("Nuevo Título", "Nuevo Autor", "12345");

        when(repositorioLibros.findById(1L)).thenReturn(Optional.of(libroExistente));
        when(repositorioLibros.save(any(Libro.class))).thenAnswer(invocation -> invocation.getArgument(0));

        Libro resultado = servicioLibros.updateLibro(1L, libroActualizado);

        assertEquals(1L, resultado.getId());
        assertEquals("Nuevo Título", resultado.getTitulo());
    }

    @Test
    void testUpdateLibro_NotFound() {
        Libro libro = new Libro("Cualquiera", "Autor", "00000");

        when(repositorioLibros.findById(999L)).thenReturn(Optional.empty());

        RuntimeException ex = assertThrows(RuntimeException.class, () -> {
            servicioLibros.updateLibro(999L, libro);
        });

        assertEquals("Libro no encontrado con ID: 999", ex.getMessage());
    }

    @Test
    void testDeleteLibro() {
        doNothing().when(repositorioLibros).deleteById(1L);

        servicioLibros.deleteLibro(1L);

        verify(repositorioLibros, times(1)).deleteById(1L);
    }
}
