package com.sistema.examenes.sistema_examenes_backend.servicios;

import com.sistema.examenes.sistema_examenes_backend.entidades.Servicio;

import java.util.Set;

public interface ServicioService {

    Servicio agregarServicio(Servicio servicio);

    Servicio actualizarServicio(Servicio servicio);

    Set<Servicio> obtenerServicios();

    Servicio obtenerServicio(Long servicioId);

    void eliminarServicio(Long servicioId);

    Set<Servicio> obtenerServiciosActivos();
}
