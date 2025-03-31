package com.example.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.example.restapi.model.Usuario;
import com.example.restapi.service.ServicioUsuarios;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/usuarios")
@Tag(name = "usuarios", description = "Operaciones relacionadas con la gestión de usuarios")
public class UsuarioController {

    private final ServicioUsuarios servicioUsuarios;

    @Autowired
    public UsuarioController(ServicioUsuarios servicioUsuarios) {
        this.servicioUsuarios = servicioUsuarios;
    }

    // Endpoint to get all users
    @Operation(summary = "Obtener todos los usuarios", description = "Devuelve una lista de todos los usuarios registrados")
    @GetMapping("/all")
    public List<Usuario> getAllUsers() {
        return servicioUsuarios.findAll();
    }

    // Endpoint to get a user by ID
    @Operation(summary = "Obtener usuario por ID", description = "Devuelve un usuario específico por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Usuario> getUserById(@PathVariable Integer id) {
        Optional<Usuario> usuario = servicioUsuarios.findById(id);
        return usuario.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Endpoint to add a new user
    @Operation(summary = "Agregar nuevo usuario", description = "Crea un nuevo usuario en el sistema")
    @PostMapping("/add")
    public ResponseEntity<String> addUser(@RequestBody Usuario usuario) {
        try {
            servicioUsuarios.addUsuario(usuario);
            return new ResponseEntity<>("Usuario agregado correctamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al agregar el usuario: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Endpoint to delete a user by email
    @Operation(summary = "Eliminar usuario por correo", description = "Elimina un usuario específico por su correo")
    @DeleteMapping("/delete/{correo}")
    public ResponseEntity<String> deleteUser(@PathVariable String correo) {
        try {
            servicioUsuarios.deleteUsuario(correo);
            return new ResponseEntity<>("Usuario eliminado correctamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar el usuario: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}