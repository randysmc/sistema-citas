package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;

import com.sistema.examenes.sistema_examenes_backend.entidades.HorarioLaboral;
import com.sistema.examenes.sistema_examenes_backend.excepciones.EntityNotFoundException;
import com.sistema.examenes.sistema_examenes_backend.excepciones.HorarioExistenteException;
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
    public Optional obtenerHorarioPorId(Long id) {
        Optional<HorarioLaboral> horarioLaboral = horarioLaboralRepository.findById(id);
        if(!horarioLaboral.isPresent()){
            throw new EntityNotFoundException("Horario", id);
        }
        return horarioLaboral;

    }

    @Override
    public HorarioLaboral guardarHorario(HorarioLaboral horarioLaboral) {
        // Verificar si ya existe un horario que se traslape
        List<HorarioLaboral> horariosExistentes = horarioLaboralRepository.findAll(); // Obtener todos los horarios

        for (HorarioLaboral existente : horariosExistentes) {
            if (horariosSeTraslapan(horarioLaboral, existente)) {
                throw new HorarioExistenteException("Ya existe un horario establecido");
            }
        }

        return horarioLaboralRepository.save(horarioLaboral);
    }

    @Override
    public HorarioLaboral actualizarHorario(HorarioLaboral horarioLaboral) {
        // Primero buscamos el horario existente por el ID
        Optional<HorarioLaboral> horarioExistenteOpt = horarioLaboralRepository.findById(horarioLaboral.getHorarioLaboralId());

        if (!horarioExistenteOpt.isPresent()) {
            throw new HorarioExistenteException("Ya existe un horario establecido");
        }

        HorarioLaboral horarioExistente = horarioExistenteOpt.get();

        // Actualizamos los campos del horario laboral con los nuevos valores
        horarioExistente.setDia(horarioLaboral.getDia());
        horarioExistente.setHoraInicio(horarioLaboral.getHoraInicio());
        horarioExistente.setHoraFin(horarioLaboral.getHoraFin());
        horarioExistente.setTipoHorario(horarioLaboral.getTipoHorario());

        // Verificamos si el horario actualizado se traslapa con algún otro horario existente
        List<HorarioLaboral> horariosExistentes = horarioLaboralRepository.findAll();
        for (HorarioLaboral existente : horariosExistentes) {
            if (horariosSeTraslapan(horarioExistente, existente)) {
                throw new HorarioExistenteException("Ya existe un horario establecido");
            }
        }

        // Guardamos el horario actualizado
        return horarioLaboralRepository.save(horarioExistente);
    }

    @Override
    public void eliminarHorario(Long id) {
        Optional<HorarioLaboral> horarioLaboral = horarioLaboralRepository.findById(id);
        if (!horarioLaboral.isPresent()) {
            throw new IllegalArgumentException("El horario laboral con ID " + id + " no existe.");
        }

        // Si existe, lo eliminamos
        horarioLaboralRepository.deleteById(id);
    }



    private boolean horariosSeTraslapan(HorarioLaboral nuevo, HorarioLaboral existente) {
        return nuevo.getDia() == existente.getDia() &&
                (nuevo.getHoraInicio().isBefore(existente.getHoraFin()) &&
                        nuevo.getHoraFin().isAfter(existente.getHoraInicio()));
    }

}
