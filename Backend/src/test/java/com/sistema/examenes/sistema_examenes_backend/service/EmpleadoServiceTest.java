package com.sistema.examenes.sistema_examenes_backend.service;

import com.sistema.examenes.sistema_examenes_backend.entidades.Rol;
import com.sistema.examenes.sistema_examenes_backend.entidades.Usuario;
import com.sistema.examenes.sistema_examenes_backend.entidades.UsuarioRol;
import com.sistema.examenes.sistema_examenes_backend.excepciones.UsuarioExistenteException;
import com.sistema.examenes.sistema_examenes_backend.repositorios.EmpleadoRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.RolRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.UsuarioRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.implementacion.EmpleadoServiceImpl;
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

import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test") // Usa el archivo application-test.properties
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // No reemplazar H2 por otra base de datos
@Transactional
public class EmpleadoServiceTest {

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @InjectMocks
    private EmpleadoServiceImpl empleadoService;

    private Set<UsuarioRol> usuarioRoles;

    private Usuario empleadoGlobal;


    @BeforeEach
    public void setUp() {
        // Inicializa el usuario global para los tests
        empleadoGlobal = new Usuario();
        empleadoGlobal.setId(1L);
        empleadoGlobal.setUsername("usuarioEjemplo");
        empleadoGlobal.setEmail("usuario@example.com");
        empleadoGlobal.setPassword("contraseña");
        empleadoGlobal.setNit("123456789");
        empleadoGlobal.setCui("987654321");

        // Inicializa los roles del usuario
        usuarioRoles = new HashSet<>();
        Rol rol = new Rol();
        rol.setRolNombre("EMPLEADO");
        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setRol(rol);
        usuarioRoles.add(usuarioRol);

        // Asigna los roles al usuario global
        empleadoGlobal.setUsuarioRoles(usuarioRoles);
    }


    @Test
    @DisplayName("Prueba para guardar un empleado con un solo rol")
    public void testGuardarEmpleadoConUnRol() throws Exception {
        // Configura el comportamiento simulado
        given(empleadoRepository.findByUsername(empleadoGlobal.getUsername())).willReturn(null);
        given(empleadoRepository.findByEmail(empleadoGlobal.getEmail())).willReturn(null);
        given(empleadoRepository.findByNit(empleadoGlobal.getNit())).willReturn(null);
        given(empleadoRepository.findByCui(empleadoGlobal.getCui())).willReturn(null);
        given(empleadoRepository.save(any(Usuario.class))).willReturn(empleadoGlobal);

        // Llama al método con el empleado global y un conjunto que contiene un solo rol
        Usuario resultado = empleadoService.guardarEmpleado(empleadoGlobal, usuarioRoles);

        // Verifica el resultado
        assertThat(resultado).isNotNull();
        assertThat(resultado.getId()).isEqualTo(empleadoGlobal.getId());
        assertThat(resultado.getUsername()).isEqualTo(empleadoGlobal.getUsername());
    }

    @Test
    @DisplayName("Prueba para lanzar excepción si el empleado ya existe")
    public void testGuardarEmpleadoExistente() throws Exception {
        // Configura el comportamiento simulado
        given(empleadoRepository.findByUsername(empleadoGlobal.getUsername())).willReturn(empleadoGlobal); // Simula que el empleado ya existe

        // Verifica que se lanza la excepción
        assertThrows(UsuarioExistenteException.class, () -> {
            empleadoService.guardarEmpleado(empleadoGlobal, usuarioRoles);
        });
    }

    @Test
    @DisplayName("Prueba para lanzar excepción si el nombre de usuario ya existe")
    public void testGuardarEmpleadoNombreExistente() throws Exception {
        // Configura el comportamiento simulado
        given(empleadoRepository.findByUsername(empleadoGlobal.getUsername())).willReturn(empleadoGlobal);

        // Verifica que se lanza la excepción
        assertThrows(UsuarioExistenteException.class, () -> {
            empleadoService.guardarEmpleado(empleadoGlobal, usuarioRoles);
        });
    }

    @Test
    @DisplayName("Prueba para lanzar excepción si el correo electrónico ya existe")
    public void testGuardarEmpleadoEmailExistente() throws Exception {
        // Configura el comportamiento simulado
        given(empleadoRepository.findByEmail(empleadoGlobal.getEmail())).willReturn(empleadoGlobal);

        // Verifica que se lanza la excepción
        assertThrows(UsuarioExistenteException.class, () -> {
            empleadoService.guardarEmpleado(empleadoGlobal, usuarioRoles);
        });
    }

    @Test
    @DisplayName("Prueba para lanzar excepción si el NIT ya existe")
    public void testGuardarEmpleadoNitExistente() throws Exception {
        // Configura el comportamiento simulado
        given(empleadoRepository.findByNit(empleadoGlobal.getNit())).willReturn(empleadoGlobal);

        // Verifica que se lanza la excepción
        assertThrows(UsuarioExistenteException.class, () -> {
            empleadoService.guardarEmpleado(empleadoGlobal, usuarioRoles);
        });
    }

    @Test
    @DisplayName("Prueba para lanzar excepción si el CUI ya existe")
    public void testGuardarEmpleadoCuiExistente() throws Exception {
        // Configura el comportamiento simulado
        given(empleadoRepository.findByCui(empleadoGlobal.getCui())).willReturn(empleadoGlobal);

        // Verifica que se lanza la excepción
        assertThrows(UsuarioExistenteException.class, () -> {
            empleadoService.guardarEmpleado(empleadoGlobal, usuarioRoles);
        });
    }

    @Test
    @DisplayName("Prueba para encriptar la contraseña del empleado")
    public void testGuardarEmpleadoEncriptarPassword() throws Exception {
        // Configura el comportamiento simulado para que no lance excepciones
        given(empleadoRepository.findByUsername(empleadoGlobal.getUsername())).willReturn(null);
        given(empleadoRepository.findByEmail(empleadoGlobal.getEmail())).willReturn(null);
        given(empleadoRepository.findByNit(empleadoGlobal.getNit())).willReturn(null);
        given(empleadoRepository.findByCui(empleadoGlobal.getCui())).willReturn(null);
        given(empleadoRepository.save(any(Usuario.class))).willReturn(empleadoGlobal);

        // Llama al método
        empleadoService.guardarEmpleado(empleadoGlobal, usuarioRoles);

        // Verifica que la contraseña se haya encriptado
        assertThat(empleadoGlobal.getPassword()).isNotEqualTo("contraseña");
    }


    @Test
    @DisplayName("Prueba para obtener un empleado por nombre de usuario")
    public void testObtenerEmpleado() {
        // Configura el comportamiento simulado
        given(empleadoRepository.findByUsername(empleadoGlobal.getUsername())).willReturn(empleadoGlobal);

        // Llama al método
        Usuario resultado = empleadoService.obtenerEmpleado(empleadoGlobal.getUsername());

        // Verifica el resultado
        assertThat(resultado).isNotNull();
        assertThat(resultado.getUsername()).isEqualTo(empleadoGlobal.getUsername());
    }

    @Test
    @DisplayName("Prueba para eliminar un empleado")
    public void testEliminarEmpleado() {
        // Llama al método
        empleadoService.eliminarEmpleado(empleadoGlobal.getId());

        // Verifica que se llame a deleteById con el ID correcto
        verify(empleadoRepository, times(1)).deleteById(empleadoGlobal.getId());
    }

    @Test
    @DisplayName("Prueba para obtener todos los empleados con la autoridad CLIENTE")
    public void testObtenerEmpleados() {
        // Crea empleados de prueba
        Usuario empleadoCliente = new Usuario();
        empleadoCliente.setId(1L);
        empleadoCliente.setUsername("clienteEjemplo");
        // Asigna la autoridad "CLIENTE"
        Rol rolCliente = new Rol();
        rolCliente.setRolNombre("EMPLEADO");
        UsuarioRol empleadoRolCliente = new UsuarioRol();
        empleadoRolCliente.setRol(rolCliente);
        empleadoCliente.setUsuarioRoles(new HashSet<>(Collections.singletonList(empleadoRolCliente)));

        Usuario empleadoSinCliente = new Usuario();
        empleadoSinCliente.setId(2L);
        empleadoSinCliente.setUsername("otroEmpleado");

        // Simula el comportamiento del repositorio
        given(empleadoRepository.findAll()).willReturn(Arrays.asList(empleadoCliente, empleadoSinCliente));

        // Llama al método
        List<Usuario> resultados = empleadoService.obtenerEmpleados();

        // Verifica el resultado
        assertThat(resultados).hasSize(1);
        assertThat(resultados.get(0).getUsername()).isEqualTo("clienteEjemplo");
    }

    @Test
    @DisplayName("Prueba para actualizar un empleado existente")
    public void testActualizarEmpleado() throws Exception {
        // Configura el empleado existente
        Usuario empleadoExistente = new Usuario();
        empleadoExistente.setId(empleadoGlobal.getId());
        empleadoExistente.setUsername("empleadoAntiguo");
        empleadoExistente.setNombre("Juan");
        empleadoExistente.setApellido("Pérez");
        empleadoExistente.setTelefono("123456789");
        empleadoExistente.setPerfil("perfilAntiguo");

        // Configura el nuevo empleado que se desea actualizar
        empleadoGlobal.setUsername("nuevoEmpleado");
        empleadoGlobal.setNombre("Juan");
        empleadoGlobal.setApellido("Pérez");
        empleadoGlobal.setTelefono("987654321");
        empleadoGlobal.setPerfil("nuevoPerfil");

        // Simula el comportamiento del repositorio
        given(empleadoRepository.findById(empleadoGlobal.getId())).willReturn(Optional.of(empleadoExistente));
        given(empleadoRepository.save(any(Usuario.class))).willReturn(empleadoGlobal);

        // Llama al método
        Usuario resultado = empleadoService.actualizarEmpleado(empleadoGlobal);

        // Verifica el resultado
        assertThat(resultado).isNotNull();
        assertThat(resultado.getUsername()).isEqualTo("nuevoEmpleado");
        assertThat(resultado.getTelefono()).isEqualTo("987654321");
    }

    @Test
    @DisplayName("Prueba para activar un empleado existente")
    public void testActivarEmpleado() {
        // Crea un empleado inactivo
        Usuario empleadoInactivo = new Usuario();
        empleadoInactivo.setId(1L);
        empleadoInactivo.setEnabled(false);

        // Simula el comportamiento del repositorio
        given(empleadoRepository.findById(empleadoInactivo.getId())).willReturn(Optional.of(empleadoInactivo));
        given(empleadoRepository.save(any(Usuario.class))).willReturn(empleadoInactivo);

        // Llama al método
        Usuario resultado = empleadoService.activarEmpleado(empleadoInactivo.getId());

        // Verifica que el empleado esté activo
        assertThat(resultado).isNotNull();
        assertThat(resultado.isEnabled()).isTrue();
    }

    @Test
    @DisplayName("Prueba para desactivar un empleado existente")
    public void testDesactivarEmpleado() {
        // Crea un empleado activo
        Usuario empleadoActivo = new Usuario();
        empleadoActivo.setId(1L);
        empleadoActivo.setEnabled(true);

        // Simula el comportamiento del repositorio
        given(empleadoRepository.findById(empleadoActivo.getId())).willReturn(Optional.of(empleadoActivo));
        given(empleadoRepository.save(any(Usuario.class))).willReturn(empleadoActivo);

        // Llama al método
        Usuario resultado = empleadoService.desactivarEmpleado(empleadoActivo.getId());

        // Verifica que el empleado esté inactivo
        assertThat(resultado).isNotNull();
        assertThat(resultado.isEnabled()).isFalse();
    }

    @Test
    @DisplayName("Prueba para listar empleados activos")
    public void testListarEmpleadosActivos() {
        // Establece el empleadoGlobal como activo
        empleadoGlobal.setEnabled(true);

        // Simula el comportamiento del repositorio para retornar el empleado activo
        given(empleadoRepository.findByEnabledTrue()).willReturn(Arrays.asList(empleadoGlobal));

        // Llama al método
        List<Usuario> resultados = empleadoService.listarEmpleadosActivos();

        // Verifica el resultado
        assertThat(resultados).hasSize(1);
        assertThat(resultados.get(0).getId()).isEqualTo(empleadoGlobal.getId());
    }

    @Test
    @DisplayName("Prueba para listar empleados no activos")
    public void testListarEmpleadosNoActivos() {
        // Establece el empleadoGlobal como inactivo
        empleadoGlobal.setEnabled(false);

        // Simula el comportamiento del repositorio para retornar el empleado inactivo
        given(empleadoRepository.findByEnabledFalse()).willReturn(Arrays.asList(empleadoGlobal));

        // Llama al método
        List<Usuario> resultados = empleadoService.listarEmpleadosNoActivos();

        // Verifica el resultado
        assertThat(resultados).hasSize(1);
        assertThat(resultados.get(0).getId()).isEqualTo(empleadoGlobal.getId());
    }





}
