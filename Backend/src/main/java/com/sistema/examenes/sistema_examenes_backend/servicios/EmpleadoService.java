package com.sistema.examenes.sistema_examenes_backend.servicios;

import com.sistema.examenes.sistema_examenes_backend.DTO.EmpleadoDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.UsuarioNegocio;
import com.sistema.examenes.sistema_examenes_backend.entidades.UsuarioRol;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EmpleadoService {

    Optional<EmpleadoDTO> findById(Long id);

    public EmpleadoDTO guardarEmpleado(EmpleadoDTO empleadoDTO, Set<UsuarioRol> usuarioRoles, Set<UsuarioNegocio> usuarioNegocios) throws Exception;

    public EmpleadoDTO obtenerEmpleado(String username);

    public void eliminarEmpleado(Long empleadoId);

    public List<EmpleadoDTO> findAll();
}
