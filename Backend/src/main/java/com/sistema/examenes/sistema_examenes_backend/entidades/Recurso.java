package com.sistema.examenes.sistema_examenes_backend.entidades;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sistema.examenes.sistema_examenes_backend.Enums.DiaSemana;
import com.sistema.examenes.sistema_examenes_backend.Enums.TipoRecurso;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "recursos")
public class Recurso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recursoId;

    @Column(unique = true)
    private String nombre;
    private String descripcion;
    private Boolean disponible;
    @Enumerated(EnumType.STRING)
    private TipoRecurso tipo;


    /*@OneToMany(mappedBy = "recurso", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Cita> citas = new HashSet<>();

    @OneToMany(mappedBy = "recurso", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private Set<Reserva> reservas = new HashSet<>();*/

    public Recurso(Long recursoId, String nombre, String descripcion, Boolean disponible, TipoRecurso tipo) {
        this.recursoId = recursoId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.disponible = disponible;
        this.tipo = tipo;
    }

    public Recurso() {
    }

    public Long getRecursoId() {
        return recursoId;
    }

    public void setRecursoId(Long recursoId) {
        this.recursoId = recursoId;
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

    public Boolean getDisponible() {
        return disponible;
    }

    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    public TipoRecurso getTipo() {
        return tipo;
    }

    public void setTipo(TipoRecurso tipo) {
        this.tipo = tipo;
    }

    /*public Set<Cita> getCitas() {
        return citas;
    }

    public void setCitas(Set<Cita> citas) {
        this.citas = citas;
    }

    public Set<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(Set<Reserva> reservas) {
        this.reservas = reservas;
    }*/

}
