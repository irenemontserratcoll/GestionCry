package com.example.restapi.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import com.example.restapi.model.Ordenador;
import com.example.restapi.service.ServicioOrdenadores;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ordenadores")
@Tag(name = "ordenadores", description = "Operaciones relacionadas con la gestión de ordenadores")
public class OrdenadorController {

    private final ServicioOrdenadores servicioOrdenadores;

    @Autowired
    public OrdenadorController(ServicioOrdenadores servicioOrdenadores) {
        this.servicioOrdenadores = servicioOrdenadores;
    }

    // Obtener todos los ordenadores
    @Operation(summary = "Obtener todos los ordenadores", description = "Devuelve una lista de todos los ordenadores disponibles")
    @GetMapping("/all")
    public List<Ordenador> getAllOrdenadores() {
        return servicioOrdenadores.findAll();
    }

    // Obtener un ordenador por ID
    @Operation(summary = "Obtener ordenador por ID", description = "Devuelve un ordenador específico por su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Ordenador> getOrdenadorById(@PathVariable Long id) {
        Optional<Ordenador> ordenador = servicioOrdenadores.findById(id);
        return ordenador.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Agregar un nuevo ordenador
    @Operation(summary = "Agregar nuevo ordenador", description = "Crea un nuevo ordenador en el sistema")
    @PostMapping("/add")
    public ResponseEntity<String> addOrdenador(@RequestBody Ordenador ordenador) {
        try {
            servicioOrdenadores.addOrdenador(ordenador);
            return new ResponseEntity<>("Ordenador agregado correctamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al agregar el ordenador: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Obtener ordenadores por disponibilidad
    @Operation(summary = "Obtener ordenadores disponibles", description = "Devuelve una lista de ordenadores según su disponibilidad")
    @GetMapping("/disponibles/{disponible}")
    public List<Ordenador> getOrdenadoresDisponibles(@PathVariable boolean disponible) {
        return servicioOrdenadores.findByDisponible(disponible);
    }
}