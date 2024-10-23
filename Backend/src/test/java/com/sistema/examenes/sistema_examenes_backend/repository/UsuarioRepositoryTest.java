package com.sistema.examenes.sistema_examenes_backend.repository;

import com.sistema.examenes.sistema_examenes_backend.entidades.Usuario;
import com.sistema.examenes.sistema_examenes_backend.repositorios.UsuarioRepository;
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
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    private Usuario usuarioGlobal; // Usuario que será reutilizado en todas las pruebas

    @BeforeEach
    public void setUp() {
        // Limpiar la base de datos antes de cada prueba
        usuarioRepository.deleteAll();

        // Crear el usuario que se usará en todas las pruebas
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

        // Guardar el usuario en la base de datos
        usuarioRepository.save(usuarioGlobal);
    }

    @Test
    public void testGuardarUsuario() {
        Usuario usuarioGuardado = usuarioRepository.findByUsername("nachito");
        assertThat(usuarioGuardado).isNotNull();
        assertThat(usuarioGuardado.getUsername()).isEqualTo("nachito");
    }

    @Test
    public void testEncontrarPorEmail() {
        Usuario encontrado = usuarioRepository.findByEmail("nacho@gmail.com");
        assertThat(encontrado).isNotNull();
        assertThat(encontrado.getEmail()).isEqualTo("nacho@gmail.com");
    }

    @Test
    public void testEncontrarPorNit() {
        Usuario encontrado = usuarioRepository.findByNit("465654");
        assertThat(encontrado).isNotNull();
        assertThat(encontrado.getNit()).isEqualTo("465654");
    }

    @Test
    public void testEncontrarPorCui() {
        Usuario encontrado = usuarioRepository.findByCui("654");
        assertThat(encontrado).isNotNull();
        assertThat(encontrado.getCui()).isEqualTo("654");
    }

    @Test
    public void testListarUsuarios() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        assertThat(usuarios).isNotEmpty(); // Verificar que la lista no está vacía
        assertThat(usuarios.size()).isEqualTo(1); // Debe haber solo 1 usuario insertado
    }

    // Test para obtener usuario por ID
    @Test
    public void testObtenerUsuarioPorId() {
        Long usuarioId = usuarioGlobal.getId(); // Obtener el ID del usuario guardado

        Optional<Usuario> encontrado = usuarioRepository.findById(usuarioId);
        assertThat(encontrado).isPresent(); // Verificar que el usuario existe
        assertThat(encontrado.get().getUsername()).isEqualTo("nachito");
    }

    // Test para buscar usuarios habilitados (enabled = true)
    @Test
    public void testBuscarUsuariosHabilitados() {
        List<Usuario> usuariosHabilitados = usuarioRepository.findByEnabledTrue(); // Asume que existe el método
        assertThat(usuariosHabilitados).isNotEmpty();
        assertThat(usuariosHabilitados.get(0).isEnabled()).isTrue();
    }

    // Test para buscar usuarios deshabilitados (enabled = false)
    @Test
    public void testBuscarUsuariosDeshabilitados() {
        // Crear un usuario deshabilitado
        Usuario usuarioDeshabilitado = new Usuario();
        usuarioDeshabilitado.setNombre("John");
        usuarioDeshabilitado.setApellido("Doe");
        usuarioDeshabilitado.setUsername("johnDoe");
        usuarioDeshabilitado.setPassword("password");
        usuarioDeshabilitado.setEmail("johndoe@gmail.com");
        usuarioDeshabilitado.setTelefono("123456789");
        usuarioDeshabilitado.setNit("123456");
        usuarioDeshabilitado.setCui("654321");
        usuarioDeshabilitado.setPerfil("profile.png");
        usuarioDeshabilitado.setEnabled(false); // Usuario deshabilitado

        usuarioRepository.save(usuarioDeshabilitado);

        List<Usuario> usuariosDeshabilitados = usuarioRepository.findByEnabledFalse(); // Asume que existe el método
        assertThat(usuariosDeshabilitados).isNotEmpty();
        assertThat(usuariosDeshabilitados.get(0).isEnabled()).isFalse();
    }


}
