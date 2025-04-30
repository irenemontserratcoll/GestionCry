package com.example.restapi.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class OrdenadorTest {

    @Test
    void testConstructorSinArgumentos() {
        Ordenador ordenador = new Ordenador();

        assertNotNull(ordenador);
        assertNull(ordenador.getId());
        assertNull(ordenador.getMarca());
        assertNull(ordenador.getModelo());
        assertNull(ordenador.getNumeroSerie());
        assertFalse(ordenador.isDisponible()); // boolean por defecto es false
    }

    @Test
    void testConstructorConArgumentos() {
        Ordenador ordenador = new Ordenador("HP", "EliteBook", "ABC123", true);

        assertEquals("HP", ordenador.getMarca());
        assertEquals("EliteBook", ordenador.getModelo());
        assertEquals("ABC123", ordenador.getNumeroSerie());
        assertTrue(ordenador.isDisponible());
    }

    @Test
    void testSettersAndGetters() {
        Ordenador ordenador = new Ordenador();

        ordenador.setId(1L);
        ordenador.setMarca("Dell");
        ordenador.setModelo("Latitude");
        ordenador.setNumeroSerie("XYZ789");
        ordenador.setDisponible(false);

        assertEquals(1L, ordenador.getId());
        assertEquals("Dell", ordenador.getMarca());
        assertEquals("Latitude", ordenador.getModelo());
        assertEquals("XYZ789", ordenador.getNumeroSerie());
        assertFalse(ordenador.isDisponible());
    }
}