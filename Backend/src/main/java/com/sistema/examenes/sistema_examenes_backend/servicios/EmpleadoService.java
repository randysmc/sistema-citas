package com.sistema.examenes.sistema_examenes_backend.servicios;

import com.sistema.examenes.sistema_examenes_backend.DTO.EmpleadoDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Usuario;
import com.sistema.examenes.sistema_examenes_backend.entidades.UsuarioNegocio;
import com.sistema.examenes.sistema_examenes_backend.entidades.UsuarioRol;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface EmpleadoService {

    Optional<Usuario> findById(Long id);

    public Usuario guardarEmpleado(Usuario empleado, Set<UsuarioRol> usuarioRoles, Set<UsuarioNegocio> usuarioNegocios) throws Exception;

    public Usuario obtenerEmpleado(String username);

    public void eliminarEmpleado(Long empleadoId);

    public List<Usuario> obtenerEmpleados();

    public Usuario actualizarEmpleado(Usuario empleado) throws  Exception;

    public List<Usuario> listarEmpleadosActivos();

    public List<Usuario> listarEmpleadosNoActivos();

    public Usuario activarEmpleado(Long id);

    public Usuario desactivarEmpleado(Long id);

}
