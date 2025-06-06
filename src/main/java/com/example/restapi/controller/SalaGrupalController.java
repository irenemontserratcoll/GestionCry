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

import com.example.restapi.model.SalaGrupal;
import com.example.restapi.service.ServicioSalaGrupo;

import io.swagger.v3.oas.annotations.Operation;

@RestController
@RequestMapping("/api/sala-grupal")
public class SalaGrupalController {

    private final ServicioSalaGrupo servicioSalaGrupo;

    @Autowired
    public SalaGrupalController(ServicioSalaGrupo servicioSalaGrupal) {
        this.servicioSalaGrupo = servicioSalaGrupal;
    }

    // Obtener todas las salas grupales
    @Operation(summary = "Obtener todas las salas grupales", description = "Devuelve una lista de todas las salas grupales disponibles")
    @GetMapping("/all")
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

    @DeleteMapping("/{piso}/{numeroSala}")
    public ResponseEntity<String> deleteSala(@PathVariable int piso, @PathVariable int numeroSala) {
        try {
            servicioSalaGrupo.deleteSala(piso, numeroSala);
            return ResponseEntity.ok("Sala eliminada exitosamente");
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Error: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Error inesperado: " + e.getMessage());
        }
    }

    @PostMapping("/delete-sala-grupal")
    public ResponseEntity<String> deleteSalaGrupal(@RequestParam("piso") int piso,
            @RequestParam("numeroSala") int numeroSala) {
        try {
            servicioSalaGrupo.deleteSala(piso, numeroSala); // Llama a la lógica del servicio
            return ResponseEntity.ok("Sala grupal eliminada correctamente.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al eliminar la sala grupal: " + e.getMessage());
        }
    }

    @PutMapping("/update-sala-grupal/{piso}/{numeroSala}")
    public ResponseEntity<String> updateSalaGrupal(@PathVariable int piso, @PathVariable int numeroSala,
            @RequestBody SalaGrupal salaGrupal) {
        try {
            if (piso != salaGrupal.getPiso() || numeroSala != salaGrupal.getNumeroSala()) {
                return ResponseEntity.badRequest().body("El piso y el número de sala no coinciden.");
            }
            servicioSalaGrupo.updateSala(salaGrupal);
            return ResponseEntity.ok("Sala grupal actualizada exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al actualizar la sala grupal: " + e.getMessage());
        }
    }

    @GetMapping("/all-salas-grupales")
    public ResponseEntity<List<SalaGrupal>> getAllSalasGrupales() {
        try {
            List<SalaGrupal> salas = servicioSalaGrupo.findAll(); // Obtiene todas las salas
            return ResponseEntity.ok(salas);
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body(null);
        }

    }

    @Operation(summary = "Agregar nueva sala grupal", description = "Crea una nueva sala grupal en el sistema")
    @PostMapping("/add")
    public ResponseEntity<String> addSalaGrupal(
            @RequestParam("piso") int piso,
            @RequestParam("numeroSala") int numeroSala,
            @RequestParam("numeroPersonas") int numeroPersonas) {
        try {
            SalaGrupal sala = new SalaGrupal();
            sala.setPiso(piso);
            sala.setNumeroSala(numeroSala);
            sala.setNumeroPersonas(numeroPersonas);

            servicioSalaGrupo.addSala(sala);
            return new ResponseEntity<>("Sala grupal agregada correctamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al agregar la sala grupal: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateSalaGrupalById(@PathVariable Long id, @RequestBody SalaGrupal salaGrupal) {
        try {
            Optional<SalaGrupal> existingSala = servicioSalaGrupo.findById(id);
            if (existingSala.isEmpty()) {
                return ResponseEntity.status(404).body("La sala no existe.");
            }

            SalaGrupal salaToUpdate = existingSala.get();
            salaToUpdate.setPiso(salaGrupal.getPiso());
            salaToUpdate.setNumeroSala(salaGrupal.getNumeroSala());
            salaToUpdate.setNumeroPersonas(salaGrupal.getNumeroPersonas());

            servicioSalaGrupo.updateSala(salaToUpdate);
            return ResponseEntity.ok("Sala grupal actualizada exitosamente.");
        } catch (Exception e) {
            e.printStackTrace();
            return ResponseEntity.status(500).body("Error al actualizar la sala grupal: " + e.getMessage());
        }
    }

}