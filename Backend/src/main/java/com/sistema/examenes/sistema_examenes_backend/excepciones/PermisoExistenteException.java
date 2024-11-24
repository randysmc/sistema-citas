package com.sistema.examenes.sistema_examenes_backend.excepciones;

public class PermisoExistenteException extends RuntimeException {
    public PermisoExistenteException(String message) {
        super(message);
    }
}
