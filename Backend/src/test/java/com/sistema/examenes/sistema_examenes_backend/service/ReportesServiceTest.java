package com.sistema.examenes.sistema_examenes_backend.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;

import com.sistema.examenes.sistema_examenes_backend.DTO.ReporteDTO;
import com.sistema.examenes.sistema_examenes_backend.DTO.ServiceReportDTO;
import com.sistema.examenes.sistema_examenes_backend.DTO.UsuarioReporteDTO;
import com.sistema.examenes.sistema_examenes_backend.Enums.EstadoCita;
import com.sistema.examenes.sistema_examenes_backend.Enums.TipoRecurso;
import com.sistema.examenes.sistema_examenes_backend.entidades.*;
import com.sistema.examenes.sistema_examenes_backend.repositorios.*;
import com.sistema.examenes.sistema_examenes_backend.servicios.implementacion.ReportesServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
@Transactional
public class ReportesServiceTest {

    @Mock
    private CitaRepository citaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private RecursoRepository recursoRepository;

    @Mock
    private ServicioRepository servicioRepository;

    @Mock
    private DiaFestivoRepository diaFestivoRepository;

    @Mock
    private HorarioLaboralRepository horarioLaboralRepository;

    @Mock
    private RolRepository rolRepository;

    @Mock
    private EmpleadoRepository empleadoRepository;

    @Mock
    private ReservaRepository reservaRepository;

    @Mock
    private ComprobanteRepository comprobanteRepository;

    @Mock
    private FacturaRepository facturaRepository;

    @InjectMocks
    private ReportesServiceImpl reportesService;


    private Cita citaGlobal;
    private Usuario usuarioGlobal;
    private Recurso recursoGlobal;
    private Servicio servicioGlobal;
    private Usuario empleadoGlobal;
    private Factura facturaGlobal;

    @BeforeEach
    public void setUp() {


        // Crear el usuario cliente
        usuarioGlobal = new Usuario();
        usuarioGlobal.setNombre("Ringo");
        usuarioGlobal.setApellido("Sum");
        usuarioGlobal.setUsername("nachito");
        usuarioGlobal.setPassword("password");
        usuarioGlobal.setEmail("nacho@gmail.com");
        usuarioGlobal.setTelefono("7754");
        usuarioGlobal.setNit("465654");
        usuarioGlobal.setCui("654");
        usuarioGlobal.setPerfil("foto.png");
        usuarioRepository.save(usuarioGlobal);

        // Crear el usuario empleado
        empleadoGlobal = new Usuario();
        empleadoGlobal.setNombre("Ana");
        empleadoGlobal.setApellido("Gómez");
        empleadoGlobal.setUsername("anagomez");
        empleadoGlobal.setPassword("password");
        empleadoGlobal.setEmail("ana.gomez@example.com");
        empleadoGlobal.setNit("789987987");
        empleadoGlobal.setCui("44565465");
        usuarioRepository.save(empleadoGlobal);

        // Crear el recurso
        recursoGlobal = new Recurso();
        recursoGlobal.setNombre("Recurso de Ejemplo");
        recursoGlobal.setDescripcion("Descripción del recurso de ejemplo");
        recursoGlobal.setDisponible(true);
        recursoGlobal.setTipo(TipoRecurso.INSTALACION); // Asegúrate de que este tipo esté definido
        recursoRepository.save(recursoGlobal);

        // Crear el servicio
        servicioGlobal = new Servicio();
        servicioGlobal.setNombre("Servicio de Ejemplo");
        servicioGlobal.setDescripcion("Descripción del servicio de ejemplo");
        servicioGlobal.setDuracionServicio(60);
        servicioGlobal.setPrecio(BigDecimal.valueOf(100.0));
        servicioGlobal.setDisponible(true);
        servicioRepository.save(servicioGlobal);

        citaGlobal = new Cita();
        citaGlobal.setFecha(LocalDate.of(2024, 10, 29));
        citaGlobal.setHoraInicio(LocalTime.of(10, 0));
        citaGlobal.setHoraFin(LocalTime.of(11, 0));
        citaGlobal.setEstado(EstadoCita.AGENDADA);
        citaGlobal.setCliente(usuarioGlobal);
        citaGlobal.setEmpleado(empleadoGlobal);
        citaGlobal.setRecurso(recursoGlobal);
        citaGlobal.setServicio(servicioGlobal);
        citaRepository.save(citaGlobal);


        facturaGlobal = new Factura();
        facturaGlobal.setMonto(BigDecimal.valueOf(150));
        facturaGlobal.setDetalleServicio("Servicio realizado");
        facturaGlobal.setFecha(LocalDate.now());
        facturaGlobal.setCliente(usuarioGlobal);
        facturaGlobal.setCita(citaGlobal);
        facturaRepository.save(facturaGlobal);
    }


    @Test
    @DisplayName("Prueba contar citas por cliente")
    void contarCitasPorClienteTest() {
        // Simulamos el comportamiento de la consulta para contar las citas
        List<Map<String, Object>> mockDatos = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", "Ringo");
        map.put("apellido", "Sum");
        map.put("numeroCitas", 1L);  // Como solo hay una cita, el número de citas es 1
        mockDatos.add(map);

        // Simulamos el repositorio
        given(citaRepository.contarCitasPorCliente()).willReturn(mockDatos);

        // Ejecutar el método
        List<ReporteDTO> resultado = reportesService.contarCitasPorCliente();

        // Verificación: Debe devolver una lista con un solo elemento
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        ReporteDTO reporte = resultado.get(0);

        // Verificación de los valores
        assertEquals("Ringo", reporte.getDetalles().get("nombre"));
        assertEquals("Sum", reporte.getDetalles().get("apellido"));
        assertEquals(1L, reporte.getDetalles().get("numeroCitas"));

        // Verificar que el método del repositorio se haya llamado una vez
        verify(citaRepository, times(1)).contarCitasPorCliente();
    }


    @Test
    @DisplayName("Prueba obtener citas por estado")
    void obtenerCitasPorEstadoTest() {
        // Simulamos el comportamiento de la consulta para obtener las citas por estado
        List<Map<String, Object>> mockDatos = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("estado", EstadoCita.AGENDADA);
        map.put("numeroCitas", 5L);  // Suponiendo que hay 5 citas en estado AGENDADA
        mockDatos.add(map);

        // Simulamos el repositorio
        given(citaRepository.obtenerCitasPorEstado()).willReturn(mockDatos);

        // Ejecutar el método
        List<ReporteDTO> resultado = reportesService.obtenerCitasPorEstado();

        // Verificación: Debe devolver una lista con un solo elemento
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        ReporteDTO reporte = resultado.get(0);

        // Verificación de los valores
        assertEquals(EstadoCita.AGENDADA, reporte.getDetalles().get("estado"));
        assertEquals(5L, reporte.getDetalles().get("numeroCitas"));

        // Verificar que el método del repositorio se haya llamado una vez
        verify(citaRepository, times(1)).obtenerCitasPorEstado();
    }

    @Test
    @DisplayName("Prueba obtener usuario con más citas agendadas")
    void obtenerUsuarioConMasCitasAgendadasTest() {
        // Simulamos el comportamiento de la consulta para obtener el usuario con más citas agendadas
        List<Map<String, Object>> mockDatos = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", "Ringo");
        map.put("apellido", "Sum");
        map.put("numeroCitas", 10L);  // Suponiendo que el usuario tiene 10 citas agendadas
        mockDatos.add(map);

        // Simulamos el repositorio
        given(citaRepository.obtenerUsuarioConMasCitasAgendadas(EstadoCita.AGENDADA)).willReturn(mockDatos);

        // Ejecutar el método
        List<ReporteDTO> resultado = reportesService.obtenerUsuarioConMasCitasAgendadas();

        // Verificación: Debe devolver una lista con un solo elemento
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        ReporteDTO reporte = resultado.get(0);

        // Verificación de los valores
        assertEquals("Ringo", reporte.getDetalles().get("nombre"));
        assertEquals("Sum", reporte.getDetalles().get("apellido"));
        assertEquals(10L, reporte.getDetalles().get("numeroCitas"));

        // Verificar que el método del repositorio se haya llamado una vez
        verify(citaRepository, times(1)).obtenerUsuarioConMasCitasAgendadas(EstadoCita.AGENDADA);
    }



    @Test
    @DisplayName("Prueba obtener usuario con más citas canceladas")
    void obtenerUsuarioConMasCitasCanceladasTest() {
        // Simulamos el comportamiento de la consulta para obtener el usuario con más citas canceladas
        List<Map<String, Object>> mockDatos = new ArrayList<>();
        Map<String, Object> map = new HashMap<>();
        map.put("nombre", "Ana");
        map.put("apellido", "Gómez");
        map.put("numeroCitas", 3L);  // Suponiendo que el usuario tiene 3 citas canceladas
        mockDatos.add(map);

        // Simulamos el repositorio
        given(citaRepository.obtenerUsuarioConMasCitasCanceladas(EstadoCita.CANCELADA)).willReturn(mockDatos);

        // Ejecutar el método
        List<ReporteDTO> resultado = reportesService.obtenerUsuarioConMasCitasCanceladas();

        // Verificación: Debe devolver una lista con un solo elemento
        assertNotNull(resultado);
        assertEquals(1, resultado.size());
        ReporteDTO reporte = resultado.get(0);

        // Verificación de los valores
        assertEquals("Ana", reporte.getDetalles().get("nombre"));
        assertEquals("Gómez", reporte.getDetalles().get("apellido"));
        assertEquals(3L, reporte.getDetalles().get("numeroCitas"));

        // Verificar que el método del repositorio se haya llamado una vez
        verify(citaRepository, times(1)).obtenerUsuarioConMasCitasCanceladas(EstadoCita.CANCELADA);
    }



    @Test
    @DisplayName("Prueba obtener horarios más solicitados")
    void obtenerHorariosMasSolicitadosTest() {
        // Simulamos el comportamiento de la consulta para obtener los horarios más solicitados
        List<Object[]> mockResultados = new ArrayList<>();
        mockResultados.add(new Object[] { 9, 5L }); // Hora 9:00, con 5 citas
        mockResultados.add(new Object[] { 10, 8L }); // Hora 10:00, con 8 citas

        // Simulamos el repositorio
        given(citaRepository.findMostRequestedHours()).willReturn(mockResultados);

        // Ejecutar el método
        ReporteDTO resultado = reportesService.obtenerHorariosMasSolicitados();

        // Verificación: El mapa de detalles debe tener las horas como claves y el total como valores
        assertNotNull(resultado);
        Map<String, Object> detalles = resultado.getDetalles();
        assertEquals(2, detalles.size());
        assertEquals(5L, detalles.get("9:00"));
        assertEquals(8L, detalles.get("10:00"));

        // Verificar que el método del repositorio se haya llamado una vez
        verify(citaRepository, times(1)).findMostRequestedHours();
    }

    /*@Test
    @DisplayName("Prueba obtener frecuencia de uso por día de la semana")
    void obtenerFrecuenciaUsoPorDiaSemanaTest() {
        // Simulamos el comportamiento de la consulta para obtener la frecuencia de uso por día de la semana
        List<Object[]> mockResultados = new ArrayList<>();
        mockResultados.add(new Object[] { 1, 10L }); // Lunes, 10 citas
        mockResultados.add(new Object[] { 3, 15L }); // Miércoles, 15 citas

        // Simulamos el repositorio
        given(citaRepository.findUsageFrequencyByDayOfWeek()).willReturn(mockResultados);

        // Ejecutar el método
        ReporteDTO resultado = reportesService.obtenerFrecuenciaUsoPorDiaSemana();

        // Verificación: El mapa de detalles debe tener los días de la semana como claves y el total como valores
        assertNotNull(resultado);
        Map<String, Object> detalles = resultado.getDetalles();
        assertEquals(2, detalles.size());
        assertEquals(10L, detalles.get("Lunes"));
        assertEquals(15L, detalles.get("Miércoles"));

        // Verificar que el método del repositorio se haya llamado una vez
        verify(citaRepository, times(1)).findUsageFrequencyByDayOfWeek();
    }*/



    @Test
    @DisplayName("Prueba obtener recursos más y menos utilizados")
    void obtenerRecursosMasYMenosUtilizadosTest() {
        // Simulamos el comportamiento de la consulta para obtener los recursos más y menos utilizados
        List<Object[]> mockResultados = new ArrayList<>();
        mockResultados.add(new Object[] { "Recurso 1", 10L }); // Más utilizado
        mockResultados.add(new Object[] { "Recurso 2", 2L });  // Menos utilizado

        // Simulamos el repositorio
        given(citaRepository.findResourceUsage()).willReturn(mockResultados);

        // Ejecutar el método
        ReporteDTO resultado = reportesService.obtenerRecursosMasYMenosUtilizados();

        // Verificación: El mapa de detalles debe tener las claves "Mas utilizado" y "Menos utilizado"
        assertNotNull(resultado);
        Map<String, Object> detalles = resultado.getDetalles();
        assertEquals(2, detalles.size());
        assertEquals("Recurso 1", detalles.get("Mas utilizado"));
        assertEquals("Recurso 2", detalles.get("Menos utilizado"));

        // Verificar que el método del repositorio se haya llamado una vez
        verify(citaRepository, times(1)).findResourceUsage();
    }

    @Test
    @DisplayName("Prueba obtener tasa de cancelación por servicio")
    void obtenerTasaCancelacionPorServicioTest() {
        // Simulamos el comportamiento de la consulta para obtener la tasa de cancelación por servicio
        List<Object[]> mockResultados = new ArrayList<>();
        mockResultados.add(new Object[] { "Servicio 1", 3L, 10L }); // 3 canceladas de 10
        mockResultados.add(new Object[] { "Servicio 2", 1L, 5L });  // 1 cancelada de 5

        // Simulamos el repositorio
        given(citaRepository.findCancellationRateByService()).willReturn(mockResultados);

        // Ejecutar el método
        ReporteDTO resultado = reportesService.obtenerTasaCancelacionPorServicio();

        // Verificación: El mapa de detalles debe tener los servicios como claves y la tasa de cancelación como valores
        assertNotNull(resultado);
        Map<String, Object> detalles = resultado.getDetalles();
        assertEquals(2, detalles.size());
        assertEquals("30.0%", detalles.get("Servicio 1"));
        assertEquals("20.0%", detalles.get("Servicio 2"));

        // Verificar que el método del repositorio se haya llamado una vez
        verify(citaRepository, times(1)).findCancellationRateByService();
    }

    @Test
    @DisplayName("Prueba obtener lista de recursos utilizados")
    void obtenerListaRecursosUtilizadosTest() {
        // Simulamos el comportamiento de la consulta para obtener la lista de recursos utilizados
        List<Object[]> mockResultados = new ArrayList<>();
        mockResultados.add(new Object[] { "Recurso A", 5L });
        mockResultados.add(new Object[] { "Recurso B", 8L });

        // Simulamos el repositorio
        given(citaRepository.findAllResourceUsage()).willReturn(mockResultados);

        // Ejecutar el método
        ReporteDTO resultado = reportesService.obtenerListaRecursosUtilizados();

        // Verificación: El mapa de detalles debe tener los recursos como claves y el total como valores
        assertNotNull(resultado);
        Map<String, Object> detalles = resultado.getDetalles();
        assertEquals(2, detalles.size());
        assertEquals(5L, detalles.get("Recurso A"));
        assertEquals(8L, detalles.get("Recurso B"));

        // Verificar que el método del repositorio se haya llamado una vez
        verify(citaRepository, times(1)).findAllResourceUsage();
    }

    @Test
    @DisplayName("Prueba obtener lista de servicios utilizados")
    void obtenerListaServiciosUtilizadosTest() {
        // Simulamos el comportamiento de la consulta para obtener la lista de servicios utilizados
        List<Object[]> mockResultados = new ArrayList<>();
        mockResultados.add(new Object[] { "Servicio 1", 10L });
        mockResultados.add(new Object[] { "Servicio 2", 15L });

        // Simulamos el repositorio
        given(citaRepository.findAllServiceUsage()).willReturn(mockResultados);

        // Ejecutar el método
        ReporteDTO resultado = reportesService.obtenerListaServiciosUtilizados();

        // Verificación: El mapa de detalles debe tener los servicios como claves y el total como valores
        assertNotNull(resultado);
        Map<String, Object> detalles = resultado.getDetalles();
        assertEquals(2, detalles.size());
        assertEquals(10L, detalles.get("Servicio 1"));
        assertEquals(15L, detalles.get("Servicio 2"));

        // Verificar que el método del repositorio se haya llamado una vez
        verify(citaRepository, times(1)).findAllServiceUsage();
    }


    @Test
    void testGetMostUsedServices() {
        // Arrange
        List<Object[]> results = new ArrayList<>();
        results.add(new Object[] {"Servicio1", 100L});
        results.add(new Object[] {"Servicio2", 50L});
        when(facturaRepository.findMostUsedService()).thenReturn(results);

        // Act
        List<ServiceReportDTO> serviceReportDTOs = reportesService.getMostUsedServices();

        // Assert
        assertNotNull(serviceReportDTOs);
        assertEquals(2, serviceReportDTOs.size());
        assertEquals("Servicio1", serviceReportDTOs.get(0).getServiceName());
        assertEquals(100L, serviceReportDTOs.get(0).getUsageCount());
        assertEquals("Servicio2", serviceReportDTOs.get(1).getServiceName());
        assertEquals(50L, serviceReportDTOs.get(1).getUsageCount());
    }

    @Test
    void testGetMostUsedServicesByMonth() {
        // Arrange
        List<Object[]> results = new ArrayList<>();
        results.add(new Object[] {"Servicio1", 200L});
        results.add(new Object[] {"Servicio2", 150L});
        int month = 10;
        int year = 2024;
        when(facturaRepository.findMostUsedServiceByMonth(month, year)).thenReturn(results);

        // Act
        List<ServiceReportDTO> serviceReportDTOs = reportesService.getMostUsedServicesByMonth(month, year);

        // Assert
        assertNotNull(serviceReportDTOs);
        assertEquals(2, serviceReportDTOs.size());
        assertEquals("Servicio1", serviceReportDTOs.get(0).getServiceName());
        assertEquals(200L, serviceReportDTOs.get(0).getUsageCount());
        assertEquals("Servicio2", serviceReportDTOs.get(1).getServiceName());
        assertEquals(150L, serviceReportDTOs.get(1).getUsageCount());
    }

    @Test
    void testGetRevenueByServiceAndMonth() {
        // Arrange
        List<Object[]> results = new ArrayList<>();
        results.add(new Object[] {"Servicio1", 5000L});
        results.add(new Object[] {"Servicio2", 3000L});
        int month = 10;
        int year = 2024;
        when(facturaRepository.findRevenueByServiceAndMonth(month, year)).thenReturn(results);

        // Act
        List<ServiceReportDTO> serviceReportDTOs = reportesService.getRevenueByServiceAndMonth(month, year);

        // Assert
        assertNotNull(serviceReportDTOs);
        assertEquals(2, serviceReportDTOs.size());
        assertEquals("Servicio1", serviceReportDTOs.get(0).getServiceName());
        assertEquals(5000L, serviceReportDTOs.get(0).getUsageCount());
        assertEquals("Servicio2", serviceReportDTOs.get(1).getServiceName());
        assertEquals(3000L, serviceReportDTOs.get(1).getUsageCount());
    }

    // Test para obtener los mejores clientes por citas
    @Test
    public void testGetTopClientsByCitas() {
        // Preparación de los datos simulados
        List<Object[]> results = Arrays.asList(
                new Object[]{"John", "Doe", 10L},
                new Object[]{"Jane", "Smith", 5L}
        );

        // Mock del repositorio
        Mockito.when(facturaRepository.findTopClientsByCitas()).thenReturn(results);

        // Ejecución del método
        List<UsuarioReporteDTO> response = reportesService.getTopClientsByCitas();

        // Verificación
        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("John", response.get(0).getNombre());
        assertEquals("Doe", response.get(0).getApellido());
        assertEquals(Long.valueOf(10), response.get(0).getCantidad());
    }

    // Test para obtener los mejores empleados por citas
    @Test
    public void testGetTopEmployeesByCitas() {
        List<Object[]> results = Arrays.asList(
                new Object[]{"Alice", "Johnson", 15L},
                new Object[]{"Bob", "Brown", 8L}
        );

        Mockito.when(facturaRepository.findTopEmployeesByCitas()).thenReturn(results);

        List<UsuarioReporteDTO> response = reportesService.getTopEmployeesByCitas();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Alice", response.get(0).getNombre());
        assertEquals("Johnson", response.get(0).getApellido());
        assertEquals(Long.valueOf(15), response.get(0).getCantidad());
    }

    // Test para contar citas sin empleado
    @Test
    public void testGetCitasWithNoEmpleado() {
        Long count = 5L;
        Mockito.when(facturaRepository.countCitasWithNoEmpleado()).thenReturn(count);

        Long response = reportesService.getCitasWithNoEmpleado();

        assertNotNull(response);
        assertEquals(Long.valueOf(5), response);
    }

    // Test para obtener los mejores empleados por ingresos
    @Test
    public void testGetTopEmployeesByRevenue() {
        List<Object[]> results = Arrays.asList(
                new Object[]{"Charlie", "White", 5000L},
                new Object[]{"David", "Black", 3000L}
        );

        Mockito.when(facturaRepository.findTopEmployeesByRevenue()).thenReturn(results);

        List<UsuarioReporteDTO> response = reportesService.getTopEmployeesByRevenue();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Charlie", response.get(0).getNombre());
        assertEquals("White", response.get(0).getApellido());
        assertEquals(Long.valueOf(5000), response.get(0).getCantidad());
    }

    // Test para obtener los mejores clientes por ingresos
    @Test
    public void testGetTopClientsByRevenue() {
        List<Object[]> results = Arrays.asList(
                new Object[]{"Eve", "Davis", 12000L},
                new Object[]{"Frank", "Miller", 8000L}
        );

        Mockito.when(facturaRepository.findTopClientsByRevenue()).thenReturn(results);

        List<UsuarioReporteDTO> response = reportesService.getTopClientsByRevenue();

        assertNotNull(response);
        assertEquals(2, response.size());
        assertEquals("Eve", response.get(0).getNombre());
        assertEquals("Davis", response.get(0).getApellido());
        assertEquals(Long.valueOf(12000), response.get(0).getCantidad());
    }

}
