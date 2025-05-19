package com.example.restapi.controller;

import com.example.restapi.model.Usuario;
import com.example.restapi.service.ServicioUsuarios;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.http.ResponseEntity;

import java.util.*;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

public class UsuarioControllerTest {

    @InjectMocks
    private UsuarioController usuarioController;

    @Mock
    private ServicioUsuarios servicioUsuarios;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    private Usuario crearUsuarioEjemplo() {
        return new Usuario(1, "Carlos", "carlos@example.com", "password123");
    }

    @Test
    public void testObtenerTodosLosUsuarios() {
        List<Usuario> usuarios = Collections.singletonList(crearUsuarioEjemplo());
        when(servicioUsuarios.findAll()).thenReturn(usuarios);

        List<Usuario> resultado = usuarioController.getAllUsers();

        assertEquals(1, resultado.size());
        assertEquals("Carlos", resultado.get(0).getNombre());
        verify(servicioUsuarios, times(1)).findAll();
    }

    @SuppressWarnings({ "deprecation", "null" })
    @Test
    public void testAgregarUsuarioExitoso() {
        Usuario nuevoUsuario = crearUsuarioEjemplo();
        when(servicioUsuarios.addUsuario(any(Usuario.class))).thenReturn(nuevoUsuario);

        ResponseEntity<String> response = usuarioController.addUser("Carlos", "carlos@example.com", "password123");

        assertEquals(201, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Usuario agregado correctamente"));
        verify(servicioUsuarios, times(1)).addUsuario(any(Usuario.class));
    }

    @SuppressWarnings({ "deprecation", "null" })
    @Test
    public void testAgregarUsuarioConCorreoExistente() {
        when(servicioUsuarios.addUsuario(any(Usuario.class)))
                .thenThrow(new IllegalArgumentException("Ya existe un usuario con ese correo."));

        ResponseEntity<String> response = usuarioController.addUser("Carlos", "carlos@example.com", "password123");

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Error al agregar el usuario"));
    }

    @SuppressWarnings({ "deprecation", "null" })
    @Test
    public void testEliminarUsuarioExitoso() {
        doNothing().when(servicioUsuarios).deleteUsuario("carlos@example.com");

        ResponseEntity<String> response = usuarioController.deleteUser("carlos@example.com");

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Usuario eliminado correctamente"));
    }

    @SuppressWarnings({ "deprecation", "null" })
    @Test
    public void testEliminarUsuarioConError() {
        doThrow(new RuntimeException("Error al eliminar")).when(servicioUsuarios).deleteUsuario("carlos@example.com");

        ResponseEntity<String> response = usuarioController.deleteUser("carlos@example.com");

        assertEquals(500, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Error al eliminar el usuario"));
    }

    @SuppressWarnings({ "deprecation", "null" })
    @Test
    public void testLoginExitoso() {
        Usuario usuario = crearUsuarioEjemplo();
        when(servicioUsuarios.login("carlos@example.com", "password123"))
                .thenReturn(Optional.of(usuario));

        ResponseEntity<String> response = usuarioController.login("carlos@example.com", "password123");

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Inicio de sesión exitoso"));
    }

    @SuppressWarnings({ "deprecation", "null" })
    @Test
    public void testLoginFallido() {
        when(servicioUsuarios.login("carlos@example.com", "password123"))
                .thenReturn(Optional.empty());

        ResponseEntity<String> response = usuarioController.login("carlos@example.com", "password123");

        assertEquals(401, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Credenciales inválidas"));
    }

    @SuppressWarnings({ "deprecation", "null" })
    @Test
    public void testLoginAdminExitoso() {
        ResponseEntity<String> response = usuarioController.loginAdmin("admin", "admin");

        assertEquals(200, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Inicio de sesión exitoso"));
    }

    @SuppressWarnings({ "deprecation", "null" })
    @Test
    public void testLoginAdminFallido() {
        ResponseEntity<String> response = usuarioController.loginAdmin("admin", "wrongpass");

        assertEquals(401, response.getStatusCodeValue());
        assertTrue(response.getBody().contains("Credenciales de administrador inválidas"));
    }
}
