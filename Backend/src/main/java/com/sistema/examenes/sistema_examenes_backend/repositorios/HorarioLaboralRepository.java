package com.sistema.examenes.sistema_examenes_backend.repositorios;

import com.sistema.examenes.sistema_examenes_backend.Enums.DiaSemana;
import com.sistema.examenes.sistema_examenes_backend.entidades.HorarioLaboral;
import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface HorarioLaboralRepository extends JpaRepository<HorarioLaboral, Long> {
    /*public List<HorarioLaboral> findByNegocioAndDia(Negocio negocio, DiaSemana dia);

    public List<HorarioLaboral> findByNegocio_NegocioIdAndDia(Long negocioId, DiaSemana dia);

    public List<HorarioLaboral> findByNegocioAndDia(Long negocioId, DiaSemana dia);*/

    List<HorarioLaboral> findByNegocioAndDia(Negocio negocio, DiaSemana dia);

    // Método que busca horarios laborales por ID de negocio y día
    List<HorarioLaboral> findByNegocio_NegocioIdAndDia(Long negocioId, DiaSemana dia);

}
