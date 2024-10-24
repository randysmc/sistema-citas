package com.sistema.examenes.sistema_examenes_backend.servicios;

import com.sistema.examenes.sistema_examenes_backend.entidades.DiaFestivo;


import java.util.List;
import java.util.Optional;

public interface DiaFestivoService {


    Optional<DiaFestivo> findById(Long id);

    public List<DiaFestivo> obtenerDias();

    public DiaFestivo obtenerDia(Long id);

    public DiaFestivo guardar(DiaFestivo diaFestivo);

    public DiaFestivo actualizar(DiaFestivo diaFestivo);

    public void eliminarDia(Long id);

    public List<DiaFestivo> obtenerDiasRecurrentes();

    public List<DiaFestivo> obtenerDiasNoRecurrentes();
}
