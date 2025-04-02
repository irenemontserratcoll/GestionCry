package com.example.restapi.model;

import jakarta.persistence.*;


@Entity
@Table(name = "usuarios") 
public class Usuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String nombre;
    private String correo;
    private String contrasena;

    // Constructor vacío
    public Usuario() {
    }

    // Constructor con parámetros
    public Usuario(int id, String nombre, String correo, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
    }

    public Usuario(String nombre, String correo, String contrasena) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
    }


    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) { // Corregido el tipo de retorno a void
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getCorreo() {
        return correo;
    }

    public void setCorreo(String correo) {
        this.correo = correo;
    }

    public String getContrasena() { // Corregido el nombre del método (mayúscula inicial)
        return contrasena;
    }

    public void setContrasena(String contrasena) { // Corregido el nombre del método (mayúscula inicial)
        this.contrasena = contrasena;
    }

    // Método toString
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", contrasena='" + contrasena + '\'' +
                '}';
    }

    // Método equals
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Usuario usuario = (Usuario) obj;
        return id == usuario.id &&
               nombre.equals(usuario.nombre) &&
               correo.equals(usuario.correo) &&
               contrasena.equals(usuario.contrasena);
    }

    // Método hashCode
    @Override
    public int hashCode() {
        int result = Integer.hashCode(id);
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (correo != null ? correo.hashCode() : 0);
        result = 31 * result + (contrasena != null ? contrasena.hashCode() : 0);
        return result;
    }
}