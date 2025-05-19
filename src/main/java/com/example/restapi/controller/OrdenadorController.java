package com.example.restapi.controller;

import com.example.restapi.model.Ordenador;
import com.example.restapi.service.ServicioOrdenadores;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api/ordenadores")
@Tag(name = "Ordenadores", description = "Operaciones relacionadas con la gestión de ordenadores")
public class OrdenadorController {

    private final ServicioOrdenadores servicioOrdenadores;

    @Autowired
    public OrdenadorController(ServicioOrdenadores servicioOrdenadores) {
        this.servicioOrdenadores = servicioOrdenadores;
    }

    @Operation(summary = "Obtener todos los ordenadores", description = "Devuelve una lista de todos los ordenadores")
    @GetMapping("/all")
    public List<Ordenador> getAllOrdenadores() {
        return servicioOrdenadores.findAll();
    }

    @Operation(summary = "Buscar ordenador por ID", description = "Devuelve un ordenador según su ID")
    @GetMapping("/{id}")
    public ResponseEntity<Ordenador> getOrdenadorById(@PathVariable Long id) {
        Optional<Ordenador> ordenador = servicioOrdenadores.findById(id);
        return ordenador.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.status(HttpStatus.NOT_FOUND).build());
    }

    @Operation(summary = "Agregar nuevo ordenador", description = "Crea un nuevo ordenador en el sistema")
    @PostMapping("/add")
    public ResponseEntity<String> addOrdenador(
            @RequestParam("marca") String marca,
            @RequestParam("modelo") String modelo,
            @RequestParam("numeroSerie") String numeroSerie,
            @RequestParam(value = "disponible", required = false, defaultValue = "false") boolean disponible) {
        try {
            Ordenador ordenador = new Ordenador(marca, modelo, numeroSerie, disponible);
            servicioOrdenadores.addOrdenador(ordenador);
            return new ResponseEntity<>("Ordenador agregado correctamente", HttpStatus.CREATED);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al agregar el ordenador: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Eliminar ordenador por ID", description = "Elimina un ordenador específico por su ID")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> deleteOrdenador(@PathVariable Long id) {
        try {
            servicioOrdenadores.deleteOrdenador(id);
            return new ResponseEntity<>("Ordenador eliminado correctamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al eliminar el ordenador: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @Operation(summary = "Actualizar ordenador por ID", description = "Actualiza un ordenador existente")
    @PutMapping("/update/{id}")
    public ResponseEntity<String> updateOrdenador(
            @PathVariable Long id,
            @RequestParam("marca") String marca,
            @RequestParam("modelo") String modelo,
            @RequestParam("numeroSerie") String numeroSerie,
            @RequestParam(value = "disponible", required = false, defaultValue = "false") boolean disponible) {
        try {
            Ordenador ordenador = new Ordenador(marca, modelo, numeroSerie, disponible);
            servicioOrdenadores.updateOrdenador(id, ordenador);
            return new ResponseEntity<>("Ordenador actualizado correctamente", HttpStatus.OK);
        } catch (Exception e) {
            return new ResponseEntity<>("Error al actualizar el ordenador: " + e.getMessage(),
                    HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    /*
    @PostMapping("/delete-ordenador")
    public String deleteOrdenadorPorNumeroSerie(@RequestParam String numeroSerie) {
        try {
            servicioOrdenadores.deleteByNumeroSerie(numeroSerie);
            return "redirect:/adminHome?success=Borrado+correcto";
        } catch (Exception e) {
            return "redirect:/adminHome?error=Error+al+borrar";
        }
    }*/

    @PostMapping("/delete-ordenador")
    public ResponseEntity<String> deleteOrdenadorPorNumeroSerie(@RequestParam String numeroSerie) {
        try {
            servicioOrdenadores.deleteByNumeroSerie(numeroSerie);
            return ResponseEntity.ok("Ordenador eliminado correctamente");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error al eliminar el ordenador");
        }
    }


    @GetMapping("/marca/{marca}")
    public ResponseEntity<List<Ordenador>> getOrdenadoresByMarca(@PathVariable String marca) {
        List<Ordenador> ordenadores = servicioOrdenadores.findByMarca(marca);
        if (ordenadores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ordenadores);
    }

    @GetMapping("/modelo/{modelo}")
    public ResponseEntity<List<Ordenador>> getOrdenadoresByModelo(@PathVariable String modelo) {
        List<Ordenador> ordenadores = servicioOrdenadores.findByModelo(modelo);
        if (ordenadores.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(ordenadores);
    }
}
