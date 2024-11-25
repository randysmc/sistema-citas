package com.sistema.examenes.sistema_examenes_backend.controller;

import static org.hamcrest.CoreMatchers.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.*;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.BDDMockito.*;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.sistema.examenes.sistema_examenes_backend.configuraciones.JwtUtils;
import com.sistema.examenes.sistema_examenes_backend.controladores.AuthenticationController;
import com.sistema.examenes.sistema_examenes_backend.controladores.PermisoController;
import com.sistema.examenes.sistema_examenes_backend.entidades.*;
import com.sistema.examenes.sistema_examenes_backend.repositorios.NegocioRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.PermisoRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.UsuarioRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.PermisoService;
import com.sistema.examenes.sistema_examenes_backend.servicios.implementacion.CorreoServiceImpl;
import com.sistema.examenes.sistema_examenes_backend.servicios.implementacion.UserDetailsServiceImplementacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.lang.reflect.Field;
import java.util.*;

@SpringBootTest
@AutoConfigureMockMvc
@ActiveProfiles("test")
public class AuthenticationControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private AuthenticationManager authenticationManager;

    @MockBean
    private UserDetailsServiceImplementacion userDetailsService;

    @MockBean
    private JwtUtils jwtUtils;

    @MockBean
    private CorreoServiceImpl correoService;

    @MockBean
    private UsuarioRepository usuarioRepository;

    @MockBean
    private NegocioRepository negocioRepository;

    @Autowired
    private ObjectMapper objectMapper;

    // Mapa simulado para los códigos 2FA
    private Map<String, String> twoFactorCodes = new HashMap<>();
    private String twoFactorCode = "123456";  // Código 2FA de prueba



    @BeforeEach
    public void setUp() {
        // Crear y guardar un negocio en la base de datos en memoria antes de cada prueba
        Negocio negocio = new Negocio();
        negocio.setNegocioId(1L);
        negocio.setNombre("Mi Negocio");

        // Simular que el negocio existe en la base de datos
        given(negocioRepository.findById(1L)).willReturn(Optional.of(negocio));


    }

    @Test
    void generarToken_CredencialesValidas() throws Exception {
        // Datos de entrada
        JwtRequest jwtRequest = new JwtRequest("usuario1", "password123");

        // Usuario simulado
        Usuario usuario = new Usuario();
        usuario.setUsername("usuario1");
        usuario.setEmail("usuario1@correo.com");
        usuario.setTfa(false);  // Asumiendo que no tiene 2FA

        // Simular la búsqueda del usuario
        given(usuarioRepository.findByUsername("usuario1")).willReturn(usuario);

        // Simular la carga de detalles del usuario
        given(userDetailsService.loadUserByUsername("usuario1")).willReturn(new User("usuario1", "password123", new ArrayList<>()));

        // Simular la generación de token
        String token = "dummyToken";
        given(jwtUtils.generateToken(any(UserDetails.class))).willReturn(token);

        // Realizar la solicitud POST para generar el token
        ResultActions response = mockMvc.perform(post("/generate-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(jwtRequest)));

        // Verificar la respuesta
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.token", is(token)))  // Asegurarse de que el token es retornado
                .andDo(print());
    }


    @Test
    void generarToken_CredencialesInvalidas() throws Exception {
        // Datos de entrada con credenciales incorrectas
        JwtRequest jwtRequest = new JwtRequest("usuario_incorrecto", "passwordErronea");

        // Simulamos que el usuario no existe en el repositorio
        given(usuarioRepository.findByUsername("usuario_incorrecto")).willReturn(null);

        // Realizar la solicitud POST para generar el token
        ResultActions response = mockMvc.perform(post("/generate-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(jwtRequest)));

        // Verificar que se devuelve un error 500 (interno del servidor) con el mensaje adecuado
        response.andExpect(status().isInternalServerError())
                .andExpect(jsonPath("$.message", is("Error inesperado")))
                .andDo(print());
    }


    @Test
    void generarToken_UsuarioConTFA() throws Exception {
        // Datos de entrada
        JwtRequest jwtRequest = new JwtRequest("usuario2", "password123");

        // Usuario simulado con TFA habilitado
        Usuario usuario = new Usuario();
        usuario.setUsername("usuario2");
        usuario.setEmail("usuario2@correo.com");
        usuario.setTfa(true);  // Habilitado 2FA

        // Simular la búsqueda del usuario
        given(usuarioRepository.findByUsername("usuario2")).willReturn(usuario);

        // Simular la carga de detalles del usuario
        given(userDetailsService.loadUserByUsername("usuario2")).willReturn(new User("usuario2", "password123", new ArrayList<>()));

        // Simular la generación del código 2FA
        String twoFactorCode = "123456";
        given(jwtUtils.generateToken(any(UserDetails.class))).willReturn("dummyToken");

        // Mockear el envío del correo para que no se intente enviar un correo real
        doNothing().when(correoService).enviarCorreo(anyString(), anyString(), anyString());

        // Realizar la solicitud POST para generar el token
        ResultActions response = mockMvc.perform(post("/generate-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(jwtRequest)));

        // Verificar la respuesta
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$.message", is("Código de autenticación enviado. Por favor, valida el código.")))
                .andDo(print());
    }

    /*@Test
    void generarToken_UsuarioConTFA_Y_SinCorreo() throws Exception {
        // Datos de entrada
        JwtRequest jwtRequest = new JwtRequest("usuario3", "password123");

        // Usuario simulado con TFA habilitado y sin correo
        Usuario usuario = new Usuario();
        usuario.setUsername("usuario3");
        usuario.setEmail(null);  // Sin correo
        usuario.setTfa(true);  // Habilitado 2FA

        // Simular la búsqueda del usuario
        given(usuarioRepository.findByUsername("usuario3")).willReturn(usuario);

        // Simular la carga de detalles del usuario
        given(userDetailsService.loadUserByUsername("usuario3")).willReturn(new User("usuario3", "password123", new ArrayList<>()));

        // Realizar la solicitud POST para generar el token
        ResultActions response = mockMvc.perform(post("/generate-token")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(jwtRequest)));

        // Verificar que la respuesta sea un error con el mensaje esperado
        response.andExpect(status().isBadRequest())  // Asegúrate de que el código de estado sea un error
                .andExpect(jsonPath("$.message", is("El usuario no tiene un correo registrado para 2FA.")))  // Verificar el mensaje de error
                .andDo(print());
    }*/


/*    @Test
    void validateToken_ConCodigoCorrecto() throws Exception {
        // Preparar datos de entrada para la solicitud
        TwoFactorRequest twoFactorRequest = new TwoFactorRequest();
        twoFactorRequest.setUsername("usuario3");
        twoFactorRequest.setTwoFactorCode(twoFactorCode);

        // Mock del usuario y detalles
        Usuario usuario = new Usuario();
        usuario.setUsername("usuario3");
        usuario.setEmail("usuario3@email.com");

        // Simular que el mapa contiene el código 2FA para el usuario
        twoFactorCodes.put("usuario3", twoFactorCode);

        // Simular carga de detalles del usuario
        UserDetails userDetails = new org.springframework.security.core.userdetails.User(
                "usuario3", "password123", new ArrayList<>());

        // Mock del método que genera el token JWT
        String generatedToken = "generated-jwt-token";
        when(userDetailsService.loadUserByUsername("usuario3")).thenReturn(userDetails);
        when(jwtUtils.generateToken(userDetails)).thenReturn(generatedToken);

        // Realizar la solicitud POST para validar el token
        mockMvc.perform(post("/validate-token")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(twoFactorRequest)))
                .andExpect(status().isOk())  // Verificar que el estado sea 200 OK
                .andExpect(jsonPath("$.token").value(generatedToken))  // Verificar el token generado
                .andDo(print());

        // Verificar que el código 2FA fue eliminado del mapa
        assertFalse(twoFactorCodes.containsKey("usuario3"), "El código 2FA no fue eliminado");
    }
*/

    @Test
    void forgotPassword_EmailValido() throws Exception {
        // Datos de entrada
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        resetPasswordRequest.setEmail("usuario1@correo.com"); // Asignar el email

        // Usuario simulado
        Usuario usuario = new Usuario();
        usuario.setEmail("usuario1@correo.com");

        // Simular la búsqueda del usuario
        given(usuarioRepository.findByEmail("usuario1@correo.com")).willReturn(usuario);

        // Simular el envío de correo
        doNothing().when(correoService).enviarCorreo(
                eq("usuario1@correo.com"),
                eq("Código para cambio de contraseña"),
                anyString()
        );

        // Realizar la solicitud POST para restablecer contraseña
        ResultActions response = mockMvc.perform(post("/forgot-password")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(resetPasswordRequest)));

        // Verificar la respuesta
        response.andExpect(status().isOk())
                .andExpect(jsonPath("$['Codigo generado exitosamente']").exists())
                .andDo(print());
    }






}
