package com.sistema.examenes.sistema_examenes_backend.servicios;

import com.sistema.examenes.sistema_examenes_backend.DTO.UsuarioDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Usuario;
import com.sistema.examenes.sistema_examenes_backend.entidades.UsuarioNegocio;
import com.sistema.examenes.sistema_examenes_backend.entidades.UsuarioRol;

import java.util.List;
import java.util.Optional;
import java.util.Set;

public interface UsuarioService {

    Optional <Usuario> findById(Long id);

    public Usuario guardarUsuario(Usuario usuario, Set<UsuarioRol> usuarioRoles) throws Exception;

    public Usuario obtenerUsuario(String username);

    public void eliminarUsuario(Long usuarioId);

    public List<Usuario> findAll();


}
