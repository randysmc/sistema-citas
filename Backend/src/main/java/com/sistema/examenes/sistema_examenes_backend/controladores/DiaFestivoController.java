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

    @PostMapping
    public ResponseEntity<DiaFestivo> guardar(@RequestBody DiaFestivo diaFestivo) {

            DiaFestivo nuevoDiaFestivo = diaFestivoService.guardar(diaFestivo);
            return new ResponseEntity<>(nuevoDiaFestivo, HttpStatus.CREATED);

    }



}
