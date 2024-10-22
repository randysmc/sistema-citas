package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;

import com.sistema.examenes.sistema_examenes_backend.DTO.UsuarioDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Rol;
import com.sistema.examenes.sistema_examenes_backend.entidades.Usuario;
import com.sistema.examenes.sistema_examenes_backend.entidades.UsuarioNegocio;
import com.sistema.examenes.sistema_examenes_backend.entidades.UsuarioRol;
import com.sistema.examenes.sistema_examenes_backend.excepciones.UsuarioExistenteException;
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
        if (usuarioRepository.findByUsername(usuario.getUsername()) != null) {
            throw new UsuarioExistenteException("El nombre de usuario ya existe");
        }

        if (usuarioRepository.findByEmail(usuario.getEmail()) != null) {
            throw new UsuarioExistenteException("El correo electrónico ya existe");
        }

        if (usuarioRepository.findByNit(usuario.getNit()) != null) {
            throw new UsuarioExistenteException("El NIT ya existe");
        }

        if (usuarioRepository.findByCui(usuario.getCui()) != null) {
            throw new UsuarioExistenteException("El CUI ya existe");
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
    public List<Usuario> obtenerUsuarios() {
        return usuarioRepository.findAll();
    }


    @Override
    public Usuario actualizarUsuario(Usuario usuario) throws Exception {
        // Verificar si el usuario existe
        Usuario usuarioExistente = usuarioRepository.findById(usuario.getId())
                .orElseThrow(() -> new Exception("Usuario no encontrado"));

        // Actualizar solo los campos permitidos
        usuarioExistente.setUsername(usuario.getUsername());
        usuarioExistente.setNombre(usuario.getNombre());
        usuarioExistente.setApellido(usuario.getApellido());
        usuarioExistente.setTelefono(usuario.getTelefono());
        usuarioExistente.setPerfil(usuario.getPerfil());  // Actualizar la foto de perfil
        //usuarioExistente.setCui(usuario.getCui());
        //usuarioExistente.setNit(usuario.getNit());

        // Guardar los cambios (sin modificar los roles)
        return usuarioRepository.save(usuarioExistente);
    }



    @Override
    public List<Usuario> listarUsuariosActivos() {
        return usuarioRepository.findByEnabledTrue();
    }

    @Override
    public List<Usuario> listarUsuariosNoActivos() {
        return usuarioRepository.findByEnabledFalse();
    }

    @Override
    public Usuario activarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario != null) {
            usuario.setEnabled(true); // Cambiar el estado a activo
            return usuarioRepository.save(usuario);
        }
        return null; // O lanzar una excepción si no se encuentra
    }

    @Override
    public Usuario desactivarUsuario(Long id) {
        Usuario usuario = usuarioRepository.findById(id).orElse(null);
        if (usuario != null) {
            usuario.setEnabled(false); // Cambiar el estado a inactivo
            return usuarioRepository.save(usuario);
        }
        return null; // O lanzar una excepción si no se encuentra
    }
}
