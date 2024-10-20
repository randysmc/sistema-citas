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
    public Optional<Usuario> findById(Long id) {
        return empleadoRepository.findById(id);
    }

    @Override
    public Usuario guardarEmpleado(Usuario empleado, Set<UsuarioRol> usuarioRoles, Set<UsuarioNegocio> usuarioNegocios) throws Exception {
        Usuario empleadoLocal = empleadoRepository.findByUsername(empleado.getUsername());
        if (empleadoLocal != null) {
            throw new Exception("El empleado ya existe");
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



}
