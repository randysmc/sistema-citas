package com.sistema.examenes.sistema_examenes_backend.DTO;

public class UsuarioReporteDTO {
    private String nombre;
    private String apellido;
    private Long cantidad; // Para contar citas o sumar dinero, dependiendo del reporte

    // Constructor
    public UsuarioReporteDTO(String nombre, String apellido, Long cantidad) {
        this.nombre = nombre;
        this.apellido = apellido;
        this.cantidad = cantidad;
    }

    // Getters y setters
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getApellido() {
        return apellido;
    }

    public void setApellido(String apellido) {
        this.apellido = apellido;
    }

    public Long getCantidad() {
        return cantidad;
    }

    public void setCantidad(Long cantidad) {
        this.cantidad = cantidad;
    }
}
