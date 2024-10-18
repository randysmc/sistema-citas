package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;

import com.sistema.examenes.sistema_examenes_backend.DTO.UsuarioDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Rol;
import com.sistema.examenes.sistema_examenes_backend.entidades.Usuario;
import com.sistema.examenes.sistema_examenes_backend.entidades.UsuarioNegocio;
import com.sistema.examenes.sistema_examenes_backend.entidades.UsuarioRol;
import com.sistema.examenes.sistema_examenes_backend.repositorios.NegocioRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.RolRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.UsuarioRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
//Estereotipos
public class UsuarioServiceImplementacion implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    @Override
    public Optional<Usuario> findById(Long id) {
        return usuarioRepository.findById(id);
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario, Set<UsuarioRol> usuarioRoles) throws Exception {
        Usuario usuarioLocal = usuarioRepository.findByUsername(usuario.getUsername());
        if (usuarioLocal != null) {
            throw new Exception("El usuario ya existe");
        }

        usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));

        // Asignar roles
        for (UsuarioRol usuarioRol : usuarioRoles) {
            usuarioRol.setUsuario(usuario);
            rolRepository.save(usuarioRol.getRol());
            usuario.getUsuarioRoles().add(usuarioRol);
        }

        return usuarioRepository.save(usuario);
    }

    @Override
    public Usuario obtenerUsuario(String username) {
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public void eliminarUsuario(Long usuarioId) {
        usuarioRepository.deleteById(usuarioId);
    }

    @Override
    public List<Usuario> findAll() {
        return usuarioRepository.findAll();
    }



}
