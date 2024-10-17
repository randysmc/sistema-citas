package com.sistema.examenes.sistema_examenes_backend.controladores;

import com.sistema.examenes.sistema_examenes_backend.DTO.RecursoDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;
import com.sistema.examenes.sistema_examenes_backend.servicios.NegocioService;
import com.sistema.examenes.sistema_examenes_backend.servicios.RecursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
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
    public List<RecursoDTO> obtenerRecursos(){
        return recursoService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<RecursoDTO> obtenerRecursoPorId(@PathVariable Long id){
        Optional<RecursoDTO> recursoDTO = recursoService.findById(id);
        return recursoDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<RecursoDTO> crearRecurso(@RequestBody RecursoDTO recursoDTO){
        RecursoDTO nuevoRecurso = recursoService.save(recursoDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoRecurso);
    }

    @PutMapping("/{id}")
    public ResponseEntity<RecursoDTO> actualizarRecurso(@PathVariable Long id, @RequestBody RecursoDTO recursoDTO) {
        // Establece el ID del recurso en el DTO
        recursoDTO.setRecursoId(id); // Esto es importante para que el servicio sepa cuál actualizar
        RecursoDTO recursoActualizado = recursoService.update(recursoDTO);
        return ResponseEntity.ok(recursoActualizado);
    }


}
