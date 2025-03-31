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
import org.springframework.web.bind.annotation.RestController;

import com.example.restapi.model.EspacioIndividual;
import com.example.restapi.service.ServicioEspacioIndividual;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

@RestController
@RequestMapping("/api/espacios")
@Tag(name = "espacios", description = "Operaciones relacionadas con la gestión de espacios individuales")
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
    public ResponseEntity<String> addEspacio(@RequestBody EspacioIndividual espacio) {
        try {
            servicioEspacios.addEspacio(espacio);
            return new ResponseEntity<>("Espacio agregado correctamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al agregar el espacio: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Actualizar espacio", description = "Modifica un espacio individual existente")
    @PutMapping("/update")
    public ResponseEntity<String> updateEspacio(@RequestBody EspacioIndividual espacio) {
        try {
            servicioEspacios.updateEspacio(espacio);
            return new ResponseEntity<>("Espacio actualizado correctamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar el espacio: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Eliminar espacio", description = "Elimina un espacio individual por su piso y número de asiento")
    @DeleteMapping("/delete/{piso}/{numeroAsiento}")
    public ResponseEntity<String> deleteEspacio(@PathVariable int piso, @PathVariable int numeroAsiento) {
        try {
            servicioEspacios.deleteEspacio(piso, numeroAsiento);
            return new ResponseEntity<>("Espacio eliminado correctamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar el espacio: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
