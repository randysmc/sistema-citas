package com.sistema.examenes.sistema_examenes_backend.entidades;

public class TwoFactorRequest {
    private String username;
    private String twoFactorCode;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getTwoFactorCode() {
        return twoFactorCode;
    }

    public void setTwoFactorCode(String twoFactorCode) {
        this.twoFactorCode = twoFactorCode;
    }
}
