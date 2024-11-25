package com.sistema.examenes.sistema_examenes_backend.repository;

import com.sistema.examenes.sistema_examenes_backend.entidades.Usuario;
import com.sistema.examenes.sistema_examenes_backend.repositorios.EmpleadoRepository;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;


import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;


import static org.assertj.core.api.Assertions.assertThat;


@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class EmpleadoRepositoryTest {
    @Autowired
    private EmpleadoRepository empleadoRepository;



    private Usuario usuarioGlobal;

    @BeforeEach
    public void setUp() {
        usuarioGlobal = new Usuario();
        usuarioGlobal.setNombre("Ringo");
        usuarioGlobal.setApellido("Sum");
        usuarioGlobal.setUsername("nachito");
        usuarioGlobal.setPassword("password"); // Ajustar si hay codificación
        usuarioGlobal.setEmail("nacho@gmail.com");
        usuarioGlobal.setTelefono("7754");
        usuarioGlobal.setNit("465654");
        usuarioGlobal.setCui("654");
        usuarioGlobal.setPerfil("foto.png");

        empleadoRepository.save(usuarioGlobal);
    }

    @Test
    public void testGuardarUsuario() {
        //given
        Usuario usuario1 = new Usuario();
        usuario1.setNombre("Randy");
        usuario1.setApellido("Coyoy");
        usuario1.setUsername("randysmc");
        usuario1.setPassword("password");
        usuario1.setEmail("randysmc@gmail.com");
        usuario1.setTelefono("36532064");
        usuario1.setNit("11448");
        usuario1.setCui("2253");
        usuario1.setPerfil("foto.png");

        //when
        Usuario usuarioGuardado = empleadoRepository.save(usuario1);

        //then
        assertThat(usuarioGuardado).isNotNull();
        assertThat(usuarioGuardado.getId()).isGreaterThan(0);

    }

    @Test
    public void testListarUsuarios() {
        //given
        Usuario usuario1 = new Usuario();
        usuario1.setNombre("Randy");
        usuario1.setApellido("Coyoy");
        usuario1.setUsername("randysmc");
        usuario1.setPassword("password"); // Ajustar si hay codificación
        usuario1.setEmail("randysmc@gmail.com");
        usuario1.setTelefono("36532064");
        usuario1.setNit("11448");
        usuario1.setCui("2253");
        usuario1.setPerfil("foto.png");

        empleadoRepository.save(usuarioGlobal);
        empleadoRepository.save(usuario1);

        //when
        List<Usuario> listaUsuarios = empleadoRepository.findAll();

        //then
        assertThat(listaUsuarios).isNotNull();
        assertThat(listaUsuarios.size()).isEqualTo(2);


    }

    @Test
    public void testObtenerUsuarioPorId() {
        //given
        empleadoRepository.save(usuarioGlobal);

        //when
        Usuario usuarioDB = empleadoRepository.findById(usuarioGlobal.getId()).get();

        //then
        assertThat(usuarioDB).isNotNull();
    }

    @Test
    public void testEncontrarPorEmail() {
        //given
        empleadoRepository.save(usuarioGlobal);

        //when
        Usuario usuarioDB = empleadoRepository.findByEmail(usuarioGlobal.getEmail());

        //then
        assertThat(usuarioDB).isNotNull();
    }

    @Test
    public void testEncontrarPorNit() {
        //given
        empleadoRepository.save(usuarioGlobal);

        //when
        Usuario usuarioDB = empleadoRepository.findByNit(usuarioGlobal.getNit());

        //then
        assertThat(usuarioDB).isNotNull();
    }

    @Test
    public void testEncontrarPorCui() {
        //given
        empleadoRepository.save(usuarioGlobal);

        //when
        Usuario usuarioDB = empleadoRepository.findByCui(usuarioGlobal.getCui());

        //then
        assertThat(usuarioDB).isNotNull();
    }


    @Test
    public void testBuscarUsuariosHabilitados() {
        // given
        empleadoRepository.save(usuarioGlobal);

        // when
        List<Usuario> usuariosHabilitados = empleadoRepository.findByEnabledTrue();

        // then
        assertThat(usuariosHabilitados).isNotEmpty();
        assertThat(usuariosHabilitados.get(0)).isNotNull();
        assertThat(usuariosHabilitados.get(0).isEnabled()).isTrue();
    }


    // Test para buscar usuarios deshabilitados (enabled = false)
    @Test
    public void testBuscarUsuariosDeshabilitados() {
        // given
        usuarioGlobal.setEnabled(false);
        empleadoRepository.save(usuarioGlobal);

        // when
        List<Usuario> usuariosDeshabilitados = empleadoRepository.findByEnabledFalse();

        // then
        assertThat(usuariosDeshabilitados).isNotEmpty();
        assertThat(usuariosDeshabilitados.get(0)).isNotNull();
        assertThat(usuariosDeshabilitados.get(0).isEnabled()).isFalse();
    }

    @Test
    public void testActualizarUsuario() {
        //given
        empleadoRepository.save(usuarioGlobal);

        //when
        Usuario usuarioGuardado = empleadoRepository.findById(usuarioGlobal.getId()).get();
        usuarioGuardado.setNombre("Jose");
        usuarioGuardado.setUsername("josias");
        usuarioGuardado.setTelefono("77554");
        Usuario usuarioActualizado = empleadoRepository.save(usuarioGuardado);

        //then
        assertThat(usuarioActualizado.getNombre()).isEqualTo("Jose");
        assertThat(usuarioActualizado.getUsername()).isEqualTo("josias");
        assertThat(usuarioActualizado.getTelefono()).isEqualTo("77554");
    }

    @Test
    public void testEliminarUsuario() {
        //given
        empleadoRepository.save(usuarioGlobal);

        //when
        empleadoRepository.deleteById(usuarioGlobal.getId());
        Optional<Usuario> usuarioOptional = empleadoRepository.findById(usuarioGlobal.getId());

        //then
        assertThat(usuarioOptional).isEmpty();
    }

}
