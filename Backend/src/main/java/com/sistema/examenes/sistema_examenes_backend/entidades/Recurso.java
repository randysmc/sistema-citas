package com.sistema.examenes.sistema_examenes_backend.entidades;

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
    private boolean disponible;

    //Muchos recursos pueden pertenecerle a un Negocio
    @ManyToOne (fetch = FetchType.EAGER)
    private Negocio negocio;

    @OneToMany(mappedBy = "recurso", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Cita> citas = new HashSet<>();

    @OneToMany(mappedBy = "recurso", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Reserva> reservas = new HashSet<>();


    public Recurso() {
    }

    public Recurso(Long recursoId, String nombre, String descripcion, boolean disponible, Negocio negocio) {
        this.recursoId = recursoId;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.disponible = disponible;
        this.negocio = negocio;
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

    public boolean isDisponible() {
        return disponible;
    }

    public void setDisponible(boolean disponible) {
        this.disponible = disponible;
    }

    public Negocio getNegocio() {
        return negocio;
    }

    public void setNegocio(Negocio negocio) {
        this.negocio = negocio;
    }
}
