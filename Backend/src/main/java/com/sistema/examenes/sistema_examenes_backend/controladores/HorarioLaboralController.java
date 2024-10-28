package com.sistema.examenes.sistema_examenes_backend.controladores;

import com.sistema.examenes.sistema_examenes_backend.entidades.HorarioLaboral;
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


    @PostMapping
    public ResponseEntity<HorarioLaboral> crearHorario(@RequestBody HorarioLaboral horarioLaboral) {
        try {
            HorarioLaboral nuevoHorario = horarioLaboralService.guardarHorario(horarioLaboral);
            return ResponseEntity.status(HttpStatus.CREATED).body(nuevoHorario);
        } catch (IllegalArgumentException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(null);
        }
    }


}
