package com.sistema.examenes.sistema_examenes_backend.controladores;

import com.sistema.examenes.sistema_examenes_backend.DTO.NegocioDTO;
import com.sistema.examenes.sistema_examenes_backend.DTO.RecursoDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;
import com.sistema.examenes.sistema_examenes_backend.entidades.Recurso;
import com.sistema.examenes.sistema_examenes_backend.servicios.NegocioService;
import com.sistema.examenes.sistema_examenes_backend.servicios.RecursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/negocios")
@CrossOrigin("*")

public class NegocioController {

    @Autowired
    private NegocioService negocioService;

    @Autowired
    private RecursoService recursoService;

    @GetMapping
    public List<Negocio> obtenerNegocios(){
        return negocioService.obtenerNegocios();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Negocio> obtenerNegocioPorId(@PathVariable Long id) {
        Optional<Negocio> negocio = negocioService.findById(id);
        return negocio.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }


    @PostMapping("/")
    public ResponseEntity<Negocio> crearNegocio(@RequestBody Negocio negocio) {
        Negocio nuevoNegocio = negocioService.guardarNegocio(negocio);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoNegocio);
    }

    // Actualizar un negocio existente
    @PutMapping("/{id}")
    public ResponseEntity<Negocio> actualizarNegocio(@PathVariable Long id, @RequestBody Negocio negocio) {
        negocio.setNegocioId(id);
        Negocio negocioActualizado = negocioService.actualizarNegocio(negocio);
        return ResponseEntity.ok(negocioActualizado);
    }

    // Eliminar un negocio por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNegocio(@PathVariable Long id) {
        negocioService.eliminarNegocio(id);
        return ResponseEntity.noContent().build();
    }

}
