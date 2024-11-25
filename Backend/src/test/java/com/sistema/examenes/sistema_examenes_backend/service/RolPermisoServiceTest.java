package com.sistema.examenes.sistema_examenes_backend.service;

import com.sistema.examenes.sistema_examenes_backend.entidades.Permiso;
import com.sistema.examenes.sistema_examenes_backend.entidades.Rol;
import com.sistema.examenes.sistema_examenes_backend.entidades.RolPermiso;
import com.sistema.examenes.sistema_examenes_backend.excepciones.EntityNotFoundException;
import com.sistema.examenes.sistema_examenes_backend.repositorios.PermisoRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.RolPermisoRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.RolRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.implementacion.RolPermisoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.context.ActiveProfiles;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.BDDMockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class RolPermisoServiceTest {

    @Mock
    private RolRepository rolRepository;

    @Mock
    private PermisoRepository permisoRepository;

    @Mock
    private RolPermisoRepository rolPermisoRepository;

    @InjectMocks
    private RolPermisoServiceImpl rolPermisoService;

    private Rol rolGlobal;
    private Permiso permisoGlobal;

    @BeforeEach
    public void setup() {
        // Configuración de un rol global
        rolGlobal = new Rol();
        rolGlobal.setRolId(1L);
        rolGlobal.setRolNombre("ADMIN");

        // Configuración de un permiso global
        permisoGlobal = new Permiso();
        permisoGlobal.setId(1L);
        permisoGlobal.setNombre("CREAR");
    }

    @DisplayName("Test para asignar permisos a un rol exitosamente")
    @Test
    public void testAsignarPermisosARol() {
        // given
        given(rolRepository.findById(rolGlobal.getRolId())).willReturn(Optional.of(rolGlobal));
        given(permisoRepository.findAllById(List.of(permisoGlobal.getId()))).willReturn(List.of(permisoGlobal));
        given(rolPermisoRepository.existsByRolAndPermiso(rolGlobal, permisoGlobal)).willReturn(false);

        // when
        rolPermisoService.asignarPermisosARol(rolGlobal.getRolId(), List.of(permisoGlobal.getId()));

        // then
        verify(rolPermisoRepository, times(1)).saveAll(anyList());
    }

    @DisplayName("Test para asignar permisos a un rol con permisos inexistentes")
    @Test
    public void testAsignarPermisosARolConPermisosInexistentes() {
        // given
        given(rolRepository.findById(rolGlobal.getRolId())).willReturn(Optional.of(rolGlobal));
        given(permisoRepository.findAllById(List.of(permisoGlobal.getId()))).willReturn(Collections.emptyList());

        // when & then
        assertThrows(EntityNotFoundException.class, () -> {
            rolPermisoService.asignarPermisosARol(rolGlobal.getRolId(), List.of(permisoGlobal.getId()));
        });

        verify(rolPermisoRepository, never()).saveAll(anyList());
    }

    @DisplayName("Test para obtener permisos de un rol exitosamente")
    @Test
    public void testObtenerPermisosDeRol() {
        // given
        RolPermiso rolPermiso = new RolPermiso(null, rolGlobal, permisoGlobal);
        rolGlobal.setRolPermisos(new HashSet<>()); // Usar HashSet para cumplir con el tipo Set
        rolGlobal.getRolPermisos().add(rolPermiso); // Agregar el permiso
        given(rolRepository.findById(rolGlobal.getRolId())).willReturn(Optional.of(rolGlobal));

        // when
        List<Permiso> permisos = rolPermisoService.obtenerPermisosDeRol(rolGlobal.getRolId());

        // then
        assertThat(permisos).isNotNull();
        assertThat(permisos.size()).isEqualTo(1);
        assertThat(permisos.get(0).getNombre()).isEqualTo("CREAR");
    }



    @DisplayName("Test para obtener permisos de un rol inexistente")
    @Test
    public void testObtenerPermisosDeRolInexistente() {
        // given
        given(rolRepository.findById(rolGlobal.getRolId())).willReturn(Optional.empty());

        // when & then
        assertThrows(EntityNotFoundException.class, () -> {
            rolPermisoService.obtenerPermisosDeRol(rolGlobal.getRolId());
        });
    }
}
