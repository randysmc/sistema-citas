package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;

import com.sistema.examenes.sistema_examenes_backend.DTO.NegocioDTO;
import com.sistema.examenes.sistema_examenes_backend.DTO.ServicioDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;
import com.sistema.examenes.sistema_examenes_backend.entidades.Recurso;
import com.sistema.examenes.sistema_examenes_backend.entidades.Servicio;
import com.sistema.examenes.sistema_examenes_backend.excepciones.NegocioExistenteException;
import com.sistema.examenes.sistema_examenes_backend.repositorios.NegocioRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.NegocioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class NegocioServiceImplementacion implements NegocioService {

    @Autowired
    private NegocioRepository negocioRepository;


    @Override
    public Optional<Negocio> findById(Long id) {
        return negocioRepository.findById(id);
    }

    @Override
    public List<Negocio> obtenerNegocios() {
        return negocioRepository.findAll();
    }

    @Override
    public Negocio obtenerNegocio(Long id) {
        return null;
    }

    @Override
    public Negocio guardarNegocio(Negocio negocio) {
        // Verificar si el nombre ya existe
        if (negocioRepository.existsByNombre(negocio.getNombre())) {
            throw new NegocioExistenteException("El nombre del negocio ya existe.");
        }
        return negocioRepository.save(negocio);
    }


    @Override
    public Negocio actualizarNegocio(Negocio negocio) {
        Negocio existingNegocio = negocioRepository.findById(negocio.getNegocioId())
                .orElseThrow(() -> new RuntimeException("Negocio no encontrado con ID: " + negocio.getNegocioId()));

        // Solo actualizamos los campos que vienen en la solicitud
        if (negocio.getNombre() != null) {
            if (!existingNegocio.getNombre().equals(negocio.getNombre()) && negocioRepository.existsByNombre(negocio.getNombre())) {
                throw new IllegalArgumentException("El nombre del negocio ya existe.");
            }
            existingNegocio.setNombre(negocio.getNombre());
        }

        if (negocio.getDescripcion() != null) {
            existingNegocio.setDescripcion(negocio.getDescripcion());
        }

        if (negocio.getDireccion() != null) {
            existingNegocio.setDireccion(negocio.getDireccion());
        }

        if (negocio.getTelefono() != null) {
            existingNegocio.setTelefono(negocio.getTelefono());
        }

        return negocioRepository.save(existingNegocio);
    }


    @Override
    public void eliminarNegocio(Long id) {
        negocioRepository.deleteById(id);
    }


}
