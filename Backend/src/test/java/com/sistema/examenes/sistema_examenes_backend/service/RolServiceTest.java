package com.sistema.examenes.sistema_examenes_backend.service;

import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;
import com.sistema.examenes.sistema_examenes_backend.entidades.Rol;
import com.sistema.examenes.sistema_examenes_backend.entidades.Servicio;
import com.sistema.examenes.sistema_examenes_backend.repositorios.NegocioRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.RolRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.ServicioRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.implementacion.NegocioServiceImplementacion;
import com.sistema.examenes.sistema_examenes_backend.servicios.implementacion.RolServiceImplementacion;
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
public class RolServiceTest {

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private RolServiceImplementacion rolService;

    private Rol rol;

    private Rol rolGlobal;

    @BeforeEach
    public void setUp() {
        // Inicializar el rol global
        rolGlobal = new Rol();
        rolGlobal.setRolId(1L);
        rolGlobal.setRolNombre("ADMIN");
    }

    @Test
    @DisplayName("Prueba para guardar un rol")
    public void testGuardarRol() {
        // Simula el comportamiento del repositorio
        given(rolRepository.save(rolGlobal)).willReturn(rolGlobal);

        // Llama al método
        Rol resultado = rolService.save(rolGlobal);

        // Verifica el resultado
        assertThat(resultado).isNotNull();
        assertThat(resultado.getRolNombre()).isEqualTo(rolGlobal.getRolNombre());
    }

    @Test
    @DisplayName("Prueba para obtener un rol por ID")
    public void testObtenerRolPorId() {
        // Simula el comportamiento del repositorio
        given(rolRepository.findById(rolGlobal.getRolId())).willReturn(Optional.of(rolGlobal));

        // Llama al método
        Optional<Rol> resultado = rolService.findById(rolGlobal.getRolId());

        // Verifica el resultado
        assertThat(resultado).isPresent();
        assertThat(resultado.get().getRolNombre()).isEqualTo(rolGlobal.getRolNombre());
    }





    @Test
    @DisplayName("Prueba para eliminar un rol")
    public void testEliminarRol() {
        // Simula el comportamiento del repositorio
        given(rolRepository.findById(rolGlobal.getRolId())).willReturn(Optional.of(rolGlobal));

        // Llama al método
        rolService.delete(rolGlobal.getRolId());

        // Verifica que el rol se haya eliminado
        given(rolRepository.findById(rolGlobal.getRolId())).willReturn(Optional.empty());
        Optional<Rol> resultado = rolService.findById(rolGlobal.getRolId());

        assertThat(resultado).isNotPresent();
    }


}
