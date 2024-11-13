package com.sistema.examenes.sistema_examenes_backend.controladores;


import com.sistema.examenes.sistema_examenes_backend.entidades.Permiso;
import com.sistema.examenes.sistema_examenes_backend.entidades.Rol;
import com.sistema.examenes.sistema_examenes_backend.responses.ResponseMessage;
import com.sistema.examenes.sistema_examenes_backend.servicios.PermisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/permisos/")
@CrossOrigin("http://localhost:4200")
public class PermisoController {

    @Autowired
    private PermisoService permisoService;

    @GetMapping
    public ResponseEntity<List<Permiso>> obtenerPermisos(){
        List<Permiso> permisos = permisoService.findAll();
        if(permisos.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return  new ResponseEntity<>(permisos, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ResponseMessage<Permiso>> crearPermiso(@RequestBody Permiso permiso){
        if(permiso.getNombre() == null || permiso.getNombre().isEmpty()){
            ResponseMessage<Permiso> response  = new ResponseMessage<>("El nombre del permiso no puede estar vacio", null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
        try {
            Permiso nuevoPermiso = permisoService.save(permiso);
            ResponseMessage<Permiso> response = new ResponseMessage<>("Permiso creado exitosamente", nuevoPermiso);
            return new ResponseEntity<>(response, HttpStatus.CREATED); // Retorna el rol creado con código 201
        } catch (Exception e) {
            ResponseMessage<Permiso> response = new ResponseMessage<>("Error al crear el permiso: " + e.getMessage(), null);
            return new ResponseEntity<>(response, HttpStatus.INTERNAL_SERVER_ERROR); // Retorna error 500
        }

    }
}
