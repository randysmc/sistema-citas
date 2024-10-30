package com.sistema.examenes.sistema_examenes_backend.service;

import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;
import com.sistema.examenes.sistema_examenes_backend.entidades.Servicio;
import com.sistema.examenes.sistema_examenes_backend.repositorios.NegocioRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.ServicioRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.implementacion.NegocioServiceImplementacion;
import com.sistema.examenes.sistema_examenes_backend.servicios.implementacion.ServicioServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.verify;


@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test") // Usa el archivo application-test.properties
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // No reemplazar H2 por otra base de datos
@Transactional
public class NegocioServiceTest {

    @Mock
    private NegocioRepository negocioRepository;

    @InjectMocks
    private NegocioServiceImplementacion negocioService;

    private Negocio negocio;

    private Negocio negocioGlobal;


    @BeforeEach
    public void setUp() {
        // Inicializa el negocio para el test
        negocioGlobal = new Negocio();
        negocioGlobal.setNombre("Negocio de Ejemplo");
        negocioGlobal.setDescripcion("Descripción del negocio de ejemplo");
        negocioGlobal.setDireccion("Calle Ejemplo 123");
        negocioGlobal.setTelefono("123456789");

        // Inicializa otro negocio para guardar
        negocio = new Negocio();
        negocio.setNombre("Negocio de Cortes");
        negocio.setDescripcion("Negocio de cortes de cabello");
        negocio.setDireccion("Avenida Cortes 456");
        negocio.setTelefono("987654321");
    }


    @DisplayName("Test para guardar un nuevo negocio")
    @Test
    void testGuardarNegocio() {
        // given: configura el comportamiento del mock
        given(negocioRepository.existsByNombre(negocio.getNombre())).willReturn(false);
        given(negocioRepository.save(negocio)).willReturn(negocio);

        // when: llama al método de servicio
        Negocio negocioGuardado = negocioService.guardarNegocio(negocio);

        // then: verifica el resultado
        assertThat(negocioGuardado).isNotNull();
        assertThat(negocioGuardado.getNombre()).isEqualTo(negocio.getNombre());
        assertThat(negocioGuardado.getDescripcion()).isEqualTo(negocio.getDescripcion());
        assertThat(negocioGuardado.getDireccion()).isEqualTo(negocio.getDireccion());
        assertThat(negocioGuardado.getTelefono()).isEqualTo(negocio.getTelefono());
        assertThat(negocioGuardado.getEmail()).isEqualTo(negocio.getEmail());
        assertThat(negocioGuardado.getSlogan()).isEqualTo(negocio.getSlogan());
    }

    @DisplayName("Test para encontrar un negocio por ID")
    @Test
    void testFindById() {
        // given
        given(negocioRepository.findById(1L)).willReturn(Optional.of(negocio));

        // when
        Optional<Negocio> foundNegocio = negocioService.findById(1L);

        // then
        assertThat(foundNegocio).isPresent();
        assertThat(foundNegocio.get().getNombre()).isEqualTo(negocio.getNombre());
    }

    @DisplayName("Test para encontrar todos los negocios")
    @Test
    void testFindAll() {
        // given
        List<Negocio> negociosList = new ArrayList<>();
        negociosList.add(negocioGlobal);
        negociosList.add(negocio);
        given(negocioRepository.findAll()).willReturn(negociosList);

        // when
        List<Negocio> foundNegocios = negocioService.obtenerNegocios();

        // then
        assertThat(foundNegocios).hasSize(2);
        assertThat(foundNegocios).contains(negocioGlobal, negocio);
    }

    @DisplayName("Test para actualizar un negocio")
    @Test
    void testActualizarNegocio() {
        // given
        negocioGlobal.setDescripcion("Descripción actualizada");
        given(negocioRepository.findById(negocioGlobal.getNegocioId())).willReturn(Optional.of(negocioGlobal));
        given(negocioRepository.save(negocioGlobal)).willReturn(negocioGlobal);

        // when
        Negocio updatedNegocio = negocioService.actualizarNegocio(negocioGlobal);

        // then
        assertThat(updatedNegocio.getDescripcion()).isEqualTo("Descripción actualizada");
    }

    @DisplayName("Test para eliminar un negocio")
    @Test
    void testEliminarNegocio() {
        // given: configura el comportamiento del mock si es necesario
        given(negocioRepository.existsById(1L)).willReturn(true); // Esto puede ser opcional si no se usa

        // when: llama al método de servicio para eliminar
        negocioService.eliminarNegocio(1L);

        // then: verifica que se haya llamado a deleteById en el repositorio
        verify(negocioRepository).deleteById(1L);
    }


    @DisplayName("Test para actualizar campos del negocio")
    @Test
    void testActualizarCamposNegocio() {
        // given
        negocioGlobal.setNombre("Nuevo Nombre");
        given(negocioRepository.findById(negocioGlobal.getNegocioId())).willReturn(Optional.of(negocioGlobal));
        given(negocioRepository.save(negocioGlobal)).willReturn(negocioGlobal);

        // when
        Negocio updatedNegocio = negocioService.actualizarNegocio(negocioGlobal);

        // then
        assertThat(updatedNegocio.getNombre()).isEqualTo("Nuevo Nombre");
    }

    // Repite para otros campos
    @Test
    void testActualizarDescripcionNegocio() {
        // given
        negocioGlobal.setDescripcion("Descripción actualizada");
        given(negocioRepository.findById(negocioGlobal.getNegocioId())).willReturn(Optional.of(negocioGlobal));
        given(negocioRepository.save(negocioGlobal)).willReturn(negocioGlobal);

        // when
        Negocio updatedNegocio = negocioService.actualizarNegocio(negocioGlobal);

        // then
        assertThat(updatedNegocio.getDescripcion()).isEqualTo("Descripción actualizada");
    }


    @Test
    void testActualizarDireccionNegocio() {
        // given
        negocioGlobal.setDireccion("Nueva Dirección");
        given(negocioRepository.findById(negocioGlobal.getNegocioId())).willReturn(Optional.of(negocioGlobal));
        given(negocioRepository.save(negocioGlobal)).willReturn(negocioGlobal);

        // when
        Negocio updatedNegocio = negocioService.actualizarNegocio(negocioGlobal);

        // then
        assertThat(updatedNegocio.getDireccion()).isEqualTo("Nueva Dirección");
    }


    @Test
    void testActualizarTelefonoNegocio() {
        // given
        negocioGlobal.setTelefono("123456789");
        given(negocioRepository.findById(negocioGlobal.getNegocioId())).willReturn(Optional.of(negocioGlobal));
        given(negocioRepository.save(negocioGlobal)).willReturn(negocioGlobal);

        // when
        Negocio updatedNegocio = negocioService.actualizarNegocio(negocioGlobal);

        // then
        assertThat(updatedNegocio.getTelefono()).isEqualTo("123456789");
    }


    @Test
    void testActualizarEmailNegocio() {
        // given
        negocioGlobal.setEmail("nuevoemail@ejemplo.com");
        given(negocioRepository.findById(negocioGlobal.getNegocioId())).willReturn(Optional.of(negocioGlobal));
        given(negocioRepository.save(negocioGlobal)).willReturn(negocioGlobal);

        // when
        Negocio updatedNegocio = negocioService.actualizarNegocio(negocioGlobal);

        // then
        assertThat(updatedNegocio.getEmail()).isEqualTo("nuevoemail@ejemplo.com");
    }



    @Test
    void testActualizarSloganNegocio() {
        // given
        negocioGlobal.setSlogan("Nuevo Slogan");
        given(negocioRepository.findById(negocioGlobal.getNegocioId())).willReturn(Optional.of(negocioGlobal));
        given(negocioRepository.save(negocioGlobal)).willReturn(negocioGlobal);

        // when
        Negocio updatedNegocio = negocioService.actualizarNegocio(negocioGlobal);

        // then
        assertThat(updatedNegocio.getSlogan()).isEqualTo("Nuevo Slogan");
    }







}
