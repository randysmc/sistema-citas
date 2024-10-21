package com.sistema.examenes.sistema_examenes_backend.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "negocios")
public class Negocio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long negocioId;

    private String nombre;
    private String direccion;
    private String descripcion;
    private String telefono;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "negocio")
    @JsonIgnore
    private Set<UsuarioNegocio> usuarioNegocios = new HashSet<>();

    //Un negocio puede tener muchos recursos
    @OneToMany(mappedBy = "negocio", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Recurso> recursos =  new LinkedHashSet<>();

    @OneToMany(mappedBy = "negocio", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Servicio> servicios = new LinkedHashSet<>();

    @OneToMany(mappedBy = "negocio", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<DiaFestivo> diasFestivos = new LinkedHashSet<>();

    @OneToMany(mappedBy = "negocio", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<HorarioLaboral> horarioLaborales = new LinkedHashSet<>();

    @OneToMany(mappedBy = "negocio",fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Cita> citas = new LinkedHashSet<>();

    @OneToMany(mappedBy = "negocio", cascade = CascadeType.ALL)
    @JsonIgnore
    private Set<Reserva> reservas = new LinkedHashSet<>();


    public Negocio() {
    }

    public Negocio(Long negocioId, String direccion, String nombre, String descripcion, String telefono) {
        this.negocioId = negocioId;
        this.direccion = direccion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.telefono = telefono;

    }

    public Long getNegocioId() {
        return negocioId;
    }

    public void setNegocioId(Long negocioId) {
        this.negocioId = negocioId;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getDireccion() {
        return direccion;
    }

    public void setDireccion(String direccion) {
        this.direccion = direccion;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public Set<UsuarioNegocio> getUsuarioNegocios() {
        return usuarioNegocios;
    }

    public void setUsuarioNegocios(Set<UsuarioNegocio> usuarioNegocios) {
        this.usuarioNegocios = usuarioNegocios;
    }

    public Set<Recurso> getRecursos() {
        return recursos;
    }

    public void setRecursos(Set<Recurso> recursos) {
        this.recursos = recursos;
    }

    public Set<Servicio> getServicios() {
        return servicios;
    }

    public void setServicios(Set<Servicio> servicios) {
        this.servicios = servicios;
    }

    public Set<DiaFestivo> getDiasFestivos() {
        return diasFestivos;
    }

    public void setDiasFestivos(Set<DiaFestivo> diasFestivos) {
        this.diasFestivos = diasFestivos;
    }

    public Set<HorarioLaboral> getHorarioLaborales() {
        return horarioLaborales;
    }

    public void setHorarioLaborales(Set<HorarioLaboral> horarioLaborales) {
        this.horarioLaborales = horarioLaborales;
    }

    public Set<Cita> getCitas() {
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
    }
}
