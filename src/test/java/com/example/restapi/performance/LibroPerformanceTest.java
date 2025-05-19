/*package com.example.restapi.performance;

import com.github.noconnor.junitperf.JUnitPerfTest;
import com.github.noconnor.junitperf.JUnitPerfTestActiveConfig;
import com.github.noconnor.junitperf.JUnitPerfInterceptor;
import com.github.noconnor.junitperf.JUnitPerfReportingConfig;
import com.github.noconnor.junitperf.JUnitPerfTestRequirement;
import com.github.noconnor.junitperf.reporting.providers.HtmlReportGenerator;

import com.example.restapi.model.Libro;
import com.example.restapi.service.ServicioLibros;

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
import java.util.stream.IntStream;

@SpringBootTest
@ExtendWith(JUnitPerfInterceptor.class)
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class LibroPerformanceTest {

    @Autowired
    private ServicioLibros servicioLibros;

    @JUnitPerfTestActiveConfig
    private final static JUnitPerfReportingConfig PERF_CONFIG = JUnitPerfReportingConfig.builder()
            .reportGenerator(new HtmlReportGenerator(System.getProperty("user.dir") + "/target/reports/perf-libros-report.html"))
            .build();

    private final String TITULO_BASE = "LibroTituloBase";

    @BeforeAll
    void init() {
        // Insertar 1000 libros con títulos únicos
        IntStream.range(0, 1000).forEach(i -> {
            Libro libro = new Libro();
            libro.setTitulo(TITULO_BASE + i);
            libro.setAutor("Autor" + i);
            libro.setIsbn("ISBN-" + i);
            servicioLibros.addLibro(libro);
        });
    }

    @AfterAll
    void cleanupFinal() {
        List<Libro> libros = servicioLibros.findAll();
        libros.forEach(libro -> {
            try {
                servicioLibros.deleteLibro(libro.getId());
            } catch (Exception e) {
                // Ignorar errores de eliminación
            }
        });
    }

    @AfterEach
    void cleanupLibrosDinamicos() {
        List<Libro> libros = servicioLibros.findAll();
        libros.stream()
                .filter(libro -> libro.getTitulo().startsWith("LibroNuevoPerf"))
                .forEach(libro -> {
                    try {
                        servicioLibros.deleteLibro(libro.getId());
                    } catch (Exception e) {
                        // Ignorar errores de eliminación
                    }
                });
    }

    @Test
    @JUnitPerfTest(threads = 10, durationMs = 8000, warmUpMs = 2000)
    @JUnitPerfTestRequirement(executionsPerSec = 50, percentiles = "95:300ms")
    public void testFindAllLibrosPerformance() {
        servicioLibros.findAll();
    }

    @Test
    @JUnitPerfTest(threads = 20, durationMs = 6000, warmUpMs = 2000)
    @JUnitPerfTestRequirement(executionsPerSec = 80, percentiles = "95:250ms")
    public void testFindByTituloPerformance() {
        // Buscar un título entre los precargados
        servicioLibros.findByTitulo(TITULO_BASE + "500");
    }

    @Test
    @JUnitPerfTest(threads = 10, durationMs = 5000, warmUpMs = 1000)
    @JUnitPerfTestRequirement(executionsPerSec = 30, percentiles = "90:250ms,95:300ms")
    public void testAddLibroPerformance() {
        Libro nuevoLibro = new Libro();
        long uniqueId = Thread.currentThread().getId() + System.nanoTime();
        nuevoLibro.setTitulo("LibroNuevoPerf" + uniqueId);
        nuevoLibro.setAutor("AutorPerf");
        nuevoLibro.setIsbn("ISBN-PERF-" + uniqueId);

        servicioLibros.addLibro(nuevoLibro);
    }
}
*/
