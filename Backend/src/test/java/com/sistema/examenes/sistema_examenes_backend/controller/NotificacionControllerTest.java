package com.sistema.examenes.sistema_examenes_backend.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistema.examenes.sistema_examenes_backend.DTO.ReporteDTO;
import com.sistema.examenes.sistema_examenes_backend.DTO.ServiceReportDTO;
import com.sistema.examenes.sistema_examenes_backend.DTO.UsuarioReporteDTO;
import com.sistema.examenes.sistema_examenes_backend.Enums.EstadoCita;
import com.sistema.examenes.sistema_examenes_backend.entidades.*;
import com.sistema.examenes.sistema_examenes_backend.servicios.CitaService;
import com.sistema.examenes.sistema_examenes_backend.servicios.NotificacionService;
import com.sistema.examenes.sistema_examenes_backend.servicios.ReporteService;
import com.sistema.examenes.sistema_examenes_backend.servicios.UsuarioNotificacionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.*;

import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.is;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class NotificacionControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private NotificacionService notificacionService;

    @MockBean
    private UsuarioNotificacionService usuarioNotificacionService;

    @Autowired
    private ObjectMapper objectMapper;


    @Test
    public void crearNotificacion() throws Exception {
        // Crea el objeto Notificacion y asigna valores a sus campos
        Notificacion notificacion = new Notificacion();
        notificacion.setId(1L); // Asigna un ID
        notificacion.setMensaje("Hola"); // Asigna un mensaje
        notificacion.setLeido(false); // Establece el estado de 'leído'

        // Configura el comportamiento del mock para el servicio
        when(notificacionService.crearNotificacion(any(Notificacion.class))).thenReturn(notificacion);

        // Realiza la petición POST al endpoint /notificaciones/
        mockMvc.perform(post("/notificaciones/")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(notificacion))) // Asegúrate de enviar la notificación como JSON
                .andExpect(status().isCreated()) // Espera que la respuesta sea '201 Created'
                .andExpect(jsonPath("$.mensaje", is("Hola"))) // Verifica que el mensaje sea el correcto
                .andExpect(jsonPath("$.leido", is(false))); // Verifica que el estado de 'leído' sea el correcto
    }

    @Test
    public void obtenerNotificaciones() throws Exception {
        // Crea algunas notificaciones de ejemplo
        Notificacion notificacion1 = new Notificacion();
        notificacion1.setId(1L);
        notificacion1.setMensaje("Notificación 1");
        notificacion1.setLeido(false);

        Notificacion notificacion2 = new Notificacion();
        notificacion2.setId(2L);
        notificacion2.setMensaje("Notificación 2");
        notificacion2.setLeido(true);

        List<Notificacion> notificaciones = Arrays.asList(notificacion1, notificacion2);

        // Configura el comportamiento del mock para el servicio
        when(notificacionService.obtenerTodas()).thenReturn(notificaciones);

        // Realiza la petición GET al endpoint /notificaciones/
        mockMvc.perform(get("/notificaciones/"))
                .andExpect(status().isOk()) // Espera que la respuesta sea '200 OK'
                .andExpect(jsonPath("$", hasSize(2))) // Verifica que haya dos notificaciones
                .andExpect(jsonPath("$[0].mensaje", is("Notificación 1"))) // Verifica el mensaje de la primera notificación
                .andExpect(jsonPath("$[1].mensaje", is("Notificación 2"))); // Verifica el mensaje de la segunda notificación
    }

    @Test
    public void obtenerNotificacionPorIdExistente() throws Exception {
        // Crea una notificación de ejemplo
        Notificacion notificacion = new Notificacion();
        notificacion.setId(1L);
        notificacion.setMensaje("Notificación encontrada");
        notificacion.setLeido(false);

        // Configura el comportamiento del mock para el servicio
        when(notificacionService.findById(1L)).thenReturn(Optional.of(notificacion));

        // Realiza la petición GET al endpoint /notificaciones/{id}
        mockMvc.perform(get("/notificaciones/1"))
                .andExpect(status().isOk()) // Espera que la respuesta sea '200 OK'
                .andExpect(jsonPath("$.mensaje", is("Notificación encontrada"))) // Verifica que el mensaje sea el correcto
                .andExpect(jsonPath("$.leido", is(false))); // Verifica que el estado de 'leído' sea el correcto
    }

    @Test
    public void obtenerNotificacionPorIdNoExistente() throws Exception {
        // Configura el comportamiento del mock para el servicio, simulando que la notificación no existe
        when(notificacionService.findById(1L)).thenReturn(Optional.empty());

        // Realiza la petición GET al endpoint /notificaciones/{id}
        mockMvc.perform(get("/notificaciones/1"))
                .andExpect(status().isNotFound()); // Espera que la respuesta sea '404 Not Found'
    }

    @Test
    public void asignarNotificacionAUsuario() throws Exception {
        // Crea los objetos de Usuario y Notificación de ejemplo
        Usuario usuario = new Usuario();
        usuario.setId(1L); // Asigna un ID
        usuario.setNombre("Juan");

        Notificacion notificacion = new Notificacion();
        notificacion.setId(1L); // Asigna un ID
        notificacion.setMensaje("Nueva notificación");

        // Crea el objeto UsuarioNotificacion que los enlaza
        UsuarioNotificacion usuarioNotificacion = new UsuarioNotificacion();
        usuarioNotificacion.setUsuario(usuario);
        usuarioNotificacion.setNotificacion(notificacion);

        // Configura el comportamiento del mock para el servicio
        when(usuarioNotificacionService.asignarNotificacionAUsuario(any(UsuarioNotificacion.class)))
                .thenReturn(usuarioNotificacion); // Ahora el servicio devuelve el objeto

        // Realiza la petición POST al endpoint /enviar
        mockMvc.perform(post("/notificaciones/enviar")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(usuarioNotificacion))) // Convierte el objeto a JSON
                .andExpect(status().isCreated()) // Espera que la respuesta sea '201 Created'
                .andExpect(content().string("Notificación asignada correctamente.")); // Verifica el mensaje de la respuesta
    }


    @Test
    public void obtenerNotificacionesPorUsuarioConNotificaciones() throws Exception {
        // Crea un usuario de ejemplo
        Usuario usuario = new Usuario();
        usuario.setId(1L);
        usuario.setNombre("Juan");

        // Crea algunas notificaciones de ejemplo
        Notificacion notificacion1 = new Notificacion();
        notificacion1.setId(1L);
        notificacion1.setMensaje("Notificación 1");

        Notificacion notificacion2 = new Notificacion();
        notificacion2.setId(2L);
        notificacion2.setMensaje("Notificación 2");

        // Crea los objetos UsuarioNotificacion que los enlazan
        UsuarioNotificacion usuarioNotificacion1 = new UsuarioNotificacion();
        usuarioNotificacion1.setUsuario(usuario);
        usuarioNotificacion1.setNotificacion(notificacion1);

        UsuarioNotificacion usuarioNotificacion2 = new UsuarioNotificacion();
        usuarioNotificacion2.setUsuario(usuario);
        usuarioNotificacion2.setNotificacion(notificacion2);

        List<UsuarioNotificacion> usuarioNotificaciones = Arrays.asList(usuarioNotificacion1, usuarioNotificacion2);

        // Configura el comportamiento del mock para el servicio
        when(usuarioNotificacionService.obtenerNotificacionesPorUsuario(1L)).thenReturn(usuarioNotificaciones);

        // Realiza la petición GET al endpoint /usuario/{usuarioId}
        mockMvc.perform(get("/notificaciones/usuario/1"))
                .andExpect(status().isOk()) // Espera que la respuesta sea '200 OK'
                .andExpect(jsonPath("$", hasSize(2))) // Verifica que haya dos notificaciones
                .andExpect(jsonPath("$[0].notificacion.mensaje", is("Notificación 1"))) // Verifica el mensaje de la primera notificación
                .andExpect(jsonPath("$[1].notificacion.mensaje", is("Notificación 2"))); // Verifica el mensaje de la segunda notificación
    }

    @Test
    public void obtenerNotificacionesPorUsuarioSinNotificaciones() throws Exception {
        // Configura el comportamiento del mock para el servicio, simulando que el usuario no tiene notificaciones
        when(usuarioNotificacionService.obtenerNotificacionesPorUsuario(1L)).thenReturn(Collections.emptyList());

        // Realiza la petición GET al endpoint /usuario/{usuarioId}
        mockMvc.perform(get("/notificaciones/usuario/1"))
                .andExpect(status().isNoContent()); // Espera que la respuesta sea '204 No Content'
    }



}
