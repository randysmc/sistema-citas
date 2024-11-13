package com.sistema.examenes.sistema_examenes_backend.excepciones;

import com.sistema.examenes.sistema_examenes_backend.responses.ErrorResponse;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ControllerAdvice;

@ControllerAdvice
public class GlobalExceptionHandler {


    // Captura la excepción de rol existente y devuelve un mensaje de error
    @ExceptionHandler(EntityExistenteException.class)
    public ResponseEntity<ErrorResponse> handleEntityExistente(EntityExistenteException ex) {
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.BAD_REQUEST.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    // Manejo de excepción para entidad no encontrada (cuando es un solo elemento)
    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<ErrorResponse> handleEntityNotFound(EntityNotFoundException ex) {
        // Si la excepción incluye una lista de IDs no encontrados, se maneja de manera especial
        if (ex.getMessage().contains("no encontrados")) {
            ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
            return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
        }

        // Para casos de una sola entidad no encontrada (por ejemplo, un Rol)
        ErrorResponse errorResponse = new ErrorResponse(ex.getMessage(), HttpStatus.NOT_FOUND.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }

    // Manejador de excepciones generales
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ErrorResponse> handleGeneralException(Exception ex) {
        ErrorResponse errorResponse = new ErrorResponse("Ocurrió un error inesperado", HttpStatus.INTERNAL_SERVER_ERROR.value());
        return new ResponseEntity<>(errorResponse, HttpStatus.INTERNAL_SERVER_ERROR);
    }


    /*@ExceptionHandler(UsuarioExistenteException.class)
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
    }*/

}
