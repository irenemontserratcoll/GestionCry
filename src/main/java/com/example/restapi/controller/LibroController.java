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
    @GetMapping("/all")
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
    @PostMapping("/add")
    public ResponseEntity<String> addLibro(
            @RequestParam("titulo") String titulo,
            @RequestParam("autor") String autor,
            @RequestParam("isbn") String isbn) {
        try {
            Libro libro = new Libro(titulo, autor, isbn);
            servicioLibros.addLibro(libro);
            return new ResponseEntity<>("Libro agregado correctamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al agregar el libro: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Eliminar libro por ID", description = "Elimina un libro específico por su ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLibro(@PathVariable Long id) {
        try {
            servicioLibros.deleteLibro(id);
            return new ResponseEntity<>("Libro eliminado correctamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar el libro: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Actualizar libro por ID", description = "Actualiza un libro existente")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateLibro(
            @PathVariable Long id,
            @RequestParam("titulo") String titulo,
            @RequestParam("autor") String autor,
            @RequestParam("isbn") String isbn) {
        try {
            Libro libro = new Libro(titulo, autor, isbn);
            servicioLibros.updateLibro(id, libro);
            return new ResponseEntity<>("Libro actualizado correctamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar el libro: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
