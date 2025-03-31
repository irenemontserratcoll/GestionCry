
package com.example.restapi.model;
public class EspacioIndividual {
    private int piso;
    private int numeroAsiento;
    private String tiempoReservado;

    public EspacioIndividual(int piso, int numeroAsiento) {
        this.piso = piso;
        this.numeroAsiento = numeroAsiento;
    }

    public int getPiso() {
        return piso;
    }

    public int getNumeroAsiento() {
        return numeroAsiento;
    }

    public String getTiempoReservado() {
        return tiempoReservado;
    }

    public void setPiso(int piso) {
        if (piso <= 0) throw new IllegalArgumentException("El piso debe ser mayor a 0");
        this.piso = piso;
    }

    public void setNumeroAsiento(int numeroAsiento) {
        if (numeroAsiento <= 0) throw new IllegalArgumentException("El número de asiento debe ser mayor a 0");
        this.numeroAsiento = numeroAsiento;
    }

    public void setTiempoReservado(String tiempoReservado) {
        if (tiempoReservado == null || tiempoReservado.isEmpty())
            throw new IllegalArgumentException("El tiempo reservado no puede estar vacío");
        this.tiempoReservado = tiempoReservado;
    }

    @Override
    public String toString() {
        return "Piso: " + piso + ", Asiento: " + numeroAsiento + (tiempoReservado != null ? ", Tiempo reservado: " + tiempoReservado : "");
    }
}
