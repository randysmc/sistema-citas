package com.sistema.examenes.sistema_examenes_backend.DTO;

import java.util.Set;

public class UsuarioResponseDTO {
    private String username;
    private String nombre;
    private String apellido;
    private String email;
    private String telefono;
    private boolean enabled;
    private String perfil;
    private Set<String> roles;      // O puedes usar Set<Long> si prefieres los IDs
    private Set<String> negocios;

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

    public String getTelefono() {
        return telefono;
    }

    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getPerfil() {
        return perfil;
    }

    public void setPerfil(String perfil) {
        this.perfil = perfil;
    }

    public Set<String> getRoles() {
        return roles;
    }

    public void setRoles(Set<String> roles) {
        this.roles = roles;
    }

    public Set<String> getNegocios() {
        return negocios;
    }

    public void setNegocios(Set<String> negocios) {
        this.negocios = negocios;
    }
}
