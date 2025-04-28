package com.example.restapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.Test;

class LibroTest {

    @Test
    void testConstructorConArgumentos() {
        // Given
        String titulo = "El Quijote";
        String autor = "Miguel de Cervantes";
        String isbn = "1234567890";

        // When
        Libro libro = new Libro(titulo, autor, isbn);

        // Then
        assertEquals(titulo, libro.getTitulo());
        assertEquals(autor, libro.getAutor());
        assertEquals(isbn, libro.getIsbn());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Libro libro = new Libro();
        String titulo = "Cien años de soledad";
        String autor = "Gabriel García Márquez";
        String isbn = "0987654321";

        // When
        libro.setTitulo(titulo);
        libro.setAutor(autor);
        libro.setIsbn(isbn);

        // Then
        assertEquals(titulo, libro.getTitulo());
        assertEquals(autor, libro.getAutor());
        assertEquals(isbn, libro.getIsbn());
    }

}
