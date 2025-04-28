package com.example.restapi.model;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class SalaGrupalTest {

    @Test
    void testConstructorSinArgumentos() {
        SalaGrupal sala = new SalaGrupal();
        
        assertNotNull(sala);
        assertEquals(0, sala.getPiso());
        assertEquals(0, sala.getNumeroSala());
        assertEquals(0, sala.getNumeroPersonas());
    }

    @Test
    void testConstructorConArgumentos() {
        SalaGrupal sala = new SalaGrupal(2, 101, 6);

        assertEquals(2, sala.getPiso());
        assertEquals(101, sala.getNumeroSala());
        assertEquals(6, sala.getNumeroPersonas());
    }

    @Test
    void testSettersAndGetters() {
        SalaGrupal sala = new SalaGrupal();

        sala.setPiso(3);
        sala.setNumeroSala(205);
        sala.setNumeroPersonas(8);

        assertEquals(3, sala.getPiso());
        assertEquals(205, sala.getNumeroSala());
        assertEquals(8, sala.getNumeroPersonas());
    }

    @Test
    void testSetterPisoInvalidValue() {
        SalaGrupal sala = new SalaGrupal();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> sala.setPiso(0));
        assertEquals("El piso debe ser mayor a 0", exception.getMessage());
    }

    @Test
    void testSetterNumeroSalaInvalidValue() {
        SalaGrupal sala = new SalaGrupal();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> sala.setNumeroSala(0));
        assertEquals("El número de sala debe ser mayor a 0", exception.getMessage());
    }

    @Test
    void testSetterNumeroPersonasInvalidValue() {
        SalaGrupal sala = new SalaGrupal();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> sala.setNumeroPersonas(0));
        assertEquals("El número de personas debe ser mayor a 0", exception.getMessage());
    }

    @Test
    void testToString() {
        SalaGrupal sala = new SalaGrupal(1, 101, 4);
        String expected = "Piso: 1, Sala: 101, Personas: 4";
        assertEquals(expected, sala.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        SalaGrupal sala1 = new SalaGrupal(1, 101, 4);
        SalaGrupal sala2 = new SalaGrupal(2, 202, 8);

        sala1.setId(1L);
        sala2.setId(1L);

        assertEquals(sala1, sala2);
        assertEquals(sala1.hashCode(), sala2.hashCode());

        sala2.setId(2L);

        assertNotEquals(sala1, sala2);
        assertNotEquals(sala1.hashCode(), sala2.hashCode());
    }

    @Test
    void testEqualsWithNullAndDifferentClass() {
        SalaGrupal sala = new SalaGrupal(1, 101, 4);

        assertNotEquals(null, sala);
        assertNotEquals("some string", sala);
    }
}