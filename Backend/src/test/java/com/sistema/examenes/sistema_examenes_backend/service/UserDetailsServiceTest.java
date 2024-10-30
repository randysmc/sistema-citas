package com.sistema.examenes.sistema_examenes_backend.service;

import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;
import com.sistema.examenes.sistema_examenes_backend.entidades.Rol;
import com.sistema.examenes.sistema_examenes_backend.entidades.Servicio;
import com.sistema.examenes.sistema_examenes_backend.entidades.Usuario;
import com.sistema.examenes.sistema_examenes_backend.repositorios.NegocioRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.RolRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.ServicioRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.UsuarioRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.implementacion.NegocioServiceImplementacion;
import com.sistema.examenes.sistema_examenes_backend.servicios.implementacion.RolServiceImplementacion;
import com.sistema.examenes.sistema_examenes_backend.servicios.implementacion.ServicioServiceImpl;
import com.sistema.examenes.sistema_examenes_backend.servicios.implementacion.UserDetailsServiceImplementacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
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
public class UserDetailsServiceTest {

    @Mock
    private UsuarioRepository usuarioRepository;

    @InjectMocks
    private UserDetailsServiceImplementacion userDetailsService;

    @Test
    @DisplayName("Prueba para cargar usuario por username - Usuario encontrado")
    public void testLoadUserByUsernameUsuarioEncontrado() {
        // Datos de prueba
        String username = "usuario1";
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        // Simula el comportamiento del repositorio
        given(usuarioRepository.findByUsername(username)).willReturn(usuario);

        // Llama al método
        UserDetails result = userDetailsService.loadUserByUsername(username);

        // Verifica el resultado
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo(username);
    }

    @Test
    @DisplayName("Prueba para cargar usuario por username - Usuario no encontrado")
    public void testLoadUserByUsernameUsuarioNoEncontrado() {
        // Datos de prueba
        String username = "usuarioNoExistente";
        // Simula el comportamiento del repositorio
        given(usuarioRepository.findByUsername(username)).willReturn(null);

        // Verifica que se lance la excepción
        assertThatThrownBy(() -> userDetailsService.loadUserByUsername(username))
                .isInstanceOf(UsernameNotFoundException.class)
                .hasMessageContaining("Usuario no encontrado");
    }

    @Test
    @DisplayName("Prueba para obtener usuario por username - Usuario encontrado")
    public void testGetUsuarioByUsernameUsuarioEncontrado() {
        // Datos de prueba
        String username = "usuario1";
        Usuario usuario = new Usuario();
        usuario.setUsername(username);
        // Simula el comportamiento del repositorio
        given(usuarioRepository.findByUsername(username)).willReturn(usuario);

        // Llama al método
        Usuario result = userDetailsService.getUsuarioByUsername(username);

        // Verifica el resultado
        assertThat(result).isNotNull();
        assertThat(result.getUsername()).isEqualTo(username);
    }

    @Test
    @DisplayName("Prueba para obtener usuario por username - Usuario no encontrado")
    public void testGetUsuarioByUsernameUsuarioNoEncontrado() {
        // Datos de prueba
        String username = "usuarioNoExistente";
        // Simula el comportamiento del repositorio
        given(usuarioRepository.findByUsername(username)).willReturn(null);

        // Llama al método
        Usuario result = userDetailsService.getUsuarioByUsername(username);

        // Verifica que el resultado sea null
        assertThat(result).isNull();
    }


}
