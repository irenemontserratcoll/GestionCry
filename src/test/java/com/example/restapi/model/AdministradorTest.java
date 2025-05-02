package com.example.restapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import org.junit.jupiter.api.Test;

class AdministradorTest {

    @Test
    void testConstructorConParametros() {
        // Given
        Long id = 1L;
        String nombre = "Admin Uno";
        String correo = "admin@ejemplo.com";
        String contrasena = "admin123";

        // When
        Administrador admin = new Administrador(id, nombre, correo, contrasena);

        // Then
        assertEquals(id, admin.getId());
        assertEquals(nombre, admin.getNombre());
        assertEquals(correo, admin.getCorreo());
        assertEquals(contrasena, admin.getContrasena());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        Administrador admin = new Administrador();
        Long id = 2L;
        String nombre = "Admin Dos";
        String correo = "admin2@ejemplo.com";
        String contrasena = "clave456";

        // When
        admin.setId(id);
        admin.setNombre(nombre);
        admin.setCorreo(correo);
        admin.setContrasena(contrasena);

        // Then
        assertEquals(id, admin.getId());
        assertEquals(nombre, admin.getNombre());
        assertEquals(correo, admin.getCorreo());
        assertEquals(contrasena, admin.getContrasena());
    }

    @Test
    void testToString() {
        // Given
        Administrador admin = new Administrador(3L, "Admin Tres", "admin3@ejemplo.com", "abc123");

        // When
        String result = admin.toString();

        // Then
        assertNotNull(result);
        String expected = "Administrador{id=3, nombre='Admin Tres', correo='admin3@ejemplo.com', contrasena='abc123'}";
        assertEquals(expected, result);
    }
}
