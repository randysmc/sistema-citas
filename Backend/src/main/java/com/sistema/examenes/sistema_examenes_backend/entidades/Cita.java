package com.sistema.examenes.sistema_examenes_backend.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sistema.examenes.sistema_examenes_backend.Enums.EstadoCita;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "citas")
public class Cita {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idCita;

    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    @Enumerated(EnumType.STRING)
    private EstadoCita estado;

    @ManyToOne (fetch = FetchType.EAGER)
    private Recurso recurso;

    @ManyToOne (fetch = FetchType.EAGER)
    private Servicio servicio;

    @ManyToOne (fetch = FetchType.EAGER)
    private Usuario cliente;

    @ManyToOne (fetch = FetchType.EAGER)
    private Usuario empleado;

    /*@OneToMany(mappedBy = "cita", cascade = CascadeType.ALL, fetch = FetchType.LAZY) //
    @JsonIgnore
    private Set<Reserva> reservas = new HashSet<>();*/


    public Cita() {
    }

    public Cita(Long idCita, LocalDate fecha, LocalTime horaInicio, LocalTime horaFin, EstadoCita estado, Recurso recurso, Servicio servicio, Usuario cliente, Usuario empleado) {
        this.idCita = idCita;
        this.fecha = fecha;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.estado = estado;
        this.recurso = recurso;
        this.servicio = servicio;
        this.cliente = cliente;
        this.empleado = empleado;
    }

    public Long getIdCita() {
        return idCita;
    }

    public void setIdCita(Long idCita) {
        this.idCita = idCita;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public EstadoCita getEstado() {
        return estado;
    }

    public void setEstado(EstadoCita estado) {
        this.estado = estado;
    }


    public Recurso getRecurso() {
        return recurso;
    }

    public void setRecurso(Recurso recurso) {
        this.recurso = recurso;
    }

    public Servicio getServicio() {
        return servicio;
    }

    public void setServicio(Servicio servicio) {
        this.servicio = servicio;
    }

    public Usuario getCliente() {
        return cliente;
    }

    public void setCliente(Usuario cliente) {
        this.cliente = cliente;
    }

    public Usuario getEmpleado() {
        return empleado;
    }

    public void setEmpleado(Usuario empleado) {
        this.empleado = empleado;
    }

    /*public Set<Reserva> getReservas() {
        return reservas;
    }

    public void setReservas(Set<Reserva> reservas) {
        this.reservas = reservas;
    }*/
}
