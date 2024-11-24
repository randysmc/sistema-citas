package com.sistema.examenes.sistema_examenes_backend.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "notificaciones")
public class Notificacion {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String mensaje;

    private boolean leido;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY ,mappedBy = "notificacion")
    @JsonIgnore
    private Set<UsuarioNotificacion> usuarioNotificaciones = new HashSet<>();

    public Notificacion(Long id, String mensaje, boolean leido, Set<UsuarioNotificacion> usuarioNotificaciones) {
        this.id = id;
        this.mensaje = mensaje;
        this.leido = leido;
        this.usuarioNotificaciones = usuarioNotificaciones;
    }


    public Notificacion() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public boolean isLeido() {
        return leido;
    }

    public void setLeido(boolean leido) {
        this.leido = leido;
    }

    public Set<UsuarioNotificacion> getUsuarioNotificaciones() {
        return usuarioNotificaciones;
    }

    public void setUsuarioNotificaciones(Set<UsuarioNotificacion> usuarioNotificaciones) {
        this.usuarioNotificaciones = usuarioNotificaciones;
    }
}
