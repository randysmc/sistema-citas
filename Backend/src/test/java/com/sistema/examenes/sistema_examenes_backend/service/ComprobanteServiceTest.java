package com.sistema.examenes.sistema_examenes_backend.service;

import com.sistema.examenes.sistema_examenes_backend.entidades.Comprobante;
import com.sistema.examenes.sistema_examenes_backend.entidades.Cita;
import com.sistema.examenes.sistema_examenes_backend.entidades.Factura;
import com.sistema.examenes.sistema_examenes_backend.entidades.Usuario;
import com.sistema.examenes.sistema_examenes_backend.repositorios.ComprobanteRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.CitaRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.UsuarioRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.implementacion.ComprobanteServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
public class ComprobanteServiceTest {

    @Mock
    private ComprobanteRepository comprobanteRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private CitaRepository citaRepository;

    @InjectMocks
    private ComprobanteServiceImpl comprobanteService;

    private Comprobante comprobanteGlobal;
    private Usuario usuarioGlobal;
    private Cita citaGlobal;

    @BeforeEach
    public void setUp() {
        usuarioGlobal = new Usuario();
        usuarioGlobal.setId(1L);
        usuarioGlobal.setUsername("usuario1");

        citaGlobal = new Cita();
        citaGlobal.setIdCita(1L);

        comprobanteGlobal = new Comprobante();
        comprobanteGlobal.setComprobanteId(1L);
        comprobanteGlobal.setCliente(usuarioGlobal);
        comprobanteGlobal.setCita(citaGlobal);
    }

    @DisplayName("Test para crear un comprobante")
    @Test
    public void testCrearComprobante() {
        // given
        given(usuarioRepository.findById(1L)).willReturn(Optional.of(usuarioGlobal));
        given(citaRepository.findById(1L)).willReturn(Optional.of(citaGlobal));
        given(comprobanteRepository.save(comprobanteGlobal)).willReturn(comprobanteGlobal);

        // when
        Comprobante comprobanteCreado = comprobanteService.crearComprobante(comprobanteGlobal);

        // then
        assertThat(comprobanteCreado).isNotNull();
        assertThat(comprobanteCreado.getComprobanteId()).isEqualTo(1L);
        assertThat(comprobanteCreado.getCliente().getUsername()).isEqualTo("usuario1");
        assertThat(comprobanteCreado.getCita().getIdCita()).isEqualTo(1L);
    }

    @DisplayName("Test para crear una comprobante - Usuario no encontrado")
    @Test
    public void testCrearComprobanteUsuarioNoEncontrado(){
        // Dado que no existe el usuario
        given(usuarioRepository.findById(1L)).willReturn(Optional.empty());

        // Cuando se intenta crear la factura
        // Entonces lanza una excepción
        assertThrows(RuntimeException.class, () -> comprobanteService.crearComprobante(comprobanteGlobal));
    }

    @DisplayName("Test para crear una comprobante - Cita no encontrada")
    @Test
    public void testCrearComprobanteCitaNoEncontrada(){
        // Dado que el usuario está presente, pero la cita no
        given(usuarioRepository.findById(1L)).willReturn(Optional.of(usuarioGlobal));
        given(citaRepository.findById(1L)).willReturn(Optional.empty());

        // Cuando se intenta crear la factura
        // Entonces lanza una excepción
        assertThrows(RuntimeException.class, () -> comprobanteService.crearComprobante(comprobanteGlobal));
    }

    @DisplayName("Test para obtener todos los comprobantes")
    @Test
    public void testObtenerComprobantes() {
        // given
        given(comprobanteRepository.findAll()).willReturn(List.of(comprobanteGlobal));

        // when
        List<Comprobante> comprobantes = comprobanteService.obtenerComprobantes();

        // then
        assertThat(comprobantes).isNotEmpty();
        assertThat(comprobantes.size()).isEqualTo(1);
        assertThat(comprobantes.get(0).getComprobanteId()).isEqualTo(1L);
    }


    @DisplayName("Test para obtener un comprobante por su ID")
    @Test
    public void testObtenerComprobantePorId() {
        // given
        given(comprobanteRepository.findById(1L)).willReturn(Optional.of(comprobanteGlobal));

        // when
        Comprobante comprobante = comprobanteService.obtenerComprobantePorId(1L);

        // then
        assertThat(comprobante).isNotNull();
        assertThat(comprobante.getComprobanteId()).isEqualTo(1L);
    }

    /*@DisplayName("Test para obtener un comprobante por su ID - Comprobante no encontrado")
    @Test
    public void testObtenerComprobantePorId_NotFound() {
        // Dado que no existe una factura con el ID solicitado
        given(comprobanteRepository.findById(1L)).willReturn(Optional.empty());

        // Cuando se llama al método obtenerFacturaPorId
        // Entonces lanza una RuntimeException
        assertThrows(RuntimeException.class, () -> comprobanteService.obtenerComprobantePorId(1L));
    }*/

    @DisplayName("Test para actualizar un comprobante - sin implementación")
    @Test
    public void testActualizarComprobante() {
        // Aunque no hay implementación, simplemente verificamos que no se lanza ninguna excepción.
        assertDoesNotThrow(() -> comprobanteService.actualizarComprobante(comprobanteGlobal));

        // Verificamos que no se interactúa con el repositorio porque el método no tiene implementación.
        verify(comprobanteRepository, times(0)).save(any(Comprobante.class));
    }

    @DisplayName("Test para eliminar un comprobante - sin implementación")
    @Test
    public void testEliminarFactura() {
        // Aunque no hay implementación, simplemente verificamos que no se lanza ninguna excepción.
        assertDoesNotThrow(() -> comprobanteService.eliminarComprobante(1L));

        // Verificamos que no se interactúa con el repositorio porque el método no tiene implementación.
        verify(comprobanteRepository, times(0)).deleteById(anyLong());
    }
}
