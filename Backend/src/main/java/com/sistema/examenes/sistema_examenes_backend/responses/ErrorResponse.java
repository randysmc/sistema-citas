package com.sistema.examenes.sistema_examenes_backend.responses;

public class ErrorResponse {

    private String mensaje;
    private int codigo;

    public ErrorResponse(String mensaje, int codigo) {
        this.mensaje = mensaje;
        this.codigo = codigo;
    }

    public ErrorResponse() {
    }

    public String getMensaje() {
        return mensaje;
    }

    public void setMensaje(String mensaje) {
        this.mensaje = mensaje;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        this.codigo = codigo;
    }
}