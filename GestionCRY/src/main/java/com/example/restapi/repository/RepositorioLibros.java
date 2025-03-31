package com.example.restapi.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

import com.example.restapi.model.Libro;

@Repository
public interface RepositorioLibros extends JpaRepository<Libro, Long> {
    
    Optional<Libro> findById(Long id);
    Optional<Libro> findByTitle(String titulo);
    Optional<Libro> findByIsbn(String isbn);
    List<Libro> findByAuthor(String autor);
    List<Libro> findAll(); 
}