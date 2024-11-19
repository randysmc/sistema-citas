package com.sistema.examenes.sistema_examenes_backend.repository;

import com.sistema.examenes.sistema_examenes_backend.Enums.TipoRecurso;
import com.sistema.examenes.sistema_examenes_backend.entidades.Servicio;
import com.sistema.examenes.sistema_examenes_backend.repositorios.ServicioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class ServicioRepositoryTest {

    @Autowired
    private ServicioRepository servicioRepository;

    private Servicio servicioGlobal;

    @BeforeEach
    public void setup() {
        servicioRepository.deleteAll();
        servicioGlobal = new Servicio();
        servicioGlobal.setNombre("Limpieza Dental");
        servicioGlobal.setDescripcion("Elimina la placa y el sarro acumulado.");
        servicioGlobal.setDuracionServicio(30);
        servicioGlobal.setPrecio(new BigDecimal("500.00"));
        servicioGlobal.setDisponible(true);
        servicioGlobal.setTipo(TipoRecurso.PERSONAL);
        servicioRepository.save(servicioGlobal);
    }

    @DisplayName("Test para guardar un servicio")
    @Test
    public void testGuardarServicio() {
        // given
        Servicio servicio = new Servicio();
        servicio.setNombre("Extracción de muela");
        servicio.setDescripcion("Extracción profesional de muelas dañadas.");
        servicio.setDuracionServicio(45);
        servicio.setPrecio(new BigDecimal("1200.00"));
        servicio.setDisponible(true);
        servicio.setTipo(TipoRecurso.PERSONAL);

        // when
        Servicio servicioGuardado = servicioRepository.save(servicio);

        // then
        assertThat(servicioGuardado).isNotNull();
        assertThat(servicioGuardado.getServicioId()).isGreaterThan(0);
        assertThat(servicioGuardado.getNombre()).isEqualTo("Extracción de muela");
    }

    @DisplayName("Test para listar todos los servicios")
    @Test
    public void testListarServicios() {
        // given
        Servicio servicio2 = new Servicio();
        servicio2.setNombre("Blanqueamiento Dental");
        servicio2.setDescripcion("Tratamiento para blanquear los dientes.");
        servicio2.setDuracionServicio(60);
        servicio2.setPrecio(new BigDecimal("2500.00"));
        servicio2.setDisponible(false);
        servicio2.setTipo(TipoRecurso.INSTALACION);
        servicioRepository.save(servicio2);

        // when
        List<Servicio> servicios = servicioRepository.findAll();

        // then
        assertThat(servicios).isNotNull();
        assertThat(servicios.size()).isEqualTo(2); // servicioGlobal + servicio2
    }

    @DisplayName("Test para obtener un servicio por ID")
    @Test
    public void testObtenerServicioPorId() {
        // given
        Long id = servicioGlobal.getServicioId();

        // when
        Optional<Servicio> servicio = servicioRepository.findById(id);

        // then
        assertThat(servicio).isPresent();
        assertThat(servicio.get().getNombre()).isEqualTo("Limpieza Dental");
    }

    @DisplayName("Test para listar servicios disponibles")
    @Test
    public void testListarServiciosDisponibles() {
        // given
        Servicio servicioNoDisponible = new Servicio();
        servicioNoDisponible.setNombre("Ortodoncia");
        servicioNoDisponible.setDescripcion("Colocación de brackets.");
        servicioNoDisponible.setDuracionServicio(120);
        servicioNoDisponible.setPrecio(new BigDecimal("15000.00"));
        servicioNoDisponible.setDisponible(false);
        servicioNoDisponible.setTipo(TipoRecurso.INSTALACION);
        servicioRepository.save(servicioNoDisponible);

        // when
        List<Servicio> serviciosDisponibles = servicioRepository.findByDisponibleTrue();

        // then
        assertThat(serviciosDisponibles).isNotNull();
        assertThat(serviciosDisponibles.size()).isEqualTo(1); // Solo "Limpieza Dental"
        assertThat(serviciosDisponibles.get(0).getNombre()).isEqualTo("Limpieza Dental");
    }

    @DisplayName("Test para listar servicios no disponibles")
    @Test
    public void testListarServiciosNoDisponibles() {
        // given
        Servicio servicioNoDisponible = new Servicio();
        servicioNoDisponible.setNombre("Ortodoncia");
        servicioNoDisponible.setDescripcion("Colocación de brackets.");
        servicioNoDisponible.setDuracionServicio(120);
        servicioNoDisponible.setPrecio(new BigDecimal("15000.00"));
        servicioNoDisponible.setDisponible(false);
        servicioNoDisponible.setTipo(TipoRecurso.INSTALACION);
        servicioRepository.save(servicioNoDisponible);

        // when
        List<Servicio> serviciosNoDisponibles = servicioRepository.findByDisponibleFalse();

        // then
        assertThat(serviciosNoDisponibles).isNotNull();
        assertThat(serviciosNoDisponibles.size()).isEqualTo(1); // Solo "Ortodoncia"
        assertThat(serviciosNoDisponibles.get(0).getNombre()).isEqualTo("Ortodoncia");
    }

    @DisplayName("Test para verificar si un servicio existe por nombre")
    @Test
    public void testExisteServicioPorNombre() {
        // given
        String nombreServicio = "Limpieza Dental";

        // when
        boolean existe = servicioRepository.existsByNombre(nombreServicio);

        // then
        assertThat(existe).isTrue();
    }

    @DisplayName("Test para actualizar un servicio")
    @Test
    public void testActualizarServicio() {
        // given
        Servicio servicioGuardado = servicioRepository.findById(servicioGlobal.getServicioId()).get();
        servicioGuardado.setDescripcion("Limpieza completa para dientes y encías.");
        servicioGuardado.setPrecio(new BigDecimal("600.00"));

        // when
        Servicio servicioActualizado = servicioRepository.save(servicioGuardado);

        // then
        assertThat(servicioActualizado.getDescripcion()).isEqualTo("Limpieza completa para dientes y encías.");
        assertThat(servicioActualizado.getPrecio()).isEqualTo(new BigDecimal("600.00"));
    }

    @DisplayName("Test para eliminar un servicio")
    @Test
    public void testEliminarServicio() {
        // given
        Long id = servicioGlobal.getServicioId();

        // when
        servicioRepository.deleteById(id);
        Optional<Servicio> servicio = servicioRepository.findById(id);

        // then
        assertThat(servicio).isEmpty();
    }


}
