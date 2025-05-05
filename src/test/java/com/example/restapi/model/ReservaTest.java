package com.example.restapi.model;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ReservaTest {

    private Reserva reserva;

    @BeforeEach
    void setUp() {
        reserva = new Reserva();
    }

    @Test
    void testSetAndGetId() {
        reserva.setId(1L);
        assertEquals(1L, reserva.getId());
    }

    @Test
    void testSetAndGetNombreCliente() {
        reserva.setNombreCliente("Juan Pérez");
        assertEquals("Juan Pérez", reserva.getNombreCliente());
    }

    @Test
    void testSetAndGetEmailCliente() {
        reserva.setEmailCliente("juan@example.com");
        assertEquals("juan@example.com", reserva.getEmailCliente());
    }

    @Test
    void testSetAndGetFechaReserva() {
        Date fecha = new Date();
        reserva.setFechaReserva(fecha);
        assertEquals(fecha, reserva.getFechaReserva());
    }

    @Test
    void testSetAndGetHoraReserva() {
        reserva.setHoraReserva("12:30");
        assertEquals("12:30", reserva.getHoraReserva());
    }

    @Test
    void testSetAndGetNumPersonas() {
        reserva.setNumPersonas(4);
        assertEquals(4, reserva.getNumPersonas());
    }

    @Test
    void testSetAndGetLibro() {
        Libro libro = new Libro();
        reserva.setLibro(libro);
        assertEquals(libro, reserva.getLibro());
    }

    @Test
    void testSetAndGetOrdenador() {
        Ordenador ordenador = new Ordenador();
        reserva.setOrdenador(ordenador);
        assertEquals(ordenador, reserva.getOrdenador());
    }

    @Test
    void testSetAndGetSalaGrupal() {
        SalaGrupal sala = new SalaGrupal();
        reserva.setSalaGrupal(sala);
        assertEquals(sala, reserva.getSalaGrupal());
    }

    @Test
    void testSetAndGetEspacioIndividual() {
        EspacioIndividual espacio = new EspacioIndividual();
        reserva.setEspacioIndividual(espacio);
        assertEquals(espacio, reserva.getEspacioIndividual());
    }

    @Test
    void testSetAndGetAuxiliaryIds() {
        reserva.setLibroId(10L);
        reserva.setOrdenadorId(20L);
        reserva.setSalaGrupalId(30L);
        reserva.setEspacioIndividualId(40L);

        assertEquals(10L, reserva.getLibroId());
        assertEquals(20L, reserva.getOrdenadorId());
        assertEquals(30L, reserva.getSalaGrupalId());
        assertEquals(40L, reserva.getEspacioIndividualId());
    }

    @Test
    void testToStringWithNoNullFields() {
        reserva.setId(1L);
        reserva.setNombreCliente("Cliente Test");
        reserva.setEmailCliente("cliente@test.com");

        String toString = reserva.toString();

        assertTrue(toString.contains("Cliente Test"));
        assertTrue(toString.contains("cliente@test.com"));
        assertTrue(toString.contains("libro=N/A"));
        assertTrue(toString.contains("ordenador=N/A"));
        assertTrue(toString.contains("salaGrupal=N/A"));
        assertTrue(toString.contains("espacioIndividual=N/A"));
    }

    @Test
    void testToStringWithNonNullFields() {
        Reserva reserva = new Reserva();
        reserva.setId(2L);
        reserva.setNombreCliente("Cliente Test 2");
        reserva.setEmailCliente("cliente2@test.com");

        // Crear instancias mínimas no nulas
        reserva.setLibro(new Libro());
        reserva.setOrdenador(new Ordenador());
        reserva.setSalaGrupal(new SalaGrupal());
        reserva.setEspacioIndividual(new EspacioIndividual());

        String toString = reserva.toString();

        assertTrue(toString.contains("libro=")); // No debería ser "N/A"
        assertTrue(toString.contains("ordenador="));
        assertTrue(toString.contains("salaGrupal="));
        assertTrue(toString.contains("espacioIndividual="));
    }
}