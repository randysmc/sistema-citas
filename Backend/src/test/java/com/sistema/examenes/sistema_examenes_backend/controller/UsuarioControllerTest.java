package com.sistema.examenes.sistema_examenes_backend.controller;

import com.sistema.examenes.sistema_examenes_backend.controladores.UsuarioController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistema.examenes.sistema_examenes_backend.DTO.UsuarioDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Usuario;
import com.sistema.examenes.sistema_examenes_backend.servicios.UsuarioService;
import com.sistema.examenes.sistema_examenes_backend.servicios.RolService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.util.Collections;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import javax.transaction.Transactional;

@WebMvcTest
@ActiveProfiles("test") // Usa el archivo application-test.properties
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // No reemplazar H2 por otra base de datos
@Transactional
public class UsuarioControllerTest {

    /*@Autowired
    private MockMvc mockMvc;

    @Mock
    private UsuarioService usuarioService;

    @Mock
    private RolService rolService;

    @InjectMocks
    private UsuarioController usuarioController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        mockMvc = MockMvcBuilders.standaloneSetup(usuarioController).build();
    }

    @Test
    void listarUsuarios() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("testuser");

        when(usuarioService.obtenerUsuarios()).thenReturn(Collections.singletonList(usuario));

        mockMvc.perform(get("/usuarios"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].username").value("testuser"));

        verify(usuarioService, times(1)).obtenerUsuarios();
    }

    @Test
    void guardarUsuario() throws Exception {
        UsuarioDTO usuarioDTO = new UsuarioDTO();
        usuarioDTO.setUsername("testuser");
        usuarioDTO.setPassword("password");
        usuarioDTO.setNombre("Test");
        usuarioDTO.setApellido("User");

        Usuario usuarioGuardado = new Usuario();
        usuarioGuardado.setId(1L);
        usuarioGuardado.setUsername("testuser");

        when(usuarioService.guardarUsuario(any(), any())).thenReturn(usuarioGuardado);

        mockMvc.perform(post("/usuarios/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(usuarioDTO)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.username").value("testuser"));

        verify(usuarioService, times(1)).guardarUsuario(any(), any());
    }

    @Test
    void obtenerUsuarioPorId() throws Exception {
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setUsername("testuser");

        when(usuarioService.findById(1L)).thenReturn(Optional.of(usuario));

        mockMvc.perform(get("/usuarios/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.username").value("testuser"));

        verify(usuarioService, times(1)).findById(1L);
    }

    @Test
    void activarUsuario() throws Exception {
        Usuario usuarioActivado = new Usuario();
        usuarioActivado.setId(1L);
        usuarioActivado.setEnabled(true);

        when(usuarioService.activarUsuario(1L)).thenReturn(usuarioActivado);

        mockMvc.perform(put("/usuarios/activar/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.enabled").value(true));

        verify(usuarioService, times(1)).activarUsuario(1L);
    }

    @Test
    void desactivarUsuario() throws Exception {
        Usuario usuarioDesactivado = new Usuario();
        usuarioDesactivado.setId(1L);
        usuarioDesactivado.setEnabled(false);

        when(usuarioService.desactivarUsuario(1L)).thenReturn(usuarioDesactivado);

        mockMvc.perform(put("/usuarios/desactivar/{id}", 1L))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.enabled").value(false));

        verify(usuarioService, times(1)).desactivarUsuario(1L);
    }*/
}
