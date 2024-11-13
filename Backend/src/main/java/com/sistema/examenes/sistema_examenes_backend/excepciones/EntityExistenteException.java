package com.sistema.examenes.sistema_examenes_backend.excepciones;


public class EntityExistenteException extends RuntimeException {

    public EntityExistenteException(String entityName, String fieldName, Object fieldValue){
        super("Ya existe un(a) " + entityName + " con " + fieldName + " = " + fieldValue);
    }
}
