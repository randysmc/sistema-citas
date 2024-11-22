package com.sistema.examenes.sistema_examenes_backend.DTO;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class UsuarioResponseDTOTest {

    @Test
    void testSettersAndGetters() {
        // Arrange
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        String username = "usuarioPrueba";
        String nombre = "Juan";
        String apellido = "Pérez";
        String email = "juan.perez@example.com";
        String telefono = "123456789";
        boolean enabled = true;
        String perfil = "usuario_perfil";
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        roles.add("ROLE_ADMIN");
        Set<String> negocios = new HashSet<>();
        negocios.add("Negocio1");
        negocios.add("Negocio2");

        // Act
        usuarioResponseDTO.setUsername(username);
        usuarioResponseDTO.setNombre(nombre);
        usuarioResponseDTO.setApellido(apellido);
        usuarioResponseDTO.setEmail(email);
        usuarioResponseDTO.setTelefono(telefono);
        usuarioResponseDTO.setEnabled(enabled);
        usuarioResponseDTO.setPerfil(perfil);
        usuarioResponseDTO.setRoles(roles);
        usuarioResponseDTO.setNegocios(negocios);

        // Assert
        assertEquals(username, usuarioResponseDTO.getUsername());
        assertEquals(nombre, usuarioResponseDTO.getNombre());
        assertEquals(apellido, usuarioResponseDTO.getApellido());
        assertEquals(email, usuarioResponseDTO.getEmail());
        assertEquals(telefono, usuarioResponseDTO.getTelefono());
        assertTrue(usuarioResponseDTO.isEnabled());
        assertEquals(perfil, usuarioResponseDTO.getPerfil());
        assertEquals(roles, usuarioResponseDTO.getRoles());
        assertEquals(negocios, usuarioResponseDTO.getNegocios());
    }

    @Test
    void testDefaultValues() {
        // Act
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();

        // Assert
        assertNull(usuarioResponseDTO.getUsername(), "Default username should be null");
        assertNull(usuarioResponseDTO.getNombre(), "Default nombre should be null");
        assertNull(usuarioResponseDTO.getApellido(), "Default apellido should be null");
        assertNull(usuarioResponseDTO.getEmail(), "Default email should be null");
        assertNull(usuarioResponseDTO.getTelefono(), "Default telefono should be null");
        assertFalse(usuarioResponseDTO.isEnabled(), "Default enabled should be false");
        assertNull(usuarioResponseDTO.getPerfil(), "Default perfil should be null");
        assertNull(usuarioResponseDTO.getRoles(), "Default roles should be null");
        assertNull(usuarioResponseDTO.getNegocios(), "Default negocios should be null");
    }

    @Test
    void testRolesAndNegociosHandling() {
        // Arrange
        UsuarioResponseDTO usuarioResponseDTO = new UsuarioResponseDTO();
        Set<String> roles = new HashSet<>();
        roles.add("ROLE_USER");
        roles.add("ROLE_MANAGER");
        Set<String> negocios = new HashSet<>();
        negocios.add("NegocioA");
        negocios.add("NegocioB");

        // Act
        usuarioResponseDTO.setRoles(roles);
        usuarioResponseDTO.setNegocios(negocios);

        // Assert
        assertNotNull(usuarioResponseDTO.getRoles(), "Roles should not be null");
        assertEquals(2, usuarioResponseDTO.getRoles().size());
        assertTrue(usuarioResponseDTO.getRoles().contains("ROLE_USER"));
        assertTrue(usuarioResponseDTO.getRoles().contains("ROLE_MANAGER"));

        assertNotNull(usuarioResponseDTO.getNegocios(), "Negocios should not be null");
        assertEquals(2, usuarioResponseDTO.getNegocios().size());
        assertTrue(usuarioResponseDTO.getNegocios().contains("NegocioA"));
        assertTrue(usuarioResponseDTO.getNegocios().contains("NegocioB"));
    }
}
