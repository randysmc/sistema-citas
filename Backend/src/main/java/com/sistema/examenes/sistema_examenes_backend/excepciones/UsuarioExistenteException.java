package com.sistema.examenes.sistema_examenes_backend.excepciones;

public class UsuarioExistenteException extends RuntimeException{

    public UsuarioExistenteException(String mensaje){
        super(mensaje);
    }
}
