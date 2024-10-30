package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;

import com.sistema.examenes.sistema_examenes_backend.Enums.EstadoComprobante;
import com.sistema.examenes.sistema_examenes_backend.entidades.Cita;
import com.sistema.examenes.sistema_examenes_backend.entidades.Comprobante;
import com.sistema.examenes.sistema_examenes_backend.entidades.Usuario;
import com.sistema.examenes.sistema_examenes_backend.repositorios.CitaRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.ComprobanteRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.UsuarioRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.ComprobanteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@Service
public class ComprobanteServiceImpl implements ComprobanteService {

    @Autowired
    public ComprobanteRepository comprobanteRepository;

    @Autowired
    UsuarioRepository usuarioRepository;

    @Autowired
    CitaRepository citaRepository;

    @Override
    public Comprobante crearComprobante(Comprobante comprobante) {
        // Verificar que el usuario existe
        Usuario usuario = usuarioRepository.findById(comprobante.getCliente().getId())
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        // Verificar que la cita existe
        Cita cita = citaRepository.findById(comprobante.getCita().getIdCita())
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        // Asignar usuario y cita al comprobante antes de guardarlo
        comprobante.setCliente(usuario);
        comprobante.setCita(cita);

        // Guardar y retornar el comprobante creado
        return comprobanteRepository.save(comprobante);
    }

    @Override
    public List<Comprobante> obtenerComprobantes() {
        return comprobanteRepository.findAll();
    }

    @Override
    public Comprobante obtenerComprobantePorId(Long id) {
        return comprobanteRepository.findById(id).orElse(null);
    }

    @Override
    public Comprobante actualizarComprobante(Comprobante comprobante) {
        return null;
    }

    @Override
    public Comprobante eliminarComprobante(Long id) {
        return null;
    }

    @Override
    public List<Comprobante> obtenerComprobantesPorCliente(Long clienteId) {
        return comprobanteRepository.findByCliente_Id(clienteId);
    }

    @Override
    public List<Comprobante> obtenerComprobantesPorEstado(EstadoComprobante estadoComprobante) {
        return comprobanteRepository.findByEstadoComprobante(estadoComprobante);
    }

    @Override
    public Comprobante crearComprobantePorCita(Long citaId, Comprobante comprobante) {
        Cita cita = citaRepository.findById(citaId)
                .orElseThrow(() -> new RuntimeException("Cita no encontrada"));

        comprobante.setCita(cita);
        return comprobanteRepository.save(comprobante);
    }






}
