package com.example.restapi.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

/**
 * @class Usuario
 * @brief Representa a un usuario del sistema.
 *
 * Esta clase contiene los datos básicos de un usuario como su ID, nombre,
 * correo electrónico y contraseña. Está mapeada a la tabla "usuarios"
 * en la base de datos.
 */
@Entity
@Table(name = "usuarios") 
public class Usuario {

    /**
     * Identificador único del usuario.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    /**
     * Nombre del usuario.
     */
    private String nombre;

    /**
     * Correo electrónico del usuario.
     */
    @Column(unique = true)
    private String correo;

    /**
     * Contraseña del usuario.
     */
    private String contrasena;

    /**
     * @brief Constructor por defecto.
     */
    public Usuario() {
    }

    /**
     * @brief Constructor con todos los campos.
     * @param id ID del usuario.
     * @param nombre Nombre del usuario.
     * @param correo Correo electrónico del usuario.
     * @param contrasena Contraseña del usuario.
     */
    public Usuario(int id, String nombre, String correo, String contrasena) {
        this.id = id;
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
    }

    /**
     * @brief Constructor sin ID (para nuevos usuarios).
     * @param nombre Nombre del usuario.
     * @param correo Correo electrónico del usuario.
     * @param contrasena Contraseña del usuario.
     */
    public Usuario(String nombre, String correo, String contrasena) {
        this.nombre = nombre;
        this.correo = correo;
        this.contrasena = contrasena;
    }

    /**
     * @brief Obtiene el ID del usuario.
     * @return ID del usuario.
     */
    public int getId() {
        return id;
    }

    /**
     * @brief Establece el ID del usuario.
     * @param id Nuevo ID a establecer.
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @brief Obtiene el nombre del usuario.
     * @return Nombre del usuario.
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @brief Establece el nombre del usuario.
     * @param nombre Nuevo nombre a establecer.
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @brief Obtiene el correo electrónico del usuario.
     * @return Correo electrónico.
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @brief Establece el correo electrónico del usuario.
     * @param correo Nuevo correo a establecer.
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @brief Obtiene la contraseña del usuario.
     * @return Contraseña del usuario.
     */
    public String getContrasena() {
        return contrasena;
    }

    /**
     * @brief Establece la contraseña del usuario.
     * @param contrasena Nueva contraseña a establecer.
     */
    public void setContrasena(String contrasena) {
        this.contrasena = contrasena;
    }

    /**
     * @brief Representación en forma de cadena del objeto Usuario.
     * @return Cadena representando al usuario.
     */
    @Override
    public String toString() {
        return "Usuario{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", correo='" + correo + '\'' +
                ", contrasena='" + contrasena + '\'' +
                '}';
    }

    /**
     * @brief Compara si dos objetos Usuario son iguales.
     * @param obj Objeto a comparar.
     * @return true si son iguales, false en caso contrario.
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) return true;
        if (obj == null || getClass() != obj.getClass()) return false;

        Usuario usuario = (Usuario) obj;

        return id == usuario.id &&
            (nombre == null ? usuario.nombre == null : nombre.equals(usuario.nombre)) &&
            (correo == null ? usuario.correo == null : correo.equals(usuario.correo)) &&
            (contrasena == null ? usuario.contrasena == null : contrasena.equals(usuario.contrasena));
    }

    /**
     * @brief Genera un valor hash para el objeto Usuario.
     * @return Código hash del objeto.
     */
    @Override
    public int hashCode() {
        int result = Integer.hashCode(id);
        result = 31 * result + (nombre != null ? nombre.hashCode() : 0);
        result = 31 * result + (correo != null ? correo.hashCode() : 0);
        result = 31 * result + (contrasena != null ? contrasena.hashCode() : 0);
        return result;
    }
}
