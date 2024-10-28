package com.sistema.examenes.sistema_examenes_backend.servicios;

import com.sistema.examenes.sistema_examenes_backend.DTO.ReporteDTO;
import com.sistema.examenes.sistema_examenes_backend.Enums.EstadoCita;

import java.util.List;

public interface ReporteService {


    public List<ReporteDTO> contarCitasPorCliente();

    public List<ReporteDTO> obtenerCitasPorEstado();

    // Nuevo reporte: Usuario con más citas agendadas
    List<ReporteDTO> obtenerUsuarioConMasCitasAgendadas();

    // Nuevo reporte: Usuario con más citas canceladas
    List<ReporteDTO> obtenerUsuarioConMasCitasCanceladas();

    public ReporteDTO obtenerHorariosMasSolicitados();

    public ReporteDTO obtenerFrecuenciaUsoPorDiaSemana();

    public ReporteDTO obtenerRecursosMasYMenosUtilizados();

    public ReporteDTO obtenerTasaCancelacionPorServicio();

    public ReporteDTO obtenerListaRecursosUtilizados();

    public ReporteDTO obtenerListaServiciosUtilizados();



}
