package com.example.restapi.repository;

import org.springframework.stereotype.Repository;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.List;

import com.example.restapi.model.Usuario; 


@Repository
public interface RepositorioUsuarios extends JpaRepository<Usuario, Integer> { // Changed int to Integer
    Optional<Usuario> findByCorreo(String correo); // Corrected method name to match the field "correo"
    Optional<Usuario> findById(Integer id); // Changed int to Integer
    Optional<Usuario> deleteByCorreo(String correo);
    List<Usuario> findAll(); // Find all users

}