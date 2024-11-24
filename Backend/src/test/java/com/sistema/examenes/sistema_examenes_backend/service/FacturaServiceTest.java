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

    @InjectMocks
    private FacturaServiceImpl facturaService;


    private Cita citaGlobal;
    private Usuario usuarioGlobal;
    private Factura facturaGlobal;

    @BeforeEach
    public void setUp(){
        // Inicializa el usuario global para los tests
        usuarioGlobal = new Usuario();
        usuarioGlobal.setId(1L);
        usuarioGlobal.setUsername("randysmc");

        citaGlobal = new Cita();
        citaGlobal.setIdCita(1L);

        facturaGlobal= new Factura();
        facturaGlobal.setFacturaId(1L);
        facturaGlobal.setCita(citaGlobal);
        facturaGlobal.setCliente(usuarioGlobal);
        facturaGlobal.setFecha(LocalDate.of(2024,12,25));
        facturaGlobal.setMonto(BigDecimal.valueOf(100));
        facturaGlobal.setDetalleServicio("Un buen servicio");


    }


    @DisplayName("Test para crear una factura")
    @Test
    public void testGuardarFactura(){
        // Dado que los repositorios devuelven el objeto correcto
        given(usuarioRepository.findById(1L)).willReturn(Optional.of(usuarioGlobal));
        given(citaRepository.findById(1L)).willReturn(Optional.of(citaGlobal));
        given(facturaRepository.save(facturaGlobal)).willReturn(facturaGlobal);

        // Cuando se llama al método para crear la factura
        Factura facturaGuardada = facturaService.crearFactura(facturaGlobal);

        // Entonces la factura no debe ser nula
        assertThat(facturaGuardada).isNotNull();
        // Verifica que se haya llamado al repositorio de factura para guardar la factura
        verify(facturaRepository).save(facturaGlobal);
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

    /*@DisplayName("Test para crear una factura desde una cita - éxito")
    @Test
    public void testCrearFacturaDesdeCita() {
        // Dado que el repositorio de citas devuelve la cita correcta
        given(citaRepository.findById(1L)).willReturn(Optional.of(citaGlobal));
        given(facturaRepository.save(any(Factura.class))).willReturn(facturaGlobal);

        // Cuando se llama al método crearFacturaDesdeCita
        Factura facturaCreada = facturaService.crearFacturaDesdeCita(1L);

        // Entonces la factura debe ser igual a la que se crea
        assertThat(facturaCreada).isNotNull();
        assertThat(facturaCreada.getCliente()).isEqualTo(citaGlobal.getCliente());
        assertThat(facturaCreada.getMonto()).isEqualTo(citaGlobal.getServicio().getPrecio());
        assertThat(facturaCreada.getDetalleServicio()).contains("Factura por utilizar el servicio de");
    }*/

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

    @DisplayName("Test para eliminar una factura - sin implementación")
    @Test
    public void testEliminarFactura() {
        // Aunque no hay implementación, simplemente verificamos que no se lanza ninguna excepción.
        assertDoesNotThrow(() -> facturaService.eliminarFactura(1L));

        // Verificamos que no se interactúa con el repositorio porque el método no tiene implementación.
        verify(facturaRepository, times(0)).deleteById(anyLong());
    }
}
