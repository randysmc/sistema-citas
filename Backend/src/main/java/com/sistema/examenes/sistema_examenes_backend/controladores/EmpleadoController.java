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
    public List<EmpleadoDTO> obtenerEmpleados(){
        return empleadoService.findAll();
    }

    @PostMapping("/")
    public EmpleadoDTO guardarEmpleado(@RequestBody EmpleadoDTO empleadoDTO) throws Exception {
        // Crear el conjunto para roles y negocios asociados
        Set<UsuarioRol> usuarioRoles = new HashSet<>();
        Set<UsuarioNegocio> usuarioNegocios = new HashSet<>();

        // Procesar cada rol ID desde el EmpleadoDTO
        for (Long rolId : empleadoDTO.getRoles()) {
            Optional<RolDTO> rolDTOOptional = rolService.findById(rolId);
            if (!rolDTOOptional.isPresent()) {
                throw new Exception("El rol con ID " + rolId + " no existe.");
            }

            // Convertir RolDTO a Rol y asociar
            Rol rol = rolService.convertRolToEntity(rolDTOOptional.get());
            UsuarioRol usuarioRol = new UsuarioRol();
            usuarioRol.setRol(rol);
            usuarioRoles.add(usuarioRol);
        }

        // Procesar cada negocio ID desde el EmpleadoDTO
        for (Long negocioId : empleadoDTO.getNegocios()) {
            Optional<NegocioDTO> negocioDTOOptional = negocioService.findById(negocioId);
            if (!negocioDTOOptional.isPresent()) {
                throw new Exception("El negocio con ID " + negocioId + " no existe.");
            }

            // Convertir NegocioDTO a Negocio y asociar
            Negocio negocio = negocioService.convertNegocioToEntity(negocioDTOOptional.get());
            UsuarioNegocio usuarioNegocio = new UsuarioNegocio();
            usuarioNegocio.setNegocio(negocio);
            usuarioNegocios.add(usuarioNegocio);
        }

        // Llamar al servicio para guardar el empleado con los roles y negocios asociados
        return empleadoService.guardarEmpleado(empleadoDTO, usuarioRoles, usuarioNegocios);
    }




    /*@PostMapping("/")
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
            //Negocio negocio = negocioService.findById(negocioId).orElseThrow(() -> new Exception("Negocio no entonct"));
            Negocio negocio = negocioService.findById(negocioId).orElseThrow(() -> new Exception("Negocio no encontrado"));
            UsuarioNegocio usuarioNegocio = new UsuarioNegocio();
            usuarioNegocio.setUsuario(empleado);
            usuarioNegocio.setNegocio(negocio);
            usuarioNegocios.add(usuarioNegocio);
        }

        // Guardar el empleado usando UsuarioService
        return usuarioService.guardarEmpleado(empleado, usuarioRoles, usuarioNegocios);
    }*/
}


