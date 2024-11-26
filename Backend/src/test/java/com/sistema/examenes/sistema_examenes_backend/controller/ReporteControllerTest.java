package com.sistema.examenes.sistema_examenes_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistema.examenes.sistema_examenes_backend.DTO.ReporteDTO;
import com.sistema.examenes.sistema_examenes_backend.DTO.ServiceReportDTO;
import com.sistema.examenes.sistema_examenes_backend.DTO.UsuarioReporteDTO;
import com.sistema.examenes.sistema_examenes_backend.Enums.EstadoCita;
import com.sistema.examenes.sistema_examenes_backend.entidades.Cita;
import com.sistema.examenes.sistema_examenes_backend.entidades.CitaResponse;
import com.sistema.examenes.sistema_examenes_backend.servicios.CitaService;
import com.sistema.examenes.sistema_examenes_backend.servicios.ReporteService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

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
public class ReporteControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private ReporteService reporteService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    @DisplayName("Prueba contar citas por cliente")
    void contarCitasPorClienteTest() throws Exception {
        // Crear datos simulados en forma de mapa
        Map<String, Object> detallesReporte = Map.of(
                "nombre", "Juan",
                "apellido", "Pérez",
                "numeroCitas", 3L
        );

        // Crear la lista de ReporteDTO con los datos simulados
        List<ReporteDTO> mockDatos = List.of(new ReporteDTO(detallesReporte));

        // Simular el comportamiento del servicio
        when(reporteService.contarCitasPorCliente()).thenReturn(mockDatos);

        // Ejecutar la solicitud GET al endpoint
        mockMvc.perform(get("/reportes/citas/por-cliente"))
                .andExpect(status().isOk()) // Verificar que la respuesta sea 200 OK
                .andExpect(jsonPath("$.length()").value(1)) // Verificar que hay un elemento en la lista
                .andExpect(jsonPath("$[0].detalles.nombre").value("Juan")) // Verificar el nombre
                .andExpect(jsonPath("$[0].detalles.apellido").value("Pérez")) // Verificar el apellido
                .andExpect(jsonPath("$[0].detalles.numeroCitas").value(3)); // Verificar el número de citas
    }

    @Test
    @DisplayName("Prueba obtener usuario con más citas agendadas")
    void obtenerUsuarioConMasCitasAgendadasTest() throws Exception {
        // Crear datos simulados
        Map<String, Object> detallesReporte = Map.of(
                "nombre", "Ringo",
                "apellido", "Sum",
                "numeroCitas", 10L
        );

        // Crear la lista de ReporteDTO con los datos simulados
        List<ReporteDTO> mockDatos = List.of(new ReporteDTO(detallesReporte));

        // Simular el comportamiento del servicio
        when(reporteService.obtenerUsuarioConMasCitasAgendadas()).thenReturn(mockDatos);

        // Ejecutar la solicitud GET al endpoint
        mockMvc.perform(get("/reportes/citas/usuario-mas-agendadas"))
                .andExpect(status().isOk()) // Verificar que la respuesta sea 200 OK
                .andExpect(jsonPath("$.length()").value(1)) // Verificar que hay un elemento en la lista
                .andExpect(jsonPath("$[0].detalles.nombre").value("Ringo")) // Verificar el nombre
                .andExpect(jsonPath("$[0].detalles.apellido").value("Sum")) // Verificar el apellido
                .andExpect(jsonPath("$[0].detalles.numeroCitas").value(10)); // Verificar el número de citas


    }

    @Test
    @DisplayName("Prueba obtener usuario con más citas canceladas")
    void obtenerUsuarioConMasCitasCanceladasTest() throws Exception {
        // Crear datos simulados
        Map<String, Object> detallesReporte = Map.of(
                "nombre", "Ana",
                "apellido", "López",
                "numeroCitas", 5L
        );

        // Crear la lista de ReporteDTO con los datos simulados
        List<ReporteDTO> mockDatos = List.of(new ReporteDTO(detallesReporte));

        // Simular el comportamiento del servicio
        when(reporteService.obtenerUsuarioConMasCitasCanceladas()).thenReturn(mockDatos);

        // Ejecutar la solicitud GET al endpoint
        mockMvc.perform(get("/reportes/citas/usuario-mas-canceladas"))
                .andExpect(status().isOk()) // Verificar que la respuesta sea 200 OK
                .andExpect(jsonPath("$.length()").value(1)) // Verificar que hay un elemento en la lista
                .andExpect(jsonPath("$[0].detalles.nombre").value("Ana")) // Verificar el nombre
                .andExpect(jsonPath("$[0].detalles.apellido").value("López")) // Verificar el apellido
                .andExpect(jsonPath("$[0].detalles.numeroCitas").value(5)); // Verificar el número de citas

    }

    @Test
    @DisplayName("Prueba obtener horarios más solicitados")
    void obtenerHorariosMasSolicitadosTest() throws Exception {
        // Simulamos el comportamiento del servicio (ReporteService)
        Map<String, Object> mockDetalles = new HashMap<>();
        mockDetalles.put("9:00", 5L);
        mockDetalles.put("10:00", 8L);
        ReporteDTO mockReporteDTO = new ReporteDTO(mockDetalles);

        // Mockeamos el servicio
        when(reporteService.obtenerHorariosMasSolicitados()).thenReturn(mockReporteDTO);

        // Realizamos la solicitud HTTP (GET)
        mockMvc.perform(get("/reportes/horarios-mas-solicitados"))
                .andExpect(status().isOk()) // Verificamos que el código de estado es 200
                .andExpect(jsonPath("$.detalles['9:00']").value(5)) // Verificamos el valor del detalle para 9:00
                .andExpect(jsonPath("$.detalles['10:00']").value(8)); // Verificamos el valor del detalle para 10:00

        // Verificamos que el servicio haya sido llamado una vez

    }


    @Test
    @DisplayName("Prueba obtener recursos más y menos utilizados")
    void obtenerRecursosMasYMenosUtilizadosTest() throws Exception {
        // Simulamos el comportamiento del servicio
        Map<String, Object> mockDetalles = new HashMap<>();
        mockDetalles.put("Mas utilizado", "Recurso 1");
        mockDetalles.put("Menos utilizado", "Recurso 2");
        ReporteDTO mockReporteDTO = new ReporteDTO(mockDetalles);

        // Mockeamos el servicio
        when(reporteService.obtenerRecursosMasYMenosUtilizados()).thenReturn(mockReporteDTO);

        // Realizamos la solicitud HTTP (GET)
        mockMvc.perform(get("/reportes/recursos-mas-menos-utilizados"))
                .andExpect(status().isOk()) // Verificamos que el código de estado es 200
                .andExpect(jsonPath("$.detalles['Mas utilizado']").value("Recurso 1"))
                .andExpect(jsonPath("$.detalles['Menos utilizado']").value("Recurso 2"));

        // Verificamos que el servicio haya sido llamado una vez
    }

    @Test
    @DisplayName("Prueba obtener tasa de cancelación por servicio")
    void obtenerTasaCancelacionPorServicioTest() throws Exception {
        // Simulamos el comportamiento del servicio
        Map<String, Object> mockDetalles = new HashMap<>();
        mockDetalles.put("Servicio 1", "30.0%");
        mockDetalles.put("Servicio 2", "20.0%");
        ReporteDTO mockReporteDTO = new ReporteDTO(mockDetalles);

        // Mockeamos el servicio
        when(reporteService.obtenerTasaCancelacionPorServicio()).thenReturn(mockReporteDTO);

        // Realizamos la solicitud HTTP (GET)
        mockMvc.perform(get("/reportes/tasa-cancelacion-servicio"))
                .andExpect(status().isOk()) // Verificamos que el código de estado es 200
                .andExpect(jsonPath("$.detalles['Servicio 1']").value("30.0%"))
                .andExpect(jsonPath("$.detalles['Servicio 2']").value("20.0%"));

    }

    @Test
    @DisplayName("Prueba obtener lista de recursos utilizados")
    void obtenerListaRecursosUtilizadosTest() throws Exception {
        // Simulamos el comportamiento del servicio
        Map<String, Object> mockDetalles = new HashMap<>();
        mockDetalles.put("Recurso A", 5L);
        mockDetalles.put("Recurso B", 8L);
        ReporteDTO mockReporteDTO = new ReporteDTO(mockDetalles);

        // Mockeamos el servicio
        when(reporteService.obtenerListaRecursosUtilizados()).thenReturn(mockReporteDTO);

        // Realizamos la solicitud HTTP (GET)
        mockMvc.perform(get("/reportes/lista-recursos-utilizados"))
                .andExpect(status().isOk()) // Verificamos que el código de estado es 200
                .andExpect(jsonPath("$.detalles['Recurso A']").value(5))
                .andExpect(jsonPath("$.detalles['Recurso B']").value(8));


    }

    @Test
    @DisplayName("Prueba obtener lista de servicios utilizados")
    void obtenerListaServiciosUtilizadosTest() throws Exception {
        // Simulamos el comportamiento del servicio
        Map<String, Object> mockDetalles = new HashMap<>();
        mockDetalles.put("Servicio 1", 10L);
        mockDetalles.put("Servicio 2", 15L);
        ReporteDTO mockReporteDTO = new ReporteDTO(mockDetalles);

        // Mockeamos el servicio
        when(reporteService.obtenerListaServiciosUtilizados()).thenReturn(mockReporteDTO);

        // Realizamos la solicitud HTTP (GET)
        mockMvc.perform(get("/reportes/lista-servicios-utilizados"))
                .andExpect(status().isOk()) // Verificamos que el código de estado es 200
                .andExpect(jsonPath("$.detalles['Servicio 1']").value(10))
                .andExpect(jsonPath("$.detalles['Servicio 2']").value(15));


    }

    @Test
    @DisplayName("Prueba obtener los servicios más utilizados")
    void testGetMostUsedServices() throws Exception {
        // Simulamos el comportamiento del servicio
        List<ServiceReportDTO> mockReporte = new ArrayList<>();
        mockReporte.add(new ServiceReportDTO("Servicio1", 100L));
        mockReporte.add(new ServiceReportDTO("Servicio2", 50L));

        // Mockeamos el servicio
        when(reporteService.getMostUsedServices()).thenReturn(mockReporte);

        // Realizamos la solicitud HTTP (GET)
        mockMvc.perform(get("/reportes/servicio-mas-usado"))
                .andExpect(status().isOk()) // Verificamos que el código de estado es 200
                .andExpect(jsonPath("$[0].serviceName").value("Servicio1"))
                .andExpect(jsonPath("$[0].usageCount").value(100))
                .andExpect(jsonPath("$[1].serviceName").value("Servicio2"))
                .andExpect(jsonPath("$[1].usageCount").value(50));
    }

    @Test
    @DisplayName("Prueba obtener los servicios más utilizados por mes")
    void testGetMostUsedServicesByMonth() throws Exception {
        // Simulamos el comportamiento del servicio
        List<ServiceReportDTO> mockReporte = new ArrayList<>();
        mockReporte.add(new ServiceReportDTO("Servicio1", 200L));
        mockReporte.add(new ServiceReportDTO("Servicio2", 150L));

        int month = 10;
        int year = 2024;

        // Mockeamos el servicio
        when(reporteService.getMostUsedServicesByMonth(month, year)).thenReturn(mockReporte);

        // Realizamos la solicitud HTTP (GET)
        mockMvc.perform(get("/reportes/servicio-mas-usado-por-mes")
                        .param("month", String.valueOf(month))
                        .param("year", String.valueOf(year)))
                .andExpect(status().isOk()) // Verificamos que el código de estado es 200
                .andExpect(jsonPath("$[0].serviceName").value("Servicio1"))
                .andExpect(jsonPath("$[0].usageCount").value(200))
                .andExpect(jsonPath("$[1].serviceName").value("Servicio2"))
                .andExpect(jsonPath("$[1].usageCount").value(150));
    }

    @Test
    @DisplayName("Prueba obtener los ingresos por servicio y mes")
    void testGetRevenueByServiceAndMonth() throws Exception {
        // Simulamos el comportamiento del servicio
        List<ServiceReportDTO> mockReporte = new ArrayList<>();
        mockReporte.add(new ServiceReportDTO("Servicio1", 5000L));
        mockReporte.add(new ServiceReportDTO("Servicio2", 3000L));

        int month = 10;
        int year = 2024;

        // Mockeamos el servicio
        when(reporteService.getRevenueByServiceAndMonth(month, year)).thenReturn(mockReporte);

        // Realizamos la solicitud HTTP (GET)
        mockMvc.perform(get("/reportes/ingresos-por-servicio")
                        .param("month", String.valueOf(month))
                        .param("year", String.valueOf(year)))
                .andExpect(status().isOk()) // Verificamos que el código de estado es 200
                .andExpect(jsonPath("$[0].serviceName").value("Servicio1"))
                .andExpect(jsonPath("$[0].usageCount").value(5000))
                .andExpect(jsonPath("$[1].serviceName").value("Servicio2"))
                .andExpect(jsonPath("$[1].usageCount").value(3000));
    }

    @Test
    @DisplayName("Prueba obtener los clientes con más citas")
    void testGetTopClientsByCitas() throws Exception {
        // Simulamos los datos de respuesta
        List<UsuarioReporteDTO> mockResponse = Arrays.asList(
                new UsuarioReporteDTO("John", "Doe", 10L),
                new UsuarioReporteDTO("Jane", "Smith", 5L)
        );

        // Mockeamos el servicio
        when(reporteService.getTopClientsByCitas()).thenReturn(mockResponse);

        // Realizamos la solicitud HTTP (GET)
        mockMvc.perform(get("/reportes/clientes-mas-citas"))
                .andExpect(status().isOk()) // Verificamos que el código de estado es 200
                .andExpect(jsonPath("$[0].nombre").value("John"))
                .andExpect(jsonPath("$[0].apellido").value("Doe"))
                .andExpect(jsonPath("$[0].cantidad").value(10))
                .andExpect(jsonPath("$[1].nombre").value("Jane"))
                .andExpect(jsonPath("$[1].apellido").value("Smith"))
                .andExpect(jsonPath("$[1].cantidad").value(5));
    }

    @Test
    @DisplayName("Prueba obtener los empleados con más citas")
    void testGetTopEmployeesByCitas() throws Exception {
        // Simulamos los datos de respuesta
        List<UsuarioReporteDTO> mockResponse = Arrays.asList(
                new UsuarioReporteDTO("Alice", "Johnson", 15L),
                new UsuarioReporteDTO("Bob", "Brown", 8L)
        );

        // Mockeamos el servicio
        when(reporteService.getTopEmployeesByCitas()).thenReturn(mockResponse);

        // Realizamos la solicitud HTTP (GET)
        mockMvc.perform(get("/reportes/empleados-mas-citas"))
                .andExpect(status().isOk()) // Verificamos que el código de estado es 200
                .andExpect(jsonPath("$[0].nombre").value("Alice"))
                .andExpect(jsonPath("$[0].apellido").value("Johnson"))
                .andExpect(jsonPath("$[0].cantidad").value(15))
                .andExpect(jsonPath("$[1].nombre").value("Bob"))
                .andExpect(jsonPath("$[1].apellido").value("Brown"))
                .andExpect(jsonPath("$[1].cantidad").value(8));
    }

    @Test
    @DisplayName("Prueba contar citas sin empleado")
    void testGetCitasWithNoEmpleado() throws Exception {
        // Simulamos la respuesta del servicio
        Long mockCount = 5L;

        // Mockeamos el servicio
        when(reporteService.getCitasWithNoEmpleado()).thenReturn(mockCount);

        // Realizamos la solicitud HTTP (GET)
        mockMvc.perform(get("/reportes/citas-sin-empleado"))
                .andExpect(status().isOk()) // Verificamos que el código de estado es 200
                .andExpect(jsonPath("$").value(5));
    }

    @Test
    @DisplayName("Prueba obtener los empleados con más ingresos")
    void testGetTopEmployeesByRevenue() throws Exception {
        // Simulamos los datos de respuesta
        List<UsuarioReporteDTO> mockResponse = Arrays.asList(
                new UsuarioReporteDTO("Charlie", "White", 5000L),
                new UsuarioReporteDTO("David", "Black", 3000L)
        );

        // Mockeamos el servicio
        when(reporteService.getTopEmployeesByRevenue()).thenReturn(mockResponse);

        // Realizamos la solicitud HTTP (GET)
        mockMvc.perform(get("/reportes/empleado-mas-dinero"))
                .andExpect(status().isOk()) // Verificamos que el código de estado es 200
                .andExpect(jsonPath("$[0].nombre").value("Charlie"))
                .andExpect(jsonPath("$[0].apellido").value("White"))
                .andExpect(jsonPath("$[0].cantidad").value(5000))
                .andExpect(jsonPath("$[1].nombre").value("David"))
                .andExpect(jsonPath("$[1].apellido").value("Black"))
                .andExpect(jsonPath("$[1].cantidad").value(3000));
    }

    @Test
    @DisplayName("Prueba obtener los clientes con más ingresos")
    void testGetTopClientsByRevenue() throws Exception {
        // Simulamos los datos de respuesta
        List<UsuarioReporteDTO> mockResponse = Arrays.asList(
                new UsuarioReporteDTO("Eve", "Davis", 12000L),
                new UsuarioReporteDTO("Frank", "Miller", 8000L)
        );

        // Mockeamos el servicio
        when(reporteService.getTopClientsByRevenue()).thenReturn(mockResponse);

        // Realizamos la solicitud HTTP (GET)
        mockMvc.perform(get("/reportes/cliente-mas-dinero"))
                .andExpect(status().isOk()) // Verificamos que el código de estado es 200
                .andExpect(jsonPath("$[0].nombre").value("Eve"))
                .andExpect(jsonPath("$[0].apellido").value("Davis"))
                .andExpect(jsonPath("$[0].cantidad").value(12000))
                .andExpect(jsonPath("$[1].nombre").value("Frank"))
                .andExpect(jsonPath("$[1].apellido").value("Miller"))
                .andExpect(jsonPath("$[1].cantidad").value(8000));
    }


}
