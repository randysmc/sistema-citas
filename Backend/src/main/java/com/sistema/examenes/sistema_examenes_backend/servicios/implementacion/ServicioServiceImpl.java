package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;

import com.sistema.examenes.sistema_examenes_backend.entidades.Servicio;
import com.sistema.examenes.sistema_examenes_backend.repositorios.ServicioRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.LinkedHashSet;
import java.util.Set;

public class ServicioServiceImpl  implements ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;


    @Override
    public Servicio agregarServicio(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    @Override
    public Servicio actualizarServicio(Servicio servicio) {
        return servicioRepository.save(servicio);
    }

    @Override
    public Set<Servicio> obtenerServicios() {
        return new LinkedHashSet<>(servicioRepository.findAll());
    }

    @Override
    public Servicio obtenerServicio(Long servicioId) {
        return servicioRepository.findById(servicioId).get();
    }

    @Override
    public void eliminarServicio(Long servicioId) {
        Servicio servicio = new Servicio();
        servicio.setServicioId(servicioId);
        servicioRepository.delete(servicio);
    }

    @Override
    public Set<Servicio> obtenerServiciosActivos() {
        return Set.of();
    }


}
