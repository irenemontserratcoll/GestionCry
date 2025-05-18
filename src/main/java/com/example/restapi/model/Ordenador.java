package com.example.restapi.model;

import java.util.List;
import jakarta.persistence.*;

@Entity
@Table(name = "ordenadores")
@PrimaryKeyJoinColumn(name = "id")
@DiscriminatorValue("ORDENADORES")
public class Ordenador extends RecursoReservable {
    private String marca;
    private String modelo;
    private String numeroSerie;
    private boolean disponible;

    // Constructor sin argumentos
    public Ordenador() {
    }

    // Constructor con todos los atributos
    public Ordenador(String marca, String modelo, String numeroSerie, boolean disponible) {
        this.marca = marca;
        this.modelo = modelo;
        this.numeroSerie = numeroSerie;
        this.disponible = disponible;
    }

    // Getters y Setters
    public String getMarca() {
        return marca;
    }

    public void setMarca(String marca) {
        this.marca = marca;
    }

    public String getModelo() {
        return modelo;
    }

    public void setModelo(String modelo) {
        this.modelo = modelo;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public static void buscarOrdenador(List<Ordenador> ordenadores, String criterio) {
        boolean encontrado = false;

        for (Ordenador ordenador : ordenadores) {
            if (ordenador.getMarca().equalsIgnoreCase(criterio) ||
                    ordenador.getModelo().equalsIgnoreCase(criterio) ||
                    ordenador.getNumeroSerie().equalsIgnoreCase(criterio) ||
                    String.valueOf(ordenador.isDisponible()).equalsIgnoreCase(criterio)) {
                System.out.println("Ordenador encontrado:");
                System.out.println("Marca: " + ordenador.getMarca());
                System.out.println("Modelo: " + ordenador.getModelo());
                System.out.println("Número de Serie: " + ordenador.getNumeroSerie());
                System.out.println("Disponible: " + ordenador.isDisponible());
                System.out.println("-----------");
                encontrado = true;
            }
        }

        if (!encontrado) {
            System.out.println("No se ha encontrado ningún ordenador con ese criterio.");
        }
    }
}