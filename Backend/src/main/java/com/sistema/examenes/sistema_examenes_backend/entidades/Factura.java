package com.sistema.examenes.sistema_examenes_backend.entidades;

import javax.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Table(name = "facturas")

public class Factura {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long facturaId;

    private BigDecimal monto;
    private String detalleServicio;
    private LocalDate fecha;

    @ManyToOne
    private Usuario cliente;

    @OneToOne
    private Cita cita;

    public Factura(Long facturaId, BigDecimal monto, Usuario cliente, String detalleServicio, Cita cita) {
        this.facturaId = facturaId;
        this.monto = monto;
        this.cliente = cliente;
        this.detalleServicio = detalleServicio;
        this.cita = cita;
    }

    public Factura() {
    }

    public Long getFacturaId() {
        return facturaId;
    }

    public void setFacturaId(Long facturaId) {
        this.facturaId = facturaId;
    }

    public BigDecimal getMonto() {
        return monto;
    }

    public void setMonto(BigDecimal monto) {
        this.monto = monto;
    }

    public String getDetalleServicio() {
        return detalleServicio;
    }

    public void setDetalleServicio(String detalleServicio) {
        this.detalleServicio = detalleServicio;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }
}
