package com.sistema.examenes.sistema_examenes_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistema.examenes.sistema_examenes_backend.DTO.EmpleadoDTO;
import com.sistema.examenes.sistema_examenes_backend.DTO.UsuarioDTO;
import com.sistema.examenes.sistema_examenes_backend.controladores.EmpleadoController;
import com.sistema.examenes.sistema_examenes_backend.entidades.Rol;
import com.sistema.examenes.sistema_examenes_backend.entidades.Usuario;
import com.sistema.examenes.sistema_examenes_backend.entidades.UsuarioRol;
import com.sistema.examenes.sistema_examenes_backend.excepciones.UsuarioExistenteException;
import com.sistema.examenes.sistema_examenes_backend.servicios.EmpleadoService;
import com.sistema.examenes.sistema_examenes_backend.servicios.RolService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.*;

import static org.hamcrest.CoreMatchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anySet;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class EmpleadoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private EmpleadoService empleadoService;

    @MockBean
    private RolService rolService;

    @Autowired
    private ObjectMapper objectMapper;



    @Test
    void guardarEmpleado() throws Exception {
        // given
        EmpleadoDTO empleadoDTO = new EmpleadoDTO();
        empleadoDTO.setUsername("nuevoUsuario");
        empleadoDTO.setPassword("password123");
        empleadoDTO.setNombre("Nombre");
        empleadoDTO.setApellido("Apellido");
        empleadoDTO.setEmail("correo@dominio.com");
        empleadoDTO.setTelefono("123456789");
        empleadoDTO.setEnabled(true);
        empleadoDTO.setPerfil("Admin");
        empleadoDTO.setCui("12345");
        empleadoDTO.setNit("67890");
        empleadoDTO.setRoles(new HashSet<>(Arrays.asList(1L, 2L)));

        // Crear empleado
        Usuario empleado = new Usuario();
        empleado.setId(1L);
        empleado.setUsername(empleadoDTO.getUsername());

        // Crear roles simulados
        UsuarioRol rol1 = new UsuarioRol();
        rol1.setRol(new Rol(1L, "ROLE_USER"));
        UsuarioRol rol2 = new UsuarioRol();
        rol2.setRol(new Rol(2L, "ROLE_ADMIN"));

        // Simular la búsqueda de roles en el rolService
        given(rolService.findById(1L)).willReturn(Optional.of(rol1.getRol()));
        given(rolService.findById(2L)).willReturn(Optional.of(rol2.getRol()));

        // Simular la creación de empleado
        given(empleadoService.guardarEmpleado(any(Usuario.class), anySet())).willReturn(empleado);

        // when
        ResultActions response = mockMvc.perform(post("/empleados/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empleadoDTO)));

        // then
        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is(empleado.getUsername())))
                .andDo(print());
    }



    @Test
    public void testObtenerUsuarioPorId() throws Exception {
        // given
        Long empleadoId = 1L;
        Usuario empleado = new Usuario();
        empleado.setId(empleadoId);
        empleado.setUsername("empleado1");

        given(empleadoService.findById(empleadoId)).willReturn(Optional.of(empleado));

        // when
        ResultActions response = mockMvc.perform(get("/empleados/{id}", empleadoId));

        // then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(empleado.getUsername())))
                .andDo(print());
    }

    @Test
    public void testListarEmpleados() throws Exception {
        // given
        Usuario empleado1 = new Usuario();
        empleado1.setId(1L);
        empleado1.setUsername("usuario1");

        Usuario empleado2 = new Usuario();
        empleado2.setId(2L);
        empleado2.setUsername("usuario2");

        List<Usuario> empleados = new ArrayList<>();
        empleados.add(empleado1);
        empleados.add(empleado2);

        given(empleadoService.obtenerEmpleados()).willReturn(empleados);

        // when
        ResultActions response = mockMvc.perform(get("/empleados"));

        // then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(empleados.size())))
                .andDo(print());
    }

    @Test
    public void testListarUsuariosActivos() throws Exception {
        // given
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setUsername("usuarioActivo");
        usuario1.setEnabled(true);

        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setUsername("usuarioActivo2");
        usuario2.setEnabled(true);

        List<Usuario> usuariosActivos = Arrays.asList(usuario1, usuario2);

        given(empleadoService.listarEmpleadosActivos()).willReturn(usuariosActivos);

        // when
        ResultActions response = mockMvc.perform(get("/empleados/activos"));

        // then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(usuariosActivos.size())))
                .andDo(print());
    }


    @Test
    public void testListarUsuariosNoActivos() throws Exception {
        // given
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setUsername("usuarioNoActivo");
        usuario1.setEnabled(false);

        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setUsername("usuarioNoActivo2");
        usuario2.setEnabled(false);

        List<Usuario> usuariosNoActivos = Arrays.asList(usuario1, usuario2);

        given(empleadoService.listarEmpleadosNoActivos()).willReturn(usuariosNoActivos);

        // when
        ResultActions response = mockMvc.perform(get("/empleados/desactivados"));

        // then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(usuariosNoActivos.size())))
                .andDo(print());
    }

    @Test
    public void testActivarUsuario() throws Exception {
        // given
        Long usuarioId = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        usuario.setUsername("usuario1");

        given(empleadoService.activarEmpleado(usuarioId)).willReturn(usuario);

        // when
        ResultActions response = mockMvc.perform(put("/empleados/activar/{id}", usuarioId));

        // then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(usuario.getUsername())))
                .andDo(print());
    }

    @Test
    public void testDesactivarUsuario() throws Exception {
        // given
        Long usuarioId = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        usuario.setUsername("usuario1");

        given(empleadoService.desactivarEmpleado(usuarioId)).willReturn(usuario);

        // when
        ResultActions response = mockMvc.perform(put("/empleados/desactivar/{id}", usuarioId));

        // then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(usuario.getUsername())))
                .andDo(print());
    }

    @Test
    public void testGuardarEmpleadoExistente() throws Exception{
        // given
        EmpleadoDTO empleadoDTO = new EmpleadoDTO();
        empleadoDTO.setUsername("nuevoUsuario");
        empleadoDTO.setPassword("password123");
        empleadoDTO.setNombre("Nombre");
        empleadoDTO.setApellido("Apellido");
        empleadoDTO.setEmail("correo@dominio.com");
        empleadoDTO.setTelefono("123456789");
        empleadoDTO.setEnabled(true);
        empleadoDTO.setPerfil("Admin");
        empleadoDTO.setCui("12345");
        empleadoDTO.setNit("67890");
        empleadoDTO.setRoles(new HashSet<>(Arrays.asList(1L, 2L)));

        // Crear empleado
        Usuario empleado = new Usuario();
        empleado.setId(1L);
        empleado.setUsername(empleadoDTO.getUsername());

        // Crear roles simulados
        UsuarioRol rol1 = new UsuarioRol();
        rol1.setRol(new Rol(1L, "ROLE_USER"));
        UsuarioRol rol2 = new UsuarioRol();
        rol2.setRol(new Rol(2L, "ROLE_ADMIN"));

        // Simular la búsqueda de roles en el rolService
        given(rolService.findById(1L)).willReturn(Optional.of(rol1.getRol()));
        given(rolService.findById(2L)).willReturn(Optional.of(rol2.getRol()));

        // Simular la creación de empleado
        given(empleadoService.guardarEmpleado(any(Usuario.class), anySet()))
                .willThrow(new UsuarioExistenteException("Usuario ya existe"));

        // when
        ResultActions response = mockMvc.perform(post("/empleados/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empleadoDTO)));

        // then
        response.andExpect(status().isBadRequest()) // Debemos esperar un 400 por el error de usuario existente
                .andExpect(jsonPath("$.message", is("Usuario ya existe")))
                .andExpect(jsonPath("$.details", is("Asegúrate de que el nombre de usuario, email, CUI o NIT no estén duplicados.")))
                .andDo(print());
    }

    @Test
    public void testGuardarEmpleado_Error() throws Exception{
        // given
        EmpleadoDTO empleadoDTO = new EmpleadoDTO();
        empleadoDTO.setUsername("nuevoUsuario");
        empleadoDTO.setPassword("password123");
        empleadoDTO.setNombre("Nombre");
        empleadoDTO.setApellido("Apellido");
        empleadoDTO.setEmail("correo@dominio.com");
        empleadoDTO.setTelefono("123456789");
        empleadoDTO.setEnabled(true);
        empleadoDTO.setPerfil("Admin");
        empleadoDTO.setCui("12345");
        empleadoDTO.setNit("67890");
        empleadoDTO.setRoles(new HashSet<>(Arrays.asList(1L, 2L)));

        // Crear empleado
        Usuario empleado = new Usuario();
        empleado.setId(1L);
        empleado.setUsername(empleadoDTO.getUsername());

        // Crear roles simulados
        UsuarioRol rol1 = new UsuarioRol();
        rol1.setRol(new Rol(1L, "ROLE_USER"));
        UsuarioRol rol2 = new UsuarioRol();
        rol2.setRol(new Rol(2L, "ROLE_ADMIN"));

        // Simular la búsqueda de roles en el rolService
        given(rolService.findById(1L)).willReturn(Optional.of(rol1.getRol()));
        given(rolService.findById(2L)).willReturn(Optional.of(rol2.getRol()));

        // Simular la creación de empleado
        given(empleadoService.guardarEmpleado(any(Usuario.class), anySet()))
                .willThrow(new Exception("Error inesperado"));

        // when
        ResultActions response = mockMvc.perform(post("/empleados/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(empleadoDTO)));

        // then
        response.andExpect(status().isInternalServerError()) // Esperamos un 500 por el error inesperado
                .andExpect(jsonPath("$.message", is("Error inesperado")))
                .andExpect(jsonPath("$.details", is("Error inesperado")))
                .andDo(print());
    }

    @Test
    public void testActualizarUsuarioMultiplesCampos() throws Exception {
        // given
        Long usuarioId = 1L;

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(usuarioId);
        usuarioExistente.setUsername("usuarioOriginal");
        usuarioExistente.setNombre("Nombre Original");

        given(empleadoService.findById(usuarioId)).willReturn(Optional.of(usuarioExistente));
        given(empleadoService.actualizarEmpleado(any(Usuario.class))).willAnswer(invocation -> {
            Usuario updatedUsuario = invocation.getArgument(0);
            updatedUsuario.setUsername("nuevoUsername");
            updatedUsuario.setNombre("Nuevo Nombre");
            return updatedUsuario;
        });

        // when
        ResultActions response = mockMvc.perform(
                multipart("/empleados/{id}", usuarioId)
                        .param("username", "nuevoUsername")
                        .param("nombre", "Nuevo Nombre")
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        );

        // then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("nuevoUsername")))
                .andExpect(jsonPath("$.nombre", is("Nuevo Nombre")))
                .andExpect(jsonPath("$.apellido").doesNotExist())
                .andExpect(jsonPath("$.telefono").doesNotExist())
                .andDo(print());
    }
}
