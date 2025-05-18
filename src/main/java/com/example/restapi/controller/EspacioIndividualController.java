package com.example.restapi.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.example.restapi.model.EspacioIndividual;
import com.example.restapi.repository.RepositorioEspacioIndividual;
import com.example.restapi.service.ServicioEspacioIndividual;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/Espacios-Individuales")
@Tag(name = "Espacios Individuales", description = "Operaciones relacionadas con la gestión de espacios individuales")
public class EspacioIndividualController {

    private final ServicioEspacioIndividual servicioEspacios;

    @Autowired
    public EspacioIndividualController(ServicioEspacioIndividual servicioEspacios) {
        this.servicioEspacios = servicioEspacios;
    }

    @Operation(summary = "Obtener todos los espacios", description = "Devuelve una lista de todos los espacios individuales disponibles")
    @GetMapping("/all")
    public List<EspacioIndividual> getAllEspacios() {
        return servicioEspacios.findAll();
    }

    @Operation(summary = "Obtener espacio por piso y número de asiento", description = "Devuelve un espacio específico por su piso y número de asiento")
    @GetMapping("/{piso}/{numeroAsiento}")
    public ResponseEntity<EspacioIndividual> getEspacio(@PathVariable int piso, @PathVariable int numeroAsiento) {
        Optional<EspacioIndividual> espacio = servicioEspacios.findByPisoAndAsiento(piso, numeroAsiento);
        return espacio.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Agregar nuevo espacio", description = "Crea un nuevo espacio individual en el sistema")
    @PostMapping("/add")
    public ResponseEntity<String> addEspacio(
            @RequestParam("piso") int piso,
            @RequestParam("numeroAsiento") int numeroAsiento) {
        try {
            EspacioIndividual espacio = new EspacioIndividual();
            espacio.setPiso(piso);
            espacio.setNumeroAsiento(numeroAsiento);

            servicioEspacios.addEspacio(espacio);
            return new ResponseEntity<>("Espacio agregado correctamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al agregar el espacio: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Actualizar espacio", description = "Modifica un espacio individual existente")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateEspacio(
            @PathVariable Long id,
            @RequestParam("piso") int piso,
            @RequestParam("numeroAsiento") int numeroAsiento) {
        try {
            EspacioIndividual espacio = new EspacioIndividual();
            espacio.setId(id);
            espacio.setPiso(piso);
            espacio.setNumeroAsiento(numeroAsiento);

            servicioEspacios.updateEspacio(espacio);
            return new ResponseEntity<>("Espacio actualizado correctamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar el espacio: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteEspacio(@PathVariable Long id) {
        try {
            servicioEspacios.deleteEspacio(id);
            return new ResponseEntity<>("Espacio eliminado correctamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar el espacio: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
