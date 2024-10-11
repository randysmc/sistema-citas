package com.sistema.examenes.sistema_examenes_backend.entidades;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "negocios")
public class Negocio {

    @Id
    private Long negocioId;
    private String nombre;
    private String direccion;
    private String descripcion;
    private String telefono;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "negocio")
    private Set<UsuarioNegocio> usuarioNegocios = new HashSet<>();

    public Negocio() {
    }

    public Negocio(Long negocioId, String direccion, String nombre, String descripcion, String telefono, Set<UsuarioNegocio> usuarioNegocios) {
        this.negocioId = negocioId;
        this.direccion = direccion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.telefono = telefono;
        this.usuarioNegocios = usuarioNegocios;
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
}
