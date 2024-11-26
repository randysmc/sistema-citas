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
import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;
import com.sistema.examenes.sistema_examenes_backend.entidades.Permiso;
import com.sistema.examenes.sistema_examenes_backend.excepciones.NegocioExistenteException;
import com.sistema.examenes.sistema_examenes_backend.repositorios.PermisoRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.NegocioService;
import com.sistema.examenes.sistema_examenes_backend.servicios.PermisoService;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class NegocioControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NegocioService negocioService;

    @Autowired
    private ObjectMapper objectMapper;






    @Test
    public void testObtenerNegocioPorId() throws Exception {
        //given
        Long negocioId = 1L;

        Negocio negocio = new Negocio();
        negocio.setNegocioId(1L);
        negocio.setNombre("Negocio prueba");
        given(negocioService.findById(negocioId)).willReturn(Optional.of(negocio));

        //when
        ResultActions response = mockMvc.perform(get("/negocios/{id}", negocioId));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect((jsonPath("$.nombre", is(negocio.getNombre()))));
    }

    @Test
    public void testHabilitarCitasAleatorias() throws Exception {
        Long negocioId = 1L;

        // Simulamos el comportamiento del servicio
        willDoNothing().given(negocioService).cambiarTipoCitas(negocioId, true);

        // when
        ResultActions response = mockMvc.perform(put("/negocios/habilitar/{id}", negocioId));

        // then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Citas aleatorias habilitadas")));
    }

    @Test
    public void testDeshabilitarCitasAleatorias() throws Exception {
        Long negocioId = 1L;

        // Simulamos el comportamiento del servicio
        willDoNothing().given(negocioService).cambiarTipoCitas(negocioId, false);

        // when
        ResultActions response = mockMvc.perform(put("/negocios/deshabilitar/{id}", negocioId));

        // then
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Citas aleatorias deshabilitadas")));
    }


    @Test
    public void testEliminarNegocio() throws Exception {
        Long negocioId = 1L;

        // Simulamos el comportamiento del servicio
        willDoNothing().given(negocioService).eliminarNegocio(negocioId);

        // when
        ResultActions response = mockMvc.perform(delete("/negocios/{id}", negocioId));

        // then
        response.andExpect(status().isNoContent())
                .andDo(print());
    }



}

