package com.sistema.examenes.sistema_examenes_backend.entidades;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class RolPermisoTest {

    @Test
    void testConstructorAndGetters() {
        // Arrange
        Rol rol = new Rol(); // Suponiendo que existe un constructor sin argumentos en Rol
        Permiso permiso = new Permiso(); // Suponiendo que existe un constructor sin argumentos en Permiso
        Long id = 1L;

        // Act
        RolPermiso rolPermiso = new RolPermiso(id, rol, permiso);

        // Assert
        assertEquals(id, rolPermiso.getId(), "El ID debería coincidir con el valor proporcionado al constructor");
        assertEquals(rol, rolPermiso.getRol(), "El rol debería coincidir con el objeto proporcionado al constructor");
        assertEquals(permiso, rolPermiso.getPermiso(), "El permiso debería coincidir con el objeto proporcionado al constructor");
    }

    @Test
    void testSetters() {
        // Arrange
        RolPermiso rolPermiso = new RolPermiso();
        Rol rol = new Rol();
        Permiso permiso = new Permiso();
        Long id = 2L;

        // Act
        rolPermiso.setId(id);
        rolPermiso.setRol(rol);
        rolPermiso.setPermiso(permiso);

        // Assert
        assertEquals(id, rolPermiso.getId(), "El ID debería coincidir con el valor establecido");
        assertEquals(rol, rolPermiso.getRol(), "El rol debería coincidir con el valor establecido");
        assertEquals(permiso, rolPermiso.getPermiso(), "El permiso debería coincidir con el valor establecido");
    }

    @Test
    void testDefaultConstructor() {
        // Act
        RolPermiso rolPermiso = new RolPermiso();

        // Assert
        assertNull(rolPermiso.getId(), "El ID debería ser null por defecto");
        assertNull(rolPermiso.getRol(), "El rol debería ser null por defecto");
        assertNull(rolPermiso.getPermiso(), "El permiso debería ser null por defecto");
    }
}
