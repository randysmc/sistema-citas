package com.sistema.examenes.sistema_examenes_backend.controladores;

import com.sistema.examenes.sistema_examenes_backend.entidades.HorarioLaboral;
import com.sistema.examenes.sistema_examenes_backend.entidades.Permiso;
import com.sistema.examenes.sistema_examenes_backend.excepciones.EntityNotFoundException;
import com.sistema.examenes.sistema_examenes_backend.excepciones.HorarioExistenteException;
import com.sistema.examenes.sistema_examenes_backend.servicios.HorarioLaboralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/api/v1/horarios-laborales")
@RequestMapping("/horarios-laborales")
@CrossOrigin("*")
public class HorarioLaboralController {

    @Autowired
    HorarioLaboralService horarioLaboralService;


    @GetMapping
    public ResponseEntity<List<HorarioLaboral>> obtenerTodos() {
        return new ResponseEntity<>(horarioLaboralService.obtenerHorarios(), HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<HorarioLaboral> obtenerPermisoPorId(@PathVariable Long id) {
        try {
            HorarioLaboral horarioLaboral = horarioLaboralService.findById(id).orElseThrow(() ->
                    new EntityNotFoundException("Horario", id));
            return new ResponseEntity<>(horarioLaboral, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @PostMapping
    public ResponseEntity<HorarioLaboral> crearHorario(@RequestBody HorarioLaboral horarioLaboral) {
        try {
            HorarioLaboral nuevoHorario = horarioLaboralService.guardarHorario(horarioLaboral);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoHorario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<HorarioLaboral> actualizarHorario(@PathVariable Long id, @RequestBody HorarioLaboral horarioLaboral) {
        // Establecemos el ID en el objeto de horario laboral
        horarioLaboral.setHorarioLaboralId(id);

        try {
            HorarioLaboral horarioActualizado = horarioLaboralService.actualizarHorario(horarioLaboral);
            return ResponseEntity.status(HttpStatus.OK).body(horarioActualizado);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        } catch (HorarioExistenteException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }

    // Eliminar horario
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarHorario(@PathVariable Long id) {
        try {
            horarioLaboralService.eliminarHorario(id);
            return ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }
    }


}
