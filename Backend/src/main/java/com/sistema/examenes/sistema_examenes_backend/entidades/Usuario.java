package com.sistema.examenes.sistema_examenes_backend.entidades;

import com.fasterxml.jackson.annotation.JsonIgnore;
//import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
//import com.fasterxml.jackson.annotation.JsonManagedReference;
//import com.sistema.examenes.sistema_examenes_backend.DTO.NegocioDTO;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import javax.persistence.*;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Entity
@Table(name = "usuarios", uniqueConstraints = {
        @UniqueConstraint(columnNames = "username"),
        @UniqueConstraint(columnNames = "email"),
        @UniqueConstraint(columnNames = "nit"),
        @UniqueConstraint(columnNames = "cui")
})
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotBlank(message = "El nombre de usuario no puede estar vacío.")
    private String username;
    @NotBlank(message = "Password no puede estar vacío.")
    private String password;
    @NotBlank(message = "El nombre no puede estar vacío.")
    private String nombre;
    @NotBlank(message = "El apellido no puede estar vacío.")
    private String apellido;
    @Email(message = "El email debe ser válido.")
    @NotBlank(message = "El email no puede estar vacío.")
    private String email;
    private String telefono;
    private boolean enabled = true;
    private String perfil;
    @NotBlank(message = "El NIT no puede estar vacío.")
    private String nit;
    @NotBlank(message = "El CUI no puede estar vacío.")
    private String cui;
    private boolean tfa = true;




    @OneToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER,mappedBy = "usuario")
    @JsonIgnore
    private Set<UsuarioRol> usuarioRoles = new HashSet<>();


    /*@OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "cliente")
    @JsonIgnore
    private Set<Cita> citasComoCliente = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "empleado")
    @JsonIgnore
    private Set<Cita> citasComoEmpleado = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "empleado")
    @JsonIgnore
    private Set<Reserva> reservasComoEmpleado = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "cliente")
    @JsonIgnore
    private Set<Reserva> reservasComoCliente = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "cliente")
    @JsonIgnore
    private Set<Comprobante> comprobanteCliente = new HashSet<>();

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER, mappedBy = "cliente")
    @JsonIgnore
    private Set<Factura> facturaCliente = new HashSet<>();*/

    public Usuario() {
    }

    public Usuario(Long id, String username, String password, String nombre, String apellido, String email, String telefono, Boolean enabled, String perfil, String nit, String cui, Boolean tfa) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.nombre = nombre;
        this.apellido = apellido;
        this.email = email;
        this.telefono = telefono;
        this.enabled = enabled;
        this.perfil = perfil;
        this.nit = nit;
        this.cui = cui;
        this.tfa = tfa;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<Authority> autoridades = new HashSet<>();
        this.usuarioRoles.forEach(usuarioRol -> {
            autoridades.add(new Authority(usuarioRol.getRol().getRolNombre()));
        });
        return autoridades;
    }


    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
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

    public Set<UsuarioRol> getUsuarioRoles() {
        return usuarioRoles;
    }

    public void setUsuarioRoles(Set<UsuarioRol> usuarioRoles) {
        this.usuarioRoles = usuarioRoles;
    }

    public String getNit() {
        return nit;
    }

    public void setNit(String nit) {
        this.nit = nit;
    }

    public String getCui() {
        return cui;
    }

    public void setCui(String cui) {
        this.cui = cui;
    }

    public boolean isTfa() {
        return tfa;
    }

    public void setTfa(boolean tfa) {
        this.tfa = tfa;
    }


    /*public Set<Cita> getCitasComoCliente() {
        return citasComoCliente;
    }

    public void setCitasComoCliente(Set<Cita> citasComoCliente) {
        this.citasComoCliente = citasComoCliente;
    }

    public Set<Cita> getCitasComoEmpleado() {
        return citasComoEmpleado;
    }

    public void setCitasComoEmpleado(Set<Cita> citasComoEmpleado) {
        this.citasComoEmpleado = citasComoEmpleado;
    }

    public Set<Reserva> getReservasComoEmpleado() {
        return reservasComoEmpleado;
    }

    public void setReservasComoEmpleado(Set<Reserva> reservasComoEmpleado) {
        this.reservasComoEmpleado = reservasComoEmpleado;
    }

    public Set<Reserva> getReservasComoCliente() {
        return reservasComoCliente;
    }

    public void setReservasComoCliente(Set<Reserva> reservasComoCliente) {
        this.reservasComoCliente = reservasComoCliente;
    }*/


}