package com.sistema.examenes.sistema_examenes_backend.excepciones;

import java.util.List;

public class EntityNotFoundException extends RuntimeException {

    // Constructor para un solo ID
    public EntityNotFoundException(String entityName, Object id) {
        super(entityName + " con ID " + id + " no encontrado");
    }

    // Constructor para múltiples IDs (cuando no se encuentran varios elementos)
    public EntityNotFoundException(String entityName, List<Object> ids) {
        super(entityName + " con los siguientes IDs no encontrados: " + ids);
    }
}
