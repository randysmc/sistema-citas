package com.sistema.examenes.sistema_examenes_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistema.examenes.sistema_examenes_backend.Enums.EstadoCita;
import com.sistema.examenes.sistema_examenes_backend.entidades.Cita;
import com.sistema.examenes.sistema_examenes_backend.entidades.CitaResponse;
import com.sistema.examenes.sistema_examenes_backend.entidades.Factura;
import com.sistema.examenes.sistema_examenes_backend.servicios.CitaService;
import com.sistema.examenes.sistema_examenes_backend.servicios.FacturaService;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class FacturaControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacturaService facturaService;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void obtenerFacturas_debeRetornarListaDeFacturas() throws Exception {
        // Configuración de datos simulados
        Factura factura1 = new Factura();
        factura1.setFacturaId(1L);
        factura1.setMonto(BigDecimal.valueOf(150.00));
        factura1.setDetalleServicio("Servicio realizado");
        factura1.setFecha(LocalDate.now());

        Factura factura2 = new Factura();
        factura2.setFacturaId(2L);
        factura2.setMonto(BigDecimal.valueOf(200.00));
        factura2.setDetalleServicio("Otro servicio");
        factura2.setFecha(LocalDate.now());

        Mockito.when(facturaService.obtenerFacturass()).thenReturn(List.of(factura1, factura2));

        // Petición al endpoint y validación
        mockMvc.perform(get("/facturas"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].facturaId", is(1)))
                .andExpect(jsonPath("$[0].monto", is(150.00)))
                .andExpect(jsonPath("$[0].detalleServicio", is("Servicio realizado")))
                .andExpect(jsonPath("$[1].facturaId", is(2)))
                .andExpect(jsonPath("$[1].monto", is(200.00)))
                .andExpect(jsonPath("$[1].detalleServicio", is("Otro servicio")));
    }

    @Test
    public void crearFactura_debeRetornarFacturaCreada() throws Exception {
        // Configuración de datos simulados
        Factura factura = new Factura();
        factura.setMonto(BigDecimal.valueOf(100.00));
        factura.setDetalleServicio("Servicio ejemplo");
        factura.setFecha(LocalDate.now());

        Factura facturaCreada = new Factura();
        facturaCreada.setFacturaId(1L);
        facturaCreada.setMonto(factura.getMonto());
        facturaCreada.setDetalleServicio(factura.getDetalleServicio());
        facturaCreada.setFecha(factura.getFecha());

        Mockito.when(facturaService.crearFactura(Mockito.any(Factura.class))).thenReturn(facturaCreada);

        // Petición al endpoint y validación
        mockMvc.perform(post("/facturas/crear")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(factura)))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.facturaId", is(1)))
                .andExpect(jsonPath("$.monto", is(100.00)))
                .andExpect(jsonPath("$.detalleServicio", is("Servicio ejemplo")));
    }

    @Test
    public void obtenerFacturaPorId_debeRetornarFactura() throws Exception {
        // Configuración de datos simulados
        Factura factura = new Factura();
        factura.setFacturaId(1L);
        factura.setMonto(BigDecimal.valueOf(150.00));
        factura.setDetalleServicio("Servicio específico");
        factura.setFecha(LocalDate.now());

        Mockito.when(facturaService.obtenerFacturaPorId(1L)).thenReturn(factura);

        // Petición al endpoint y validación
        mockMvc.perform(get("/facturas/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.facturaId", is(1)))
                .andExpect(jsonPath("$.monto", is(150.00)))
                .andExpect(jsonPath("$.detalleServicio", is("Servicio específico")));
    }

    @Test
    public void obtenerFacturaPorId_debeRetornar404SiNoExiste() throws Exception {
        // Configuración de datos simulados
        Mockito.when(facturaService.obtenerFacturaPorId(1L)).thenReturn(null);

        // Petición al endpoint y validación
        mockMvc.perform(get("/facturas/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("Error: Factura no encontrada con ID 1")));
    }

    @Test
    public void obtenerFacturasPorUsuario_debeRetornarFacturas() throws Exception {
        // Configuración de datos simulados
        Factura factura = new Factura();
        factura.setFacturaId(1L);
        factura.setMonto(BigDecimal.valueOf(100.0));
        factura.setDetalleServicio("Servicio realizado");
        factura.setFecha(LocalDate.now());

        Mockito.when(facturaService.obtenerFacturasPorUsuario(1L)).thenReturn(List.of(factura));

        // Petición al endpoint y validación
        mockMvc.perform(get("/facturas/usuario/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(1)))
                .andExpect(jsonPath("$[0].facturaId", is(1)))
                .andExpect(jsonPath("$[0].monto", is(100.0)))
                .andExpect(jsonPath("$[0].detalleServicio", is("Servicio realizado")));
    }

    @Test
    public void obtenerFacturasPorUsuario_debeRetornar404SiNoHayFacturas() throws Exception {
        Mockito.when(facturaService.obtenerFacturasPorUsuario(1L)).thenReturn(List.of());

        mockMvc.perform(get("/facturas/usuario/1"))
                .andExpect(status().isNotFound())
                .andExpect(content().string(containsString("No se encontraron facturas para el usuario con ID 1")));
    }


    @Test
    public void actualizarFactura_debeRetornarFacturaActualizada() throws Exception {
        Factura factura = new Factura();
        factura.setFacturaId(1L);
        factura.setMonto(BigDecimal.valueOf(150.0));
        factura.setDetalleServicio("Servicio actualizado");
        factura.setFecha(LocalDate.now());

        Mockito.when(facturaService.actualizarFactura(Mockito.any(Factura.class))).thenReturn(factura);

        mockMvc.perform(put("/facturas")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(factura)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.facturaId", is(1)))
                .andExpect(jsonPath("$.monto", is(150.0)))
                .andExpect(jsonPath("$.detalleServicio", is("Servicio actualizado")));
    }


    @Test
    public void crearFacturaDesdeCita_debeRetornarFacturaCreada() throws Exception {
        Factura factura = new Factura();
        factura.setFacturaId(1L);
        factura.setMonto(BigDecimal.valueOf(200.0));
        factura.setDetalleServicio("Factura desde cita");
        factura.setFecha(LocalDate.now());

        Mockito.when(facturaService.crearFacturaDesdeCita(1L)).thenReturn(factura);

        mockMvc.perform(post("/facturas/crearDesdeCita/1"))
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.facturaId", is(1)))
                .andExpect(jsonPath("$.monto", is(200.0)))
                .andExpect(jsonPath("$.detalleServicio", is("Factura desde cita")));
    }

    @Test
    public void obtenerFacturaPorCita_debeRetornarFactura() throws Exception {
        Factura factura = new Factura();
        factura.setFacturaId(1L);
        factura.setMonto(BigDecimal.valueOf(120.0));
        factura.setDetalleServicio("Factura por cita");
        factura.setFecha(LocalDate.now());

        Mockito.when(facturaService.obtenerFacturaPorCita(1L)).thenReturn(factura);

        mockMvc.perform(get("/facturas/cita/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.facturaId", is(1)))
                .andExpect(jsonPath("$.monto", is(120.0)))
                .andExpect(jsonPath("$.detalleServicio", is("Factura por cita")));
    }


}
