package com.sistema.examenes.sistema_examenes_backend.entidades;

public class NegociosDTO {
    private Long id; // O cualquier otro identificador que necesites
    private String nombre; // O cualquier otra propiedad relevante

    public NegociosDTO() {
    }

    public NegociosDTO(Long id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }
}
