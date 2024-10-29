package com.sistema.examenes.sistema_examenes_backend.controller;

import com.sistema.examenes.sistema_examenes_backend.controladores.RolController;
import com.sistema.examenes.sistema_examenes_backend.entidades.Rol;
import com.sistema.examenes.sistema_examenes_backend.servicios.RolService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;

@WebMvcTest
@ActiveProfiles("test") // Usa el archivo application-test.properties
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // No reemplazar H2 por otra base de datos
@Transactional
public class RolControllerTest {
    /*@Autowired
    private MockMvc mockMvc;

    @Mock
    private RolService rolService;

    @InjectMocks
    private RolController rolController;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    public void testObtenerRoles() throws Exception {
        // Datos de prueba
        Rol rol1 = new Rol(1L, "ADMIN");
        Rol rol2 = new Rol(2L, "USER");
        List<Rol> roles = Arrays.asList(rol1, rol2);

        // Comportamiento simulado
        when(rolService.findAll()).thenReturn(roles);

        // Realizar la petición GET y verificar la respuesta
        mockMvc.perform(get("/roles/"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$[0].nombre").value("ADMIN"))
                .andExpect(jsonPath("$[1].nombre").value("USER"));

        // Verificar que se llamó al servicio
        verify(rolService, times(1)).findAll();
    }

    @Test
    public void testCrearRol() throws Exception {
        // Datos de prueba
        Rol nuevoRol = new Rol(3L, "MANAGER");
        when(rolService.save(any(Rol.class))).thenReturn(nuevoRol);

        // Realizar la petición POST y verificar la respuesta
        mockMvc.perform(post("/roles/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"nombre\":\"MANAGER\"}")) // Suponiendo que Rol tiene un campo nombre
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.nombre").value("MANAGER"));

        // Verificar que se llamó al servicio
        verify(rolService, times(1)).save(any(Rol.class));
    }*/
}
