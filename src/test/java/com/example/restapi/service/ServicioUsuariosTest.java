package com.example.restapi.service;

import com.example.restapi.model.Usuario;
import com.example.restapi.repository.RepositorioUsuarios;
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

class ServicioUsuariosTest {

    @Mock
    private RepositorioUsuarios repositorioUsuarios;

    @InjectMocks
    private ServicioUsuarios servicioUsuarios;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void testFindByCorreo() {
        Usuario usuario = new Usuario("Juan Pérez", "juan@gmail.com", "pass");
        when(repositorioUsuarios.findByCorreo("juan@email.com")).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = servicioUsuarios.findByCorreo("juan@email.com");

        assertTrue(resultado.isPresent());
        assertEquals("Juan Pérez", resultado.get().getNombre());
    }


    @Test
    void testFindAll() {
        Usuario usuario1 = new Usuario("Juan Pérez", "juan@gmail.com", "pass");
        Usuario usuario2 = new Usuario("Ana García", "ana@gmail.com", "pass");
        List<Usuario> listaUsuarios = Arrays.asList(usuario1, usuario2);

        when(repositorioUsuarios.findAll()).thenReturn(listaUsuarios);

        List<Usuario> resultado = servicioUsuarios.findAll();

        assertEquals(2, resultado.size());
    }

    @Test
    void testAddUsuario_Success() {
        Usuario usuario = new Usuario("Juan Etxarri", "juanin@gmail.com", "pass");

        when(repositorioUsuarios.findByCorreo("juanin@gmail.com")).thenReturn(Optional.empty());
        when(repositorioUsuarios.save(usuario)).thenReturn(usuario);

        Usuario resultado = servicioUsuarios.addUsuario(usuario);

        assertNotNull(resultado);
        assertEquals("Juan Etxarri", resultado.getNombre());
    }

    @Test
    void testAddUsuario_ExistingEmail() {
        Usuario usuario = new Usuario("María López", "maria@email.com", "pass");
        when(repositorioUsuarios.findByCorreo("maria@email.com")).thenReturn(Optional.of(usuario));

        assertThrows(IllegalArgumentException.class, () -> servicioUsuarios.addUsuario(usuario));
    }

    @Test
    void testDeleteUsuario() {
        doNothing().when(repositorioUsuarios).deleteByCorreo("user@email.com");

        servicioUsuarios.deleteUsuario("user@email.com");

        verify(repositorioUsuarios, times(1)).deleteByCorreo("user@email.com");
    }

    @Test
    void testLogin_Success() {
        Usuario usuario = new Usuario("Pepe Torres", "pepe@email.com", "pass");
        when(repositorioUsuarios.findByCorreo("pepe@email.com")).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = servicioUsuarios.login("pepe@email.com", "pass");

        assertTrue(resultado.isPresent());
        assertEquals("Pepe Torres", resultado.get().getNombre());
    }

    @Test
    void testLogin_Failure() {
        Usuario usuario = new Usuario("Pepe Torres", "pepe@email.com", "pass");
        when(repositorioUsuarios.findByCorreo("pepe@email.com")).thenReturn(Optional.of(usuario));

        Optional<Usuario> resultado = servicioUsuarios.login("pepe@email.com", "wrongpass");

        assertFalse(resultado.isPresent());
    }
}

