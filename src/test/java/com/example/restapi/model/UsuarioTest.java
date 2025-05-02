package com.example.restapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;

import org.junit.jupiter.api.Test;

class UsuarioTest {

    @Test
    void testConstructorConArgumentos() {
        // Given
        int id = 1;
        String nombre = "Juan Pérez";
        String correo = "juan@example.com";
        String contrasena = "secreta123";

        // When
        Usuario usuario = new Usuario(id, nombre, correo, contrasena);

        // Then
        assertEquals(id, usuario.getId());
        assertEquals(nombre, usuario.getNombre());
        assertEquals(correo, usuario.getCorreo());
        assertEquals(contrasena, usuario.getContrasena());
    }

    @Test
    void testConstructorSinId() {
        // Given
        String nombre = "Ana Gómez";
        String correo = "ana@example.com";
        String contrasena = "password456";

        // When
        Usuario usuario = new Usuario(nombre, correo, contrasena);

        // Then
        assertEquals(nombre, usuario.getNombre());
        assertEquals(correo, usuario.getCorreo());
        assertEquals(contrasena, usuario.getContrasena());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Usuario usuario = new Usuario();
        int id = 2;
        String nombre = "Carlos Ruiz";
        String correo = "carlos@example.com";
        String contrasena = "clave789";

        // When
        usuario.setId(id);
        usuario.setNombre(nombre);
        usuario.setCorreo(correo);
        usuario.setContrasena(contrasena);

        // Then
        assertEquals(id, usuario.getId());
        assertEquals(nombre, usuario.getNombre());
        assertEquals(correo, usuario.getCorreo());
        assertEquals(contrasena, usuario.getContrasena());
    }

    @Test
    void testEqualsAndHashCode() {
        // Given
        Usuario usuario1 = new Usuario(1, "Laura", "laura@example.com", "abc123");
        Usuario usuario2 = new Usuario(1, "Laura", "laura@example.com", "abc123");
        Usuario usuario3 = new Usuario(2, "Luis", "luis@example.com", "xyz789");

        // Then
        assertEquals(usuario1, usuario2);
        assertEquals(usuario1.hashCode(), usuario2.hashCode());
        assertNotEquals(usuario1, usuario3);
    }

    @Test
    void testEqualsConNull() {
        Usuario usuario = new Usuario(1, "Test", "test@example.com", "123");
        assertNotEquals(usuario, null);
    }

    @Test
    void testEqualsConOtraClase() {
        Usuario usuario = new Usuario(1, "Test", "test@example.com", "123");
        String otroObjeto = "no es un usuario";
        assertNotEquals(usuario, otroObjeto);
    }

    @Test
    void testEqualsConCamposNulos() {
        Usuario usuario1 = new Usuario(1, null, null, null);
        Usuario usuario2 = new Usuario(1, null, null, null);
        assertEquals(usuario1, usuario2);
        assertEquals(usuario1.hashCode(), usuario2.hashCode());
    }


    @Test
    void testToString() {
        // Given
        Usuario usuario = new Usuario(1, "Marta", "marta@example.com", "123abc");

        // Then
        String expected = "Usuario{id=1, nombre='Marta', correo='marta@example.com', contrasena='123abc'}";
        assertEquals(expected, usuario.toString());
    }

}
