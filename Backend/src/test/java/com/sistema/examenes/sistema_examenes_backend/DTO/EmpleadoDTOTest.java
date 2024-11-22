package com.sistema.examenes.sistema_examenes_backend.DTO;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class EmpleadoDTOTest {

    @Test
    void testSettersAndGetters() {
        // Arrange
        EmpleadoDTO empleadoDTO = new EmpleadoDTO();
        Long id = 1L;
        String username = "empleadoUser";
        String password = "securePassword";
        String nombre = "Carlos";
        String apellido = "Sanchez";
        String email = "carlos.sanchez@example.com";
        String telefono = "987654321";
        boolean enabled = false;
        String perfil = "empleado_perfil";
        String nit = "567890123";
        String cui = "321098765";
        boolean tfa = false;
        Set<Long> roles = new HashSet<>();
        roles.add(10L);
        roles.add(20L);

        // Act
        empleadoDTO.setId(id);
        empleadoDTO.setUsername(username);
        empleadoDTO.setPassword(password);
        empleadoDTO.setNombre(nombre);
        empleadoDTO.setApellido(apellido);
        empleadoDTO.setEmail(email);
        empleadoDTO.setTelefono(telefono);
        empleadoDTO.setEnabled(enabled);
        empleadoDTO.setPerfil(perfil);
        empleadoDTO.setNit(nit);
        empleadoDTO.setCui(cui);
        empleadoDTO.setTfa(tfa);
        empleadoDTO.setRoles(roles);

        // Assert
        assertEquals(id, empleadoDTO.getId());
        assertEquals(username, empleadoDTO.getUsername());
        assertEquals(password, empleadoDTO.getPassword());
        assertEquals(nombre, empleadoDTO.getNombre());
        assertEquals(apellido, empleadoDTO.getApellido());
        assertEquals(email, empleadoDTO.getEmail());
        assertEquals(telefono, empleadoDTO.getTelefono());
        assertEquals(enabled, empleadoDTO.isEnabled());
        assertEquals(perfil, empleadoDTO.getPerfil());
        assertEquals(nit, empleadoDTO.getNit());
        assertEquals(cui, empleadoDTO.getCui());
        assertEquals(tfa, empleadoDTO.isTfa());
        assertEquals(roles, empleadoDTO.getRoles());
    }

    @Test
    void testDefaultValues() {
        // Act
        EmpleadoDTO empleadoDTO = new EmpleadoDTO();

        // Assert
        assertTrue(empleadoDTO.isEnabled(), "Default value of 'enabled' should be true");
        assertTrue(empleadoDTO.isTfa(), "Default value of 'tfa' should be true");
    }

    @Test
    void testRolesHandling() {
        // Arrange
        EmpleadoDTO empleadoDTO = new EmpleadoDTO();
        Set<Long> roles = new HashSet<>();
        roles.add(5L);
        roles.add(15L);

        // Act
        empleadoDTO.setRoles(roles);

        // Assert
        assertNotNull(empleadoDTO.getRoles());
        assertEquals(2, empleadoDTO.getRoles().size());
        assertTrue(empleadoDTO.getRoles().contains(5L));
        assertTrue(empleadoDTO.getRoles().contains(15L));
    }
}
