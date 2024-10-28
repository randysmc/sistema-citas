package com.sistema.examenes.sistema_examenes_backend.excepciones;

import com.sistema.examenes.sistema_examenes_backend.entidades.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(UsuarioExistenteException.class)
    public ResponseEntity<ErrorResponse> manejarUsuarioExistenteException(UsuarioExistenteException ex) {
        // Devolver el mensaje de error en formato JSON
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage(), "Asegúrate de que el nombre de usuario, email, CUI o NIT no estén duplicados."));
    }

    @ExceptionHandler(NegocioExistenteException.class)
    public ResponseEntity<ErrorResponse> manejarNegocioExistenteException(NegocioExistenteException ex) {
        // Devolver el mensaje de error en formato JSON
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage(), "Asegúrate de que el nombre de negocio no este duplicado."));
    }

    @ExceptionHandler(RecursoExistenteException.class)
    public ResponseEntity<ErrorResponse> manejarRecursoExistenteException(HorarioExistenteException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage(), "El nombre del recurso que intentas guardar ya existe"));
    }

    @ExceptionHandler(HorarioExistenteException.class)
    public ResponseEntity<ErrorResponse> manejarHorarioExistenteException(HorarioExistenteException ex) {
        return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                .body(new ErrorResponse(ex.getMessage(), "El horario que intentas guardar ya existe o se traslapa con otro."));
    }


    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> manejarGlobalException(Exception ex) {
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                .body(new ErrorResponse("Error inesperado", ex.getMessage()));
    }

}
