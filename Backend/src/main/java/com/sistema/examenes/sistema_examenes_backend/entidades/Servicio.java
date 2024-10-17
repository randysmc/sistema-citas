package com.sistema.examenes.sistema_examenes_backend.entidades;

import javax.persistence.*;
import java.math.BigDecimal;

@Entity
@Table (name = "servicios")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long servicioId;

    private String nombre;
    private String descripcion;
    private int duracionServicio;
    private BigDecimal precio;

    @ManyToOne (fetch = FetchType.EAGER)
    private Negocio negocio;

    public Servicio(Long servicioId, String nombre, String descripcion, int duracionServicio, BigDecimal precio, Negocio negocio) {
        this.servicioId = servicioId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracionServicio = duracionServicio;
        this.precio = precio;
        this.negocio = negocio;
    }

    public Servicio() {
    }

    public Long getServicioId() {
        return servicioId;
    }

    public void setServicioId(Long servicioId) {
        this.servicioId = servicioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public int getDuracionServicio() {
        return duracionServicio;
    }

    public void setDuracionServicio(int duracionServicio) {
        this.duracionServicio = duracionServicio;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Negocio getNegocio() {
        return negocio;
    }

    public void setNegocio(Negocio negocio) {
        this.negocio = negocio;
    }
}
