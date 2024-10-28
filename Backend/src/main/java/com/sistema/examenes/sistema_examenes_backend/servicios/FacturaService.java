package com.sistema.examenes.sistema_examenes_backend.servicios;

import com.sistema.examenes.sistema_examenes_backend.entidades.Factura;

import java.util.List;

public interface FacturaService {

    public Factura crearFactura(Factura factura);

    public List<Factura> obtenerFacturass();

    //public List<Factura> obtenerFacturasPorUsuario();

    public Factura obtenerFacturaPorId(Long id);

    public Factura actualizarFactura(Factura factura);

    public Factura eliminarFactura(Long id);
}
