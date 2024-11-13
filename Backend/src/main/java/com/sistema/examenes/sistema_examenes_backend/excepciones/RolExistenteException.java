package com.sistema.examenes.sistema_examenes_backend.excepciones;

public class RolExistenteException extends RuntimeException {
    public RolExistenteException(String message) {
        super(message);
    }
}
