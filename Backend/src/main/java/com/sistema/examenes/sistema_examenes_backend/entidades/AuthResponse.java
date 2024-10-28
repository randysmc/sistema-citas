package com.sistema.examenes.sistema_examenes_backend.entidades;

import java.util.List;

public class AuthResponse {
    private String token;
    private String username;
    private String nombre;
    private String apellido;
    private String email;
    private List<String> roles;
    private List<String> negocios;  // Nueva lista para negocios

    public AuthResponse(String token, String username, String nombre, String apellido, String email, List<String> roles, List<String> negocios) {
        this.token = token;
        this.username = username;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.roles = roles;
        this.negocios = negocios;  // Inicializa la lista de negocios
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<String> getRoles() {
        return roles;
    }

    public void setRoles(List<String> roles) {
        this.roles = roles;
    }

    public List<String> getNegocios() {
        return negocios;
    }

    public void setNegocios(List<String> negocios) {
        this.negocios = negocios;
    }
}
