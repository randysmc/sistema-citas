package com.sistema.examenes.sistema_examenes_backend.controladores;

import com.sistema.examenes.sistema_examenes_backend.DTO.NegocioDTO;
import com.sistema.examenes.sistema_examenes_backend.servicios.NegocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/negocios/")
@CrossOrigin("*")

public class NegocioController {

    @Autowired
    private NegocioService negocioService;

    @GetMapping
    public List<NegocioDTO> obtenerNegocios(){
        return negocioService.findAll();
    }

    @GetMapping("/{id}")
    public ResponseEntity<NegocioDTO> obtenerNegocioPorId(@PathVariable Long id) {
        Optional<NegocioDTO> negocioDTO = negocioService.findById(id);
        return negocioDTO.map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping("/")
    public ResponseEntity<NegocioDTO> crearNegocio(@RequestBody NegocioDTO negocioDTO) {
        NegocioDTO nuevoNegocio = negocioService.save(negocioDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevoNegocio);
    }

    // Actualizar un negocio existente
    @PutMapping("/{id}")
    public ResponseEntity<NegocioDTO> actualizarNegocio(@PathVariable Long id, @RequestBody NegocioDTO negocioDTO) {
        // Establecer el ID en el DTO para asegurarse de que se actualiza el correcto
        negocioDTO.setNegocioId(id);
        NegocioDTO negocioActualizado = negocioService.update(negocioDTO);
        return ResponseEntity.ok(negocioActualizado);
    }

    // Eliminar un negocio por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNegocio(@PathVariable Long id) {
        negocioService.delete(id);
        return ResponseEntity.noContent().build();
    }


}
