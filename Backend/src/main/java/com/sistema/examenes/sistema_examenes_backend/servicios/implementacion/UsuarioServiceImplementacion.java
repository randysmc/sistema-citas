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

    /*@Autowired
    private NegocioRepository negocioRepository;*/

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;



    @Override
    public Optional<UsuarioDTO> findById(Long id) {
        return usuarioRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public UsuarioDTO guardarUsuario(UsuarioDTO usuarioDTO, Set<UsuarioRol> usuarioRoles) throws Exception {
        Usuario usuarioLocal = usuarioRepository.findByUsername(usuarioDTO.getUsername());
        if (usuarioLocal != null) {
            throw new Exception("El usuario ya existe");
        } else {
            Usuario usuario = convertToEntity(usuarioDTO);
            // Codificar la contraseña
            usuario.setPassword(bCryptPasswordEncoder.encode(usuario.getPassword()));

            // Asignar roles
            for (UsuarioRol usuarioRol : usuarioRoles) {
                usuarioRol.setUsuario(usuario); // Establecer la relación
                rolRepository.save(usuarioRol.getRol()); // Guarda el rol si es necesario
                usuario.getUsuarioRoles().add(usuarioRol); // Añade el rol al usuario
            }

            // Guardar usuario en el repositorio
            usuarioLocal = usuarioRepository.save(usuario);
        }
        return convertToDTO(usuarioLocal);
    }

    @Override
    public UsuarioDTO obtenerUsuario(String username) {
        Usuario usuario = usuarioRepository.findByUsername(username);
        return convertToDTO(usuario);
    }

    @Override
    public void eliminarUsuario(Long usuarioId) {
        usuarioRepository.deleteById(usuarioId);
    }

    @Override
    public List<UsuarioDTO> findAll() {
        return usuarioRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }


    private UsuarioDTO convertToDTO(Usuario usuario) {
        UsuarioDTO dto = new UsuarioDTO();
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
        return dto;
    }

    private Usuario convertToEntity(UsuarioDTO dto) {
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






    /*@Override
    public List<Usuario> findAll(){
        return usuarioRepository.findAll();
    }

    @Override
    public Usuario guardarUsuario(Usuario usuario, Set<UsuarioRol> usuarioRoles) throws Exception {
        Usuario usuarioLocal = usuarioRepository.findByUsername(usuario.getUsername());
        if(usuarioLocal != null){
            System.out.println("El usuario ya existe");
            throw new Exception("El usuario ya existe");
        }
        else{
            for(UsuarioRol usuarioRol:usuarioRoles){
                rolRepository.save(usuarioRol.getRol());
            }

            usuario.getUsuarioRoles().addAll(usuarioRoles);
            usuarioLocal = usuarioRepository.save(usuario);
        }
        return usuarioLocal;
    }

    @Override
    public Usuario guardarEmpleado(Usuario usuario, Set<UsuarioRol> usuarioRoles, Set<UsuarioNegocio> usuarioNegocios) throws Exception {
        Usuario usuarioEmpleado = usuarioRepository.findByUsername(usuario.getUsername());

        if (usuarioEmpleado != null) {
            System.out.println("Usuario empleado ya existente");
            throw new Exception("El usuario ya existe");
        } else {
            // Guardar roles asociados
            for (UsuarioRol usuarioRol : usuarioRoles) {
                rolRepository.save(usuarioRol.getRol());
            }

            // Guardar negocios asociados
            for (UsuarioNegocio usuarioNegocio : usuarioNegocios) {
                negocioRepository.save(usuarioNegocio.getNegocio());
            }

            // Asociar roles y negocios al usuario
            usuario.getUsuarioRoles().addAll(usuarioRoles);
            usuario.getUsuarioNegocios().addAll(usuarioNegocios);

            // Guardar usuario en el repositorio
            usuarioEmpleado = usuarioRepository.save(usuario);  // Guardar y asignar a usuarioEmpleado
        }

        return usuarioEmpleado;  // Devolver el usuario guardado
    }

    @Override
    public Usuario obtenerUsuario(String username){
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public void eliminarUsuario(Long usuarioId){
        usuarioRepository.deleteById(usuarioId);
    }*/



}
