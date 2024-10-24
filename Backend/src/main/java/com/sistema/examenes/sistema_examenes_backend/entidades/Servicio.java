package com.sistema.examenes.sistema_examenes_backend.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table (name = "servicios")
public class Servicio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long servicioId;
    @Column(unique = true)
    private String nombre;
    @NotBlank(message = "La descripcion no puede estar vacía.")
    private String descripcion;
    @NotNull(message = "La duracion no puede estar vacía.")
    private Integer duracionServicio;
    private BigDecimal precio;
    private Boolean disponible;


    /*@OneToMany(mappedBy = "servicio", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Cita> citas = new HashSet<>();*/


    public Servicio(Long servicioId, String nombre, String descripcion, Integer duracionServicio, BigDecimal precio) {
        this.servicioId = servicioId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.duracionServicio = duracionServicio;
        this.precio = precio;

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


    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    /*public Set<Cita> getCitas() {
        return citas;
    }

    public void setCitas(Set<Cita> citas) {
        this.citas = citas;
    }*/
}
