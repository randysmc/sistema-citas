package com.sistema.examenes.sistema_examenes_backend.servicios;

import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;

import java.util.Optional;

public interface NegocioService {

    Optional<Negocio> findById(Long id);
}
