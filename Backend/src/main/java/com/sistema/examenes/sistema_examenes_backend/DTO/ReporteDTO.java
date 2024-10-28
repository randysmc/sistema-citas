package com.sistema.examenes.sistema_examenes_backend.DTO;

import java.util.Map;

public class ReporteDTO {

    private Map<String, Object> detalles;

    public ReporteDTO(Map<String, Object> detalles) {
        this.detalles = detalles;
    }

    public Map<String, Object> getDetalles() {
        return detalles;
    }

    public void setDetalles(Map<String, Object> detalles) {
        this.detalles = detalles;
    }
}
