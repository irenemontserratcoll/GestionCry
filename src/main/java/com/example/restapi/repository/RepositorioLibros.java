package com.example.restapi.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.restapi.model.Libro;
public interface RepositorioLibros extends JpaRepository<Libro, Long> {
    Optional<Libro> findById(Long id);
    Optional<Libro> findByTitulo(String titulo);
    Optional<Libro> findByIsbn(String isbn);
    Optional<Libro> findByAutor(String autor);
    // No es necesario definir deleteById ya que ya está provisto por JpaRepository
    List<Libro> findAll(); 
}
