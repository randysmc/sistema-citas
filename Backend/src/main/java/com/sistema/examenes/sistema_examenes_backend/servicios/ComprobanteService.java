package com.sistema.examenes.sistema_examenes_backend.servicios;

import com.sistema.examenes.sistema_examenes_backend.Enums.EstadoComprobante;
import com.sistema.examenes.sistema_examenes_backend.entidades.Comprobante;

import java.util.List;

public interface ComprobanteService {

    public Comprobante crearComprobante(Comprobante comprobante);

    public List<Comprobante> obtenerComprobantes();

    //public List<Comprobante> obtenerComprobantesPorUsuario();

    public Comprobante obtenerComprobantePorId(Long id);

    public Comprobante actualizarComprobante(Comprobante comprobante);

    public Comprobante eliminarComprobante(Long id);

    public List<Comprobante> obtenerComprobantesPorCliente(Long clienteId);

    public List<Comprobante> obtenerComprobantesPorEstado(EstadoComprobante estadoComprobante);

    public Comprobante crearComprobantePorCita(Long citaId, Comprobante comprobante);






}
