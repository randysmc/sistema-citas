package com.sistema.examenes.sistema_examenes_backend.servicios;



import com.sistema.examenes.sistema_examenes_backend.entidades.HorarioLaboral;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface HorarioLaboralService {
    Optional<HorarioLaboral> findById(Long id);

    public List<HorarioLaboral> obtenerHorarios();

    public HorarioLaboral obtenerHorario(Long id);

    public HorarioLaboral guardarHorario(HorarioLaboral horarioLaboral);

    public HorarioLaboral actualizarHorario(HorarioLaboral horarioLaboral);

    public void eliminarHorario(Long id);



}
