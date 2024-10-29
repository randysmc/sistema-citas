package com.sistema.examenes.sistema_examenes_backend.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.sistema.examenes.sistema_examenes_backend.controladores.ServicioController;
import com.sistema.examenes.sistema_examenes_backend.entidades.Servicio;
import com.sistema.examenes.sistema_examenes_backend.servicios.ServicioService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@WebMvcTest
@ActiveProfiles("test") // Usa el archivo application-test.properties
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // No reemplazar H2 por otra base de datos
@Transactional
public class ServicioControllerTest {

    /*@Autowired
    private MockMvc mockMvc;

    @MockBean
    private ServicioService servicioService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    void testCrearServicio() throws Exception {
        // Dado
        Servicio servicio = new Servicio();
        servicio.setNombre("Servicio de Prueba");
        servicio.setDescripcion("Descripción del servicio de prueba");
        servicio.setDuracionServicio(30);
        servicio.setPrecio(BigDecimal.valueOf(100.00));
        servicio.setDisponible(true);

        given(servicioService.save(any(Servicio.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        // Cuando
        ResultActions response = mockMvc.perform(post("/servicios")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(servicio)));

        // Entonces
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre", is(servicio.getNombre())))
                .andExpect(jsonPath("$.descripcion", is(servicio.getDescripcion())))
                .andExpect(jsonPath("$.duracionServicio", is(servicio.getDuracionServicio())))
                .andExpect(jsonPath("$.precio", is(servicio.getPrecio().doubleValue())))
                .andExpect(jsonPath("$.disponible", is(servicio.getDisponible())));
    }*/
}
