package com.example.restapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@DiscriminatorValue("SALAS_GRUPALES")
@Table(name = "salas_grupales") // Nombre de la tabla en la base de datos
public class SalaGrupal extends RecursoReservable {

    // @Id
    // @GeneratedValue(strategy = GenerationType.IDENTITY) // Genera automáticamente
    // el ID
    // private Long id; // Identificador de tipo Long

    @Column(name = "piso", nullable = false) // Columna 'piso', no puede ser nula
    private int piso;

    @Column(name = "numero_sala", nullable = false) // Columna 'numero_sala', no puede ser nula
    private int numeroSala;

    @Column(name = "numero_personas", nullable = false) // Columna 'numero_personas', no puede ser nula
    private int numeroPersonas;

    // Constructor vacío (requerido por JPA)
    public SalaGrupal() {
    }

    // Constructor con parámetros
    public SalaGrupal(int piso, int numeroSala, int numeroPersonas) {
        this.piso = piso;
        this.numeroSala = numeroSala;
        this.numeroPersonas = numeroPersonas;
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

    public int getNumeroSala() {
        return numeroSala;
    }

    public void setNumeroSala(int numeroSala) {
        if (numeroSala <= 0)
            throw new IllegalArgumentException("El número de sala debe ser mayor a 0");
        this.numeroSala = numeroSala;
    }

    public int getNumeroPersonas() {
        return numeroPersonas;
    }

    public void setNumeroPersonas(int numeroPersonas) {
        if (numeroPersonas <= 0)
            throw new IllegalArgumentException("El número de personas debe ser mayor a 0");
        this.numeroPersonas = numeroPersonas;
    }

    // Método toString
    @Override
    public String toString() {
        return "Piso: " + piso + ", Sala: " + numeroSala + ", Personas: " + numeroPersonas;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        SalaGrupal sala = (SalaGrupal) obj;
        return getId() != null && getId().equals(sala.getId());
    }

    @Override
    public int hashCode() {
        return getId() != null ? getId().hashCode() : 0;
    }

}
