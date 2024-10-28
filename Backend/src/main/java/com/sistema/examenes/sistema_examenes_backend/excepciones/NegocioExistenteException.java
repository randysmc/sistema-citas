package com.sistema.examenes.sistema_examenes_backend.excepciones;

public class NegocioExistenteException extends RuntimeException {
    public NegocioExistenteException(String message) {
        super(message);
    }
}
