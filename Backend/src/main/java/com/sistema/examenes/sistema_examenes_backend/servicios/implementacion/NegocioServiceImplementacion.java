package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;

import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;
import com.sistema.examenes.sistema_examenes_backend.repositorios.NegocioRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.NegocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NegocioServiceImplementacion implements NegocioService {

    @Autowired
    private NegocioRepository negocioRepository;

    @Override
    public Optional<Negocio> findById(Long id) {
        return negocioRepository.findById(id);
    }
}
