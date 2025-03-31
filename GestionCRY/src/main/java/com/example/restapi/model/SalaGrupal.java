package com.example.restapi.model;

public class SalaGrupal {
    private int piso;
    private int numeroSala;
    private int numeroPersonas;

    public SalaGrupal(int piso, int numeroSala, int numeroPersonas) {
        this.piso = piso;
        this.numeroSala = numeroSala;
        this.numeroPersonas = numeroPersonas;
    }

    public int getPiso() {
        return piso;
    }

    public int getNumeroSala() {
        return numeroSala;
    }

    public int getNumeroPersonas() {
        return numeroPersonas;
    }

    public void setPiso(int piso) {
        if (piso <= 0) throw new IllegalArgumentException("El piso debe ser mayor a 0");
        this.piso = piso;
    }

    public void setNumeroSala(int numeroSala) {
        if (numeroSala <= 0) throw new IllegalArgumentException("El número de sala debe ser mayor a 0");
        this.numeroSala = numeroSala;
    }

    public void setNumeroPersonas(int numeroPersonas) {
        if (numeroPersonas <= 0) throw new IllegalArgumentException("El número de personas debe ser mayor a 0");
        this.numeroPersonas = numeroPersonas;
    }

    @Override
    public String toString() {
        return "Piso: " + piso + ", Sala: " + numeroSala + ", Personas: " + numeroPersonas;
    }
}
