package com.sistema.examenes.sistema_examenes_backend.controladores;

import com.sistema.examenes.sistema_examenes_backend.entidades.Comprobante;
import com.sistema.examenes.sistema_examenes_backend.entidades.Factura;
import com.sistema.examenes.sistema_examenes_backend.repositorios.FacturaRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
@RestController
@RequestMapping("/facturas")
public class FacturaController {

    @Autowired
    FacturaService facturaService;


    @GetMapping
    public ResponseEntity<List<Factura>> obtenerReservas() {
        List<Factura> facturas = facturaService.obtenerFacturass();
        return ResponseEntity.ok(facturas); // 200 OK
    }


    @PostMapping("/crear")
    public ResponseEntity<?> crearComprobante(@RequestBody Factura factura) {
        try {
            // Intentar crear el comprobante
            Factura nuevaFactura = facturaService.crearFactura(factura);
            return new ResponseEntity<>(nuevaFactura, HttpStatus.CREATED);
        } catch (RuntimeException e) {
            // Si ocurre algún error, retornamos un JSON con el mensaje de error
            return new ResponseEntity<>(Map.of("error", e.getMessage()), HttpStatus.BAD_REQUEST);
        } catch (Exception e) {
            return new ResponseEntity<>(Map.of("error", "Error inesperado: " + e.getMessage()), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}
