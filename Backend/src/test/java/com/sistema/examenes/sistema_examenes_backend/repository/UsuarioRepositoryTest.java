package com.sistema.examenes.sistema_examenes_backend.repository;

import com.sistema.examenes.sistema_examenes_backend.entidades.Usuario;
import com.sistema.examenes.sistema_examenes_backend.repositorios.UsuarioRepository;
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
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;



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

        usuarioRepository.save(usuarioGlobal);
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
        Usuario usuarioGuardado = usuarioRepository.save(usuario1);

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

        usuarioRepository.save(usuarioGlobal);
        usuarioRepository.save(usuario1);

        //when
        List<Usuario> listaUsuarios = usuarioRepository.findAll();

        //then
        assertThat(listaUsuarios).isNotNull();
        assertThat(listaUsuarios.size()).isEqualTo(2);


    }

    @Test
    public void testObtenerUsuarioPorId() {
        //given
        usuarioRepository.save(usuarioGlobal);

        //when
        Usuario usuarioDB = usuarioRepository.findById(usuarioGlobal.getId()).get();

        //then
        assertThat(usuarioDB).isNotNull();
    }

    @Test
    public void testEncontrarPorEmail() {
        //given
        usuarioRepository.save(usuarioGlobal);

        //when
        Usuario usuarioDB = usuarioRepository.findByEmail(usuarioGlobal.getEmail());

        //then
        assertThat(usuarioDB).isNotNull();
    }

    @Test
    public void testEncontrarPorNit() {
        //given
        usuarioRepository.save(usuarioGlobal);

        //when
        Usuario usuarioDB = usuarioRepository.findByNit(usuarioGlobal.getNit());

        //then
        assertThat(usuarioDB).isNotNull();
    }

    @Test
    public void testEncontrarPorCui() {
        //given
        usuarioRepository.save(usuarioGlobal);

        //when
        Usuario usuarioDB = usuarioRepository.findByCui(usuarioGlobal.getCui());

        //then
        assertThat(usuarioDB).isNotNull();
    }


    @Test
    public void testBuscarUsuariosHabilitados() {
        // given
        usuarioRepository.save(usuarioGlobal);

        // when
        List<Usuario> usuariosHabilitados = usuarioRepository.findByEnabledTrue();

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
        usuarioRepository.save(usuarioGlobal);

        // when
        List<Usuario> usuariosDeshabilitados = usuarioRepository.findByEnabledFalse();

        // then
        assertThat(usuariosDeshabilitados).isNotEmpty();
        assertThat(usuariosDeshabilitados.get(0)).isNotNull();
        assertThat(usuariosDeshabilitados.get(0).isEnabled()).isFalse();
    }

    @Test
    public void testActualizarUsuario() {
        //given
        usuarioRepository.save(usuarioGlobal);

        //when
        Usuario usuarioGuardado = usuarioRepository.findById(usuarioGlobal.getId()).get();
        usuarioGuardado.setNombre("Jose");
        usuarioGuardado.setUsername("josias");
        usuarioGuardado.setTelefono("77554");
        Usuario usuarioActualizado = usuarioRepository.save(usuarioGuardado);

        //then
        assertThat(usuarioActualizado.getNombre()).isEqualTo("Jose");
        assertThat(usuarioActualizado.getUsername()).isEqualTo("josias");
        assertThat(usuarioActualizado.getTelefono()).isEqualTo("77554");
    }

    @Test
    public void testEliminarUsuario() {
        //given
        usuarioRepository.save(usuarioGlobal);

        //when
        usuarioRepository.deleteById(usuarioGlobal.getId());
        Optional<Usuario> usuarioOptional = usuarioRepository.findById(usuarioGlobal.getId());

        //then
        assertThat(usuarioOptional).isEmpty();
    }



    /*@Test
    public void testGuardarUsuarioConNitDuplicado() {
        // Guardar un primer usuario
        usuarioRepository.save(usuarioGlobal);

        // Intentar guardar un segundo usuario con el mismo NIT
        Usuario usuarioDuplicado = new Usuario();
        usuarioDuplicado.setNombre("Jane");
        usuarioDuplicado.setApellido("Doe");
        usuarioDuplicado.setUsername("janeDoe");
        usuarioDuplicado.setPassword("password");
        usuarioDuplicado.setEmail("janedoe@gmail.com");
        usuarioDuplicado.setTelefono("987654321");
        usuarioDuplicado.setNit("465654"); // Mismo NIT que el usuarioGlobal
        usuarioDuplicado.setCui("123456");
        usuarioDuplicado.setPerfil("profile2.png");

        assertThrows(DataIntegrityViolationException.class, () -> {
            usuarioRepository.save(usuarioDuplicado);
        });
    }





    @Test
    public void testGuardarUsuarioConUsernameDuplicado() {
        // Guardar un primer usuario
        usuarioRepository.save(usuarioGlobal);

        // Intentar guardar un segundo usuario con el mismo username
        Usuario usuarioDuplicado = new Usuario();
        usuarioDuplicado.setNombre("Jane");
        usuarioDuplicado.setApellido("Doe");
        usuarioDuplicado.setUsername(usuarioGlobal.getUsername()); // Mismo username
        usuarioDuplicado.setPassword("password");
        usuarioDuplicado.setEmail("janedoe@gmail.com");
        usuarioDuplicado.setTelefono("987654321");
        usuarioDuplicado.setNit("987654");
        usuarioDuplicado.setCui("123456");
        usuarioDuplicado.setPerfil("profile2.png");

        assertThrows(DataIntegrityViolationException.class, () -> {
            usuarioRepository.save(usuarioDuplicado);
        });
    }


    @Test
    public void testGuardarUsuarioConEmailDuplicado() {
        // Guardar un primer usuario
        usuarioRepository.save(usuarioGlobal);

        // Intentar guardar un segundo usuario con el mismo email
        Usuario usuarioDuplicado = new Usuario();
        usuarioDuplicado.setNombre("Jane");
        usuarioDuplicado.setApellido("Doe");
        usuarioDuplicado.setUsername("janeDoe");
        usuarioDuplicado.setPassword("password");
        usuarioDuplicado.setEmail(usuarioGlobal.getEmail()); // Mismo email
        usuarioDuplicado.setTelefono("987654321");
        usuarioDuplicado.setNit("987654");
        usuarioDuplicado.setCui("123456");
        usuarioDuplicado.setPerfil("profile2.png");

        assertThrows(DataIntegrityViolationException.class, () -> {
            usuarioRepository.save(usuarioDuplicado);
        });
    }

    @Test
    public void testGuardarUsuarioConCuiDuplicado() {
        // Guardar un primer usuario
        usuarioRepository.save(usuarioGlobal);

        // Intentar guardar un segundo usuario con el mismo CUI
        Usuario usuarioDuplicado = new Usuario();
        usuarioDuplicado.setNombre("Jane");
        usuarioDuplicado.setApellido("Doe");
        usuarioDuplicado.setUsername("janeDoe");
        usuarioDuplicado.setPassword("password");
        usuarioDuplicado.setEmail("janedoe@gmail.com");
        usuarioDuplicado.setTelefono("987654321");
        usuarioDuplicado.setNit("987654");
        usuarioDuplicado.setCui(usuarioGlobal.getCui()); // Mismo CUI
        usuarioDuplicado.setPerfil("profile2.png");

        assertThrows(DataIntegrityViolationException.class, () -> {
            usuarioRepository.save(usuarioDuplicado);
        });
    }

    @Test
    public void testGuardarUsuarioConContraseñaEncriptada() {
        // Crear un nuevo usuario
        Usuario usuarioParaGuardar = new Usuario();
        usuarioParaGuardar.setNombre("Jane");
        usuarioParaGuardar.setApellido("Doe");
        usuarioParaGuardar.setUsername("janeDoe");
        usuarioParaGuardar.setPassword("password"); // Contraseña en texto plano
        usuarioParaGuardar.setEmail("janedoe@gmail.com");
        usuarioParaGuardar.setTelefono("987654321");
        usuarioParaGuardar.setNit("123456");
        usuarioParaGuardar.setCui("78910");
        usuarioParaGuardar.setPerfil("profile2.png");

        // Encriptar la contraseña antes de guardar
        String contrasenaEncriptada = passwordEncoder.encode(usuarioParaGuardar.getPassword());
        usuarioParaGuardar.setPassword(contrasenaEncriptada);

        // Guardar el usuario en la base de datos
        usuarioRepository.save(usuarioParaGuardar);

        // Obtener el usuario y verificar que la contraseña esté encriptada
        Usuario usuarioGuardado = usuarioRepository.findByUsername(usuarioParaGuardar.getUsername());
        assertThat(usuarioGuardado).isNotNull(); // Verificar que el usuario ha sido guardado
        assertThat(passwordEncoder.matches("password", usuarioGuardado.getPassword())).isTrue(); // Verificar la contraseña
    }



    /*@Test
    public void testGuardarUsuarioConRol() {
        // Crear y guardar el rol
        Rol rolAdmin = new Rol();
        rolAdmin.setRolId(1L);
        rolAdmin.setRolNombre("ADMIN");
        rolRepository.save(rolAdmin); // Asegurarse de guardar el rol primero

        // Crear una relación Usuario-Rol
        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setRol(rolAdmin); // Asignar el rol ya guardado

        // Crear un usuario y asignarle la relación Usuario-Rol
        Usuario usuario = new Usuario();
        usuario.setNombre("Carlos");
        usuario.setApellido("Santana");
        usuario.setUsername("csantana");
        usuario.setPassword(passwordEncoder.encode("password"));
        usuario.setEmail("carlos.santana@example.com");
        usuario.setTelefono("123456789");
        usuario.setNit("11111111");
        usuario.setCui("22222222");
        usuario.setPerfil("profile.jpg");

        // Asignar roles al usuario
        usuarioRol.setUsuario(usuario);
        usuario.getUsuarioRoles().add(usuarioRol);

        // Guardar el usuario en el repositorio
        usuarioRepository.save(usuario);

        // Verificar que el usuario se ha guardado correctamente
        Usuario usuarioGuardado = usuarioRepository.findByUsername("csantana");
        assertThat(usuarioGuardado).isNotNull();
        assertThat(usuarioGuardado.getUsername()).isEqualTo("csantana");
        assertThat(usuarioGuardado.getUsuarioRoles()).isNotEmpty();
        assertThat(usuarioGuardado.getUsuarioRoles().iterator().next().getRol().getRolNombre()).isEqualTo("ADMIN");
    }

    @Test
    public void testGuardarUsuarioConMultiplesRoles() {
        // Crear roles y guardarlos
        Rol rolAdmin = new Rol();
        rolAdmin.setRolId(1L);
        rolAdmin.setRolNombre("ADMIN");
        rolRepository.save(rolAdmin);

        Rol rolUser = new Rol();
        rolUser.setRolId(2L);
        rolUser.setRolNombre("USER");
        rolRepository.save(rolUser);

        // Crear un usuario y asignarle múltiples relaciones Usuario-Rol
        Usuario usuario = new Usuario();
        usuario.setNombre("Luis");
        usuario.setApellido("Pérez");
        usuario.setUsername("lperez");
        usuario.setNit("11111111");
        usuario.setCui("22222222");
        usuario.setPassword(passwordEncoder.encode("password123"));
        usuario.setEmail("luis.perez@example.com");

        // Relacionar los roles con el usuario
        UsuarioRol usuarioRolAdmin = new UsuarioRol();
        usuarioRolAdmin.setUsuario(usuario);
        usuarioRolAdmin.setRol(rolAdmin);

        UsuarioRol usuarioRolUser = new UsuarioRol();
        usuarioRolUser.setUsuario(usuario);
        usuarioRolUser.setRol(rolUser);

        usuario.getUsuarioRoles().add(usuarioRolAdmin);
        usuario.getUsuarioRoles().add(usuarioRolUser);

        // Guardar el usuario con múltiples roles
        usuarioRepository.save(usuario);

        // Verificar que el usuario tiene ambos roles
        Usuario usuarioGuardado = usuarioRepository.findByUsername("lperez");
        assertThat(usuarioGuardado.getUsuarioRoles()).hasSize(2);
    }



*/


}
