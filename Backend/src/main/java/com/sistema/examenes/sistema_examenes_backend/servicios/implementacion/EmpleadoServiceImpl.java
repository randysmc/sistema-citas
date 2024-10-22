package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;

import com.sistema.examenes.sistema_examenes_backend.DTO.EmpleadoDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Usuario;
import com.sistema.examenes.sistema_examenes_backend.entidades.UsuarioNegocio;
import com.sistema.examenes.sistema_examenes_backend.entidades.UsuarioRol;
import com.sistema.examenes.sistema_examenes_backend.excepciones.UsuarioExistenteException;
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
    public Optional<Usuario> findById(Long id) {
        return empleadoRepository.findById(id);
    }

    @Override
    public Usuario guardarEmpleado(Usuario empleado, Set<UsuarioRol> usuarioRoles, Set<UsuarioNegocio> usuarioNegocios) throws Exception {

        if (empleadoRepository.findByUsername(empleado.getUsername()) != null) {
            throw new UsuarioExistenteException("El nombre de usuario ya existe");
        }

        if (empleadoRepository.findByEmail(empleado.getEmail()) != null) {
            throw new UsuarioExistenteException("El correo electrónico ya existe");
        }

        if (empleadoRepository.findByNit(empleado.getNit()) != null) {
            throw new UsuarioExistenteException("El NIT ya existe");
        }

        if (empleadoRepository.findByCui(empleado.getCui()) != null) {
            throw new UsuarioExistenteException("El CUI ya existe");
        }


        empleado.setPassword(bCryptPasswordEncoder.encode(empleado.getPassword()));

        // Asignar roles
        for (UsuarioRol usuarioRol : usuarioRoles) {
            usuarioRol.setUsuario(empleado);
            rolRepository.save(usuarioRol.getRol());
            empleado.getUsuarioRoles().add(usuarioRol);
        }

        // Asignar negocios
        for (UsuarioNegocio usuarioNegocio : usuarioNegocios) {
            usuarioNegocio.setUsuario(empleado);
            negocioRepository.save(usuarioNegocio.getNegocio());
            empleado.getUsuarioNegocios().add(usuarioNegocio);
        }

        return empleadoRepository.save(empleado);
    }


    @Override
    public Usuario obtenerEmpleado(String username) {
        return empleadoRepository.findByUsername(username);
    }

    @Override
    public void eliminarEmpleado(Long empleadoId) {
        empleadoRepository.deleteById(empleadoId);
    }


    @Override
    public List<Usuario> obtenerEmpleados() {
        return empleadoRepository.findAll();
    }

    @Override
    public Usuario actualizarEmpleado(Usuario empleado) throws Exception {
        Usuario empleadoExistente = empleadoRepository.findById(empleado.getId())
                .orElseThrow(() -> new Exception("Empleado no encontrado"));

        //Actualizamos los campos permitidos
        empleadoExistente.setUsername(empleado.getUsername());
        empleadoExistente.setNombre(empleado.getNombre());
        empleadoExistente.setTelefono(empleado.getTelefono());
        empleadoExistente.setPerfil(empleado.getPerfil());

        return empleadoRepository.save(empleadoExistente);
    }

    @Override
    public List<Usuario> listarEmpleadosActivos() {
        List<Usuario> todosEmpleadosActivos = empleadoRepository.findByEnabledTrue();

        // Filtrar solo aquellos con rol de EMPLEADO
        return todosEmpleadosActivos.stream()
                .filter(usuario -> usuario.getUsuarioRoles().stream()
                        .anyMatch(usuarioRol -> usuarioRol.getRol().getRolNombre().equals("EMPLEADO")))
                .collect(Collectors.toList());
    }

    @Override
    public List<Usuario> listarEmpleadosNoActivos() {
        List<Usuario> todosEmpleadosActivos = empleadoRepository.findByEnabledFalse();

        // Filtrar solo aquellos con rol de EMPLEADO
        return todosEmpleadosActivos.stream()
                .filter(usuario -> usuario.getUsuarioRoles().stream()
                        .anyMatch(usuarioRol -> usuarioRol.getRol().getRolNombre().equals("EMPLEADO")))
                .collect(Collectors.toList());
    }

    @Override
    public Usuario activarEmpleado(Long id) {
        Usuario usuario = empleadoRepository.findById(id).orElse(null);
        if (usuario != null) {
            usuario.setEnabled(true); // Cambiar el estado a activo
            return empleadoRepository.save(usuario);
        }
        return null; // O lanzar una excepción si no se encuentra
    }

    @Override
    public Usuario desactivarEmpleado(Long id) {
        Usuario usuario = empleadoRepository.findById(id).orElse(null);
        if (usuario != null) {
            usuario.setEnabled(false); // Cambiar el estado a inactivo
            return empleadoRepository.save(usuario);
        }
        return null; // O lanzar una excepción si no se encuentra
    }


}
