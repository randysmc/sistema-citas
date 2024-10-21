package com.sistema.examenes.sistema_examenes_backend.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name = "dias_festivos")
public class DiaFestivo {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idDiaFestivo;

    private LocalDate fecha;
    private String descripcion;
    private boolean recurrente;
    private Integer anyo;

    @ManyToOne (fetch = FetchType.EAGER)

    private Negocio negocio;

    public DiaFestivo() {
    }

    public DiaFestivo(Long idDiaFestivo, LocalDate fecha, String descripcion, boolean recurrente, Integer anyo, Negocio negocio) {
        this.idDiaFestivo = idDiaFestivo;
        this.fecha = fecha;
        this.descripcion = descripcion;
        this.recurrente = recurrente;
        this.anyo = anyo;
        this.negocio = negocio;
    }

    public Long getIdDiaFestivo() {
        return idDiaFestivo;
    }

    public void setIdDiaFestivo(Long idDiaFestivo) {
        this.idDiaFestivo = idDiaFestivo;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }

    public boolean isRecurrente() {
        return recurrente;
    }

    public void setRecurrente(boolean recurrente) {
        this.recurrente = recurrente;
    }

    public Integer getAnyo() {
        return anyo;
    }

    public void setAnyo(Integer anyo) {
        this.anyo = anyo;
    }

    public Negocio getNegocio() {
        return negocio;
    }

    public void setNegocio(Negocio negocio) {
        this.negocio = negocio;
    }


}
