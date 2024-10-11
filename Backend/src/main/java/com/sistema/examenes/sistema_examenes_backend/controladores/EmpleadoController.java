package com.sistema.examenes.sistema_examenes_backend.controladores;

import com.sistema.examenes.sistema_examenes_backend.DTO.EmpleadoDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.*;
import com.sistema.examenes.sistema_examenes_backend.servicios.NegocioService;
import com.sistema.examenes.sistema_examenes_backend.servicios.RolService;
import com.sistema.examenes.sistema_examenes_backend.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private RolService rolService;

    @Autowired
    private NegocioService negocioService;

    @PostMapping("/")
    public Usuario guardarEmpleado(@RequestBody EmpleadoDTO empleadoDTO) throws Exception {
        // Convertir EmpleadoDTO a la entidad Usuario
        Usuario empleado = new Usuario();
        empleado.setUsername(empleadoDTO.getUsername());
        empleado.setPassword(empleadoDTO.getPassword());  // Recuerda codificar la contraseña
        empleado.setNombre(empleadoDTO.getNombre());
        empleado.setApellido(empleadoDTO.getApellido());
        empleado.setEmail(empleadoDTO.getEmail());
        empleado.setTelefono(empleadoDTO.getTelefono());
        empleado.setEnabled(empleadoDTO.isEnabled());
        empleado.setPerfil(empleadoDTO.getPerfil());

        // Manejar los roles
        Set<UsuarioRol> usuarioRoles = new HashSet<>();
        for (Long rolId : empleadoDTO.getRoles()) {
            Rol rol = rolService.findById(rolId).orElseThrow(() -> new Exception("Rol no encontrado"));
            UsuarioRol usuarioRol = new UsuarioRol();
            usuarioRol.setUsuario(empleado);
            usuarioRol.setRol(rol);
            usuarioRoles.add(usuarioRol);
        }

        // Manejar los negocios
        Set<UsuarioNegocio> usuarioNegocios = new HashSet<>();
        for (Long negocioId : empleadoDTO.getNegocios()) {
            Negocio negocio = negocioService.findById(negocioId).orElseThrow(() -> new Exception("Negocio no encontrado"));
            UsuarioNegocio usuarioNegocio = new UsuarioNegocio();
            usuarioNegocio.setUsuario(empleado);
            usuarioNegocio.setNegocio(negocio);
            usuarioNegocios.add(usuarioNegocio);
        }

        // Guardar el empleado usando UsuarioService
        return usuarioService.guardarEmpleado(empleado, usuarioRoles, usuarioNegocios);
    }
}


