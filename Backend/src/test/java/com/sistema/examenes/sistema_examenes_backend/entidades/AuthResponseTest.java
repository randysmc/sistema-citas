package com.sistema.examenes.sistema_examenes_backend.entidades;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class AuthResponseTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        String token = "abc123";
        String username = "usuarioPrueba";
        String nombre = "Juan";
        String apellido = "Pérez";
        String email = "juan.perez@example.com";
        List<String> roles = Arrays.asList("ROLE_USER", "ROLE_ADMIN");
        List<String> negocios = Arrays.asList("Negocio1", "Negocio2");

        // Act
        AuthResponse authResponse = new AuthResponse(token, username, nombre, apellido, email, roles, negocios);

        // Assert
        assertEquals(token, authResponse.getToken());
        assertEquals(username, authResponse.getUsername());
        assertEquals(nombre, authResponse.getNombre());
        assertEquals(apellido, authResponse.getApellido());
        assertEquals(email, authResponse.getEmail());
        assertEquals(roles, authResponse.getRoles());
        assertEquals(negocios, authResponse.getNegocios());
    }

    @Test
    void testSetters() {
        // Arrange
        AuthResponse authResponse = new AuthResponse(null, null, null, null, null, null, null);
        String token = "xyz789";
        String username = "nuevoUsuario";
        String nombre = "María";
        String apellido = "López";
        String email = "maria.lopez@example.com";
        List<String> roles = Arrays.asList("ROLE_MANAGER", "ROLE_EDITOR");
        List<String> negocios = Arrays.asList("NegocioA", "NegocioB");

        // Act
        authResponse.setToken(token);
        authResponse.setUsername(username);
        authResponse.setNombre(nombre);
        authResponse.setApellido(apellido);
        authResponse.setEmail(email);
        authResponse.setRoles(roles);
        authResponse.setNegocios(negocios);

        // Assert
        assertEquals(token, authResponse.getToken());
        assertEquals(username, authResponse.getUsername());
        assertEquals(nombre, authResponse.getNombre());
        assertEquals(apellido, authResponse.getApellido());
        assertEquals(email, authResponse.getEmail());
        assertEquals(roles, authResponse.getRoles());
        assertEquals(negocios, authResponse.getNegocios());
    }

    @Test
    void testEmptyConstructorAndDefaults() {
        // Arrange
        AuthResponse authResponse = new AuthResponse(null, null, null, null, null, null, null);

        // Assert
        assertNull(authResponse.getToken(), "Default token should be null");
        assertNull(authResponse.getUsername(), "Default username should be null");
        assertNull(authResponse.getNombre(), "Default nombre should be null");
        assertNull(authResponse.getApellido(), "Default apellido should be null");
        assertNull(authResponse.getEmail(), "Default email should be null");
        assertNull(authResponse.getRoles(), "Default roles should be null");
        assertNull(authResponse.getNegocios(), "Default negocios should be null");
    }
}
