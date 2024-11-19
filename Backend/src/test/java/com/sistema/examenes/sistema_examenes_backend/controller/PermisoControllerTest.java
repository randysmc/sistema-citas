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
import com.sistema.examenes.sistema_examenes_backend.repositorios.PermisoRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.PermisoService;
import org.junit.jupiter.api.BeforeEach;
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

//@WebMvcTest
@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PermisoControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private PermisoService permisoService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void testGuardarPermiso() throws Exception {
        // given
        Permiso permiso = new Permiso();
        permiso.setId(1L);
        permiso.setNombre("CREAR");

        // Configuramos el comportamiento del mock del servicio
        given(permisoService.save(any(Permiso.class))).willReturn(permiso);

        // when
        ResultActions response = mockMvc.perform(post("/permisos/")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(permiso)));

        // then
        response.andDo(print())
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.nombre", is(permiso.getNombre())));
    }

    @Test
    public void testListarPermisos() throws Exception{
        //given
        Permiso permiso1 = new Permiso();
        permiso1.setId(1L);
        permiso1.setNombre("CREAR");

        Permiso permiso2 = new Permiso();
        permiso1.setId(2L);
        permiso1.setNombre("EDITAR");

        List<Permiso> listaPermisos = new ArrayList<>();
        listaPermisos.add(permiso1);
        listaPermisos.add(permiso2);

        given(permisoService.findAll()).willReturn(listaPermisos);

        //when
        ResultActions response = mockMvc.perform(get("/permisos/"));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect(jsonPath("$.size()",is(listaPermisos.size())));
    }

    @Test
    public void testObtenerPermisoPorId() throws Exception {
        //given
        Long permisoId = 1L;

        Permiso permiso = new Permiso();
        permiso.setId(1L);
        permiso.setNombre("CREAR");
        given(permisoService.findById(permisoId)).willReturn(Optional.of(permiso));

        //when
        ResultActions response = mockMvc.perform(get("/permisos/{id}", permisoId));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect((jsonPath("$.nombre", is(permiso.getNombre()))));
    }

    @Test
    public void testObtenerPermisoNoEntontrado() throws Exception {
        //given
        Long permisoId = 1L;

        Permiso permiso = new Permiso();
        permiso.setId(1L);
        permiso.setNombre("CREAR");
        given(permisoService.findById(permisoId)).willReturn(Optional.empty());

        //when
        ResultActions response = mockMvc.perform(get("/permisos/{id}", permisoId));

        //then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }


    @Test
    public void testActualizarPermiso() throws Exception{
        //given
        Long permisoId = 1L;

        Permiso permiso = new Permiso();
        permiso.setId(1L);
        permiso.setNombre("CREAR");

        Permiso permisoActualizado = new Permiso();
        permiso.setNombre("EDITAR");

        given(permisoService.findById(permisoId)).willReturn(Optional.of(permiso));
        given(permisoService.update(any(Permiso.class)))
                .willAnswer((invocation)-> invocation.getArgument(0));

        //when
        ResultActions response = mockMvc.perform(put("/permisos/{id}", permisoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(permisoActualizado)));

        //then
        response.andExpect(status().isOk())
                .andDo(print())
                .andExpect((jsonPath("$.nombre", is(permisoActualizado.getNombre()))));
    }

    /*@Test
    public void testActualizarPermisoNoEncontrado() throws Exception {
        //given
        Long permisoId = 1L;

        Permiso permiso = new Permiso();
        permiso.setId(1L);
        permiso.setNombre("CREAR");

        Permiso permisoActualizado = new Permiso();
        permiso.setNombre("EDITAR");

        given(permisoService.findById(permisoId)).willReturn(Optional.empty());
        given(permisoService.update(any(Permiso.class)))
                .willAnswer((invocation) -> invocation.getArgument(0));

        //when
        ResultActions response = mockMvc.perform(put("/permisos/{id}", permisoId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(permisoActualizado)));

        //then
        response.andExpect(status().isNotFound())
                .andDo(print());
    }*/

    /*@Test
    public void testEliminarPermiso() throws Exception{
        //given
        Long permisoId = 1L;
        willDoNothing().given(permisoService).delete(permisoId);

        //when
        ResultActions response = mockMvc.perform(delete("/permisos/{id}", permisoId));

        //then
        response.andExpect(status().isOk())
                .andDo(print());
    }*/
}
