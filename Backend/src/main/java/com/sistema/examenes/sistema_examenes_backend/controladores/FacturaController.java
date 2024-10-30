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
@CrossOrigin("*")
public class FacturaController {

    @Autowired
    FacturaService facturaService;


    @GetMapping
    public ResponseEntity<List<Factura>> obtenerFacturas() {
        List<Factura> facturas = facturaService.obtenerFacturass();
        return ResponseEntity.ok(facturas); // 200 OK
    }


    @PostMapping("/crear")
    public ResponseEntity<?> crearFactura(@RequestBody Factura factura) {
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

    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerFacturaPorId(@PathVariable Long id) {
        Factura factura = facturaService.obtenerFacturaPorId(id);
        if (factura == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Error: Factura no encontrada con ID " + id);
        }
        return ResponseEntity.ok(factura);
    }


    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<?> obtenerFacturasPorUsuario(@PathVariable Long usuarioId) {
        List<Factura> facturas = facturaService.obtenerFacturasPorUsuario(usuarioId);
        if (facturas.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("No se encontraron facturas para el usuario con ID " + usuarioId);
        }
        return ResponseEntity.ok(facturas);
    }


    @PutMapping
    public ResponseEntity<Factura> actualizarFactura(@RequestBody Factura factura) {
        Factura facturaActualizada = facturaService.actualizarFactura(factura);
        return ResponseEntity.ok(facturaActualizada);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarFactura(@PathVariable Long id) {
        facturaService.eliminarFactura(id);
        return ResponseEntity.noContent().build();
    }


    @PostMapping("/crearDesdeCita/{citaId}")
    public ResponseEntity<Factura> crearFacturaDesdeCita(@PathVariable Long citaId) {
        Factura nuevaFactura = facturaService.crearFacturaDesdeCita(citaId);
        return new ResponseEntity<>(nuevaFactura, HttpStatus.CREATED);
    }

    @GetMapping("/cita/{citaId}")
    public ResponseEntity<Factura> obtenerFacturaPorCita(@PathVariable Long citaId) {
        Factura factura = facturaService.obtenerFacturaPorCita(citaId);
        return ResponseEntity.ok(factura);
    }





}
