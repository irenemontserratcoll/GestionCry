package com.example.restapi.model;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import org.junit.jupiter.api.Test;

class EspacioIndividualTest {

    @Test
    void testConstructorConArgumentos() {
        // Given
        int piso = 2;
        int numeroAsiento = 10;

        // When
        EspacioIndividual espacio = new EspacioIndividual(piso, numeroAsiento);

        // Then
        assertEquals(piso, espacio.getPiso());
        assertEquals(numeroAsiento, espacio.getNumeroAsiento());
    }

    @Test
    void testSettersAndGetters() {
        // Given
        EspacioIndividual espacio = new EspacioIndividual();
        int piso = 3;
        int numeroAsiento = 5;

        // When
        espacio.setPiso(piso);
        espacio.setNumeroAsiento(numeroAsiento);

        // Then
        assertEquals(piso, espacio.getPiso());
        assertEquals(numeroAsiento, espacio.getNumeroAsiento());
    }

    @Test
    void testSetPisoInvalido() {
        EspacioIndividual espacio = new EspacioIndividual();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> espacio.setPiso(0));
        assertEquals("El piso debe ser mayor a 0", exception.getMessage());
    }

    @Test
    void testSetNumeroAsientoInvalido() {
        EspacioIndividual espacio = new EspacioIndividual();
        Exception exception = assertThrows(IllegalArgumentException.class, () -> espacio.setNumeroAsiento(0));
        assertEquals("El número de asiento debe ser mayor a 0", exception.getMessage());
    }

    @Test
    void testToString() {
        EspacioIndividual espacio = new EspacioIndividual(2, 15);
        String esperado = "Piso: 2, Asiento: 15";

        assertEquals(esperado, espacio.toString());
    }

    @Test
    void testEqualsAndHashCode() {
        EspacioIndividual espacio1 = new EspacioIndividual(1, 1);
        EspacioIndividual espacio2 = new EspacioIndividual(1, 1);

        // Simulamos IDs (normalmente los pone JPA automáticamente)
        espacio1.setId(100L);
        espacio2.setId(100L);

        assertEquals(espacio1, espacio2);
        assertEquals(espacio1.hashCode(), espacio2.hashCode());
    }

    @Test
    void testNotEquals() {
        EspacioIndividual espacio1 = new EspacioIndividual(1, 1);
        EspacioIndividual espacio2 = new EspacioIndividual(1, 2);

        espacio1.setId(100L);
        espacio2.setId(100L);

        assertNotEquals(espacio1, espacio2);
    }

    @Test
    void testEqualsConObjetoDeOtraClase() {
        EspacioIndividual espacio = new EspacioIndividual(2, 5);
        espacio.setId(300L);

        assertNotEquals(espacio, "otro objeto");
    }

    void testEqualsConIdNulo() {
        EspacioIndividual espacio1 = new EspacioIndividual(2, 5);
        EspacioIndividual espacio2 = new EspacioIndividual(2, 5);

        // Ambos tienen ID null, pero los demás campos son iguales
        assertNotEquals(espacio1, espacio2);
    }
}
