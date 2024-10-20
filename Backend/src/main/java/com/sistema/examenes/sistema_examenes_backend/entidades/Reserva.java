package com.sistema.examenes.sistema_examenes_backend.entidades;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "reservas")

public class Reserva {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long reservaId;

    private LocalDate fecha;
    private LocalTime horaInicio;
    private LocalTime horaFin;

    @ManyToOne
    private Cita cita;

    @ManyToOne
    private Negocio negocio;

    @ManyToOne
    private Recurso recurso;

    @ManyToOne
    private  Usuario empleado;


}
