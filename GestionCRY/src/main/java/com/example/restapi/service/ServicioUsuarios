package com.example.restapi.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

import com.example.restapi.model.Usuario;
import com.example.restapi.repository.RepositorioUsuarios;

@Service
public class ServicioUsuarios {

    private final RepositorioUsuarios repositorioUsuarios;

    @Autowired
    public ServicioUsuarios(RepositorioUsuarios repositorioUsuarios) {
        this.repositorioUsuarios = repositorioUsuarios;
    }

    // Method to find a user by email
    public Optional<Usuario> findByCorreo(String correo) {
        return repositorioUsuarios.findByCorreo(correo);
    }

    // Method to find a user by ID
    public Optional<Usuario> findById(Integer id) {
        return repositorioUsuarios.findById(id);
    }

    // Method to get all users
    public List<Usuario> findAll() {
        return repositorioUsuarios.findAll();
    }

    // Method to add a new user
    public Usuario addUsuario(Usuario usuario) {
        return repositorioUsuarios.save(usuario);
    }

    // Method to delete an existing user
    public void deleteUsuario(String correo) {
        repositorioUsuarios.deleteByCorreo(correo);
    }
}