package com.sistema.examenes.sistema_examenes_backend.controladores;

//import com.sistema.examenes.sistema_examenes_backend.DTO.RecursoDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;
import com.sistema.examenes.sistema_examenes_backend.entidades.Recurso;
import com.sistema.examenes.sistema_examenes_backend.servicios.NegocioService;
import com.sistema.examenes.sistema_examenes_backend.servicios.RecursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/recursos")
@CrossOrigin("*")
public class RecursoController {

    @Autowired
    private RecursoService recursoService;

    @Autowired
    private NegocioService negocioService;

    @GetMapping
    public List<Recurso> obtenerRecursos() {
        return recursoService.obtenerRecursos();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Recurso> obtenerRecursoPorId(@PathVariable Long id) {
        Optional<Recurso> recurso = recursoService.findById(id);
        return recurso.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<Recurso> crearRecurso(@RequestBody Recurso recurso) {
        Recurso nuevoRecurso = recursoService.guardarRecurso(recurso);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRecurso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Recurso> actualizarRecurso(@PathVariable Long id, @RequestBody Recurso recurso) {
        recurso.setRecursoId(id); // Establece el ID del recurso en la entidad
        Recurso recursoActualizado = recursoService.actualizaRecurso(recurso);
        return ResponseEntity.ok(recursoActualizado);
    }

    // Obtener recursos disponibles
    @GetMapping("/disponibles")
    public ResponseEntity<List<Recurso>> obtenerRecursosDisponibles() {
        List<Recurso> recursosDisponibles = recursoService.obtenerRecursosDisponibles();
        return ResponseEntity.ok(recursosDisponibles);
    }

    // Obtener recursos no disponibles
    @GetMapping("/no-disponibles")
    public ResponseEntity<List<Recurso>> obtenerRecursosNoDisponibles() {
        List<Recurso> recursosNoDisponibles = recursoService.obtenerRecursosNoDisponibles();
        return ResponseEntity.ok(recursosNoDisponibles);
    }


    @PutMapping("/habilitar/{id}")
    public ResponseEntity<?> habilitarRecurso(@PathVariable Long id) {
        recursoService.cambiarDisponibilidad(id, true);
        return ResponseEntity.ok(Map.of("message", "Recurso habilitado correctamente"));
    }

    @PutMapping("/deshabilitar/{id}")
    public ResponseEntity<?> deshabilitarRecurso(@PathVariable Long id) {
        recursoService.cambiarDisponibilidad(id, false);
        return ResponseEntity.ok(Map.of("message", "Recurso deshabilitado correctamente"));
    }

}
