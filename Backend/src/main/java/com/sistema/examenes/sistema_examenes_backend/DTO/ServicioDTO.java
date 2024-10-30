/*package com.sistema.examenes.sistema_examenes_backend.DTO;

import java.math.BigDecimal;

public class ServicioDTO {
    private Long servicioId;
    private String nombre;
    private String descripcion;
    private Integer duracionServicio;
    private BigDecimal precio;
    private Long negocioId;

    public ServicioDTO(Long servicioId, String nombre, String descripcion, Integer duracionServicio, BigDecimal precio, Long negocioId) {
        this.servicioId = servicioId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracionServicio = duracionServicio;
        this.precio = precio;
        this.negocioId = negocioId;
    }

    public ServicioDTO() {
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

    public Integer getDuracionServicio() {
        return duracionServicio;
    }

    public void setDuracionServicio(Integer duracionServicio) {
        this.duracionServicio = duracionServicio;
    }

    public BigDecimal getPrecio() {
        return precio;
    }

    public void setPrecio(BigDecimal precio) {
        this.precio = precio;
    }

    public Long getNegocioId() {
        return negocioId;
    }

    public void setNegocioId(Long negocioId) {
        this.negocioId = negocioId;
    }
}

/*
*     private Long servicioId;

    private String nombre;
    private String descripcion;
    private int duracionServicio;
    private BigDecimal precio;

    @ManyToOne (fetch = FetchType.EAGER)
    private Negocio negocio;
* */