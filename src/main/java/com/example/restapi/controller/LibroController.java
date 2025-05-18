package com.example.restapi.controller;

import com.example.restapi.model.Libro;
import com.example.restapi.service.ServicioLibros;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/libros")
@Tag(name = "Libros", description = "Operaciones relacionadas con la gestión de libros")
public class LibroController {

    private final ServicioLibros servicioLibros;

    @Autowired
    public LibroController(ServicioLibros servicioLibros) {
        this.servicioLibros = servicioLibros;
    }

    @Operation(summary = "Obtener todos los libros", description = "Devuelve una lista de todos los libros")
    @GetMapping
    public List<Libro> getAllBooks() {
        return servicioLibros.findAll();
    }

    @Operation(summary = "Buscar libro por ID", description = "Devuelve un libro según su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Libro> getLibroById(@PathVariable Long id) {
        Optional<Libro> libro = servicioLibros.findById(id);
        return libro.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Agregar nuevo libro", description = "Crea un nuevo libro en el sistema")
    @PostMapping
    public ResponseEntity<Libro> addLibro(@RequestBody Libro libro) {
        try {
            Libro nuevoLibro = servicioLibros.addLibro(libro);
            return new ResponseEntity<>(nuevoLibro, HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>(null, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Eliminar libro por ID", description = "Elimina un libro específico por su ID")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteLibro(@PathVariable Long id) {
        try {
            servicioLibros.deleteLibro(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Operation(summary = "Actualizar libro por ID", description = "Actualiza un libro existente")
    @PutMapping("/{id}")
    public ResponseEntity<Libro> updateLibro(@PathVariable Long id, @RequestBody Libro libro) {
        try {
            Libro actualizado = servicioLibros.updateLibro(id, libro);
            return ResponseEntity.ok(actualizado);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
}
