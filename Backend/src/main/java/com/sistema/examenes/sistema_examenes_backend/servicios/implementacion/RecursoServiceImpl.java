package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;


import com.sistema.examenes.sistema_examenes_backend.DTO.RecursoDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;
import com.sistema.examenes.sistema_examenes_backend.entidades.Recurso;
import com.sistema.examenes.sistema_examenes_backend.repositorios.NegocioRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.RecursoRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.RecursoService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class RecursoServiceImpl implements RecursoService {

    @Autowired
    private RecursoRepository recursoRepository;

    @Autowired
    private NegocioRepository negocioRepository;

    @Override
    public Optional<RecursoDTO> findById(Long id) {
        return recursoRepository.findById(id).map(this::convertRecursoToDTO);
    }

    @Override
    public List<RecursoDTO> findAll() {
        return recursoRepository.findAll().stream()
                .map(this::convertRecursoToDTO)
                .collect(Collectors.toList());
    }

    @Override
    public RecursoDTO save(RecursoDTO recursoDTO) {
        // Verificar si el nombre ya existe
        if (recursoRepository.existsByNombre(recursoDTO.getNombre())) {
            throw new IllegalArgumentException("El nombre del recurso ya existe.");
        }

        Recurso recurso = convertRecursoToEntity(recursoDTO);
        Recurso savedRecurso = recursoRepository.save(recurso);
        return convertRecursoToDTO(savedRecurso);
    }


    @Override
    public RecursoDTO update(RecursoDTO recursoDTO) {
        Recurso existingRecurso = recursoRepository.findById(recursoDTO.getRecursoId())
                .orElseThrow(() -> new RuntimeException("Recurso no encontrado con ID: " + recursoDTO.getRecursoId()));
        if(recursoDTO.getNombre() != null){
            if(!existingRecurso.getNombre().equals(recursoDTO.getNombre()) && recursoRepository.existsByNombre(recursoDTO.getNombre())){
                throw new IllegalArgumentException("El nombre del recurso ya existe");
            }
            existingRecurso.setNombre(recursoDTO.getNombre());
        }
        if(recursoDTO.getDescripcion() != null){
            existingRecurso.setDescripcion(recursoDTO.getDescripcion());
        }

        Recurso updateRecurso = recursoRepository.save(existingRecurso);
        return convertRecursoToDTO(updateRecurso);
    }



    @Override
    public void delete(Long id) {
        recursoRepository.deleteById(id);
    }

    @Override
    public RecursoDTO convertRecursoToDTO(Recurso recurso) {
        RecursoDTO dto = new RecursoDTO();
        dto.setRecursoId(recurso.getRecursoId());
        dto.setDescripcion(recurso.getDescripcion());
        dto.setNombre(recurso.getNombre());
        dto.setDisponible(recurso.isDisponible());
        dto.setNegocioId(recurso.getNegocio().getNegocioId());
        return  dto;
    }

    @Override
    public Recurso convertRecursoToEntity(RecursoDTO dto) {
        Recurso recurso = new Recurso();
        recurso.setRecursoId(dto.getRecursoId());
        recurso.setDescripcion(dto.getDescripcion());
        recurso.setDisponible(dto.isDisponible());
        recurso.setNombre(dto.getNombre());

        // Buscar el negocio por su ID y lanzar excepción si no se encuentra
        Negocio negocio = negocioRepository.findById(dto.getNegocioId())
                .orElseThrow(() -> new RuntimeException("Negocio no encontrado con ID: " + dto.getNegocioId()));

        recurso.setNegocio(negocio);

        return recurso;
    }








    /*private RecursoDTO convertirARecursoDTO(Recurso recurso) {
        return new RecursoDTO(
                recurso.getRecursoId(),
                recurso.getNombre(),
                recurso.getDescripcion(),
                recurso.isDisponible(),
                recurso.getNegocio().getNegocioId()
        );
    }

    // Método para convertir RecursoDTO a entidad Recurso
    private Recurso convertirAEntidadRecurso(RecursoDTO recursoDTO, Negocio negocio) {
        Recurso recurso = new Recurso();
        recurso.setRecursoId(recursoDTO.getRecursoId());
        recurso.setNombre(recursoDTO.getNombre());
        recurso.setDescripcion(recursoDTO.getDescripcion());
        recurso.setDisponible(recursoDTO.isDisponible());
        recurso.setNegocio(negocio);
        return recurso;
    }


    @Override
    public Recurso agregarRercurso(Recurso recurso) {
        return recursoRepository.save(recurso);
    }

    @Override
    public Recurso actualizarRecurso(Recurso recurso) {
        return recursoRepository.save(recurso);
    }

    @Override
    public Set<Recurso> obtenerRecursos() {
        return new LinkedHashSet<>(recursoRepository.findAll());
    }

    @Override
    public Recurso obtenerRecurso(Long recursoId) {
        return recursoRepository.findById(recursoId).get();
    }

    @Override
    public void eliminarRecurso(Long recursoId) {
        Recurso recurso = new Recurso();
        recurso.setRecursoId(recursoId);
        recursoRepository.delete(recurso);

    }

    @Override
    public Set<Recurso> obtenerRecursosActivos() {
        return Set.of();
    }*/
}
