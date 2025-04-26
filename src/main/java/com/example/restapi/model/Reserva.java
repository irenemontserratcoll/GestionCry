package com.example.restapi.model;

import java.util.Date;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "reservas")
public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nombreCliente;
    private String emailCliente;
    private Date fechaReserva;
    private String horaReserva;
    private int numPersonas;

    @ManyToOne
    @JoinColumn(name = "libro_id")
    private Libro libro;

    @ManyToOne
    @JoinColumn(name = "ordenador_id")
    private Ordenador ordenador;

    @ManyToOne
    @JoinColumn(name = "sala_grupal_id")
    private SalaGrupal salaGrupal;

    @ManyToOne
    @JoinColumn(name = "espacio_individual_id")
    private EspacioIndividual espacioIndividual;

    // Campos auxiliares para recibir IDs
    @Transient
    private Long libroId;

    @Transient
    private Long ordenadorId;

    @Transient
    private Long salaGrupalId;

    @Transient
    private Long espacioIndividualId;

    // Getters y Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }

    public String getNombreCliente() { return nombreCliente; }
    public void setNombreCliente(String nombreCliente) { this.nombreCliente = nombreCliente; }

    public String getEmailCliente() { return emailCliente; }
    public void setEmailCliente(String emailCliente) { this.emailCliente = emailCliente; }

    public Date getFechaReserva() { return fechaReserva; }
    public void setFechaReserva(Date fechaReserva) { this.fechaReserva = fechaReserva; }

    public String getHoraReserva() { return horaReserva; }
    public void setHoraReserva(String horaReserva) { this.horaReserva = horaReserva; }

    public int getNumPersonas() { return numPersonas; }
    public void setNumPersonas(int numPersonas) { this.numPersonas = numPersonas; }

    public Libro getLibro() { return libro; }
    public void setLibro(Libro libro) { this.libro = libro; }

    public Ordenador getOrdenador() { return ordenador; }
    public void setOrdenador(Ordenador ordenador) { this.ordenador = ordenador; }

    public SalaGrupal getSalaGrupal() { return salaGrupal; }
    public void setSalaGrupal(SalaGrupal salaGrupal) { this.salaGrupal = salaGrupal; }

    public EspacioIndividual getEspacioIndividual() { return espacioIndividual; }
    public void setEspacioIndividual(EspacioIndividual espacioIndividual) { this.espacioIndividual = espacioIndividual; }

    // Auxiliares
    public Long getLibroId() { return libroId; }
    public void setLibroId(Long libroId) { this.libroId = libroId; }

    public Long getOrdenadorId() { return ordenadorId; }
    public void setOrdenadorId(Long ordenadorId) { this.ordenadorId = ordenadorId; }

    public Long getSalaGrupalId() { return salaGrupalId; }
    public void setSalaGrupalId(Long salaGrupalId) { this.salaGrupalId = salaGrupalId; }

    public Long getEspacioIndividualId() { return espacioIndividualId; }
    public void setEspacioIndividualId(Long espacioIndividualId) { this.espacioIndividualId = espacioIndividualId; }

    @Override
    public String toString() {
        return "Reserva{" +
                "id=" + id +
                ", nombreCliente='" + nombreCliente + '\'' +
                ", emailCliente='" + emailCliente + '\'' +
                ", fechaReserva=" + fechaReserva +
                ", horaReserva='" + horaReserva + '\'' +
                ", numPersonas=" + numPersonas +
                ", libro=" + (libro != null ? libro.toString() : "N/A") +
                ", ordenador=" + (ordenador != null ? ordenador.toString() : "N/A") +
                ", salaGrupal=" + (salaGrupal != null ? salaGrupal.toString() : "N/A") +
                ", espacioIndividual=" + (espacioIndividual != null ? espacioIndividual.toString() : "N/A") +
                ", libroId=" + libroId +
                ", ordenadorId=" + ordenadorId +
                ", salaGrupalId=" + salaGrupalId +
                ", espacioIndividualId=" + espacioIndividualId +
                '}';
    }
}
