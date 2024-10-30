package com.sistema.examenes.sistema_examenes_backend.servicios;

//import com.sistema.examenes.sistema_examenes_backend.DTO.NegocioDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;

import java.util.List;
import java.util.Optional;

public interface NegocioService {

    Optional<Negocio> findById(Long id);

    public List<Negocio> obtenerNegocios();

    public Negocio obtenerNegocio(Long id);

    public Negocio guardarNegocio(Negocio negocio);

    public Negocio actualizarNegocio(Negocio negocio);

    public void eliminarNegocio(Long id);

    //List<NegocioDTO> obtenerServiciosPorNegocio(Long negocioId);

}
