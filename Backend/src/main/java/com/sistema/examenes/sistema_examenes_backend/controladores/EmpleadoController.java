package com.sistema.examenes.sistema_examenes_backend.controladores;

import com.sistema.examenes.sistema_examenes_backend.DTO.EmpleadoDTO;
import com.sistema.examenes.sistema_examenes_backend.DTO.NegocioDTO;
import com.sistema.examenes.sistema_examenes_backend.DTO.RolDTO;
import com.sistema.examenes.sistema_examenes_backend.DTO.UsuarioDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.*;
import com.sistema.examenes.sistema_examenes_backend.servicios.EmpleadoService;
import com.sistema.examenes.sistema_examenes_backend.servicios.NegocioService;
import com.sistema.examenes.sistema_examenes_backend.servicios.RolService;
import com.sistema.examenes.sistema_examenes_backend.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/empleados/")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private RolService rolService;

    @Autowired
    private NegocioService negocioService;


    @GetMapping
    public List<Usuario> obtenerEmpleados() {
        return empleadoService.obtenerEmpleados();
    }

    // Guardar un nuevo empleado
    @PostMapping("/")
    public Usuario guardarEmpleado(@RequestBody EmpleadoDTO empleadoDTO) throws Exception {
        // Convertir EmpleadoDTO a la entidad Usuario
        Usuario empleado = new Usuario();
        empleado.setUsername(empleadoDTO.getUsername());
        empleado.setPassword(empleadoDTO.getPassword());  // Recuerda codificar la contraseña en el servicio
        empleado.setNombre(empleadoDTO.getNombre());
        empleado.setApellido(empleadoDTO.getApellido());
        empleado.setEmail(empleadoDTO.getEmail());
        empleado.setTelefono(empleadoDTO.getTelefono());
        empleado.setEnabled(empleadoDTO.isEnabled());
        empleado.setPerfil(empleadoDTO.getPerfil());

        // Manejar los roles
        Set<UsuarioRol> usuarioRoles = new HashSet<>();
        for (Long rolId : empleadoDTO.getRoles()) {
            UsuarioRol usuarioRol = new UsuarioRol();
            usuarioRol.setRol(rolService.findById(rolId).orElseThrow(() -> new Exception("Rol no encontrado")));
            usuarioRol.setUsuario(empleado);
            usuarioRoles.add(usuarioRol);
        }

        // Manejar los negocios
        Set<UsuarioNegocio> usuarioNegocios = new HashSet<>();
        for (Long negocioId : empleadoDTO.getNegocios()) {
            UsuarioNegocio usuarioNegocio = new UsuarioNegocio();
            usuarioNegocio.setNegocio(negocioService.findById(negocioId).orElseThrow(() -> new Exception("Negocio no encontrado")));
            usuarioNegocio.setUsuario(empleado);
            usuarioNegocios.add(usuarioNegocio);
        }

        // Guardar el empleado usando el servicio
        return empleadoService.guardarEmpleado(empleado, usuarioRoles, usuarioNegocios);
    }

    // Obtener un empleado por ID
    @GetMapping("/{id}")
    public Optional<Usuario> obtenerEmpleadoPorId(@PathVariable Long id) {
        return empleadoService.findById(id);
    }

    // Eliminar un empleado por ID
    @DeleteMapping("/{id}")
    public void eliminarEmpleado(@PathVariable Long id) {
        empleadoService.eliminarEmpleado(id);
    }
}


