package com.sistema.examenes.sistema_examenes_backend.repository;

import com.sistema.examenes.sistema_examenes_backend.entidades.Notificacion;
import com.sistema.examenes.sistema_examenes_backend.repositorios.NotificacionRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class NotificacionRepositoryTest {

    @Autowired
    private NotificacionRepository notificacionRepository;

    private Notificacion notificacionGlobal;

    @BeforeEach
    public void setup() {
        notificacionRepository.deleteAll();
        notificacionGlobal = new Notificacion();
        notificacionGlobal.setMensaje("Notificación inicial");
        notificacionGlobal.setLeido(false);
        notificacionRepository.save(notificacionGlobal);
    }

    @DisplayName("Test para guardar una notificación")
    @Test
    public void testGuardarNotificacion() {
        // given
        Notificacion nuevaNotificacion = new Notificacion();
        nuevaNotificacion.setMensaje("Nueva notificación");
        nuevaNotificacion.setLeido(true);

        // when
        Notificacion notificacionGuardada = notificacionRepository.save(nuevaNotificacion);

        // then
        assertThat(notificacionGuardada).isNotNull();
        assertThat(notificacionGuardada.getId()).isGreaterThan(0);
        assertThat(notificacionGuardada.getMensaje()).isEqualTo("Nueva notificación");
        assertThat(notificacionGuardada.isLeido()).isTrue();
    }

    @DisplayName("Test para listar las notificaciones")
    @Test
    public void testListarNotificaciones() {
        // given
        Notificacion notificacion2 = new Notificacion();
        notificacion2.setMensaje("Otra notificación");
        notificacion2.setLeido(false);

        notificacionRepository.save(notificacionGlobal);
        notificacionRepository.save(notificacion2);

        // when
        List<Notificacion> listaNotificaciones = notificacionRepository.findAll();

        // then
        assertThat(listaNotificaciones).isNotNull();
        assertThat(listaNotificaciones.size()).isEqualTo(2);
    }

    @DisplayName("Test para obtener una notificación por ID")
    @Test
    public void testObtenerNotificacionPorId() {
        // given
        notificacionRepository.save(notificacionGlobal);

        // when
        Optional<Notificacion> notificacionBD = notificacionRepository.findById(notificacionGlobal.getId());

        // then
        assertThat(notificacionBD).isPresent();
        assertThat(notificacionBD.get().getMensaje()).isEqualTo("Notificación inicial");
    }

    @DisplayName("Test para actualizar una notificación")
    @Test
    public void testActualizarNotificacion() {
        // given
        notificacionRepository.save(notificacionGlobal);

        // when
        Notificacion notificacionGuardada = notificacionRepository.findById(notificacionGlobal.getId()).get();
        notificacionGuardada.setMensaje("Notificación actualizada");
        notificacionGuardada.setLeido(true);
        Notificacion notificacionActualizada = notificacionRepository.save(notificacionGuardada);

        // then
        assertThat(notificacionActualizada.getMensaje()).isEqualTo("Notificación actualizada");
        assertThat(notificacionActualizada.isLeido()).isTrue();
    }

    @DisplayName("Test para eliminar una notificación")
    @Test
    public void testEliminarNotificacion() {
        // given
        notificacionRepository.save(notificacionGlobal);

        // when
        notificacionRepository.deleteById(notificacionGlobal.getId());
        Optional<Notificacion> notificacionOptional = notificacionRepository.findById(notificacionGlobal.getId());

        // then
        assertThat(notificacionOptional).isEmpty();
    }
}
