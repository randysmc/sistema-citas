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
        // Si es recurrente, verificamos solo la fecha, ignorando el año
        if (diaFestivo.isRecurrente()) {
            Optional<DiaFestivo> diaExistente = diaFestivoRepository.findByFecha(diaFestivo.getFecha());
            if (diaExistente.isPresent()) {
                throw new IllegalArgumentException("El día festivo recurrente ya existe para la fecha: " + diaFestivo.getFecha());
            }
        } else {
            // Si no es recurrente, verificamos tanto la fecha como el año
            Optional<DiaFestivo> diaExistente = diaFestivoRepository.findByFechaAndAnyo(diaFestivo.getFecha(), diaFestivo.getAnyo());
            if (diaExistente.isPresent()) {
                throw new IllegalArgumentException("El día festivo ya existe para la fecha y año: " + diaFestivo.getFecha() + " - " + diaFestivo.getAnyo());
            }
        }

        // Si no se encontró un día festivo duplicado, lo guardamos
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

    @Override
    public List<DiaFestivo> obtenerDiasRecurrentes() {
        return diaFestivoRepository.findByRecurrenteTrue();
    }

    @Override
    public List<DiaFestivo> obtenerDiasNoRecurrentes() {
        return diaFestivoRepository.findByRecurrenteFalse();
    }
}
