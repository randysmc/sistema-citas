package com.sistema.examenes.sistema_examenes_backend.repositorios;

import com.sistema.examenes.sistema_examenes_backend.Enums.DiaSemana;
import com.sistema.examenes.sistema_examenes_backend.entidades.HorarioLaboral;
import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HorarioLaboralRepository extends JpaRepository<HorarioLaboral, Long> {
    List<HorarioLaboral> findByNegocioAndDia(Negocio negocio, DiaSemana dia);
}
