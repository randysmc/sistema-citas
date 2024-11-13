package com.sistema.examenes.sistema_examenes_backend.controladores;

//import com.sistema.examenes.sistema_examenes_backend.DTO.RolDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Rol;
import com.sistema.examenes.sistema_examenes_backend.responses.ResponseMessage;
import com.sistema.examenes.sistema_examenes_backend.servicios.RolService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles/")
@CrossOrigin("*")

public class RolController {
    @Autowired
    private RolService rolService;

    @GetMapping
    public ResponseEntity<List<Rol>> obtenerRoles() {
        List<Rol> roles = rolService.findAll();
        if (roles.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Si no hay roles
        }
        return new ResponseEntity<>(roles, HttpStatus.OK); // Devuelve solo los roles
    }

    // Crear un nuevo rol
    @PostMapping
    public ResponseEntity<ResponseMessage<Rol>> crearRol(@RequestBody Rol rol) {
        if (rol.getRolNombre() == null || rol.getRolNombre().isEmpty()) {
            ResponseMessage<Rol> response = new ResponseMessage<>("El nombre del rol no puede estar vacío", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }

        try {
            Rol nuevoRol = rolService.save(rol);
            ResponseMessage<Rol> response = new ResponseMessage<>("Rol creado exitosamente", nuevoRol);
            return new ResponseEntity<>(response, HttpStatus.CREATED); // Retorna el rol creado con código 201
        } catch (Exception e) {
            ResponseMessage<Rol> response = new ResponseMessage<>("Error al crear el rol: " + e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); // Retorna error 500
        }
    }
}
