package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;

import com.sistema.examenes.sistema_examenes_backend.DTO.NegocioDTO;
import com.sistema.examenes.sistema_examenes_backend.DTO.ServicioDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;
import com.sistema.examenes.sistema_examenes_backend.entidades.Recurso;
import com.sistema.examenes.sistema_examenes_backend.entidades.Servicio;
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
    public List<Negocio> findAll() {
        return negocioRepository.findAll();
    }

    @Override
    public Negocio save(Negocio negocio) {
        // Verificar si el nombre ya existe
        if (negocioRepository.existsByNombre(negocio.getNombre())) {
            throw new IllegalArgumentException("El nombre del negocio ya existe.");
        }
        return negocioRepository.save(negocio);
    }


    @Override
    public Negocio update(Negocio negocio) {
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
    public void delete(Long id) {
        negocioRepository.deleteById(id);
    }


    /*@Override
    public List<ServicioDTO> obtenerServiciosPorNegocio(Long negocioId) {
        Negocio negocio = negocioRepository.findById(negocioId)
                .orElseThrow(() -> new RuntimeException("Negocio no encontrado con ID: " + negocioId));

        return negocio.getServicios().stream()
                .map(this::convertServicioToDTO)  // Debes crear este método en tu clase de implementación
                .collect(Collectors.toList());
    }

    @Override
    public NegocioDTO convertNegocioToDTO(Negocio negocio) {
        NegocioDTO dto = new NegocioDTO();
        dto.setNegocioId(negocio.getNegocioId());
        dto.setNombre(negocio.getNombre());
        dto.setDireccion(negocio.getDireccion());
        dto.setDescripcion(negocio.getDescripcion());
        dto.setTelefono(negocio.getTelefono());
        return dto;
    }

    @Override
    public Negocio convertNegocioToEntity(NegocioDTO dto) {
        Negocio negocio = new Negocio();
        negocio.setNegocioId(dto.getNegocioId());
        negocio.setNombre(dto.getNombre());
        negocio.setDireccion(dto.getDireccion());
        negocio.setDescripcion(dto.getDescripcion());
        negocio.setTelefono(dto.getTelefono());
        return negocio;
    }

    private ServicioDTO convertServicioToDTO(Servicio servicio) {
        ServicioDTO dto = new ServicioDTO();
        dto.setServicioId(servicio.getServicioId());
        dto.setNombre(servicio.getNombre());
        dto.setDescripcion(servicio.getDescripcion());
        dto.setDuracionServicio(servicio.getDuracionServicio());
        dto.setPrecio(servicio.getPrecio());
        return dto;
    }*/
}
