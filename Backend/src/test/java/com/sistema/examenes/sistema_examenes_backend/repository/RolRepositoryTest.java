package com.sistema.examenes.sistema_examenes_backend.repository;

import com.sistema.examenes.sistema_examenes_backend.entidades.Rol;
import com.sistema.examenes.sistema_examenes_backend.repositorios.RolRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.swing.text.html.Option;
import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test") // Usa el archivo application-test.properties
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // No reemplazar H2 por otra base de datos
@Transactional
public class RolRepositoryTest {

    @Autowired
    private RolRepository rolRepository;

    private Rol rolGlobal;

    @BeforeEach
    public void setUp() {
        // Inicializar el rol global antes de cada prueba
        rolRepository.deleteAll();
        rolGlobal = new Rol();
        rolGlobal.setRolNombre("ADMIN");
        rolRepository.save(rolGlobal);
    }

    @DisplayName("Test para guardar rol")
    @Test
    public void testGuardarRol() {
        //Given
        Rol rol1 = new Rol();
        rol1.setRolNombre("EMPLEADO");

        //when
        Rol rolGuardado = rolRepository.save(rol1);

        //then
        assertThat(rolGuardado).isNotNull();
        assertThat(rolGuardado.getRolId()).isGreaterThan(0);
    }

    @DisplayName("Test para obtener todos los roles")
    @Test
    public void testListarRoles() {
        //given
        Rol rol1 = new Rol();
        rol1.setRolNombre("EMPLEADO");

        rolRepository.save(rolGlobal);
        rolRepository.save(rol1);

        //when
        List<Rol> listaRoles = rolRepository.findAll();

        //then
        assertThat(listaRoles).isNotNull();
        assertThat(listaRoles.size()).isEqualTo(2);
    }

    @DisplayName("Testa para obtener Rol por Id")
    @Test
    public void testObtenerRolPorId(){
        //given
        rolRepository.save(rolGlobal);

        //when
        Rol rolBD = rolRepository.findById(rolGlobal.getRolId()).get();

        //then
        assertThat(rolBD).isNotNull();
    }



    @Test
    public void testActualizarRol() {
        //given
        rolRepository.save(rolGlobal);

        //when
        Rol rolGuardado = rolRepository.findById(rolGlobal.getRolId()).get();
        rolGuardado.setRolNombre("CLIENTE");
        Rol rolActualizado = rolRepository.save(rolGuardado);

        //then
        assertThat(rolActualizado.getRolNombre()).isEqualTo("CLIENTE");
    }

    @Test
    public void testEliminarRol() {
        //given
        rolRepository.save(rolGlobal);

        //when
        rolRepository.deleteById(rolGlobal.getRolId());
        Optional<Rol> rolOptional = rolRepository.findById(rolGlobal.getRolId());

        //then
        assertThat(rolOptional).isEmpty();
    }


}
