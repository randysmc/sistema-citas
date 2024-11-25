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
import com.sistema.examenes.sistema_examenes_backend.controladores.PermisoController;
import com.sistema.examenes.sistema_examenes_backend.entidades.Permiso;
import com.sistema.examenes.sistema_examenes_backend.entidades.Rol;
import com.sistema.examenes.sistema_examenes_backend.repositorios.PermisoRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.PermisoService;
import com.sistema.examenes.sistema_examenes_backend.servicios.RolService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private RolService rolService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("POST /roles - Crear rol exitosamente")
    void testCrearRolExitoso() throws Exception {
        // Datos de entrada
        Rol rol = new Rol(null, "ADMIN");
        Rol rolCreado = new Rol(1L, "ADMIN");

        // Mockear el servicio
        given(rolService.save(any(Rol.class))).willReturn(rolCreado);

        // Petición al controlador
        mockMvc.perform(post("/roles/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rol)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.message", is("Rol creado exitosamente")))
                .andExpect(jsonPath("$.data.rolNombre", is("ADMIN")))
                .andDo(print());
    }

    @Test
    @DisplayName("POST /roles - Error por nombre vacío")
    void testCrearRolConNombreVacio() throws Exception {
        // Datos de entrada: nombre vacío
        Rol rol = new Rol(null, "");

        // Petición al controlador
        mockMvc.perform(post("/roles/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rol)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$.message", is("El nombre del rol no puede estar vacío")))
                .andDo(print());
    }

    @Test
    @DisplayName("POST /roles - Error al guardar rol")
    void testCrearRolError() throws Exception {
        // Datos de entrada
        Rol rol = new Rol(null, "ADMIN");

        // Mockear el servicio para lanzar una excepción
        given(rolService.save(any(Rol.class))).willThrow(new RuntimeException("Error inesperado"));

        // Petición al controlador
        mockMvc.perform(post("/roles/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(rol)))
                .andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is("Error al crear el rol: Error inesperado")))
                .andDo(print());
    }


    @DisplayName("GET /roles - Obtener roles con contenido")
    @Test
    void testObtenerRolesConContenido() throws Exception {
        // Datos simulados
        List<Rol> roles = new ArrayList<>();
        roles.add(new Rol(1L, "ADMIN"));
        roles.add(new Rol(2L, "USER"));

        // Mockear el servicio
        given(rolService.findAll()).willReturn(roles);

        // Petición al controlador
        mockMvc.perform(get("/roles/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(2)))
                .andExpect(jsonPath("$[0].rolNombre", is("ADMIN")))
                .andExpect(jsonPath("$[1].rolNombre", is("USER")))
                .andDo(print());
    }

    @Test
    @DisplayName("GET /roles - Sin contenido")
    void testObtenerRolesSinContenido() throws Exception {
        // Datos simulados: lista vacía
        given(rolService.findAll()).willReturn(new ArrayList<>());

        // Petición al controlador
        mockMvc.perform(get("/roles/")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNoContent())
                .andDo(print());
    }


}
