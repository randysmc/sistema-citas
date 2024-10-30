package com.sistema.examenes.sistema_examenes_backend.service;


import com.sistema.examenes.sistema_examenes_backend.entidades.Rol;
import com.sistema.examenes.sistema_examenes_backend.entidades.Usuario;
import com.sistema.examenes.sistema_examenes_backend.entidades.UsuarioRol;
import com.sistema.examenes.sistema_examenes_backend.excepciones.UsuarioExistenteException;
import com.sistema.examenes.sistema_examenes_backend.repositorios.RolRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.UsuarioRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.implementacion.UsuarioServiceImplementacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import static org.assertj.core.api.Assertions.assertThat;

import javax.transaction.Transactional;
import java.util.*;


import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test") // Usa el archivo application-test.properties
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // No reemplazar H2 por otra base de datos
@Transactional
public class UsuarioServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private UsuarioServiceImplementacion usuarioService;

    private Usuario usuarioGlobal;
    private Set<UsuarioRol> usuarioRoles;

    @BeforeEach
    public void setUp() {
        // Inicializa el usuario global para los tests
        usuarioGlobal = new Usuario();
        usuarioGlobal.setId(1L);
        usuarioGlobal.setUsername("usuarioEjemplo");
        usuarioGlobal.setEmail("usuario@example.com");
        usuarioGlobal.setPassword("contraseña");
        usuarioGlobal.setNit("123456789");
        usuarioGlobal.setCui("987654321");

        // Inicializa los roles del usuario
        usuarioRoles = new HashSet<>();
        Rol rol = new Rol();
        rol.setRolNombre("CLIENTE");
        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setRol(rol);
        usuarioRoles.add(usuarioRol);

        // Asigna los roles al usuario global
        usuarioGlobal.setUsuarioRoles(usuarioRoles);
    }


    @Test
    @DisplayName("Prueba para guardar un usuario con un solo rol")
    public void testGuardarUsuarioConUnRol() throws Exception{
        // Configura el comportamiento simulado
        given(usuarioRepository.findByUsername(usuarioGlobal.getUsername())).willReturn(null);
        given(usuarioRepository.findByEmail(usuarioGlobal.getEmail())).willReturn(null);
        given(usuarioRepository.findByNit(usuarioGlobal.getNit())).willReturn(null);
        given(usuarioRepository.findByCui(usuarioGlobal.getCui())).willReturn(null);
        given(usuarioRepository.save(any(Usuario.class))).willReturn(usuarioGlobal);

        // Llama al método con el usuario global y un conjunto que contiene un solo rol
        Usuario resultado = usuarioService.guardarUsuario(usuarioGlobal, usuarioRoles);

        // Verifica el resultado
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(usuarioGlobal.getId());
        assertThat(resultado.getUsername()).isEqualTo(usuarioGlobal.getUsername());
    }

    @Test
    @DisplayName("Prueba para lanzar excepción si el usuario ya existe")
    public void testGuardarUsuarioUsuarioExistente() throws Exception {
        // Configura el comportamiento simulado
        given(usuarioRepository.findByUsername(usuarioGlobal.getUsername())).willReturn(usuarioGlobal); // Simula que el usuario ya existe

        // Verifica que se lanza la excepción
        assertThrows(UsuarioExistenteException.class, () -> {
            usuarioService.guardarUsuario(usuarioGlobal, usuarioRoles);
        });
    }

    @Test
    @DisplayName("Prueba para lanzar excepción si el nombre de usuario ya existe")
    public void testGuardarUsuarioNombreExistente() throws Exception {
        // Configura el comportamiento simulado
        given(usuarioRepository.findByUsername(usuarioGlobal.getUsername())).willReturn(usuarioGlobal);

        // Verifica que se lanza la excepción
        assertThrows(UsuarioExistenteException.class, () -> {
            usuarioService.guardarUsuario(usuarioGlobal, usuarioRoles);
        });
    }

    @Test
    @DisplayName("Prueba para lanzar excepción si el correo electrónico ya existe")
    public void testGuardarUsuarioEmailExistente() throws Exception {
        // Configura el comportamiento simulado
        given(usuarioRepository.findByEmail(usuarioGlobal.getEmail())).willReturn(usuarioGlobal);

        // Verifica que se lanza la excepción
        assertThrows(UsuarioExistenteException.class, () -> {
            usuarioService.guardarUsuario(usuarioGlobal, usuarioRoles);
        });
    }


    @Test
    @DisplayName("Prueba para lanzar excepción si el NIT ya existe")
    public void testGuardarUsuarioNitExistente() throws Exception {
        // Configura el comportamiento simulado
        given(usuarioRepository.findByNit(usuarioGlobal.getNit())).willReturn(usuarioGlobal);

        // Verifica que se lanza la excepción
        assertThrows(UsuarioExistenteException.class, () -> {
            usuarioService.guardarUsuario(usuarioGlobal, usuarioRoles);
        });
    }

    @Test
    @DisplayName("Prueba para lanzar excepción si el CUI ya existe")
    public void testGuardarUsuarioCuiExistente() throws Exception {
        // Configura el comportamiento simulado
        given(usuarioRepository.findByCui(usuarioGlobal.getCui())).willReturn(usuarioGlobal);

        // Verifica que se lanza la excepción
        assertThrows(UsuarioExistenteException.class, () -> {
            usuarioService.guardarUsuario(usuarioGlobal, usuarioRoles);
        });
    }

    @Test
    @DisplayName("Prueba para encriptar la contraseña del usuario")
    public void testGuardarUsuarioEncriptarPassword() throws Exception {
        // Configura el comportamiento simulado para que no lance excepciones
        given(usuarioRepository.findByUsername(usuarioGlobal.getUsername())).willReturn(null);
        given(usuarioRepository.findByEmail(usuarioGlobal.getEmail())).willReturn(null);
        given(usuarioRepository.findByNit(usuarioGlobal.getNit())).willReturn(null);
        given(usuarioRepository.findByCui(usuarioGlobal.getCui())).willReturn(null);
        given(usuarioRepository.save(any(Usuario.class))).willReturn(usuarioGlobal);

        // Llama al método
        usuarioService.guardarUsuario(usuarioGlobal, usuarioRoles);

        // Verifica que la contraseña se haya encriptado
        assertThat(usuarioGlobal.getPassword()).isNotEqualTo("contraseña");
    }


    @Test
    @DisplayName("Prueba para obtener un usuario por nombre de usuario")
    public void testObtenerUsuario() {
        // Configura el comportamiento simulado
        given(usuarioRepository.findByUsername(usuarioGlobal.getUsername())).willReturn(usuarioGlobal);

        // Llama al método
        Usuario resultado = usuarioService.obtenerUsuario(usuarioGlobal.getUsername());

        // Verifica el resultado
        assertThat(resultado).isNotNull();
        assertThat(resultado.getUsername()).isEqualTo(usuarioGlobal.getUsername());
    }


    @Test
    @DisplayName("Prueba para eliminar un usuario")
    public void testEliminarUsuario() {
        // Llama al método
        usuarioService.eliminarUsuario(usuarioGlobal.getId());

        // Verifica que se llame a deleteById con el ID correcto
        verify(usuarioRepository, times(1)).deleteById(usuarioGlobal.getId());
    }


    @Test
    @DisplayName("Prueba para obtener todos los usuarios con la autoridad CLIENTE")
    public void testObtenerUsuarios() {
        // Crea usuarios de prueba
        Usuario usuarioCliente = new Usuario();
        usuarioCliente.setId(1L);
        usuarioCliente.setUsername("clienteEjemplo");
        // Asigna la autoridad "CLIENTE"
        Rol rolCliente = new Rol();
        rolCliente.setRolNombre("CLIENTE");
        UsuarioRol usuarioRolCliente = new UsuarioRol();
        usuarioRolCliente.setRol(rolCliente);
        usuarioCliente.setUsuarioRoles(new HashSet<>(Collections.singletonList(usuarioRolCliente)));

        Usuario usuarioSinCliente = new Usuario();
        usuarioSinCliente.setId(2L);
        usuarioSinCliente.setUsername("otroUsuario");

        // Simula el comportamiento del repositorio
        given(usuarioRepository.findAll()).willReturn(Arrays.asList(usuarioCliente, usuarioSinCliente));

        // Llama al método
        List<Usuario> resultados = usuarioService.obtenerUsuarios();

        // Verifica el resultado
        assertThat(resultados).hasSize(1);
        assertThat(resultados.get(0).getUsername()).isEqualTo("clienteEjemplo");
    }

    @Test
    @DisplayName("Prueba para actualizar un usuario existente")
    public void testActualizarUsuario() throws Exception {
        // Configura el usuario existente
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(usuarioGlobal.getId());
        usuarioExistente.setUsername("usuarioAntiguo");
        usuarioExistente.setNombre("Juan");
        usuarioExistente.setApellido("Pérez");
        usuarioExistente.setTelefono("123456789");
        usuarioExistente.setPerfil("perfilAntiguo");

        // Configura el nuevo usuario que se desea actualizar
        usuarioGlobal.setUsername("nuevoUsuario");
        usuarioGlobal.setNombre("Juan");
        usuarioGlobal.setApellido("Pérez");
        usuarioGlobal.setTelefono("987654321");
        usuarioGlobal.setPerfil("nuevoPerfil");

        // Simula el comportamiento del repositorio
        given(usuarioRepository.findById(usuarioGlobal.getId())).willReturn(Optional.of(usuarioExistente));
        given(usuarioRepository.save(any(Usuario.class))).willReturn(usuarioGlobal);

        // Llama al método
        Usuario resultado = usuarioService.actualizarUsuario(usuarioGlobal);

        // Verifica el resultado
        assertThat(resultado).isNotNull();
        assertThat(resultado.getUsername()).isEqualTo("nuevoUsuario");
        assertThat(resultado.getTelefono()).isEqualTo("987654321");
    }



    @Test
    @DisplayName("Prueba para activar un usuario existente")
    public void testActivarUsuario() {
        // Crea un usuario inactivo
        Usuario usuarioInactivo = new Usuario();
        usuarioInactivo.setId(1L);
        usuarioInactivo.setEnabled(false);

        // Simula el comportamiento del repositorio
        given(usuarioRepository.findById(usuarioInactivo.getId())).willReturn(Optional.of(usuarioInactivo));
        given(usuarioRepository.save(any(Usuario.class))).willReturn(usuarioInactivo);

        // Llama al método
        Usuario resultado = usuarioService.activarUsuario(usuarioInactivo.getId());

        // Verifica que el usuario esté activo
        assertThat(resultado).isNotNull();
        assertThat(resultado.isEnabled()).isTrue();
    }


    @Test
    @DisplayName("Prueba para desactivar un usuario existente")
    public void testDesactivarUsuario() {
        // Crea un usuario activo
        Usuario usuarioActivo = new Usuario();
        usuarioActivo.setId(1L);
        usuarioActivo.setEnabled(true);

        // Simula el comportamiento del repositorio
        given(usuarioRepository.findById(usuarioActivo.getId())).willReturn(Optional.of(usuarioActivo));
        given(usuarioRepository.save(any(Usuario.class))).willReturn(usuarioActivo);

        // Llama al método
        Usuario resultado = usuarioService.desactivarUsuario(usuarioActivo.getId());

        // Verifica que el usuario esté inactivo
        assertThat(resultado).isNotNull();
        assertThat(resultado.isEnabled()).isFalse();
    }

    @Test
    @DisplayName("Prueba para listar usuarios activos")
    public void testListarUsuariosActivos() {
        // Establece el usuarioGlobal como activo
        usuarioGlobal.setEnabled(true);

        // Simula el comportamiento del repositorio para retornar el usuario activo
        given(usuarioRepository.findByEnabledTrue()).willReturn(Arrays.asList(usuarioGlobal));

        // Llama al método
        List<Usuario> resultados = usuarioService.listarUsuariosActivos();

        // Verifica el resultado
        assertThat(resultados).hasSize(1);
        assertThat(resultados.get(0).getId()).isEqualTo(usuarioGlobal.getId());
    }

    @Test
    @DisplayName("Prueba para listar usuarios no activos")
    public void testListarUsuariosNoActivos() {
        // Establece el usuarioGlobal como inactivo
        usuarioGlobal.setEnabled(false);

        // Simula el comportamiento del repositorio para retornar el usuario inactivo
        given(usuarioRepository.findByEnabledFalse()).willReturn(Arrays.asList(usuarioGlobal));

        // Llama al método
        List<Usuario> resultados = usuarioService.listarUsuariosNoActivos();

        // Verifica el resultado
        assertThat(resultados).hasSize(1);
        assertThat(resultados.get(0).getId()).isEqualTo(usuarioGlobal.getId());
    }









}
