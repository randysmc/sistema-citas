package com.sistema.examenes.sistema_examenes_backend.controladores;

//import com.sistema.examenes.sistema_examenes_backend.DTO.RolDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Rol;
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
    public List<Rol> obtenerRoles() {
        return rolService.findAll();
    }

    @PostMapping
    public ResponseEntity<Rol> crearRol(@RequestBody Rol rol) {
        Rol nuevoRol = rolService.save(rol);
        return new ResponseEntity<>(nuevoRol, HttpStatus.CREATED);
    }
}
