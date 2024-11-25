package com.sistema.examenes.sistema_examenes_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistema.examenes.sistema_examenes_backend.Enums.EstadoCita;
import com.sistema.examenes.sistema_examenes_backend.entidades.Cita;
import com.sistema.examenes.sistema_examenes_backend.entidades.CitaResponse;
import com.sistema.examenes.sistema_examenes_backend.servicios.CitaService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class CitaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private CitaService citaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCrearCita() throws Exception {
        Cita cita = new Cita();
        cita.setFecha(LocalDate.of(2024, 11, 25));
        cita.setHoraInicio(LocalTime.of(10, 0));
        cita.setHoraFin(LocalTime.of(11, 0));
        cita.setEstado(EstadoCita.AGENDADA);

        // Simula el comportamiento del servicio
        when(citaService.crearCita(any(Cita.class))).thenReturn(cita);

        // Cambia el estado en el JSON a "AGENDADA" para que coincida con el valor asignado en el test
        mockMvc.perform(post("/citas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fecha\": \"2024-11-25\", \"horaInicio\": \"10:00\", \"horaFin\": \"11:00\", \"estado\": \"AGENDADA\"}"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.fecha[0]").value(2024))
                .andExpect(jsonPath("$.fecha[1]").value(11))
                .andExpect(jsonPath("$.fecha[2]").value(25))
                .andExpect(jsonPath("$.horaInicio[0]").value(10))
                .andExpect(jsonPath("$.horaInicio[1]").value(0))
                .andExpect(jsonPath("$.horaFin[0]").value(11))
                .andExpect(jsonPath("$.horaFin[1]").value(0))
                .andExpect(jsonPath("$.estado").value("AGENDADA"));
    }

    @Test
    public void testCrearCitaBadRequest() throws Exception {
        // Simula un error de tipo IllegalArgumentException
        when(citaService.crearCita(any(Cita.class))).thenThrow(new IllegalArgumentException("Datos inválidos"));

        // Realiza la solicitud y verifica el error 400
        mockMvc.perform(post("/citas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fecha\": \"2024-11-25\", \"horaInicio\": \"10:00\", \"horaFin\": \"11:00\", \"estado\": \"AGENDADA\"}"))
                .andExpect(status().isBadRequest()) // Espera el código de estado 400
                .andExpect(jsonPath("$.error").value("Datos inválidos")); // Verifica el mensaje de error
    }


    @Test
    public void testCrearCitaInternalServerError() throws Exception {
        // Simula un error genérico no controlado (Exception)
        when(citaService.crearCita(any(Cita.class))).thenThrow(new RuntimeException("Error inesperado"));

        // Realiza la solicitud y verifica el error 500
        mockMvc.perform(post("/citas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fecha\": \"2024-11-25\", \"horaInicio\": \"10:00\", \"horaFin\": \"11:00\", \"estado\": \"AGENDADA\"}"))
                .andExpect(status().isInternalServerError()) // Espera el código de estado 500
                .andExpect(jsonPath("$.error").value("Ocurrió un error inesperado: Error inesperado")); // Verifica el mensaje de error
    }


    @Test
    public void testCrearCitaAleatoria() throws Exception {
        Cita cita = new Cita();
        cita.setFecha(LocalDate.of(2024, 11, 25));
        cita.setHoraInicio(LocalTime.of(10, 0));
        cita.setHoraFin(LocalTime.of(11, 0));
        cita.setEstado(EstadoCita.AGENDADA);

        // Simula el comportamiento del servicio para citas aleatorias
        when(citaService.crearCitaAleatoria(any(Cita.class))).thenReturn(cita);

        // Realiza la solicitud para crear la cita aleatoria
        mockMvc.perform(post("/citas/aleatoria")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")) // No necesita contenido, ya que es aleatoria
                .andExpect(status().isCreated()) // Espera el código de estado 201
                .andExpect(jsonPath("$.fecha[0]").value(2024))
                .andExpect(jsonPath("$.fecha[1]").value(11))
                .andExpect(jsonPath("$.fecha[2]").value(25))
                .andExpect(jsonPath("$.horaInicio[0]").value(10))
                .andExpect(jsonPath("$.horaInicio[1]").value(0))
                .andExpect(jsonPath("$.horaFin[0]").value(11))
                .andExpect(jsonPath("$.horaFin[1]").value(0))
                .andExpect(jsonPath("$.estado").value("AGENDADA"));
    }




    @Test
    public void testCrearCitaAleatoriaBadRequest() throws Exception {
        // Simula un error de tipo IllegalArgumentException en el servicio
        when(citaService.crearCitaAleatoria(any(Cita.class))).thenThrow(new IllegalArgumentException("Datos inválidos"));

        // Realiza la solicitud para crear la cita aleatoria
        mockMvc.perform(post("/citas/aleatoria")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{}")) // No necesita contenido, ya que es aleatoria
                .andExpect(status().isBadRequest()) // Espera el código de estado 400
                .andExpect(jsonPath("$.error").value("Datos inválidos")); // Verifica el mensaje de error
    }

    @Test
    public void testCrearCitaAleatoriaInternalServerError() throws Exception {
        // Simula un error genérico no controlado (Exception)
        when(citaService.crearCitaAleatoria(any(Cita.class))).thenThrow(new RuntimeException("Error inesperado"));

        // Realiza la solicitud y verifica el error 500
        mockMvc.perform(post("/citas/aleatoria")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fecha\": \"2024-11-25\", \"horaInicio\": \"10:00\", \"horaFin\": \"11:00\", \"estado\": \"AGENDADA\"}"))
                .andExpect(status().isInternalServerError()) // Espera el código de estado 500
                .andExpect(jsonPath("$.error").value("Ocurrió un error inesperado: Error inesperado")); // Verifica el mensaje de error
    }

    @Test
    public void testObtenerCitas() throws Exception {
        List<Cita> citas = Arrays.asList(new Cita(), new Cita());
        when(citaService.obtenerCitas()).thenReturn(citas);

        mockMvc.perform(get("/citas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // Verifica que hay 2 citas
                .andExpect(jsonPath("$[0]").isNotEmpty()); // Verifica que la primera cita no esté vacía
    }


    @Test
    public void testObtenerCitasAgendadas() throws Exception {
        List<Cita> citasAgendadas = Arrays.asList(new Cita(), new Cita());
        when(citaService.obtenerCitasAgendadas()).thenReturn(citasAgendadas);

        mockMvc.perform(get("/citas/agendadas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // Verifica que hay 2 citas agendadas
                .andExpect(jsonPath("$[0]").isNotEmpty()); // Verifica que la primera cita no esté vacía
    }

    @Test
    public void testObtenerCitasCanceladas() throws Exception {
        List<Cita> citasCanceladas = Arrays.asList(new Cita(), new Cita());
        when(citaService.obtenerCitasCanceladas()).thenReturn(citasCanceladas);

        mockMvc.perform(get("/citas/canceladas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // Verifica que hay 2 citas agendadas
                .andExpect(jsonPath("$[0]").isNotEmpty()); // Verifica que la primera cita no esté vacía
    }


    @Test
    public void testObtenerCitasRealizadas() throws Exception {
        List<Cita> citasRealizadas = Arrays.asList(new Cita(), new Cita());
        when(citaService.obtenerCitasRealizadas()).thenReturn(citasRealizadas);

        mockMvc.perform(get("/citas/realizadas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // Verifica que hay 2 citas realizadas
                .andExpect(jsonPath("$[0]").isNotEmpty()); // Verifica que la primera cita no esté vacía
    }

    @Test
    public void testObtenerCitasPorUsuario() throws Exception {
        Long usuarioId = 1L;
        List<Cita> citas = Arrays.asList(new Cita(), new Cita());
        when(citaService.obtenerCitaPorUsuario(usuarioId)).thenReturn(citas);

        mockMvc.perform(get("/citas/usuario/{usuarioId}", usuarioId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // Verifica que hay 2 citas para el usuario
                .andExpect(jsonPath("$[0]").isNotEmpty()); // Verifica que la primera cita no esté vacía
    }

    @Test
    public void testObtenerCitaPorId() throws Exception {
        Long citaId = 1L;
        Cita cita = new Cita();
        when(citaService.obtenerCitaPorId(citaId)).thenReturn(cita);

        mockMvc.perform(get("/citas/{id}", citaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty()); // Verifica que la cita no esté vacía
    }

    @Test
    public void testActualizarCita() throws Exception {
        Cita cita = new Cita();
        cita.setFecha(LocalDate.of(2024, 11, 25));
        cita.setHoraInicio(LocalTime.of(10, 0));
        cita.setHoraFin(LocalTime.of(11, 0));
        cita.setEstado(EstadoCita.AGENDADA);

        when(citaService.actualizaCita(any(Cita.class))).thenReturn(cita);

        mockMvc.perform(put("/citas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content("{\"fecha\":\"2024-11-25\",\"horaInicio\":\"10:00\",\"horaFin\":\"11:00\",\"estado\":\"AGENDADA\"}"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.fecha[0]").value(2024))
                .andExpect(jsonPath("$.fecha[1]").value(11))
                .andExpect(jsonPath("$.fecha[2]").value(25))
                .andExpect(jsonPath("$.horaInicio[0]").value(10))
                .andExpect(jsonPath("$.horaInicio[1]").value(0))
                .andExpect(jsonPath("$.horaFin[0]").value(11))
                .andExpect(jsonPath("$.horaFin[1]").value(0))
                .andExpect(jsonPath("$.estado").value("AGENDADA"));
    }


    @Test
    public void testCancelarCita() throws Exception {
        Long citaId = 1L;
        Cita citaCancelada = new Cita();
        CitaResponse response = new CitaResponse("La cita fue cancelada.", citaCancelada);

        when(citaService.cancelarCita(citaId)).thenReturn(citaCancelada);

        mockMvc.perform(put("/citas/{id}/cancelar", citaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("La cita fue cancelada."));
    }


    @Test
    public void testConfirmarCita() throws Exception {
        Long citaId = 1L;
        Cita citaConfirmada = new Cita();
        CitaResponse response = new CitaResponse("La cita fue confirmada.", citaConfirmada);

        when(citaService.confirmarCita(citaId)).thenReturn(citaConfirmada);

        mockMvc.perform(put("/citas/{id}/confirmar", citaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("La cita fue confirmada."));
    }


    @Test
    public void testRealizarCita() throws Exception {
        Long citaId = 1L;
        Cita citaCompletada = new Cita();
        CitaResponse response = new CitaResponse("La cita fue realizada.", citaCompletada);

        when(citaService.completarCita(citaId)).thenReturn(citaCompletada);

        mockMvc.perform(put("/citas/{id}/completar", citaId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.mensaje").value("La cita fue realizada."));
    }


    @Test
    public void testObtenerCitasPorEmpleado() throws Exception {
        Long empleadoId = 1L;
        List<Cita> citas = Arrays.asList(new Cita(), new Cita());
        when(citaService.obtenerCitaPorEmpleado(empleadoId)).thenReturn(citas);

        mockMvc.perform(get("/citas/empleado/{empleadoId}", empleadoId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2))) // Verifica que hay 2 citas para el empleado
                .andExpect(jsonPath("$[0]").isNotEmpty()); // Verifica que la primera cita no esté vacía
    }





}
