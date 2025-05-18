package com.example.restapi.service;

import com.example.restapi.model.Libro;
import com.example.restapi.repository.RepositorioLibros;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import org.springframework.transaction.annotation.Transactional;

@Service
public class ServicioLibros {

    private final RepositorioLibros repositorioLibros;

    @Autowired
    public ServicioLibros(RepositorioLibros repositorioLibros) {
        this.repositorioLibros = repositorioLibros;
    }

    // Obtener todos los libros
    public List<Libro> findAll() {
        return repositorioLibros.findAll();
    }

    // Obtener un libro por ID
    public Optional<Libro> findById(Long id) {
        return repositorioLibros.findById(id);
    }

    // Obtener un libro por título
    public Optional<Libro> findByTitulo(String titulo) {
        return repositorioLibros.findByTitulo(titulo);
    }

    // Obtener un libro por autor
    public Optional<Libro> findByAutor(String autor) {
        return repositorioLibros.findByAutor(autor);
    }

    // Obtener un libro por ISBN
    public Optional<Libro> findByIsbn(String isbn) {
        return repositorioLibros.findByIsbn(isbn);
    }

    @Transactional
    public Libro addLibro(Libro libro) {
        libro.setId(null); // Añade esta línea para asegurarte que el libro es nuevo
        return repositorioLibros.save(libro);
    }

    @Transactional
    public Libro updateLibro(Long id, Libro libro) {
        return repositorioLibros.findById(id).map(libroDB -> {
            libroDB.setTitulo(libro.getTitulo());
            libroDB.setAutor(libro.getAutor());
            libroDB.setIsbn(libro.getIsbn());
            return repositorioLibros.save(libroDB);
        }).orElseThrow(() -> new RuntimeException("Libro no encontrado con ID: " + id));
    }

    @Transactional
    public void deleteLibro(Long id) {
        if (repositorioLibros.existsById(id)) {
            repositorioLibros.deleteById(id);
        } else {
            throw new RuntimeException("Libro no encontrado con ID: " + id);
        }
    }
}
