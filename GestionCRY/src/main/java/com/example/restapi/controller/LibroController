package com.example.restapi.controller;

import com.example.restapi.model.Libro;
import com.example.restapi.service.ServicioLibros;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/libros")
@Tag(name = "libros", description = "Operaciones relacionadas con la gestión de libros")
public class LibroController {

    private final ServicioLibros servicioLibros;

    @Autowired
    public LibroController(ServicioLibros servicioLibros) {
        this.servicioLibros = servicioLibros;

    }
    // Obtener todos los libros
    @Operation(summary = "Obtener todos los libros", description = "Devuelve una lista de todos los libros disponibles")   
    @GetMapping("/all")
    public List<Libro> getAllLibros() {
        return servicioLibros.findAll();
    }
    // Obtener un libro por ID
    @Operation(summary = "Obtener libro por ID", description = "Devuelve un libro específico por su ID")   
    @GetMapping("/{id}")
    public ResponseEntity<Libro> getLibroById(@PathVariable Long id) {
        Optional<Libro> libro = servicioLibros.findById(id);
        return libro.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    //Obtener un libro por título
    @Operation(summary = "Obtener libro por título", description = "Devuelve un libro específico por su título")
    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<Libro> getLibroByTitulo(@PathVariable String titulo) {
        Optional<Libro> libro = servicioLibros.findByTitulo(titulo);
        return libro.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    // Obtener un libro por autor
    @Operation(summary = "Obtener libro por autor", description = "Devuelve un libro específico por su autor")
    @GetMapping("/autor/{autor}")
    public ResponseEntity<Libro> getLibroByAutor(@PathVariable String autor) {
        Optional<Libro> libro = servicioLibros.findByAutor(autor);
        return libro.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }
    // Obtener un libro por ISBN
    @Operation(summary = "Obtener libro por ISBN", description = "Devuelve un libro específico por su ISBN")
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Libro> getLibroByIsbn(@PathVariable String isbn) {
        Optional<Libro> libro = servicioLibros.findByIsbn(isbn);
        return libro.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Agregar un nuevo libro
    @Operation(summary = "Agregar nuevo libro", description = "Crea un nuevo libro en el sistema")
    @PostMapping("/add")
    public ResponseEntity<String> addLibro(@RequestBody Libro libro) {
        try {
            servicioLibros.addLibro(libro);
            return new ResponseEntity<>("Libro agregado correctamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al agregar el libro: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // Actualizar un libro
    @Operation(summary = "Actualizar libro", description = "Actualiza un libro existente en el sistema")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateLibro(@PathVariable Long id, @RequestBody Libro libro) {
        try {
            servicioLibros.updateLibro(id, libro);
            return new ResponseEntity<>("Libro actualizado correctamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar el libro: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    // Eliminar un libro  
    @Operation(summary = "Eliminar libro", description = "Elimina un libro existente del sistema")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLibro(@PathVariable Long id) {
        try {
            servicioLibros.deleteLibro(id);
            return new ResponseEntity<>("Libro eliminado correctamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar el libro: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
}
