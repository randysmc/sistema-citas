package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;

import com.sistema.examenes.sistema_examenes_backend.DTO.NegocioDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;
import com.sistema.examenes.sistema_examenes_backend.entidades.Recurso;
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
    public Optional<NegocioDTO> findById(Long id) {
        return negocioRepository.findById(id).map(this::convertNegocioToDTO);
    }

    @Override
    public List<NegocioDTO> findAll() {
        return negocioRepository.findAll().stream()
                .map(this::convertNegocioToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public NegocioDTO save(NegocioDTO negocioDTO) {
        // Verificar si el nombre ya existe
        if (negocioRepository.existsByNombre(negocioDTO.getNombre())) {
            throw new IllegalArgumentException("El nombre del recurso ya existe.");
        }

        Negocio negocio = convertNegocioToEntity(negocioDTO);
        Negocio savedNegocio = negocioRepository.save(negocio);
        return convertNegocioToDTO(savedNegocio);
    }

    @Override
    public NegocioDTO update(NegocioDTO negocioDTO) {
        Negocio existingNegocio = negocioRepository.findById(negocioDTO.getNegocioId())
                .orElseThrow(() -> new RuntimeException("Negocio no encontrado con ID: " + negocioDTO.getNegocioId()));

        // Solo actualizamos los campos que vienen en la solicitud
        if (negocioDTO.getNombre() != null) {
            if (!existingNegocio.getNombre().equals(negocioDTO.getNombre()) && negocioRepository.existsByNombre(negocioDTO.getNombre())) {
                throw new IllegalArgumentException("El nombre del negocio ya existe.");
            }
            existingNegocio.setNombre(negocioDTO.getNombre());
        }

        if (negocioDTO.getDescripcion() != null) {
            existingNegocio.setDescripcion(negocioDTO.getDescripcion());
        }

        if (negocioDTO.getDireccion() != null) {
            existingNegocio.setDireccion(negocioDTO.getDireccion());
        }

        if (negocioDTO.getTelefono() != null) {
            existingNegocio.setTelefono(negocioDTO.getTelefono());
        }

        Negocio updatedNegocio = negocioRepository.save(existingNegocio);
        return convertNegocioToDTO(updatedNegocio);
    }


    @Override
    public void delete(Long id) {
        negocioRepository.deleteById(id);
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
}
