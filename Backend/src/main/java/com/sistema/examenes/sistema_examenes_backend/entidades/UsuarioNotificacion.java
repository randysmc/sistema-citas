package com.sistema.examenes.sistema_examenes_backend.entidades;

import javax.persistence.*;

@Entity
@Table(name = "usuarios_notificaciones")
public class UsuarioNotificacion {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "usuario_id", nullable = false)
    private Usuario usuario;

    @ManyToOne
    @JoinColumn(name = "notificacion_id", nullable = false)
    private Notificacion notificacion;

    public UsuarioNotificacion(Long id, Usuario usuario, Notificacion notificacion) {
        this.id = id;
        this.usuario = usuario;
        this.notificacion = notificacion;
    }

    public UsuarioNotificacion() {
    }


    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Usuario getUsuario() {
        return usuario;
    }

    public void setUsuario(Usuario usuario) {
        this.usuario = usuario;
    }

    public Notificacion getNotificacion() {
        return notificacion;
    }

    public void setNotificacion(Notificacion notificacion) {
        this.notificacion = notificacion;
    }
}
