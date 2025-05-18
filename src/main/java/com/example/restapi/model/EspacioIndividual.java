package com.example.restapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue("ESPACIOS_INDIVIDUALES") // Valor del discriminador para este tipo de recurso
@Table(name = "espacios_individuales") // Nombre de la tabla en la base de datos
public class EspacioIndividual extends RecursoReservable {

    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera automáticamente
    // el ID
    // private Long id; // El tipo de id es Long

    @Column(name = "piso", nullable = false) // Columna 'piso', no puede ser nula
    private int piso;

    @Column(name = "numero_asiento", nullable = false) // Columna 'numero_asiento', no puede ser nula
    private int numeroAsiento;

    // Constructor vacío (requerido por JPA)
    public EspacioIndividual() {
    }

    // Constructor con parámetros (sin tiempoReservado)
    public EspacioIndividual(int piso, int numeroAsiento) {
        this.piso = piso;
        this.numeroAsiento = numeroAsiento;
    }

    // Getters y Setters
    // public Long getId() {
    // return id;
    // }

    // public void setId(Long id) {
    // this.id = id;
    // }

    public int getPiso() {
        return piso;
    }

    public void setPiso(int piso) {
        if (piso <= 0)
            throw new IllegalArgumentException("El piso debe ser mayor a 0");
        this.piso = piso;
    }

    public int getNumeroAsiento() {
        return numeroAsiento;
    }

    public void setNumeroAsiento(int numeroAsiento) {
        if (numeroAsiento <= 0)
            throw new IllegalArgumentException("El número de asiento debe ser mayor a 0");
        this.numeroAsiento = numeroAsiento;
    }

    // Método toString
    @Override
    public String toString() {
        return "Piso: " + piso + ", Asiento: " + numeroAsiento;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null || getClass() != obj.getClass())
            return false;

        EspacioIndividual espacio = (EspacioIndividual) obj;

        return getId() != null && getId().equals(espacio.getId()) &&
                piso == espacio.piso &&
                numeroAsiento == espacio.numeroAsiento;
    }

    @Override
    public int hashCode() {
        int result = getId() != null ? getId().hashCode() : 0;
        result = 31 * result + Integer.hashCode(piso);
        result = 31 * result + Integer.hashCode(numeroAsiento);
        return result;
    }

}
