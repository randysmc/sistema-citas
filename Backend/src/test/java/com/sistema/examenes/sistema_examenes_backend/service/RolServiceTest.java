package com.sistema.examenes.sistema_examenes_backend.service;

import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;

import com.sistema.examenes.sistema_examenes_backend.entidades.Rol;
import com.sistema.examenes.sistema_examenes_backend.excepciones.EntityExistenteException;
import com.sistema.examenes.sistema_examenes_backend.excepciones.EntityNotFoundException;
import com.sistema.examenes.sistema_examenes_backend.repositorios.RolRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.implementacion.RolServiceImplementacion;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class RolServiceTest {

    @Mock
    private RolRepository rolRepository;

    @InjectMocks
    private RolServiceImplementacion rolService;

    private Rol rolGlobal;

    @BeforeEach
    public void setup() {
        rolGlobal = new Rol();
        rolGlobal.setRolId(1L);
        rolGlobal.setRolNombre("ADMIN");
    }

    @DisplayName("Test para guardar un rol")
    @Test
    public void testGuardarRol() {
        // given
        given(rolRepository.findByRolNombre(rolGlobal.getRolNombre())).willReturn(Optional.empty());
        given(rolRepository.save(rolGlobal)).willReturn(rolGlobal);

        // when
        Rol rolGuardado = rolService.save(rolGlobal);

        // then
        assertThat(rolGuardado).isNotNull();
        verify(rolRepository, times(1)).save(rolGlobal);
    }

    @DisplayName("Test para lanzar excepción al guardar un rol existente")
    @Test
    public void testGuardarRolExistente() {
        // given
        given(rolRepository.findByRolNombre(rolGlobal.getRolNombre())).willReturn(Optional.of(rolGlobal));

        // when/then
        org.junit.jupiter.api.Assertions.assertThrows(EntityExistenteException.class, () -> {
            rolService.save(rolGlobal);
        });
    }

    @DisplayName("Test para listar roles")
    @Test
    public void testListarRoles() {
        // given
        given(rolRepository.findAll()).willReturn(List.of(rolGlobal));

        // when
        List<Rol> roles = rolService.findAll();

        // then
        assertThat(roles).isNotEmpty();
        assertThat(roles.size()).isEqualTo(1);
        verify(rolRepository, times(1)).findAll();
    }

    @DisplayName("Test para obtener rol por Id")
    @Test
    public void testObtenerRolPorId() {
        // given
        given(rolRepository.findById(1L)).willReturn(Optional.of(rolGlobal));

        // when
        Rol rolObtenido = rolService.findById(1L).get();

        // then
        assertThat(rolObtenido).isNotNull();
        verify(rolRepository, times(1)).findById(1L);
    }

    @DisplayName("Test para lanzar excepción al buscar un rol inexistente por Id")
    @Test
    public void testObtenerRolPorIdInexistente() {
        // given
        given(rolRepository.findById(1L)).willReturn(Optional.empty());

        // when/then
        org.junit.jupiter.api.Assertions.assertThrows(EntityNotFoundException.class, () -> {
            rolService.findById(1L);
        });
    }

    @DisplayName("Test para actualizar un rol")
    @Test
    public void testActualizarRol() {
        // given
        Rol rolActualizado = new Rol();
        rolActualizado.setRolId(1L);
        rolActualizado.setRolNombre("ADMIN_ACTUALIZADO");

        given(rolRepository.findById(rolGlobal.getRolId()))
                .willReturn(Optional.of(rolGlobal));
        given(rolRepository.save(rolActualizado))
                .willReturn(rolActualizado);

        // when
        Rol resultado = rolService.update(rolActualizado);

        // then
        assertThat(resultado).isNotNull();
        assertThat(resultado.getRolNombre()).isEqualTo("ADMIN_ACTUALIZADO");
    }


    @DisplayName("Test para eliminar rol")
    @Test
    public void testEliminarRol() {
        // given
        long rolId = 1L;

        // Simulamos que el rol con el ID 1 existe
        given(rolRepository.findById(rolId)).willReturn(Optional.of(rolGlobal));

        // Simulamos que la eliminación no haga nada
        willDoNothing().given(rolRepository).deleteById(rolId);

        // when
        rolService.delete(rolId);

        // then
        // Verificamos que se haya llamado una vez a deleteById con el rolId
        verify(rolRepository, times(1)).deleteById(rolId);
    }





}
