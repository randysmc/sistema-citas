package com.sistema.examenes.sistema_examenes_backend.entidades;

import com.sistema.examenes.sistema_examenes_backend.Enums.EstadoComprobante;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table (name = "comprobantes")
public class Comprobante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long comprobanteId;

    private LocalDate fecha;
    private LocalTime horaInicio;
    private String descripcion;

    @Enumerated(EnumType.STRING)
    private EstadoComprobante estadoComprobante;

    @ManyToOne
    private Usuario cliente;

    @ManyToOne
    private Cita cita;

    public Comprobante() {
    }

    public Comprobante(Long comprobanteId, LocalDate fecha, LocalTime horaInicio, String descripcion, EstadoComprobante estadoComprobante, Usuario cliente, Cita cita) {
        this.comprobanteId = comprobanteId;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.descripcion = descripcion;
        this.estadoComprobante = estadoComprobante;
        this.cliente = cliente;
        this.cita = cita;
    }

    public Long getComprobanteId() {
        return comprobanteId;
    }

    public void setComprobanteId(Long comprobanteId) {
        this.comprobanteId = comprobanteId;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public EstadoComprobante getEstadoComprobante() {
        return estadoComprobante;
    }

    public void setEstadoComprobante(EstadoComprobante estadoComprobante) {
        this.estadoComprobante = estadoComprobante;
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


}
