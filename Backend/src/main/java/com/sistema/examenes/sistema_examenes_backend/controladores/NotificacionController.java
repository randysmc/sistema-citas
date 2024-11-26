package com.sistema.examenes.sistema_examenes_backend.controladores;

import com.sistema.examenes.sistema_examenes_backend.entidades.Notificacion;
import com.sistema.examenes.sistema_examenes_backend.entidades.Permiso;
import com.sistema.examenes.sistema_examenes_backend.entidades.UsuarioNotificacion;
import com.sistema.examenes.sistema_examenes_backend.excepciones.EntityNotFoundException;
import com.sistema.examenes.sistema_examenes_backend.servicios.NotificacionService;
import com.sistema.examenes.sistema_examenes_backend.servicios.UsuarioNotificacionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/notificaciones/")
@CrossOrigin("http://localhost:4200")
public class NotificacionController {

    @Autowired
    private NotificacionService notificacionService;

    @Autowired
    private UsuarioNotificacionService usuarioNotificacionService;

    @GetMapping
    public ResponseEntity<List<Notificacion>> obtenerNotificaciones(){
        List<Notificacion> notificaciones = notificacionService.obtenerTodas();
        if(notificaciones.isEmpty()){
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(notificaciones, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Notificacion> obtenerNotificacionPorId(@PathVariable Long id) {
        try {
            Notificacion notificacion = notificacionService.findById(id).orElseThrow(() ->
                    new EntityNotFoundException("Notificacion", id));
            return new ResponseEntity<>(notificacion, HttpStatus.OK);
        } catch (EntityNotFoundException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping
    public ResponseEntity<Notificacion> crearNotificacion(@RequestBody Notificacion notificacion){
        Notificacion nuevaNotificacion = notificacionService.crearNotificacion(notificacion);
        return ResponseEntity.status(HttpStatus.CREATED).body(nuevaNotificacion);
    }

    @PostMapping("/enviar")
    public ResponseEntity<String> asignarNotificacionAUsuario(@RequestBody UsuarioNotificacion usuarioNotificacion) {
        // Delegamos la lógica al servicio, que se encarga de asignar la notificación
        usuarioNotificacionService.asignarNotificacionAUsuario(usuarioNotificacion);
        return ResponseEntity.status(HttpStatus.CREATED).body("Notificación asignada correctamente.");
    }



    @GetMapping("/usuario/{usuarioId}")
    public ResponseEntity<List<UsuarioNotificacion>> obtenerNotificacionesPorUsuario(@PathVariable Long usuarioId) {
        List<UsuarioNotificacion> usuarioNotificaciones = usuarioNotificacionService.obtenerNotificacionesPorUsuario(usuarioId);
        if (usuarioNotificaciones.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(usuarioNotificaciones, HttpStatus.OK);
    }


}
