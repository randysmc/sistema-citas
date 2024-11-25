package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;

import com.sistema.examenes.sistema_examenes_backend.entidades.HorarioLaboral;
import com.sistema.examenes.sistema_examenes_backend.excepciones.EntityNotFoundException;
import com.sistema.examenes.sistema_examenes_backend.excepciones.HorarioExistenteException;
import com.sistema.examenes.sistema_examenes_backend.repositorios.HorarioLaboralRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.HorarioLaboralService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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

    /*@Override
    public HorarioLaboral guardarHorario(HorarioLaboral horarioLaboral) {
        // Verificar si ya existe un horario que se traslape
        List<HorarioLaboral> horariosExistentes = horarioLaboralRepository.findAll(); // Obtener todos los horarios

        for (HorarioLaboral existente : horariosExistentes) {
            if (horariosSeTraslapan(horarioLaboral, existente)) {
                throw new HorarioExistenteException("Ya existe un horario establecido");
            }
        }

        return horarioLaboralRepository.save(horarioLaboral);
    }*/

    @Override
    public HorarioLaboral guardarHorario(HorarioLaboral horarioLaboral) {
        // Validar que se envíen ambas horas
        if (horarioLaboral.getHoraInicio() == null || horarioLaboral.getHoraFin() == null) {
            throw new IllegalArgumentException("La hora de inicio y la hora de fin son obligatorias.");
        }

        // Validar que horaInicio no sea después de horaFin
        if (horarioLaboral.getHoraInicio().isAfter(horarioLaboral.getHoraFin())) {
            throw new IllegalArgumentException("La hora de inicio no puede ser después de la hora de fin.");
        }

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
        // Buscar el horario existente
        Optional<HorarioLaboral> horarioExistenteOpt = horarioLaboralRepository.findById(horarioLaboral.getHorarioLaboralId());
        if (!horarioExistenteOpt.isPresent()) {
            throw new IllegalArgumentException("El horario no existe.");
        }

        HorarioLaboral horarioExistente = horarioExistenteOpt.get();

        // Validación de horas
        LocalTime nuevaHoraInicio = horarioLaboral.getHoraInicio() != null ? horarioLaboral.getHoraInicio() : horarioExistente.getHoraInicio();
        LocalTime nuevaHoraFin = horarioLaboral.getHoraFin() != null ? horarioLaboral.getHoraFin() : horarioExistente.getHoraFin();

        if (nuevaHoraInicio.isAfter(nuevaHoraFin)) {
            throw new IllegalArgumentException("La hora de inicio no puede ser después de la hora de fin.");
        }

        // Obtener todos los horarios existentes (excepto el actual)
        List<HorarioLaboral> horariosExistentes = horarioLaboralRepository.findAll()
                .stream()
                .filter(horario -> !horario.getHorarioLaboralId().equals(horarioLaboral.getHorarioLaboralId()))
                .collect(Collectors.toList());

        // Validar traslape con otros horarios
        for (HorarioLaboral existente : horariosExistentes) {
            HorarioLaboral nuevoHorario = new HorarioLaboral();
            nuevoHorario.setDia(horarioExistente.getDia()); // El día no se cambia
            nuevoHorario.setHoraInicio(nuevaHoraInicio);
            nuevoHorario.setHoraFin(nuevaHoraFin);

            if (horariosSeTraslapan(nuevoHorario, existente)) {
                throw new HorarioExistenteException("Ya existe un horario establecido");
            }
        }

        // Actualizar solo los campos permitidos
        horarioExistente.setHoraInicio(nuevaHoraInicio);
        horarioExistente.setHoraFin(nuevaHoraFin);
        if (horarioLaboral.getTipoHorario() != null) {
            horarioExistente.setTipoHorario(horarioLaboral.getTipoHorario());
        }

        // Guardar y devolver el horario actualizado
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
