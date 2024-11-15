package com.sistema.examenes.sistema_examenes_backend.controladores;


import com.sistema.examenes.sistema_examenes_backend.Enums.EstadoComprobante;
import com.sistema.examenes.sistema_examenes_backend.entidades.Cita;
import com.sistema.examenes.sistema_examenes_backend.entidades.Comprobante;
import com.sistema.examenes.sistema_examenes_backend.entidades.Reserva;
import com.sistema.examenes.sistema_examenes_backend.servicios.ComprobanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/comprobantes")
@CrossOrigin("*")
public class ComprobanteController {
    @Autowired
    private ComprobanteService comprobanteService;


    @GetMapping
    public ResponseEntity<List<Comprobante>> obtenerComprobantes() {
        List<Comprobante> comprobantes  = comprobanteService.obtenerComprobantes();
        return ResponseEntity.ok(comprobantes); // 200 OK
    }



    @PostMapping("/crear")
    public ResponseEntity<?> crearComprobante(@RequestBody Comprobante comprobante) {
        try {
            // Intentar crear el comprobante
            Comprobante nuevoComprobante = comprobanteService.crearComprobante(comprobante);
            return new ResponseEntity<>(nuevoComprobante, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Si ocurre algún error, retornamos un JSON con el mensaje de error
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Error inesperado: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/cliente/{clienteId}")
    public ResponseEntity<List<Comprobante>> obtenerComprobantesPorCliente(@PathVariable Long clienteId) {
        List<Comprobante> comprobantes = comprobanteService.obtenerComprobantesPorCliente(clienteId);
        if (comprobantes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comprobantes);
    }

    @GetMapping("/estado/{estado}")
    public ResponseEntity<List<Comprobante>> obtenerComprobantesPorEstado(@PathVariable EstadoComprobante estado) {
        List<Comprobante> comprobantes = comprobanteService.obtenerComprobantesPorEstado(estado);
        if (comprobantes.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(comprobantes);
    }

    @PostMapping("/cita/{citaId}")
    public ResponseEntity<Comprobante> crearComprobantePorCita(@PathVariable Long citaId, @RequestBody Comprobante comprobante) {
        Comprobante nuevoComprobante = comprobanteService.crearComprobantePorCita(citaId, comprobante);
        return new ResponseEntity<>(nuevoComprobante, HttpStatus.CREATED);
    }




}
