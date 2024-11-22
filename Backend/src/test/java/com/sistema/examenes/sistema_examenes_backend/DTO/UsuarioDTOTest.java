package com.sistema.examenes.sistema_examenes_backend.DTO;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class UsuarioDTOTest {

    @Test
    void testSettersAndGetters() {
        // Arrange
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        Long id = 1L;
        String username = "testUser";
        String password = "password123";
        String nombre = "Juan";
        String apellido = "Perez";
        String email = "juan.perez@example.com";
        String telefono = "1234567890";
        boolean enabled = false;
        String perfil = "user_profile";
        String nit = "123456789";
        String cui = "987654321";
        boolean tfa = false;
        Set<Long> roles = new HashSet<>();
        roles.add(1L);
        roles.add(2L);

        // Act
        usuarioDTO.setId(id);
        usuarioDTO.setUsername(username);
        usuarioDTO.setPassword(password);
        usuarioDTO.setNombre(nombre);
        usuarioDTO.setApellido(apellido);
        usuarioDTO.setEmail(email);
        usuarioDTO.setTelefono(telefono);
        usuarioDTO.setEnabled(enabled);
        usuarioDTO.setPerfil(perfil);
        usuarioDTO.setNit(nit);
        usuarioDTO.setCui(cui);
        usuarioDTO.setTfa(tfa);
        usuarioDTO.setRoles(roles);

        // Assert
        assertEquals(id, usuarioDTO.getId());
        assertEquals(username, usuarioDTO.getUsername());
        assertEquals(password, usuarioDTO.getPassword());
        assertEquals(nombre, usuarioDTO.getNombre());
        assertEquals(apellido, usuarioDTO.getApellido());
        assertEquals(email, usuarioDTO.getEmail());
        assertEquals(telefono, usuarioDTO.getTelefono());
        assertEquals(enabled, usuarioDTO.isEnabled());
        assertEquals(perfil, usuarioDTO.getPerfil());
        assertEquals(nit, usuarioDTO.getNit());
        assertEquals(cui, usuarioDTO.getCui());
        assertEquals(tfa, usuarioDTO.isTfa());
        assertEquals(roles, usuarioDTO.getRoles());
    }

    @Test
    void testDefaultValues() {
        // Act
        UsuarioDTO usuarioDTO = new UsuarioDTO();

        // Assert
        assertTrue(usuarioDTO.isEnabled(), "Default value of 'enabled' should be true");
        assertTrue(usuarioDTO.isTfa(), "Default value of 'tfa' should be true");
    }

    @Test
    void testRolesHandling() {
        // Arrange
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        Set<Long> roles = new HashSet<>();
        roles.add(3L);
        roles.add(5L);

        // Act
        usuarioDTO.setRoles(roles);

        // Assert
        assertNotNull(usuarioDTO.getRoles());
        assertEquals(2, usuarioDTO.getRoles().size());
        assertTrue(usuarioDTO.getRoles().contains(3L));
        assertTrue(usuarioDTO.getRoles().contains(5L));
    }
}
