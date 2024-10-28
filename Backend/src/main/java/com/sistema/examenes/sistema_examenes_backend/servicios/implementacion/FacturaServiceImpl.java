package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;

import com.sistema.examenes.sistema_examenes_backend.entidades.Cita;
import com.sistema.examenes.sistema_examenes_backend.entidades.Factura;
import com.sistema.examenes.sistema_examenes_backend.entidades.Usuario;
import com.sistema.examenes.sistema_examenes_backend.repositorios.CitaRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.FacturaRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.UsuarioRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.FacturaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class FacturaServiceImpl implements FacturaService {

    @Autowired
    public FacturaRepository facturaRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    CitaRepository citaRepository;


    @Override
    public Factura crearFactura(Factura factura) {
        Usuario usuario = usuarioRepository.findById(factura.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar que la cita existe
        Cita cita = citaRepository.findById(factura.getCita().getIdCita())
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        factura.setCliente(usuario);
        factura.setCita(cita);

        return facturaRepository.save(factura);
    }

    @Override
    public List<Factura> obtenerFacturass() {
        return facturaRepository.findAll();
    }

    @Override
    public Factura obtenerFacturaPorId(Long id) {
        return facturaRepository.findById(id).orElse(null);
    }

    @Override
    public Factura actualizarFactura(Factura factura) {
        return null;
    }

    @Override
    public Factura eliminarFactura(Long id) {
        return null;
    }
}
