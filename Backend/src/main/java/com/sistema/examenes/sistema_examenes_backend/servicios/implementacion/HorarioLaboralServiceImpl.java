package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;

import com.sistema.examenes.sistema_examenes_backend.entidades.HorarioLaboral;
import com.sistema.examenes.sistema_examenes_backend.repositorios.HorarioLaboralRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.HorarioLaboralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HorarioLaboralServiceImpl implements HorarioLaboralService {

    @Autowired
    private HorarioLaboralRepository horarioLaboralRepository;


    @Override
    public Optional<HorarioLaboral> findById(Long id) {
        return horarioLaboralRepository.findById(id);
    }

    @Override
    public List<HorarioLaboral> obtenerHorarios() {
        return horarioLaboralRepository.findAll();
    }

    @Override
    public HorarioLaboral obtenerHorario(Long id) {
        return horarioLaboralRepository.findById(id).orElse(null);
    }

    @Override
    public HorarioLaboral guardarHorario(HorarioLaboral horarioLaboral) {
        // Verificar si ya existe un horario que se traslape
        List<HorarioLaboral> horariosExistentes = horarioLaboralRepository.findByNegocioAndDia(
                horarioLaboral.getNegocio(), horarioLaboral.getDia());

        for (HorarioLaboral existente : horariosExistentes) {
            if (horariosSeTraslapan(horarioLaboral, existente)) {
                throw new IllegalArgumentException("El horario laboral se traslapa con un horario existente.");
            }
        }

        return horarioLaboralRepository.save(horarioLaboral);
    }

    @Override
    public HorarioLaboral actualizarHorario(HorarioLaboral horarioLaboral) {
        return horarioLaboralRepository.save(horarioLaboral);
    }

    @Override
    public void eliminarHorario(Long id) {
        horarioLaboralRepository.deleteById(id);
    }
    private boolean horariosSeTraslapan(HorarioLaboral nuevo, HorarioLaboral existente) {
        return nuevo.getDia() == existente.getDia() &&
                (nuevo.getHoraInicio().isBefore(existente.getHoraFin()) &&
                        nuevo.getHoraFin().isAfter(existente.getHoraInicio()));
    }

}
