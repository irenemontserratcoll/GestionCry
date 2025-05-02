package com.example.restapi;

import com.example.restapi.service.ServicioUsuarios;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
class RestApiApplicationTests {

    @Autowired
    private ServicioUsuarios servicioUsuarios;

    @MockBean
    private ServicioUsuarios mockServicioUsuarios;

    @Test
    void contextLoads() {
        // Verifica que el contexto se carga correctamente
        assertNotNull(servicioUsuarios);
    }

    @Test
    void mockServiceTest() {
        // Verifica que el mock se inyecta correctamente
        assertNotNull(mockServicioUsuarios);
    }

    @Test
    void applicationStarts() {
        // Realiza alguna prueba adicional relacionada con la inicialización de beans
        assertNotNull(servicioUsuarios);
    }
}
