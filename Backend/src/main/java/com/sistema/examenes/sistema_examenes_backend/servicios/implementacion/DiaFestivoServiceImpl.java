package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;


import com.sistema.examenes.sistema_examenes_backend.entidades.DiaFestivo;
import com.sistema.examenes.sistema_examenes_backend.repositorios.DiaFestivoRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.DiaFestivoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class DiaFestivoServiceImpl  implements DiaFestivoService {

    @Autowired
    private DiaFestivoRepository diaFestivoRepository;

    @Override
    public Optional<DiaFestivo> findById(Long id) {
        return diaFestivoRepository.findById(id);
    }

    @Override
    public List<DiaFestivo> obtenerDias() {
        return diaFestivoRepository.findAll();
    }

    @Override
    public DiaFestivo obtenerDia(Long id) {
        return diaFestivoRepository.findById(id).orElse(null);
    }

    @Override
    public DiaFestivo guardar(DiaFestivo diaFestivo) {
        // Verificar si ya existe un dia festivo para el mismo negocio, año y fecha
        Optional<DiaFestivo> diaExistente = diaFestivoRepository.findByNegocioAndFechaAndAnyo(
                diaFestivo.getNegocio(), diaFestivo.getFecha(), diaFestivo.getAnyo());

        if (diaExistente.isPresent()) {
            throw new IllegalArgumentException("Ya existe un día festivo para el negocio en la fecha y año proporcionados.");
        }

        return diaFestivoRepository.save(diaFestivo);
    }


    @Override
    public DiaFestivo actualizar(DiaFestivo diaFestivo) {
        return diaFestivoRepository.save(diaFestivo);
    }

    @Override
    public void eliminarDia(Long id) {
        diaFestivoRepository.deleteById(id);
    }
}
