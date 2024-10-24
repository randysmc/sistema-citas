package com.sistema.examenes.sistema_examenes_backend.excepciones;

public class RecursoExistenteException extends RuntimeException {
    public RecursoExistenteException(String message) {
        super(message);
    }
}
