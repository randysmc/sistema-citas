package com.sistema.examenes.sistema_examenes_backend.controladores;

import com.sistema.examenes.sistema_examenes_backend.entidades.DiaFestivo;
import com.sistema.examenes.sistema_examenes_backend.servicios.DiaFestivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
//@RequestMapping("/api/v1/dias-festivos")
@RequestMapping("/dias-festivos")
@CrossOrigin("*")


public class DiaFestivoController {

    @Autowired
    DiaFestivoService diaFestivoService;

    @GetMapping
    public ResponseEntity<List<DiaFestivo>> obtenerTodos() {
        return new ResponseEntity<>(diaFestivoService.obtenerDias(), HttpStatus.OK);
    }


    // Endpoint para obtener los días festivos recurrentes
    @GetMapping("/recurrentes")
    public ResponseEntity<List<DiaFestivo>> obtenerRecurrentes() {
        return new ResponseEntity<>(diaFestivoService.obtenerDiasRecurrentes(), HttpStatus.OK);
    }

    // Endpoint para obtener los días festivos no recurrentes
    @GetMapping("/no-recurrentes")
    public ResponseEntity<List<DiaFestivo>> obtenerNoRecurrentes() {
        return new ResponseEntity<>(diaFestivoService.obtenerDiasNoRecurrentes(), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<?> guardar(@RequestBody DiaFestivo diaFestivo) {
        try {
            DiaFestivo nuevoDiaFestivo = diaFestivoService.guardar(diaFestivo);
            return new ResponseEntity<>(nuevoDiaFestivo, HttpStatus.CREATED);
        } catch (IllegalArgumentException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }
    }



}
