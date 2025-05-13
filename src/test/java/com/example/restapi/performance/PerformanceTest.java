package com.example.restapi.performance;

import com.github.noconnor.junitperf.JUnitPerfTest;
import com.github.noconnor.junitperf.JUnitPerfTestActiveConfig;
import com.github.noconnor.junitperf.JUnitPerfInterceptor;
import com.github.noconnor.junitperf.JUnitPerfReportingConfig;
import com.github.noconnor.junitperf.JUnitPerfTestRequirement;
import com.github.noconnor.junitperf.reporting.providers.HtmlReportGenerator;

import com.example.restapi.model.Usuario;
import com.example.restapi.service.ServicioUsuarios;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;
import java.util.Optional;

@SpringBootTest
@ExtendWith(JUnitPerfInterceptor.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PerformanceTest {

    @Autowired
    private ServicioUsuarios servicioUsuarios;

    @JUnitPerfTestActiveConfig
    private final static JUnitPerfReportingConfig PERF_CONFIG = JUnitPerfReportingConfig.builder()
            .reportGenerator(new HtmlReportGenerator(System.getProperty("user.dir") + "/target/reports/perf-usuarios-report.html"))
            .build();

    private final String CORREO_BASE = "test@example.com";

    @BeforeAll
    void init() {
        Optional<Usuario> usuarioExistente = servicioUsuarios.findByCorreo(CORREO_BASE);
        if (!usuarioExistente.isPresent()) {
            Usuario usuarioMock = new Usuario();
            usuarioMock.setCorreo(CORREO_BASE);
            usuarioMock.setContrasena("password123");
            usuarioMock.setNombre("Usuario de Prueba");
            servicioUsuarios.addUsuario(usuarioMock);
        }
    }

    @AfterAll
    void cleanupFinal() {
        Optional<Usuario> usuarioBase = servicioUsuarios.findByCorreo(CORREO_BASE);
        usuarioBase.ifPresent(usuario -> {
            try {
                servicioUsuarios.deleteUsuario(usuario.getCorreo());
            } catch (Exception e) {
                // Ignorar error
            }
        });
    }

    @AfterEach
    void cleanupUsuariosDinamicos() {
        // Eliminar los usuarios creados dinámicamente (por testAddUsuarioPerformance)
        try {
            List<Usuario> usuarios = servicioUsuarios.findAll();
            usuarios.stream()
                    .filter(u -> u.getCorreo().startsWith("usuario") && u.getCorreo().endsWith("@example.com"))
                    .forEach(usuario -> {
                        try {
                            servicioUsuarios.deleteUsuario(usuario.getCorreo());
                        } catch (Exception e) {
                            //System.err.println("Error al eliminar usuario " + usuario.getCorreo() + ": " + e.getMessage());
                        }
                    });
        } catch (Exception e) {
            //System.err.println("Error durante la limpieza de usuarios dinámicos: " + e.getMessage());
        }
    }



    @Test
    @JUnitPerfTest(threads = 10, durationMs = 10000, warmUpMs = 2000)
    @JUnitPerfTestRequirement(executionsPerSec = 50, percentiles = "95:300ms")
    public void testFindAllUsuariosPerformance() {
        servicioUsuarios.findAll();
    }

    @Test
    @JUnitPerfTest(threads = 20, durationMs = 8000, warmUpMs = 2000)
    @JUnitPerfTestRequirement(executionsPerSec = 100, percentiles = "95:200ms")
    public void testFindByCorreoPerformance() {
        servicioUsuarios.findByCorreo(CORREO_BASE);
    }

    @Test
    @JUnitPerfTest(threads = 10, durationMs = 6000, warmUpMs = 1000)
    @JUnitPerfTestRequirement(percentiles = "90:250ms,95:300ms", executionsPerSec = 30)
    public void testLoginPerformance() {
        servicioUsuarios.login(CORREO_BASE, "password123");
    }

    @Test
    @JUnitPerfTest(threads = 5, durationMs = 5000, warmUpMs = 1000)
    @JUnitPerfTestRequirement(percentiles = "95:400ms", executionsPerSec = 10)
    public void testAddUsuarioPerformance() {
        Usuario nuevoUsuario = new Usuario();
        String correo = "usuario" + Thread.currentThread().getId() + "-" + System.nanoTime() + "@example.com";
        nuevoUsuario.setCorreo(correo);
        nuevoUsuario.setContrasena("123456");
        nuevoUsuario.setNombre("Nuevo Usuario");

        servicioUsuarios.addUsuario(nuevoUsuario);
    }
}
