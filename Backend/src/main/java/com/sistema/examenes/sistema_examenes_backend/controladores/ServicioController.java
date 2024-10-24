package com.sistema.examenes.sistema_examenes_backend.controladores;


import com.sistema.examenes.sistema_examenes_backend.DTO.ServicioDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Servicio;
import com.sistema.examenes.sistema_examenes_backend.servicios.NegocioService;
import com.sistema.examenes.sistema_examenes_backend.servicios.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/servicios")
@CrossOrigin("*")

public class ServicioController {
    @Autowired
    private ServicioService servicioService;

    @Autowired
    private NegocioService negocioService;

    @GetMapping
    public List<Servicio> obtenerServicios() {
        return servicioService.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<Servicio> crearRecurso(@RequestBody Servicio servicio) {
        Servicio nuevoRecurso = servicioService.save(servicio);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRecurso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Servicio> actualizarRecurso(@PathVariable Long id, @RequestBody Servicio servicio) {
        servicio.setServicioId(id);
        Servicio servicioActualizado = servicioService.update(servicio);
        return ResponseEntity.ok(servicioActualizado);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Servicio> obtenerServicioPorId(@PathVariable Long id) {
        Servicio servicio = servicioService.findById(id)
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + id));
        return ResponseEntity.ok(servicio);
    }

    // Obtener servicios disponibles
    @GetMapping("/disponibles")
    public List<Servicio> obtenerServiciosDisponibles() {
        return servicioService.findByDisponibleTrue();
    }

    // Obtener servicios no disponibles
    @GetMapping("/no-disponibles")
    public List<Servicio> obtenerServiciosNoDisponibles() {
        return servicioService.findByDisponibleFalse();
    }

    // Poner un servicio como disponible (disponible = true)
    @PutMapping("/habilitar/{id}")
    public ResponseEntity<?> habilitarServicio(@PathVariable Long id) {
        servicioService.cambiarDisponibilidad(id, true);
        return ResponseEntity.ok("Servicio habilitado correctamente");
    }

    // Poner un servicio como no disponible (disponible = false)
    @PutMapping("/deshabilitar/{id}")
    public ResponseEntity<?> deshabilitarServicio(@PathVariable Long id) {
        servicioService.cambiarDisponibilidad(id, false);
        return ResponseEntity.ok("Servicio deshabilitado correctamente");
    }
}
