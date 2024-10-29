package com.sistema.examenes.sistema_examenes_backend.repository;

import com.sistema.examenes.sistema_examenes_backend.entidades.Rol;
import com.sistema.examenes.sistema_examenes_backend.repositorios.RolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test") // Usa el archivo application-test.properties
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // No reemplazar H2 por otra base de datos
public class RolRepositoryTest {

    @Autowired
    private RolRepository rolRepository;

    private Rol rolGlobal;

    @BeforeEach
    public void setUp() {
        // Inicializar el rol global antes de cada prueba
        rolGlobal = new Rol();
        rolGlobal.setRolId(1L);
        rolGlobal.setRolNombre("ROLE_ADMIN");
        rolRepository.save(rolGlobal);
    }

    @Test
    public void testGuardarRol() {
        Rol nuevoRol = new Rol();
        nuevoRol.setRolId(2L);
        nuevoRol.setRolNombre("ROLE_USER");

        Rol rolGuardado = rolRepository.save(nuevoRol);
        assertThat(rolGuardado.getRolNombre()).isEqualTo("ROLE_USER");
    }

    @Test
    public void testObtenerRolPorId() {
        Optional<Rol> rolEncontrado = rolRepository.findById(rolGlobal.getRolId());
        assertThat(rolEncontrado).isPresent();
        assertThat(rolEncontrado.get().getRolNombre()).isEqualTo("ROLE_ADMIN");
    }

    @Test
    public void testActualizarRol() {
        rolGlobal.setRolNombre("ROLE_SUPER_ADMIN");
        Rol rolActualizado = rolRepository.save(rolGlobal);

        assertThat(rolActualizado.getRolNombre()).isEqualTo("ROLE_SUPER_ADMIN");
    }

    @Test
    public void testEliminarRol() {
        rolRepository.delete(rolGlobal);
        Optional<Rol> rolEliminado = rolRepository.findById(rolGlobal.getRolId());
        assertThat(rolEliminado).isNotPresent();
    }

    @Test
    public void testObtenerTodosLosRoles() {
        // Guardar un segundo rol
        Rol otroRol = new Rol();
        otroRol.setRolId(2L);
        otroRol.setRolNombre("ROLE_USER");
        rolRepository.save(otroRol);

        // Obtener todos los roles y verificar que ambos estén presentes
        List<Rol> roles = rolRepository.findAll();
        assertThat(roles).hasSize(2);
        assertThat(roles).extracting(Rol::getRolNombre).containsExactlyInAnyOrder("ROLE_ADMIN", "ROLE_USER");
    }
}
