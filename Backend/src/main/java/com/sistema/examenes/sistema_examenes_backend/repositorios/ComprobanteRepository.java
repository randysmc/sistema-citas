package com.sistema.examenes.sistema_examenes_backend.repositorios;

import com.sistema.examenes.sistema_examenes_backend.Enums.EstadoComprobante;
import com.sistema.examenes.sistema_examenes_backend.entidades.Comprobante;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ComprobanteRepository  extends JpaRepository<Comprobante, Long> {

    public List<Comprobante> findByComprobanteId(Long comprobanteId);

    public List<Comprobante> findByCliente_Id(Long clienteId);

    // Obtener comprobantes por estado
    public List<Comprobante> findByEstadoComprobante(EstadoComprobante estadoComprobante);


}
