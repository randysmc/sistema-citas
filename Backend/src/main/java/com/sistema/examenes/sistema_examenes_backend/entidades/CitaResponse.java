package com.sistema.examenes.sistema_examenes_backend.entidades;

public class CitaResponse {
    private String mensaje;
    private Cita cita;

    public CitaResponse(String mensaje, Cita cita) {
        this.mensaje = mensaje;
        this.cita = cita;
    }

    // Getters y Setters
    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public Cita getCita() {
        return cita;
    }

    public void setCita(Cita cita) {
        this.cita = cita;
    }
}
