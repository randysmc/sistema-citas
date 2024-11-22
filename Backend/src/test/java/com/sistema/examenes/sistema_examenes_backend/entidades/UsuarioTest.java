package com.sistema.examenes.sistema_examenes_backend.entidades;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.test.context.ActiveProfiles;

import java.util.Collection;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class UsuarioTest {

    private Usuario usuario;

    @BeforeEach
    void setUp() {
        // Inicializar un objeto Usuario para las pruebas
        usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("juanito");
        usuario.setPassword("password123");
        usuario.setNombre("Juan");
        usuario.setApellido("Pérez");
        usuario.setEmail("juanito@example.com");
        usuario.setNit("123456789");
        usuario.setCui("987654321");
        usuario.setEnabled(true);
        usuario.setPerfil("admin");
        // Añadir roles para probar getAuthorities
    }

    @Test
    void testGetUsername() {
        assertEquals("juanito", usuario.getUsername());
    }


    @Test
    void testIsAccountNonExpired() {
        assertTrue(usuario.isAccountNonExpired());
    }

    @Test
    void testIsAccountNonLocked() {
        assertTrue(usuario.isAccountNonLocked());
    }

    @Test
    void testIsCredentialsNonExpired() {
        assertTrue(usuario.isCredentialsNonExpired());
    }

    @Test
    void testIsEnabled() {
        assertTrue(usuario.isEnabled());
    }

    @Test
    void testIsTfa() {
        assertTrue(usuario.isTfa()); // Verifica si el valor de tfa es verdadero
    }

    @Test
    void testSetTfa() {
        usuario.setTfa(false);  // Establecer tfa como false
        assertFalse(usuario.isTfa());  // Verificar si el valor de tfa es falso
    }

    @Test
    void testConstructorConParametros() {
        // Crear un nuevo objeto Usuario usando el constructor con parámetros
        Usuario nuevoUsuario = new Usuario(2L, "maria", "password456", "María", "Gómez", "maria@example.com", "987654321", true, "empleado", "987654", "321654", false);

        // Verificar que el objeto se creó correctamente
        assertEquals(2L, nuevoUsuario.getId());
        assertEquals("maria", nuevoUsuario.getUsername());
        assertEquals("password456", nuevoUsuario.getPassword());
        assertEquals("María", nuevoUsuario.getNombre());
        assertEquals("Gómez", nuevoUsuario.getApellido());
        assertEquals("maria@example.com", nuevoUsuario.getEmail());
        assertEquals("987654321", nuevoUsuario.getTelefono());
        assertTrue(nuevoUsuario.isEnabled());
        assertEquals("empleado", nuevoUsuario.getPerfil());
        assertEquals("987654", nuevoUsuario.getNit());
        assertEquals("321654", nuevoUsuario.getCui());
        assertFalse(nuevoUsuario.isTfa()); // Verificar si tfa es false en este caso
    }

    @Test
    void testSettersAndGetters() {
        usuario.setUsername("juanito_test");
        usuario.setPassword("newpassword");
        usuario.setNombre("Juanito");
        usuario.setApellido("Lopez");
        usuario.setEmail("juanito_test@example.com");
        usuario.setTelefono("123456789");

        assertEquals("juanito_test", usuario.getUsername());
        assertEquals("newpassword", usuario.getPassword());
        assertEquals("Juanito", usuario.getNombre());
        assertEquals("Lopez", usuario.getApellido());
        assertEquals("juanito_test@example.com", usuario.getEmail());
        assertEquals("123456789", usuario.getTelefono());
    }
}
