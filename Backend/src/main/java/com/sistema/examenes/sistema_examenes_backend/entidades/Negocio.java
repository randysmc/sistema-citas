package com.sistema.examenes.sistema_examenes_backend.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.Set;

@Entity
@Table(name = "negocios", uniqueConstraints = {
        @UniqueConstraint(columnNames = "nombre")
})
public class Negocio {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long negocioId;
    @NotBlank(message = "El nombre del negocio no puede estar vacío.")
    private String nombre;
    @NotBlank(message = "La dirección no puede estar vacía.")
    private String direccion;
    private String descripcion;
    private String telefono;
    private String fotoPerfil;
    private String email;
    private String slogan;

    /*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy = "negocio")
    @JsonIgnore
    private Set<UsuarioNegocio> usuarioNegocios = new HashSet<>();*/

    //Un negocio puede tener muchos recursos
    /*@OneToMany(mappedBy = "negocio", cascade = CascadeType.ALL)
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
    private Set<Reserva> reservas = new LinkedHashSet<>();*/


    public Negocio() {
    }

    public Negocio(Long negocioId, String direccion, String nombre, String descripcion, String fotoPerfil, String telefono, String email, String slogan) {
        this.negocioId = negocioId;
        this.direccion = direccion;
        this.nombre = nombre;
        this.descripcion = descripcion;
        this.fotoPerfil = fotoPerfil;
        this.telefono = telefono;
        this.email = email;
        this.slogan = slogan;
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


    public String getFotoPerfil() {
        return fotoPerfil;
    }

    public void setFotoPerfil(String fotoPerfil) {
        this.fotoPerfil = fotoPerfil;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSlogan() {
        return slogan;
    }

    public void setSlogan(String slogan) {
        this.slogan = slogan;
    }
}