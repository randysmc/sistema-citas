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

import java.time.LocalDate;
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
        return facturaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Factura no encontrada con ID: " + id));
    }

    @Override
    public Factura actualizarFactura(Factura factura) {
        return null;
    }

    @Override
    public Factura eliminarFactura(Long id) {
        return null;
    }

    @Override
    public List<Factura> obtenerFacturasPorUsuario(Long usuarioId) {
        return facturaRepository.findByCliente_Id(usuarioId);
    }

    @Override
    public Factura crearFacturaDesdeCita(Long citaId) {
        // Buscar la cita por su ID
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        // Crear la nueva factura usando información de la cita
        Factura nuevaFactura = new Factura();
        nuevaFactura.setCliente(cita.getCliente()); // Asumiendo que `getCliente` devuelve un objeto `Usuario`
        nuevaFactura.setCita(cita);
        nuevaFactura.setMonto(cita.getServicio().getPrecio()); // Suponiendo que la cita tiene un monto
        nuevaFactura.setFecha(LocalDate.now()); // O la fecha que necesites

        // Personalizar la descripción de la factura
        String descripcion = String.format("Factura por utilizar el servicio de %s, el día %s, sujeto a pagos trimestrales.",
                cita.getServicio().getNombre(), // Asegúrate de que `getServicio()` devuelva el servicio asociado a la cita
                cita.getFecha()); // O el campo que contenga la fecha de la cita
        nuevaFactura.setDetalleServicio(descripcion);

        // Guardar la nueva factura en la base de datos
        return facturaRepository.save(nuevaFactura);
    }

    @Override
    public Factura obtenerFacturaPorCita(Long citaId) {
        return facturaRepository.findByCita_IdCita(citaId); // Asegúrate de que el método existe en el repositorio
    }




}
