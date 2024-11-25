package com.sistema.examenes.sistema_examenes_backend.service;

import com.sistema.examenes.sistema_examenes_backend.entidades.Notificacion;
import com.sistema.examenes.sistema_examenes_backend.entidades.Usuario;
import com.sistema.examenes.sistema_examenes_backend.entidades.UsuarioNotificacion;
import com.sistema.examenes.sistema_examenes_backend.excepciones.EntityNotFoundException;
import com.sistema.examenes.sistema_examenes_backend.repositorios.NotificacionRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.UsuarioNotificacionRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.UsuarioRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.implementacion.UsuarioNotificacionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class UsuarioNotificacionServiceTest {

    @InjectMocks
    private UsuarioNotificacionServiceImpl usuarioNotificacionService;

    @Mock
    private UsuarioNotificacionRepository usuarioNotificacionRepository;

    @Mock
    private UsuarioRepository usuarioRepository;

    @Mock
    private NotificacionRepository notificacionRepository;

    private Usuario usuarioGlobal;
    private Notificacion notificacionGlobal;
    private UsuarioNotificacion usuarioNotificacionGlobal;

    @BeforeEach
    public void setup() {
        // Crear un usuario
        usuarioGlobal = new Usuario();
        usuarioGlobal.setId(1L);
        usuarioGlobal.setNombre("Juan");

        // Crear una notificación
        notificacionGlobal = new Notificacion();
        notificacionGlobal.setId(1L);
        notificacionGlobal.setMensaje("Nueva tarea asignada");

        // Crear relación usuario-notificación
        usuarioNotificacionGlobal = new UsuarioNotificacion();
        usuarioNotificacionGlobal.setId(1L);
        usuarioNotificacionGlobal.setUsuario(usuarioGlobal);
        usuarioNotificacionGlobal.setNotificacion(notificacionGlobal);
    }

    @DisplayName("Test para asignar una notificación a un usuario")
    @Test
    public void testAsignarNotificacionAUsuario() {
        // given
        given(usuarioRepository.findById(usuarioGlobal.getId())).willReturn(Optional.of(usuarioGlobal));
        given(notificacionRepository.findById(notificacionGlobal.getId())).willReturn(Optional.of(notificacionGlobal));
        given(usuarioNotificacionRepository.save(any(UsuarioNotificacion.class))).willReturn(usuarioNotificacionGlobal);

        // when
        UsuarioNotificacion resultado = usuarioNotificacionService.asignarNotificacionAUsuario(usuarioNotificacionGlobal);

        // then
        assertThat(resultado).isNotNull();
        assertThat(resultado.getUsuario().getId()).isEqualTo(usuarioGlobal.getId());
        assertThat(resultado.getNotificacion().getId()).isEqualTo(notificacionGlobal.getId());
        verify(usuarioRepository).findById(usuarioGlobal.getId());
        verify(notificacionRepository).findById(notificacionGlobal.getId());
        verify(usuarioNotificacionRepository).save(usuarioNotificacionGlobal);
    }

    @DisplayName("Test para manejar excepciones al asignar una notificación a un usuario (Usuario no encontrado)")
    @Test
    public void testAsignarNotificacionUsuarioNoEncontrado() {
        // given
        given(usuarioRepository.findById(usuarioGlobal.getId())).willReturn(Optional.empty());

        // when - then
        assertThatThrownBy(() -> usuarioNotificacionService.asignarNotificacionAUsuario(usuarioNotificacionGlobal))
                .isInstanceOf(EntityNotFoundException.class)
                .hasMessageContaining("Usuario");
        verify(usuarioRepository).findById(usuarioGlobal.getId());
        verify(notificacionRepository, never()).findById(anyLong());
        verify(usuarioNotificacionRepository, never()).save(any(UsuarioNotificacion.class));
    }

    @DisplayName("Test para obtener notificaciones de un usuario")
    @Test
    public void testObtenerNotificacionesPorUsuario() {
        // given
        given(usuarioNotificacionRepository.findByUsuarioId(usuarioGlobal.getId()))
                .willReturn(List.of(usuarioNotificacionGlobal));

        // when
        List<UsuarioNotificacion> resultado = usuarioNotificacionService.obtenerNotificacionesPorUsuario(usuarioGlobal.getId());

        // then
        assertThat(resultado).isNotNull();
        assertThat(resultado.size()).isEqualTo(1);
        assertThat(resultado.get(0).getNotificacion().getMensaje()).isEqualTo("Nueva tarea asignada");
        verify(usuarioNotificacionRepository).findByUsuarioId(usuarioGlobal.getId());
    }

    @DisplayName("Test para obtener usuarios relacionados con una notificación")
    @Test
    public void testObtenerUsuariosPorNotificacion() {
        // given
        given(usuarioNotificacionRepository.findByNotificacionId(notificacionGlobal.getId()))
                .willReturn(List.of(usuarioNotificacionGlobal));

        // when
        List<UsuarioNotificacion> resultado = usuarioNotificacionService.obtenerUsuariosPorNotificacion(notificacionGlobal.getId());

        // then
        assertThat(resultado).isNotNull();
        assertThat(resultado.size()).isEqualTo(1);
        assertThat(resultado.get(0).getUsuario().getNombre()).isEqualTo("Juan");
        verify(usuarioNotificacionRepository).findByNotificacionId(notificacionGlobal.getId());
    }

    @DisplayName("Test para eliminar una relación usuario-notificación")
    @Test
    public void testEliminarUsuarioNotificacion() {
        // when
        usuarioNotificacionService.eliminarUsuarioNotificacion(usuarioNotificacionGlobal.getId());

        // then
        verify(usuarioNotificacionRepository).deleteById(usuarioNotificacionGlobal.getId());
    }
}
