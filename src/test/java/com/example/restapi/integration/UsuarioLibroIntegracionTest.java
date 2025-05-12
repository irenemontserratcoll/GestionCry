package com.example.restapi.integration;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.example.restapi.model.Usuario;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class UsuarioLibroIntegracionTest {

    @Autowired
    private TestRestTemplate restTemplate;

    private final String nombre = "Nuevo Usuario";
    private final String correo = "nuevo@example.com";
    private final String password = "nuevopass";

    private final String titulo = "Libro de prueba";
    private final String autor = "Autor Prueba";
    private final String isbn = "1234567890123";

    @AfterEach
    void cleanUp() {
        restTemplate.delete("/api/usuarios/delete/" + correo);
    }

    @Test
    void testUsuarios() {
        agregarUsuario();
        loginUsuario();
        loginAdmin();
        obtenerTodosLosUsuarios();
        //agregarLibro();
        eliminarUsuarioYVerificar();
    }

    private void agregarUsuario() {
        String url = String.format("/api/usuarios/add?nombre=%s&correo=%s&password=%s", nombre, correo, password);
        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertTrue(response.getBody().contains("Usuario agregado correctamente"));
    }

    private void loginUsuario() {
        String url = String.format("/api/usuarios/login?Email=%s&Password=%s", correo, password);
        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Inicio de sesión exitoso"));
        assertTrue(response.getBody().contains(nombre));
    }

    private void loginAdmin() {
        String url = "/api/usuarios/login-admin?Email=admin&Password=admin";
        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().contains("Inicio de sesión exitoso"));
    }

    private void obtenerTodosLosUsuarios() {
        ResponseEntity<Usuario[]> response = restTemplate.getForEntity("/api/usuarios/all", Usuario[].class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody() != null);
        assertTrue(response.getBody().length >= 0);
    }

    /*private void agregarLibro() {
        String url = String.format("/api/libros/add?titulo=%s&autor=%s&isbn=%s", titulo, autor, isbn);
        ResponseEntity<String> response = restTemplate.postForEntity(url, null, String.class);
        assertEquals(HttpStatus.OK, response.getStatusCode()); // La API devuelve 200 en vez de 201
    }*/

    private void eliminarUsuarioYVerificar() {
        restTemplate.delete("/api/usuarios/delete/" + correo);

        String loginUrl = String.format("/api/usuarios/login?Email=%s&Password=%s", correo, password);
        ResponseEntity<String> response = restTemplate.postForEntity(loginUrl, null, String.class);

        assertEquals(HttpStatus.UNAUTHORIZED, response.getStatusCode());
        assertTrue(response.getBody().contains("Credenciales inválidas"));
    }
}
