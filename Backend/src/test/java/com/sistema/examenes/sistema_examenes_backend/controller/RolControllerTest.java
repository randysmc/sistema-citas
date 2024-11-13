package com.sistema.examenes.sistema_examenes_backend.controller;

import com.sistema.examenes.sistema_examenes_backend.controladores.RolController;
import com.sistema.examenes.sistema_examenes_backend.entidades.Rol;
import com.sistema.examenes.sistema_examenes_backend.repositorios.RolRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.RolService;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class RolControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RolRepository rolRepository;

    @BeforeEach
    void setUp() {
        rolRepository.save(new Rol(1L, "ADMIN"));
        rolRepository.save(new Rol(2L, "USER"));
    }

    @AfterEach
    void tearDown() {
        rolRepository.deleteAll();
    }

    @Test
    public void testObtenerRoles() throws Exception {
        mockMvc.perform(get("/roles/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].rolId").value(1))
                .andExpect(jsonPath("$[0].rolNombre").value("ADMIN"))
                .andExpect(jsonPath("$[1].rolId").value(2))
                .andExpect(jsonPath("$[1].rolNombre").value("USER"));
    }
}
