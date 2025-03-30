package com.example.restapi.service;

import com.example.restapi.model.Libro;
import com.example.restapi.repository.RepositorioLibros;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    //Crear un nuevo libro
    public Libro addLibro(Libro libro) {
        return repositorioLibros.save(libro);
    }
    //Actualizar un libro
    public Libro updateLibro(Long id, Libro libro) {
        Optional<Libro> libroExistente = repositorioLibros.findById(id);
        if (libroExistente.isPresent()) {
            libro.setId(id);
            return repositorioLibros.save(libro);
        } else {
            throw new RuntimeException("Libro no encontrado con ID: " + id);
        }
    }
    //Eliminar un libro
    public void deleteLibro(Long id) {
        repositorioLibros.deleteById(id);
    }
    
    
}