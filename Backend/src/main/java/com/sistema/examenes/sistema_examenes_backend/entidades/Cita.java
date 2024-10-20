package com.sistema.examenes.sistema_examenes_backend.entidades;

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
    private Negocio negocio;

    @ManyToOne (fetch = FetchType.EAGER)
    private Recurso recurso;

    @ManyToOne (fetch = FetchType.EAGER)
    private Servicio servicio;

    @ManyToOne (fetch = FetchType.EAGER)
    private Usuario cliente;

    @ManyToOne (fetch = FetchType.EAGER)
    private Usuario empleado;

    @OneToMany(mappedBy = "cita", cascade = CascadeType.ALL, fetch = FetchType.LAZY) //
    private Set<Reserva> reservas = new HashSet<>();



}
