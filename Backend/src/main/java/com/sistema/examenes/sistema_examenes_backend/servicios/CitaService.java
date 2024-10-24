package com.sistema.examenes.sistema_examenes_backend.servicios;

import com.sistema.examenes.sistema_examenes_backend.entidades.Cita;

import java.util.List;

public interface CitaService {

    public Cita crearCita(Cita cita);

    public List<Cita> obtenerCitas();

    public List<Cita> obtenerCitaPorUsuario(Long usuarioId);

    public Cita obtenerCitaPorId(Long id);

    public Cita actualizaCita(Cita cita);

    public Cita cancelarCita(Long id);

    public List<Cita> obtenerCitaPorEmpleado(Long empleadoId);
}
