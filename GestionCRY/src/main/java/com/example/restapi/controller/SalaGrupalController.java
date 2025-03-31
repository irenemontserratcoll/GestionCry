package com.example.restapi.controller;

import com.example.restapi.model.SalaGrupal;
import com.example.restapi.service.ServicioSalaGrupal;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/salas")
@Tag(name = "salas grupales", description = "Operaciones relacionadas con la reserva de salas grupales")
public class ReservarSalaGrupalController {

    private final ServicioSalasGrupales servicioSalasGrupales;

    @Autowired
    public ReservarSalaGrupalController(ServicioSalasGrupales servicioSalasGrupales) {
        this.servicioSalasGrupales = servicioSalasGrupales;
    }

    @Operation(summary = "Obtener todas las salas", description = "Devuelve una lista de todas las salas grupales registradas")
    @GetMapping("/all")
    public List<ReservarSalaGrupal> getAllSalas() {
        return servicioSalasGrupales.findAll();
    }

    @Operation(summary = "Obtener sala por piso y número de sala", description = "Devuelve una sala específica por su piso y número de sala")
    @GetMapping("/{piso}/{numeroSala}")
    public ResponseEntity<ReservarSalaGrupal> getSala(@PathVariable int piso, @PathVariable int numeroSala) {
        Optional<ReservarSalaGrupal> sala = servicioSalasGrupales.findByPisoAndSala(piso, numeroSala);
        return sala.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Reservar una nueva sala", description = "Registra una nueva reserva de sala grupal")
    @PostMapping("/add")
    public ResponseEntity<String> addSala(@RequestBody ReservarSalaGrupal sala) {
        try {
            servicioSalasGrupales.addSala(sala);
            return new ResponseEntity<>("Sala reservada correctamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al reservar la sala: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Actualizar reserva de sala", description = "Modifica una reserva de sala grupal existente")
    @PutMapping("/update")
    public ResponseEntity<String> updateSala(@RequestBody ReservarSalaGrupal sala) {
        try {
            servicioSalasGrupales.updateSala(sala);
            return new ResponseEntity<>("Reserva actualizada correctamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar la reserva: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Eliminar una reserva de sala", description = "Elimina una reserva de sala específica por su piso y número de sala")
    @DeleteMapping("/delete/{piso}/{numeroSala}")
    public ResponseEntity<String> deleteSala(@PathVariable int piso, @PathVariable int numeroSala) {
        try {
            servicioSalasGrupales.deleteSala(piso, numeroSala);
            return new ResponseEntity<>("Reserva eliminada correctamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar la reserva: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}