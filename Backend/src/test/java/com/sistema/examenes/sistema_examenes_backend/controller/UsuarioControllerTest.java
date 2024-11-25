package com.sistema.examenes.sistema_examenes_backend.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistema.examenes.sistema_examenes_backend.DTO.UsuarioDTO;
import com.sistema.examenes.sistema_examenes_backend.controladores.PermisoController;
import com.sistema.examenes.sistema_examenes_backend.controladores.UsuarioController;
import com.sistema.examenes.sistema_examenes_backend.entidades.Permiso;
import com.sistema.examenes.sistema_examenes_backend.entidades.Rol;
import com.sistema.examenes.sistema_examenes_backend.entidades.Usuario;
import com.sistema.examenes.sistema_examenes_backend.entidades.UsuarioRol;
import com.sistema.examenes.sistema_examenes_backend.excepciones.UsuarioExistenteException;
import com.sistema.examenes.sistema_examenes_backend.repositorios.PermisoRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.PermisoService;
import com.sistema.examenes.sistema_examenes_backend.servicios.RolService;
import com.sistema.examenes.sistema_examenes_backend.servicios.UsuarioService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.web.multipart.MultipartFile;

import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class UsuarioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private UsuarioService usuarioService;

    @MockBean
    private RolService rolService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testListarUsuarios() throws Exception {
        // given
        Usuario usuario1 = new Usuario();
        usuario1.setId(1L);
        usuario1.setUsername("usuario1");

        Usuario usuario2 = new Usuario();
        usuario2.setId(2L);
        usuario2.setUsername("usuario2");

        List<Usuario> usuarios = new ArrayList<>();
        usuarios.add(usuario1);
        usuarios.add(usuario2);

        given(usuarioService.obtenerUsuarios()).willReturn(usuarios);

        // when
        ResultActions response = mockMvc.perform(get("/usuarios"));

        // then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(usuarios.size())))
                .andDo(print());
    }

    @Test
    public void testObtenerUsuarioPorId() throws Exception {
        // given
        Long usuarioId = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        usuario.setUsername("usuario1");

        given(usuarioService.findById(usuarioId)).willReturn(Optional.of(usuario));

        // when
        ResultActions response = mockMvc.perform(get("/usuarios/{id}", usuarioId));

        // then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(usuario.getUsername())))
                .andDo(print());
    }

    @Test
    public void testGuardarUsuario() throws Exception {
        // given
        // Crear UsuarioDTO
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUsername("nuevoUsuario");
        usuarioDTO.setPassword("password123");
        usuarioDTO.setNombre("Nombre");
        usuarioDTO.setApellido("Apellido");
        usuarioDTO.setEmail("correo@dominio.com");
        usuarioDTO.setTelefono("123456789");
        usuarioDTO.setEnabled(true);
        usuarioDTO.setPerfil("Admin");
        usuarioDTO.setCui("12345");
        usuarioDTO.setNit("67890");
        usuarioDTO.setRoles(new HashSet<>(Arrays.asList(1L, 2L)));

        // Crear Usuario
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername(usuarioDTO.getUsername());

        // Crear roles simulados
        UsuarioRol rol1 = new UsuarioRol();
        rol1.setRol(new Rol(1L, "ROLE_USER"));
        UsuarioRol rol2 = new UsuarioRol();
        rol2.setRol(new Rol(2L, "ROLE_ADMIN"));

        // Simular la búsqueda de roles en el rolService
        given(rolService.findById(1L)).willReturn(Optional.of(rol1.getRol()));
        given(rolService.findById(2L)).willReturn(Optional.of(rol2.getRol()));

        // Simular la creación de usuario
        given(usuarioService.guardarUsuario(any(Usuario.class), anySet())).willReturn(usuario);

        // when
        ResultActions response = mockMvc.perform(post("/usuarios/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioDTO)));

        // then
        response.andExpect(status().isCreated())
                .andExpect(jsonPath("$.username", is(usuario.getUsername())))
                .andDo(print());
    }


    @Test
    public void testActivarUsuario() throws Exception {
        // given
        Long usuarioId = 1L;
        Usuario usuario = new Usuario();
        usuario.setId(usuarioId);
        usuario.setUsername("usuario1");

        given(usuarioService.activarUsuario(usuarioId)).willReturn(usuario);

        // when
        ResultActions response = mockMvc.perform(put("/usuarios/activar/{id}", usuarioId));

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

        given(usuarioService.desactivarUsuario(usuarioId)).willReturn(usuario);

        // when
        ResultActions response = mockMvc.perform(put("/usuarios/desactivar/{id}", usuarioId));

        // then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is(usuario.getUsername())))
                .andDo(print());
    }


    @Test
    public void testObtenerUsuarioNoEncontrado() throws Exception {
        // given
        Long usuarioId = 1L;

        given(usuarioService.findById(usuarioId)).willReturn(Optional.empty());

        // when
        ResultActions response = mockMvc.perform(get("/usuarios/{id}", usuarioId));

        // then
        response.andExpect(status().isNotFound())
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

        given(usuarioService.listarUsuariosActivos()).willReturn(usuariosActivos);

        // when
        ResultActions response = mockMvc.perform(get("/usuarios/activos"));

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

        given(usuarioService.listarUsuariosNoActivos()).willReturn(usuariosNoActivos);

        // when
        ResultActions response = mockMvc.perform(get("/usuarios/desactivados"));

        // then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(usuariosNoActivos.size())))
                .andDo(print());
    }


    @Test
    public void testGuardarUsuario_UsuarioExistente() throws Exception {
        // given
        // Crear UsuarioDTO
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUsername("usuarioExistente");
        usuarioDTO.setPassword("password123");
        usuarioDTO.setNombre("Nombre");
        usuarioDTO.setApellido("Apellido");
        usuarioDTO.setEmail("correo@dominio.com");
        usuarioDTO.setTelefono("123456789");
        usuarioDTO.setEnabled(true);
        usuarioDTO.setPerfil("Admin");
        usuarioDTO.setCui("12345");
        usuarioDTO.setNit("67890");
        usuarioDTO.setRoles(new HashSet<>(Arrays.asList(1L, 2L))); // Asignamos roles 1 y 2

        // Crear roles simulados
        UsuarioRol rol1 = new UsuarioRol();
        rol1.setRol(new Rol(1L, "ROLE_USER"));
        UsuarioRol rol2 = new UsuarioRol();
        rol2.setRol(new Rol(2L, "ROLE_ADMIN"));

        // Simular la búsqueda de roles en el rolService
        given(rolService.findById(1L)).willReturn(Optional.of(rol1.getRol()));
        given(rolService.findById(2L)).willReturn(Optional.of(rol2.getRol()));

        // Simular que el usuario ya existe
        given(usuarioService.guardarUsuario(any(Usuario.class), anySet()))
                .willThrow(new UsuarioExistenteException("Usuario ya existe"));

        // when
        ResultActions response = mockMvc.perform(post("/usuarios/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioDTO)));

        // then
        response.andExpect(status().isBadRequest()) // Debemos esperar un 400 por el error de usuario existente
                .andExpect(jsonPath("$.message", is("Usuario ya existe")))
                .andExpect(jsonPath("$.details", is("Asegúrate de que el nombre de usuario, email, CUI o NIT no estén duplicados.")))
                .andDo(print());
    }



    @Test
    public void testGuardarUsuario_ErrorInesperado() throws Exception {
        // given
        // Crear UsuarioDTO
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUsername("nuevoUsuario");
        usuarioDTO.setPassword("password123");
        usuarioDTO.setNombre("Nombre");
        usuarioDTO.setApellido("Apellido");
        usuarioDTO.setEmail("correo@dominio.com");
        usuarioDTO.setTelefono("123456789");
        usuarioDTO.setEnabled(true);
        usuarioDTO.setPerfil("Admin");
        usuarioDTO.setCui("12345");
        usuarioDTO.setNit("67890");
        usuarioDTO.setRoles(new HashSet<>(Arrays.asList(1L, 2L))); // Roles 1 y 2 asignados

        // Crear roles simulados
        UsuarioRol rol1 = new UsuarioRol();
        rol1.setRol(new Rol(1L, "ROLE_USER"));
        UsuarioRol rol2 = new UsuarioRol();
        rol2.setRol(new Rol(2L, "ROLE_ADMIN"));

        // Simular la búsqueda de roles en el rolService
        given(rolService.findById(1L)).willReturn(Optional.of(rol1.getRol()));
        given(rolService.findById(2L)).willReturn(Optional.of(rol2.getRol()));

        // Simular un error inesperado en el servicio de usuario
        given(usuarioService.guardarUsuario(any(Usuario.class), anySet()))
                .willThrow(new Exception("Error inesperado"));

        // when
        ResultActions response = mockMvc.perform(post("/usuarios/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(usuarioDTO)));

        // then
        response.andExpect(status().isInternalServerError()) // Esperamos un 500 por el error inesperado
                .andExpect(jsonPath("$.message", is("Error inesperado")))
                .andExpect(jsonPath("$.details", is("Error inesperado")))
                .andDo(print());
    }





    @Test
    public void testActualizarUsuarioSinPerfil() throws Exception {
        // given
        Long usuarioId = 1L;

        // Simular la respuesta de usuario existente
        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(usuarioId);
        usuarioExistente.setUsername("usuarioOriginal");

        // Simular el servicio de actualización
        given(usuarioService.findById(usuarioId)).willReturn(Optional.of(usuarioExistente));
        given(usuarioService.actualizarUsuario(any(Usuario.class))).willAnswer(invocation -> {
            Usuario updatedUsuario = invocation.getArgument(0);
            updatedUsuario.setUsername("nuevoUsername");
            updatedUsuario.setNombre("Nuevo Nombre");
            updatedUsuario.setApellido("Nuevo Apellido");
            updatedUsuario.setTelefono("987654321");
            return updatedUsuario;
        });

        // when
        ResultActions response = mockMvc.perform(
                multipart("/usuarios/{id}", usuarioId) // Cambiar a solicitud multipart
                        .param("username", "nuevoUsername") // Agregar parámetros
                        .param("nombre", "Nuevo Nombre")
                        .param("apellido", "Nuevo Apellido")
                        .param("telefono", "987654321")
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA) // Tipo de contenido
        );

        // then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("nuevoUsername"))) // Verificar el nuevo username
                .andExpect(jsonPath("$.nombre", is("Nuevo Nombre")))
                .andExpect(jsonPath("$.apellido", is("Nuevo Apellido")))
                .andExpect(jsonPath("$.telefono", is("987654321")))
                .andDo(print());
    }



    @Test
    public void testActualizarUsuarioSoloUsername() throws Exception {
        // given
        Long usuarioId = 1L;

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(usuarioId);
        usuarioExistente.setUsername("usuarioOriginal");

        given(usuarioService.findById(usuarioId)).willReturn(Optional.of(usuarioExistente));
        given(usuarioService.actualizarUsuario(any(Usuario.class))).willAnswer(invocation -> {
            Usuario updatedUsuario = invocation.getArgument(0);
            updatedUsuario.setUsername("nuevoUsername");
            return updatedUsuario;
        });

        // when
        ResultActions response = mockMvc.perform(
                multipart("/usuarios/{id}", usuarioId)
                        .param("username", "nuevoUsername")
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        );

        // then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.username", is("nuevoUsername")))
                .andExpect(jsonPath("$.nombre").doesNotExist())
                .andExpect(jsonPath("$.apellido").doesNotExist())
                .andExpect(jsonPath("$.telefono").doesNotExist())
                .andDo(print());
    }


    @Test
    public void testActualizarUsuarioSoloNombre() throws Exception {
        // given
        Long usuarioId = 1L;

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(usuarioId);
        usuarioExistente.setNombre("Nombre Original");

        given(usuarioService.findById(usuarioId)).willReturn(Optional.of(usuarioExistente));
        given(usuarioService.actualizarUsuario(any(Usuario.class))).willAnswer(invocation -> {
            Usuario updatedUsuario = invocation.getArgument(0);
            updatedUsuario.setNombre("Nuevo Nombre");
            return updatedUsuario;
        });

        // when
        ResultActions response = mockMvc.perform(
                multipart("/usuarios/{id}", usuarioId)
                        .param("nombre", "Nuevo Nombre")
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        );

        // then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.nombre", is("Nuevo Nombre")))
                .andExpect(jsonPath("$.username").doesNotExist())
                .andExpect(jsonPath("$.apellido").doesNotExist())
                .andExpect(jsonPath("$.telefono").doesNotExist())
                .andDo(print());
    }

    @Test
    public void testActualizarUsuarioSoloApellido() throws Exception {
        // given
        Long usuarioId = 1L;

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(usuarioId);
        usuarioExistente.setApellido("Apellido Original");

        given(usuarioService.findById(usuarioId)).willReturn(Optional.of(usuarioExistente));
        given(usuarioService.actualizarUsuario(any(Usuario.class))).willAnswer(invocation -> {
            Usuario updatedUsuario = invocation.getArgument(0);
            updatedUsuario.setApellido("Nuevo Apellido");
            return updatedUsuario;
        });

        // when
        ResultActions response = mockMvc.perform(
                multipart("/usuarios/{id}", usuarioId)
                        .param("apellido", "Nuevo Apellido")
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        );

        // then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.apellido", is("Nuevo Apellido")))
                .andExpect(jsonPath("$.username").doesNotExist())
                .andExpect(jsonPath("$.nombre").doesNotExist())
                .andExpect(jsonPath("$.telefono").doesNotExist())
                .andDo(print());
    }




    @Test
    public void testActualizarUsuarioSoloTelefono() throws Exception {
        // given
        Long usuarioId = 1L;

        Usuario usuarioExistente = new Usuario();
        usuarioExistente.setId(usuarioId);
        usuarioExistente.setTelefono("123456789");

        given(usuarioService.findById(usuarioId)).willReturn(Optional.of(usuarioExistente));
        given(usuarioService.actualizarUsuario(any(Usuario.class))).willAnswer(invocation -> {
            Usuario updatedUsuario = invocation.getArgument(0);
            updatedUsuario.setTelefono("987654321");
            return updatedUsuario;
        });

        // when
        ResultActions response = mockMvc.perform(
                multipart("/usuarios/{id}", usuarioId)
                        .param("telefono", "987654321")
                        .with(request -> {
                            request.setMethod("PUT");
                            return request;
                        })
                        .contentType(MediaType.MULTIPART_FORM_DATA)
        );

        // then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.telefono", is("987654321")))
                .andExpect(jsonPath("$.username").doesNotExist())
                .andExpect(jsonPath("$.nombre").doesNotExist())
                .andExpect(jsonPath("$.apellido").doesNotExist())
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

        given(usuarioService.findById(usuarioId)).willReturn(Optional.of(usuarioExistente));
        given(usuarioService.actualizarUsuario(any(Usuario.class))).willAnswer(invocation -> {
            Usuario updatedUsuario = invocation.getArgument(0);
            updatedUsuario.setUsername("nuevoUsername");
            updatedUsuario.setNombre("Nuevo Nombre");
            return updatedUsuario;
        });

        // when
        ResultActions response = mockMvc.perform(
                multipart("/usuarios/{id}", usuarioId)
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
