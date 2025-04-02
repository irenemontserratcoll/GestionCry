package com.example.restapi.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

import com.example.restapi.model.Libro;

@Repository
public interface RepositorioLibros extends JpaRepository<Libro, Integer> {
    
    Optional<Libro> findById(Long id);
    Optional<Libro> findByTitulo(String titulo);
    Optional<Libro> findByIsbn(String isbn);
    Optional<Libro> findByAutor(String autor);
    Optional<Libro> deleteById(Long id);
    List<Libro> findAll(); 
}