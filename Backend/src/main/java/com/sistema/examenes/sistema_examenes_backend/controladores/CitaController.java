package com.sistema.examenes.sistema_examenes_backend.controladores;

import com.sistema.examenes.sistema_examenes_backend.entidades.Cita;
import com.sistema.examenes.sistema_examenes_backend.servicios.CitaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/citas")
@CrossOrigin(origins = "http://localhost:4200")
public class CitaController {

    @Autowired
    private CitaService citaService;

    @PostMapping
    public ResponseEntity<Cita> crearCita(@RequestBody Cita cita) {
        Cita nuevaCita = citaService.crearCita(cita);
        return ResponseEntity.status(201).body(nuevaCita); // 201 Created
    }

    @GetMapping
    public ResponseEntity<List<Cita>> obtenerCitas() {
        List<Cita> citas = citaService.obtenerCitas();
        return ResponseEntity.ok(citas); // 200 OK
    }

    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<Cita>> obtenerCitaPorUsuario(@PathVariable Long usuarioId) {
        List<Cita> citas = citaService.obtenerCitaPorUsuario(usuarioId);
        return ResponseEntity.ok(citas); // 200 OK
    }

    @GetMapping("/{id}")
    public ResponseEntity<Cita> obtenerCitaPorId(@PathVariable Long id) {
        Cita cita = citaService.obtenerCitaPorId(id);
        return ResponseEntity.ok(cita); // 200 OK
    }

    @PutMapping
    public ResponseEntity<Cita> actualizarCita(@RequestBody Cita cita) {
        Cita citaActualizada = citaService.actualizaCita(cita);
        return ResponseEntity.ok(citaActualizada); // 200 OK
    }

    @PutMapping("/{id}/cancelar")
    public ResponseEntity<Cita> cancelarCita(@PathVariable Long id) {
        Cita citaCancelada = citaService.cancelarCita(id);
        return ResponseEntity.ok(citaCancelada); // 200 OK
    }
    @PutMapping("/{id}/confirmar")
    public ResponseEntity<Cita> confirmarCita(@PathVariable Long id) {
        Cita citaConfirmada = citaService.confirmarCita(id);
        return ResponseEntity.ok(citaConfirmada); // 200 OK
    }


    @GetMapping("/empleado/{empleadoId}")
    public ResponseEntity<List<Cita>> obtenerCitaPorEmpleado(@PathVariable Long empleadoId) {
        List<Cita> citas = citaService.obtenerCitaPorEmpleado(empleadoId);
        return ResponseEntity.ok(citas); // 200 OK
    }
}
