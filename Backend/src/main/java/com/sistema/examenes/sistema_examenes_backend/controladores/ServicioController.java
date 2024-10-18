package com.sistema.examenes.sistema_examenes_backend.controladores;


import com.sistema.examenes.sistema_examenes_backend.DTO.ServicioDTO;
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
    public List<ServicioDTO> obtenerServicios(){
        return servicioService.findAll();
    }

    @PostMapping("/")
    public ResponseEntity<ServicioDTO> crearRecurso(@RequestBody ServicioDTO servicioDTO){
        ServicioDTO nuevoRecurso = servicioService.save(servicioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRecurso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServicioDTO> actualizarRecurso(@PathVariable Long id, @RequestBody ServicioDTO servicioDTO){
        servicioDTO.setServicioId(id);
        ServicioDTO servicioActualizado = servicioService.update(servicioDTO);
        return ResponseEntity.ok(servicioActualizado);
    }
}
