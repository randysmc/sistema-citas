package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;

import com.sistema.examenes.sistema_examenes_backend.DTO.EmpleadoDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Usuario;
import com.sistema.examenes.sistema_examenes_backend.entidades.UsuarioNegocio;
import com.sistema.examenes.sistema_examenes_backend.entidades.UsuarioRol;
import com.sistema.examenes.sistema_examenes_backend.repositorios.EmpleadoRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.NegocioRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.RolRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.EmpleadoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class EmpleadoServiceImpl implements EmpleadoService {

    @Autowired
    private EmpleadoRepository empleadoRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private NegocioRepository negocioRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    @Override
    public Optional<EmpleadoDTO> findById(Long id) {
        return empleadoRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public EmpleadoDTO guardarEmpleado(EmpleadoDTO empleadoDTO, Set<UsuarioRol> usuarioRoles, Set<UsuarioNegocio> usuarioNegocios) throws Exception {
        Usuario usuarioLocal = empleadoRepository.findByUsername(empleadoDTO.getUsername());
        if (usuarioLocal != null) {
            throw new Exception("El usuario ya existe");
        } else {
            Usuario usuario = convertToEntity(empleadoDTO);
            // Codificar la contraseña
            usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));

            // Asignar roles
            for (UsuarioRol usuarioRol : usuarioRoles) {
                usuarioRol.setUsuario(usuario); // Establecer la relación
                rolRepository.save(usuarioRol.getRol()); // Guarda el rol si es necesario
                usuario.getUsuarioRoles().add(usuarioRol); // Añade el rol al usuario
            }

            // Asignar negocios
            for (UsuarioNegocio usuarioNegocio : usuarioNegocios) {
                usuarioNegocio.setUsuario(usuario); // Establecer la relación
                negocioRepository.save(usuarioNegocio.getNegocio()); // Guarda el negocio si es necesario
                usuario.getUsuarioNegocios().add(usuarioNegocio); // Añade el negocio al usuario
            }

            // Guardar usuario en el repositorio
            usuarioLocal = empleadoRepository.save(usuario);
        }
        return convertToDTO(usuarioLocal);
    }

    @Override
    public EmpleadoDTO obtenerEmpleado(String username) {
        return null;
    }

    @Override
    public void eliminarEmpleado(Long empleadoId) {

    }

    @Override
    public List<EmpleadoDTO> findAll() {
        return List.of();
    }


    private EmpleadoDTO convertToDTO(Usuario usuario) {
        EmpleadoDTO dto = new EmpleadoDTO();
        dto.setId(usuario.getId());
        dto.setUsername(usuario.getUsername());
        dto.setPassword(usuario.getPassword());
        dto.setNombre(usuario.getNombre());
        dto.setApellido(usuario.getApellido());
        dto.setEmail(usuario.getEmail());
        dto.setTelefono(usuario.getTelefono());
        dto.setEnabled(usuario.isEnabled());
        dto.setPerfil(usuario.getPerfil());
        dto.setNit(usuario.getNit());
        dto.setCui(usuario.getCui());
        dto.setTfa(usuario.isTfa());
        dto.setRoles(usuario.getUsuarioRoles().stream()
                .map(usuarioRol -> usuarioRol.getRol().getRolId())
                .collect(Collectors.toSet()));
        dto.setNegocios(usuario.getUsuarioNegocios().stream()
                .map(usuarioNegocio -> usuarioNegocio.getNegocio().getNegocioId()) // Agrega el id del negocio
                .collect(Collectors.toSet()));
        return dto;
    }

    private Usuario convertToEntity(EmpleadoDTO dto) {
        Usuario usuario = new Usuario();
        usuario.setId(dto.getId());
        usuario.setUsername(dto.getUsername());
        usuario.setPassword(dto.getPassword());
        usuario.setNombre(dto.getNombre());
        usuario.setApellido(dto.getApellido());
        usuario.setEmail(dto.getEmail());
        usuario.setTelefono(dto.getTelefono());
        usuario.setEnabled(dto.isEnabled());
        usuario.setPerfil(dto.getPerfil());
        usuario.setNit(dto.getNit());
        usuario.setCui(dto.getCui());
        usuario.setTfa(dto.isTfa());
        return usuario;
    }
}
