package com.sistema.examenes.sistema_examenes_backend.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.hibernate.validator.internal.util.Contracts.assertNotNull;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.assertj.core.api.Assertions.assertThat;


import com.sistema.examenes.sistema_examenes_backend.Enums.EstadoCita;
import com.sistema.examenes.sistema_examenes_backend.Enums.TipoRecurso;
import com.sistema.examenes.sistema_examenes_backend.entidades.*;

import com.sistema.examenes.sistema_examenes_backend.repositorios.*;

import com.sistema.examenes.sistema_examenes_backend.servicios.implementacion.FacturaServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import org.mockito.InjectMocks;
import org.mockito.Mock;

import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Optional;


@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class FacturaServiceTest {

    @Mock
    private FacturaRepository facturaRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CitaRepository citaRepository;

    @Mock
    private RecursoRepository recursoRepository;

    @Mock
    private ServicioRepository servicioRepository;

    @InjectMocks
    private FacturaServiceImpl facturaService;


    private Cita citaGlobal;
    private Usuario usuarioGlobal;
    private Factura facturaGlobal;
    private Usuario empleadoGlobal;
    private Recurso recursoGlobal;
    private Servicio servicioGlobal;

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


    @DisplayName("Test para crear una factura")
    @Test
    public void testGuardarFactura(){
        //given
        given(usuarioRepository.findById(usuarioGlobal.getId())).willReturn(Optional.of(usuarioGlobal));
        given(citaRepository.findById(citaGlobal.getIdCita())).willReturn(Optional.of(citaGlobal));
        given(facturaRepository.save(facturaGlobal)).willReturn(facturaGlobal);

        //when
        Factura facturaGuardada = facturaService.crearFactura(facturaGlobal);

        // then
        assertThat(facturaGuardada).isNotNull();
        verify(facturaRepository, times(1)).save(facturaGlobal);
    }

    @DisplayName("Test para crear una factura - Usuario no encontrado")
    @Test
    public void testCrearFacturaUsuarioNoEncontrado(){
        // Dado que no existe el usuario
        given(usuarioRepository.findById(1L)).willReturn(Optional.empty());

        // Cuando se intenta crear la factura
        // Entonces lanza una excepción
        assertThrows(RuntimeException.class, () -> facturaService.crearFactura(facturaGlobal));
    }

    @DisplayName("Test para crear una factura - Cita no encontrada")
    @Test
    public void testCrearFacturaCitaNoEncontrada(){
        // Dado que el usuario está presente, pero la cita no
        given(usuarioRepository.findById(1L)).willReturn(Optional.of(usuarioGlobal));
        given(citaRepository.findById(1L)).willReturn(Optional.empty());

        // Cuando se intenta crear la factura
        // Entonces lanza una excepción
        assertThrows(RuntimeException.class, () -> facturaService.crearFactura(facturaGlobal));
    }

    @DisplayName("Test para obtener todas las facturas")
    @Test
    public void testObtenerFacturas() {
        // Dado que el repositorio devuelve una lista de facturas
        given(facturaRepository.findAll()).willReturn(Arrays.asList(facturaGlobal));

        // Cuando se llama al método obtenerFacturas
        var facturas = facturaService.obtenerFacturass();

        // Entonces la lista no debe ser vacía y debe contener la factura
        assertThat(facturas).isNotEmpty();
        assertThat(facturas.get(0)).isEqualTo(facturaGlobal);
    }

    @DisplayName("Test para obtener una factura por ID - éxito")
    @Test
    public void testObtenerFacturaPorId() {
        // Dado que el repositorio devuelve la factura correspondiente
        given(facturaRepository.findById(1L)).willReturn(Optional.of(facturaGlobal));

        // Cuando se llama al método obtenerFacturaPorId
        Factura factura = facturaService.obtenerFacturaPorId(1L);

        // Entonces la factura debe ser igual a la que está en el repositorio
        assertThat(factura).isEqualTo(facturaGlobal);
    }

    @DisplayName("Test para obtener una factura por ID - factura no encontrada")
    @Test
    public void testObtenerFacturaPorIdFacturaNoEncontrada() {
        // Dado que no existe una factura con el ID solicitado
        given(facturaRepository.findById(1L)).willReturn(Optional.empty());

        // Cuando se llama al método obtenerFacturaPorId
        // Entonces lanza una RuntimeException
        assertThrows(RuntimeException.class, () -> facturaService.obtenerFacturaPorId(1L));
    }

    @DisplayName("Test para obtener las facturas por usuario")
    @Test
    public void testObtenerFacturasPorUsuario() {
        // Dado que el repositorio devuelve una lista de facturas
        given(facturaRepository.findByCliente_Id(1L)).willReturn(Arrays.asList(facturaGlobal));

        // Cuando se llama al método obtenerFacturasPorUsuario
        var facturas = facturaService.obtenerFacturasPorUsuario(1L);

        // Entonces la lista no debe ser vacía y debe contener la factura
        assertThat(facturas).isNotEmpty();
        assertThat(facturas.get(0)).isEqualTo(facturaGlobal);
    }


    @DisplayName("Test para crear una factura desde una cita - cita no encontrada")
    @Test
    public void testCrearFacturaDesdeCitaCitaNoEncontrada() {
        // Dado que no existe la cita con el ID solicitado
        given(citaRepository.findById(1L)).willReturn(Optional.empty());

        // Cuando se llama al método crearFacturaDesdeCita
        // Entonces lanza una RuntimeException
        assertThrows(RuntimeException.class, () -> facturaService.crearFacturaDesdeCita(1L));
    }

    @DisplayName("Test para obtener una factura por cita")
    @Test
    public void testObtenerFacturaPorCita() {
        // Dado que el repositorio devuelve una factura con la cita correspondiente
        given(facturaRepository.findByCita_IdCita(1L)).willReturn(facturaGlobal);

        // Cuando se llama al método obtenerFacturaPorCita
        Factura factura = facturaService.obtenerFacturaPorCita(1L);

        // Entonces la factura debe ser igual a la que está en el repositorio
        assertThat(factura).isEqualTo(facturaGlobal);
    }


    @DisplayName("Test para actualizar una factura - sin implementación")
    @Test
    public void testActualizarFactura() {
        // Aunque no hay implementación, simplemente verificamos que no se lanza ninguna excepción.
        assertDoesNotThrow(() -> facturaService.actualizarFactura(facturaGlobal));

        // Verificamos que no se interactúa con el repositorio porque el método no tiene implementación.
        verify(facturaRepository, times(0)).save(any(Factura.class));
    }




}
