package com.example.restapi.model;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

@Entity
@Table(name = "ordenadores") 
@DiscriminatorValue("ORDENADORES") // Valor del discriminador para este tipo de recurso
public class Ordenador extends RecursoReservable{

   // @Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    //private Long id;
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
    //public Long getId() {
    //    return id;
    //}

    //public void setId(Long id) {
    //    this.id = id;
    //}

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
}