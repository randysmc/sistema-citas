package com.sistema.examenes.sistema_examenes_backend.excepciones;

public class HorarioExistenteException  extends RuntimeException{
    public HorarioExistenteException(String message) {
        super(message);
    }
}
