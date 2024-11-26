package com.sistema.examenes.sistema_examenes_backend.repository;

import com.sistema.examenes.sistema_examenes_backend.Enums.EstadoCita;
import com.sistema.examenes.sistema_examenes_backend.Enums.TipoRecurso;
import com.sistema.examenes.sistema_examenes_backend.entidades.*;
import com.sistema.examenes.sistema_examenes_backend.repositorios.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class FacturaRepositoryTest {
    @Autowired
    private ReservaRepository reservaRepository;

    @Autowired
    private CitaRepository citaRepository;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private ServicioRepository servicioRepository;

    @Autowired
    private RecursoRepository recursoRepository;

    @Autowired
    private ComprobanteRepository comprobanteRepository;

    @Autowired
    private FacturaRepository facturaRepository;

    private Usuario usuarioGlobal;
    private Usuario empleadoGlobal;
    private Servicio servicioGlobal;
    private Recurso recursoGlobal;
    private Cita citaGlobal;
    private Factura facturaGlobal;

    @BeforeEach
    public void setUp() {
        // Limpiar la base de datos antes de cada prueba

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
    }

    @Test
    public void testCrearFactura() {
        Factura factura = new Factura();
        factura.setFacturaId(1L);
        factura.setMonto(BigDecimal.valueOf(150.00)); // Establecer un monto
        factura.setDetalleServicio("Servicio realizado"); // Establecer detalle
        factura.setFecha(LocalDate.now());
        factura.setCliente(usuarioGlobal); // Asigna el cliente
        factura.setCita(citaGlobal); // Asigna la cita

        //when
        Factura factura1 = facturaRepository.save(factura);

        //then
        assertThat(factura1).isNotNull();
        assertThat(factura1.getFacturaId()).isGreaterThan(0);
    }

    @DisplayName("Test para listar todas las facturas")
    @Test
    public void testListarFacturas() {
        //given
        facturaRepository.save(facturaGlobal);
        // when
        List<Factura> facturas = facturaRepository.findAll();

        // then
        assertThat(facturas).isNotEmpty();
        assertThat(facturas.size()).isEqualTo(1);
    }

    @Test
    public void testObtenerFacturaPorId() {
        //given
        facturaRepository.save(facturaGlobal);


        // when
        Factura facturaDB = facturaRepository.findById(facturaGlobal.getFacturaId()).get();

        // then
        assertThat(facturaDB).isNotNull();
    }

    @Test
    public void testActualizarFactura() {
        facturaRepository.save(facturaGlobal);


        //when
        Factura facturaGuardada = facturaRepository.findById(facturaGlobal.getFacturaId()).get();
        facturaGuardada.setMonto(BigDecimal.valueOf(200.00));
        facturaGuardada.setDetalleServicio("Servicio actualizado");
        Factura facturaActualizada = facturaRepository.save(facturaGuardada);

        // then
        assertThat(facturaActualizada).isNotNull();
        assertThat(facturaActualizada.getMonto()).isEqualTo(BigDecimal.valueOf(200.00));
        assertThat(facturaActualizada.getDetalleServicio()).isEqualTo("Servicio actualizado");
    }

    @Test
    public void testEliminarFactura() {
        facturaRepository.save(facturaGlobal);

        // Eliminar la factura
        facturaRepository.deleteById(facturaGlobal.getFacturaId());


        // Verificar que la factura ha sido eliminada
        Optional<Factura> facturaEliminada = facturaRepository.findById(facturaGlobal.getFacturaId());
        assertThat(facturaEliminada).isEmpty();
    }

    @Test
    public void testFindMostUsedService() {

        Factura factura1 = new Factura();
        factura1.setMonto(BigDecimal.valueOf(150));
        factura1.setDetalleServicio("Servicio realizado");
        factura1.setFecha(LocalDate.now());
        factura1.setCliente(usuarioGlobal);
        factura1.setCita(citaGlobal);
        facturaRepository.save(factura1);

        Factura factura2 = new Factura();
        factura2.setMonto(BigDecimal.valueOf(200));
        factura2.setDetalleServicio("Servicio adicional");
        factura2.setFecha(LocalDate.now());
        factura2.setCliente(usuarioGlobal);
        factura2.setCita(citaGlobal);
        facturaRepository.save(factura2);

        // Ejecutar la consulta
        List<Object[]> result = facturaRepository.findMostUsedService();

        // Verificaciones
        assertThat(result).isNotEmpty();
        assertThat(result.get(0)[0]).isEqualTo("Servicio de Ejemplo");
        assertThat(result.get(0)[1]).isEqualTo(2L);
    }

    @Test
    public void testFindMostUsedServiceByMonth() {
        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();

        // Insertar facturas
        Factura factura1 = new Factura();
        factura1.setMonto(BigDecimal.valueOf(150));
        factura1.setDetalleServicio("Servicio realizado");
        factura1.setFecha(LocalDate.now());
        factura1.setCliente(usuarioGlobal);
        factura1.setCita(citaGlobal);
        facturaRepository.save(factura1);

        Factura factura2 = new Factura();
        factura2.setMonto(BigDecimal.valueOf(200));
        factura2.setDetalleServicio("Servicio adicional");
        factura2.setFecha(LocalDate.now());
        factura2.setCliente(usuarioGlobal);
        factura2.setCita(citaGlobal);
        facturaRepository.save(factura2);

        // Ejecutar la consulta
        List<Object[]> result = facturaRepository.findMostUsedServiceByMonth(month, year);

        // Verificaciones
        assertThat(result).isNotEmpty();
        assertThat(result.get(0)[0]).isEqualTo("Servicio de Ejemplo");
        assertThat(result.get(0)[1]).isEqualTo(2L);
    }


    @Test
    public void testFindRevenueByServiceAndMonth() {
        int month = LocalDate.now().getMonthValue();
        int year = LocalDate.now().getYear();

        // Insertar facturas
        Factura factura1 = new Factura();
        factura1.setMonto(BigDecimal.valueOf(150));
        factura1.setDetalleServicio("Servicio realizado");
        factura1.setFecha(LocalDate.now());
        factura1.setCliente(usuarioGlobal);
        factura1.setCita(citaGlobal);
        facturaRepository.save(factura1);

        Factura factura2 = new Factura();
        factura2.setMonto(BigDecimal.valueOf(200));
        factura2.setDetalleServicio("Servicio adicional");
        factura2.setFecha(LocalDate.now());
        factura2.setCliente(usuarioGlobal);
        factura2.setCita(citaGlobal);
        facturaRepository.save(factura2);


        // Ejecutar la consulta
        List<Object[]> result = facturaRepository.findRevenueByServiceAndMonth(month, year);

        // Verificaciones
        assertThat(result).isNotEmpty();
        assertThat(result.get(0)[0]).isEqualTo("Servicio de Ejemplo");

        // Normalizar y comparar BigDecimal
        BigDecimal actual = (BigDecimal) result.get(0)[1];
        BigDecimal expected = BigDecimal.valueOf(350.0);
        assertThat(actual.stripTrailingZeros()).isEqualTo(expected.stripTrailingZeros());
    }


    @Test
    public void testFindTopClientsByCitas() {
        // Insertar citas para el cliente
        Cita cita1 = new Cita();
        cita1.setCliente(usuarioGlobal);
        citaRepository.save(cita1);


        // Ejecutar la consulta
        List<Object[]> result = facturaRepository.findTopClientsByCitas();

        // Verificaciones
        assertThat(result).isNotEmpty();
        assertThat(result.get(0)[0]).isEqualTo(usuarioGlobal.getNombre());
        assertThat(result.get(0)[1]).isEqualTo(usuarioGlobal.getApellido());
        assertThat(result.get(0)[2]).isEqualTo(2L);
    }

    @Test
    public void testFindTopEmployeesByCitas() {
        // Insertar citas para el empleado
        Cita cita1 = new Cita();
        cita1.setEmpleado(empleadoGlobal);
        citaRepository.save(cita1);

        // Ejecutar la consulta
        List<Object[]> result = facturaRepository.findTopEmployeesByCitas();

        // Verificaciones
        assertThat(result).isNotEmpty();
        assertThat(result.get(0)[0]).isEqualTo(empleadoGlobal.getNombre());
        assertThat(result.get(0)[1]).isEqualTo(empleadoGlobal.getApellido());
        assertThat(result.get(0)[2]).isEqualTo(2L);
    }


    @Test
    public void testCountCitasWithNoEmpleado() {
        // Insertar citas sin empleado
        Cita cita1 = new Cita();
        cita1.setEmpleado(null);
        citaRepository.save(cita1);

        Cita cita2 = new Cita();
        cita2.setEmpleado(null);
        citaRepository.save(cita2);

        // Ejecutar la consulta
        Long count = facturaRepository.countCitasWithNoEmpleado();

        // Verificaciones
        assertThat(count).isEqualTo(2L);
    }

    @Test
    public void testFindTopEmployeesByRevenue() {
        // Insertar facturas para el empleado
        Factura factura1 = new Factura();
        factura1.setMonto(BigDecimal.valueOf(150));
        factura1.setCita(citaGlobal);
        citaGlobal.setEmpleado(empleadoGlobal);
        citaRepository.save(citaGlobal);
        facturaRepository.save(factura1);

        Factura factura2 = new Factura();
        factura2.setMonto(BigDecimal.valueOf(200));
        factura2.setCita(citaGlobal);
        facturaRepository.save(factura2);

        // Ejecutar la consulta
        List<Object[]> result = facturaRepository.findTopEmployeesByRevenue();

        // Verificaciones
        assertThat(result).isNotEmpty();
        assertThat(result.get(0)[0]).isEqualTo(empleadoGlobal.getNombre());
        assertThat(result.get(0)[1]).isEqualTo(empleadoGlobal.getApellido());

        // Comparar los BigDecimal normalizados
        BigDecimal actual = (BigDecimal) result.get(0)[2];
        BigDecimal expected = BigDecimal.valueOf(350);
        assertThat(actual.stripTrailingZeros()).isEqualTo(expected.stripTrailingZeros());
    }








}
