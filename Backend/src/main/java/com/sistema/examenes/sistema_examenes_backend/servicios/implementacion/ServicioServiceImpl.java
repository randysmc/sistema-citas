package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;

import com.sistema.examenes.sistema_examenes_backend.DTO.ServicioDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;
import com.sistema.examenes.sistema_examenes_backend.entidades.Servicio;
import com.sistema.examenes.sistema_examenes_backend.repositorios.NegocioRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.ServicioRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.ServicioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class ServicioServiceImpl  implements ServicioService {

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private NegocioRepository negocioRepository;


    @Override
    public Optional<ServicioDTO> findById(Long id) {
        return servicioRepository.findById(id).map(this::convertServicioToDTO);
    }

    @Override
    public List<ServicioDTO> findAll() {
        return servicioRepository.findAll().stream()
                .map(this::convertServicioToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public ServicioDTO save(ServicioDTO servicioDTO) {
        //Verificamos si no hay otro servicio con ese nombre
        if(servicioRepository.existsByNombre(servicioDTO.getNombre())){
            throw new IllegalArgumentException("El nombre del servicio ya existe");
        }

        Servicio servicio = convertServicioToEntity(servicioDTO);
        Servicio savedServicio = servicioRepository.save(servicio);
        return convertServicioToDTO(savedServicio);
    }

    @Override
    public ServicioDTO update(ServicioDTO servicioDTO) {
        Servicio existingServicio = servicioRepository.findById(servicioDTO.getServicioId())
                .orElseThrow(() -> new RuntimeException("Servicio no encontrado con ID: " + servicioDTO.getServicioId()));

        // Solo actualizamos los campos que vienen en la solicitud
        if (servicioDTO.getNombre() != null) {
            if (!existingServicio.getNombre().equals(servicioDTO.getNombre()) && servicioRepository.existsByNombre(servicioDTO.getNombre())) {
                throw new IllegalArgumentException("El nombre del Servicio ya existe.");
            }
            existingServicio.setNombre(servicioDTO.getNombre());
        }

        if (servicioDTO.getDescripcion() != null) {
            existingServicio.setDescripcion(servicioDTO.getDescripcion());
        }

        if(servicioDTO.getDuracionServicio() != null){
            existingServicio.setDuracionServicio(servicioDTO.getDuracionServicio());
        }

        if(servicioDTO.getPrecio() !=  null){
            existingServicio.setPrecio(servicioDTO.getPrecio());
        }


        Servicio updatedServicio = servicioRepository.save(existingServicio);
        return convertServicioToDTO(updatedServicio);
    }

    @Override
    public void delete(Long id) {

    }

    @Override
    public ServicioDTO convertServicioToDTO(Servicio servicio) {
        ServicioDTO dto = new ServicioDTO();
        dto.setServicioId(servicio.getServicioId());
        dto.setDescripcion(servicio.getDescripcion());
        dto.setNombre(servicio.getNombre());
        dto.setDuracionServicio(servicio.getDuracionServicio());
        dto.setPrecio(servicio.getPrecio());
        dto.setNegocioId(servicio.getNegocio().getNegocioId());
        return dto;
    }

    @Override
        public Servicio convertServicioToEntity(ServicioDTO dto) {
        Servicio servicio = new Servicio();
        servicio.setServicioId(dto.getServicioId());
        servicio.setDescripcion(dto.getDescripcion());
        servicio.setNombre(dto.getNombre());
        servicio.setDuracionServicio(dto.getDuracionServicio());
        servicio.setPrecio(dto.getPrecio());

        Negocio negocio = negocioRepository.findById(dto.getNegocioId())
                .orElseThrow(() -> new RuntimeException("Negocio no encontrado: " +dto.getNegocioId()));

        servicio.setNegocio(negocio);

        return servicio;
        }
}
