package com.example.restapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.restapi.model.Ordenador;
import com.example.restapi.service.ServicioOrdenadores;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

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

    // Obtener un ordenador por número de serie
    @Operation(summary = "Obtener ordenador por número de serie", description = "Devuelve un ordenador específico por su número de serie")
    @GetMapping("/numeroSerie/{numeroSerie}")
    public ResponseEntity<Ordenador> getOrdenadorByNumeroSerie(@PathVariable String numeroSerie) {
        Optional<Ordenador> ordenador = servicioOrdenadores.findByNumeroSerie(numeroSerie);
        return ordenador.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    // Agregar un nuevo ordenador
    @Operation(summary = "Agregar nuevo ordenador", description = "Crea un nuevo ordenador en el sistema")
    @PostMapping("/add")
    public ResponseEntity<String> addOrdenador(
            @RequestParam("marca") String marca,
            @RequestParam("modelo") String modelo,
            @RequestParam("numeroSerie") String numeroSerie,
            @RequestParam("disponible") boolean disponible) {
        try {
            Ordenador ordenador = new Ordenador(marca, modelo, numeroSerie, disponible);
            servicioOrdenadores.addOrdenador(ordenador);
            return new ResponseEntity<>("Ordenador agregado correctamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al agregar el ordenador: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Actualizar un ordenador
    @Operation(summary = "Actualizar ordenador", description = "Actualiza un ordenador existente en el sistema")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateOrdenador(@PathVariable Long id, @RequestBody Ordenador ordenador) {
        try {
            servicioOrdenadores.updateOrdenador(id, ordenador);
            return new ResponseEntity<>("Ordenador actualizado correctamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar el ordenador: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    // Eliminar un ordenador
    @Operation(summary = "Eliminar ordenador", description = "Elimina un ordenador existente del sistema")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOrdenador(@PathVariable Long id) {
        try {
            servicioOrdenadores.deleteOrdenador(id);
            return new ResponseEntity<>("Ordenador eliminado correctamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar el ordenador: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}