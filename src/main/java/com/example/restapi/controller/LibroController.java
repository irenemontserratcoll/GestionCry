package com.example.restapi.controller;

import com.example.restapi.model.Libro;
import com.example.restapi.service.ServicioLibros;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import jakarta.validation.Valid;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/libros")
public class LibroController {

    private final ServicioLibros servicioLibros;

    @Autowired
    public LibroController(ServicioLibros servicioLibros) {
        this.servicioLibros = servicioLibros;
    }

    // Obtener todos los libros
    @GetMapping("/all")
    public ResponseEntity<List<Libro>> getAllLibros() {
        List<Libro> libros = servicioLibros.findAll();
        return new ResponseEntity<>(libros, HttpStatus.OK);
    }

    // Obtener un libro por ID
    @GetMapping("/{id}")
    public ResponseEntity<Libro> getLibroById(@PathVariable Long id) {
        Optional<Libro> libro = servicioLibros.findById(id);
        return libro.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Obtener un libro por título
    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<Libro> getLibroByTitulo(@PathVariable String titulo) {
        Optional<Libro> libro = servicioLibros.findByTitulo(titulo);
        return libro.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Obtener un libro por autor
    @GetMapping("/autor/{autor}")
    public ResponseEntity<Libro> getLibroByAutor(@PathVariable String autor) {
        Optional<Libro> libro = servicioLibros.findByAutor(autor);
        return libro.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Obtener un libro por ISBN
    @GetMapping("/isbn/{isbn}")
    public ResponseEntity<Libro> getLibroByIsbn(@PathVariable String isbn) {
        Optional<Libro> libro = servicioLibros.findByIsbn(isbn);
        return libro.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    // Agregar un nuevo libro
    @PostMapping("/add")
    public ResponseEntity<String> addLibro(@Valid @RequestBody Libro libro) {
        try {
            servicioLibros.addLibro(libro);
            return new ResponseEntity<>("Libro agregado correctamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al agregar el libro: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar un libro
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateLibro(@PathVariable Long id, @Valid @RequestBody Libro libro) {
        try {
            servicioLibros.updateLibro(id, libro);
            return new ResponseEntity<>("Libro actualizado correctamente", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar el libro: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar un libro
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteLibro(@PathVariable Long id) {
        try {
            servicioLibros.deleteLibro(id);
            return new ResponseEntity<>("Libro eliminado correctamente", HttpStatus.OK);
        } catch (RuntimeException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar el libro: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
