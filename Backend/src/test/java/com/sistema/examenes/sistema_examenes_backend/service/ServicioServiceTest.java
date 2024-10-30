package com.sistema.examenes.sistema_examenes_backend.service;

import com.sistema.examenes.sistema_examenes_backend.entidades.Servicio;
import com.sistema.examenes.sistema_examenes_backend.repositorios.ServicioRepository;
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

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test") // Usa el archivo application-test.properties
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // No reemplazar H2 por otra base de datos
@Transactional
public class ServicioServiceTest {
    @Mock
    private ServicioRepository servicioRepository;

    @InjectMocks
    private ServicioServiceImpl servicioService;

    private Servicio servicio;

    private Servicio servicioGlobal;



    @BeforeEach
    public void setUp() {
        // Inicializa el servicio para el test
        servicioGlobal = new Servicio();
        servicioGlobal.setNombre("Servicio de Ejemplo");
        servicioGlobal.setDescripcion("Descripción del servicio de ejemplo");
        servicioGlobal.setDuracionServicio(60);
        servicioGlobal.setPrecio(BigDecimal.valueOf(100.0));
        servicioGlobal.setDisponible(true);

        // Inicializa otro servicio para guardar
        servicio = new Servicio();
        servicio.setNombre("Corte de cabello");
        servicio.setDescripcion("Corte de cabello para hombres y mujeres");
        servicio.setDuracionServicio(30);
        servicio.setPrecio(BigDecimal.valueOf(150.0));
        servicio.setDisponible(true);
    }

    @DisplayName("Test para guardar un nuevo servicio")
    @Test
    void testGuardarServicio() {
        // given: configura el comportamiento del mock
        given(servicioRepository.save(servicio)).willReturn(servicio);

        // when: llama al método de servicio
        Servicio servicioGuardado = servicioService.save(servicio);

        // then: verifica el resultado
        assertThat(servicioGuardado).isNotNull();
        assertThat(servicioGuardado.getNombre()).isEqualTo(servicio.getNombre());
        assertThat(servicioGuardado.getDescripcion()).isEqualTo(servicio.getDescripcion());
        assertThat(servicioGuardado.getDuracionServicio()).isEqualTo(servicio.getDuracionServicio());
        assertThat(servicioGuardado.getPrecio()).isEqualTo(servicio.getPrecio());
        assertThat(servicioGuardado.getDisponible()).isEqualTo(servicio.getDisponible());

        // Verifica que se haya llamado al método del repositorio
        assertThat(servicioRepository.save(servicio)).isEqualTo(servicio);
    }

    @DisplayName("Test para encontrar un servicio por ID")
    @Test
    void testFindById() {
        // given
        given(servicioRepository.findById(1L)).willReturn(Optional.of(servicioGlobal));

        // when
        Optional<Servicio> foundServicio = servicioService.findById(1L);

        // then
        assertThat(foundServicio).isPresent();
        assertThat(foundServicio.get().getNombre()).isEqualTo(servicioGlobal.getNombre());
    }

    @DisplayName("Test para encontrar todos los servicios")
    @Test
    void testFindAll() {
        // given
        List<Servicio> serviciosList = new ArrayList<>();
        serviciosList.add(servicioGlobal);
        serviciosList.add(servicio);
        given(servicioRepository.findAll()).willReturn(serviciosList);

        // when
        List<Servicio> foundServicios = servicioService.findAll();

        // then
        assertThat(foundServicios).hasSize(2);
        assertThat(foundServicios).contains(servicioGlobal, servicio);
    }

    @DisplayName("Test para actualizar un servicio")
    @Test
    void testUpdate() {
        // given
        servicioGlobal.setDescripcion("Descripción actualizada");
        given(servicioRepository.findById(servicioGlobal.getServicioId())).willReturn(Optional.of(servicioGlobal));
        given(servicioRepository.save(servicioGlobal)).willReturn(servicioGlobal);

        // when
        Servicio updatedServicio = servicioService.update(servicioGlobal);

        // then
        assertThat(updatedServicio.getDescripcion()).isEqualTo("Descripción actualizada");
    }

    @DisplayName("Test para eliminar un servicio")
    @Test
    void testDelete() {
        // when
        servicioService.delete(1L);

        // then
        assertThat(servicioRepository).hasNoNullFieldsOrProperties();
    }

    @DisplayName("Test para encontrar servicios disponibles")
    @Test
    void testFindByDisponibleTrue() {
        // given
        List<Servicio> serviciosDisponibles = new ArrayList<>();
        serviciosDisponibles.add(servicioGlobal);
        given(servicioRepository.findByDisponibleTrue()).willReturn(serviciosDisponibles);

        // when
        List<Servicio> foundServicios = servicioService.findByDisponibleTrue();

        // then
        assertThat(foundServicios).contains(servicioGlobal);
    }

    @DisplayName("Test para encontrar servicios no disponibles")
    @Test
    void testFindByDisponibleFalse() {
        // given
        List<Servicio> serviciosNoDisponibles = new ArrayList<>();
        given(servicioRepository.findByDisponibleFalse()).willReturn(serviciosNoDisponibles);

        // when
        List<Servicio> foundServicios = servicioService.findByDisponibleFalse();

        // then
        assertThat(foundServicios).isEmpty();
    }

    @DisplayName("Test para cambiar la disponibilidad de un servicio")
    @Test
    void testCambiarDisponibilidad() {
        // given
        given(servicioRepository.findById(1L)).willReturn(Optional.of(servicioGlobal));
        given(servicioRepository.save(servicioGlobal)).willReturn(servicioGlobal);

        // when
        servicioService.cambiarDisponibilidad(1L, false);

        // then
        assertThat(servicioGlobal.getDisponible()).isFalse();
    }

    @DisplayName("Test para actualizar un servicio")
    @Test
    void testActualizarServicio_Exitoso() {
        // given: configura el comportamiento del mock
        given(servicioRepository.findById(servicioGlobal.getServicioId())).willReturn(Optional.of(servicioGlobal));
        given(servicioRepository.save(servicioGlobal)).willReturn(servicioGlobal);

        // when: llama al método de servicio
        servicioGlobal.setNombre("Servicio Actualizado");
        Servicio servicioActualizado = servicioService.update(servicioGlobal);

        // then: verifica el resultado
        assertThat(servicioActualizado.getNombre()).isEqualTo("Servicio Actualizado");
    }

    @DisplayName("Test para actualizar servicio que no existe")
    @Test
    void testActualizarServicio_NoExistente() {
        // given: configura el comportamiento del mock
        given(servicioRepository.findById(servicio.getServicioId())).willReturn(Optional.empty());

        // when & then: verifica que se lance una excepción
        assertThatThrownBy(() -> servicioService.update(servicio))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Servicio no encontrado con ID: " + servicio.getServicioId());
    }


    @DisplayName("Test para actualizar la descripción de un servicio")
    @Test
    void testActualizarServicio_Descripcion() {
        // given: configura el comportamiento del mock
        given(servicioRepository.findById(servicioGlobal.getServicioId())).willReturn(Optional.of(servicioGlobal));
        given(servicioRepository.save(servicioGlobal)).willReturn(servicioGlobal);

        // when: llama al método de servicio
        servicioGlobal.setDescripcion("Nueva descripción");
        Servicio servicioActualizado = servicioService.update(servicioGlobal);

        // then: verifica el resultado
        assertThat(servicioActualizado.getDescripcion()).isEqualTo("Nueva descripción");
    }

    @DisplayName("Test para actualizar la duración de un servicio")
    @Test
    void testActualizarServicio_Duracion() {
        // given: configura el comportamiento del mock
        given(servicioRepository.findById(servicioGlobal.getServicioId())).willReturn(Optional.of(servicioGlobal));
        given(servicioRepository.save(servicioGlobal)).willReturn(servicioGlobal);

        // when: llama al método de servicio
        servicioGlobal.setDuracionServicio(45);
        Servicio servicioActualizado = servicioService.update(servicioGlobal);

        // then: verifica el resultado
        assertThat(servicioActualizado.getDuracionServicio()).isEqualTo(45);
    }

    @DisplayName("Test para actualizar el precio de un servicio")
    @Test
    void testActualizarServicio_Precio() {
        // given: configura el comportamiento del mock
        given(servicioRepository.findById(servicioGlobal.getServicioId())).willReturn(Optional.of(servicioGlobal));
        given(servicioRepository.save(servicioGlobal)).willReturn(servicioGlobal);

        // when: llama al método de servicio
        servicioGlobal.setPrecio(BigDecimal.valueOf(200.0));
        Servicio servicioActualizado = servicioService.update(servicioGlobal);

        // then: verifica el resultado
        assertThat(servicioActualizado.getPrecio()).isEqualTo(BigDecimal.valueOf(200.0));
    }

    @DisplayName("Test para intentar actualizar un servicio que no existe")
    @Test
    void testActualizarServicio_NoExistente_Ex() {
        // given: configura el comportamiento del mock
        given(servicioRepository.findById(servicio.getServicioId())).willReturn(Optional.empty());

        // when & then: verifica que se lance una excepción
        assertThatThrownBy(() -> servicioService.update(servicio))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Servicio no encontrado con ID: " + servicio.getServicioId());
    }




    @DisplayName("Test para intentar actualizar con un servicio que no tiene ID")
    @Test
    void testActualizarServicio_SinId() {
        // given: crea un servicio sin ID
        Servicio servicioSinId = new Servicio();
        servicioSinId.setNombre("Nombre Sin ID");

        // when & then: verifica que se lance una excepción
        assertThatThrownBy(() -> servicioService.update(servicioSinId))
                .isInstanceOf(RuntimeException.class)
                .hasMessageContaining("Servicio no encontrado con ID: null");
    }

}
