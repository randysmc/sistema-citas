package com.sistema.examenes.sistema_examenes_backend.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.sistema.examenes.sistema_examenes_backend.Enums.DiaSemana;

import javax.persistence.*;
import java.time.LocalTime;

@Entity
@Table(name = "horarios_laborales")
public class HorarioLaboral {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long horarioLaboralId;

    @Enumerated(EnumType.STRING)
    private DiaSemana dia;
    private LocalTime horaInicio;
    private LocalTime horaFin;
    private String tipoHorario;



    public HorarioLaboral() {
    }

    public HorarioLaboral(Long horarioLaboralId, DiaSemana dia, LocalTime horaInicio, LocalTime horaFin, String tipoHorario) {
        this.horarioLaboralId = horarioLaboralId;
        this.dia = dia;
        this.horaInicio = horaInicio;
        this.horaFin = horaFin;
        this.tipoHorario = tipoHorario;

    }

    public Long getHorarioLaboralId() {
        return horarioLaboralId;
    }

    public void setHorarioLaboralId(Long horarioLaboralId) {
        this.horarioLaboralId = horarioLaboralId;
    }

    public LocalTime getHoraInicio() {
        return horaInicio;
    }

    public void setHoraInicio(LocalTime horaInicio) {
        this.horaInicio = horaInicio;
    }

    public DiaSemana getDia() {
        return dia;
    }

    public void setDia(DiaSemana dia) {
        this.dia = dia;
    }

    public LocalTime getHoraFin() {
        return horaFin;
    }

    public void setHoraFin(LocalTime horaFin) {
        this.horaFin = horaFin;
    }

    public String getTipoHorario() {
        return tipoHorario;
    }

    public void setTipoHorario(String tipoHorario) {
        this.tipoHorario = tipoHorario;
    }



}
