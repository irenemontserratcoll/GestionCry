package com.example.restapi.controller;

import com.example.restapi.model.SalaGrupal;
import com.example.restapi.service.ServicioSalaGrupo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/sala-grupal")
public class SalaGrupalController {

    private final ServicioSalaGrupo servicioSalaGrupo;

    @Autowired
    public SalaGrupalController(ServicioSalaGrupo servicioSalaGrupal) {
        this.servicioSalaGrupo = servicioSalaGrupal;
    }

    // Obtener todas las salas grupales
    @GetMapping
    public List<SalaGrupal> getAllSalas() {
        return servicioSalaGrupo.findAll();
    }

    // Obtener una sala grupal por piso y número de sala
    @GetMapping("/{piso}/{numeroSala}")
    public ResponseEntity<SalaGrupal> getSalaByPisoAndNumeroSala(
            @PathVariable int piso, @PathVariable int numeroSala) {
        Optional<SalaGrupal> salaGrupal = servicioSalaGrupo.findByPisoAndNumeroSala(piso, numeroSala);
        return salaGrupal.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    // Crear una nueva sala grupal
    @PostMapping
    public ResponseEntity<String> createSala(@RequestBody SalaGrupal salaGrupal) {
        servicioSalaGrupo.addSala(salaGrupal);
        return ResponseEntity.ok("Sala creada exitosamente");
    }

    // Actualizar una sala grupal existente
    @PutMapping("/{piso}/{numeroSala}")
    public ResponseEntity<String> updateSala(@PathVariable int piso, @PathVariable int numeroSala,
                                             @RequestBody SalaGrupal salaGrupal) {
        if (piso != salaGrupal.getPiso() || numeroSala != salaGrupal.getNumeroSala()) {
            return ResponseEntity.badRequest().body("El piso y número de sala no coinciden");
        }
        try {
            servicioSalaGrupo.updateSala(salaGrupal);
            return ResponseEntity.ok("Sala actualizada exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(404).body(e.getMessage());
        }
    }

    // Eliminar una sala grupal por piso y número de sala
    @DeleteMapping("/{piso}/{numeroSala}")
    public ResponseEntity<String> deleteSala(@PathVariable int piso, @PathVariable int numeroSala) {
        servicioSalaGrupo.deleteSala(piso, numeroSala);
        return ResponseEntity.ok("Sala eliminada exitosamente");
    }
}
