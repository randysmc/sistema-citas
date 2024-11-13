package com.sistema.examenes.sistema_examenes_backend.controladores;

import com.sistema.examenes.sistema_examenes_backend.entidades.Permiso;
import com.sistema.examenes.sistema_examenes_backend.responses.ResponseMessage;
import com.sistema.examenes.sistema_examenes_backend.servicios.RolPermisoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("api/roles/{rolId}/permisos")
@CrossOrigin(origins = "http://localhost:4200")
public class RolPermisoController {

    @Autowired
    private RolPermisoService rolPermisoService;

    @PostMapping("/asignar")
    public ResponseEntity<ResponseMessage<String>> asignarPermisosARol(@PathVariable Long rolId, @RequestBody List<Long> permisoIds) {
        try {
            rolPermisoService.asignarPermisosARol(rolId, permisoIds);
            String mensaje = "Permisos asignados al rol con éxito";
            ResponseMessage<String> response = new ResponseMessage<>(mensaje, "Permisos asignados");
            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (RuntimeException ex) {
            String mensaje = "Error: " + ex.getMessage();
            ResponseMessage<String> response = new ResponseMessage<>(mensaje, null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        } catch (Exception ex) {
            String mensaje = "Error al asignar permisos al rol: " + ex.getMessage();
            ResponseMessage<String> response = new ResponseMessage<>(mensaje, null);
            return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
        }
    }


    @GetMapping
    public ResponseEntity<ResponseMessage<List<Permiso>>> obtenerPermisosDeRol(@PathVariable Long rolId) {
        try {
            List<Permiso> permisos = rolPermisoService.obtenerPermisosDeRol(rolId);

            String mensaje = "Permisos del rol obtenidos exitosamente";
            ResponseMessage<List<Permiso>> response = new ResponseMessage<>(mensaje, permisos);

            return new ResponseEntity<>(response, HttpStatus.OK);
        } catch (Exception ex) {
            String mensaje = "Error al obtener los permisos del rol: " + ex.getMessage();
            ResponseMessage<List<Permiso>> response = new ResponseMessage<>(mensaje, null);
            return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
        }
    }


}
