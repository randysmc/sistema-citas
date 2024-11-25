package com.sistema.examenes.sistema_examenes_backend.service;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.willDoNothing;
import static org.mockito.Mockito.*;


import com.sistema.examenes.sistema_examenes_backend.entidades.Permiso;
import com.sistema.examenes.sistema_examenes_backend.excepciones.EntityNotFoundException;
import com.sistema.examenes.sistema_examenes_backend.excepciones.PermisoExistenteException;
import com.sistema.examenes.sistema_examenes_backend.excepciones.RolExistenteException;
import com.sistema.examenes.sistema_examenes_backend.repositorios.PermisoRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.implementacion.PermisoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.context.config.ConfigDataResourceNotFoundException;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class PermisoServiceTest {

    @Mock
    private PermisoRepository permisoRepository;

    @InjectMocks
    private PermisoServiceImpl permisoService;

    private Permiso permisoGlobal;

    @BeforeEach
    public void setup(){
        //permisoRepository.deleteAll();
        permisoGlobal = new Permiso();
        permisoGlobal.setId(1L);
        permisoGlobal.setNombre("CREAR");
        permisoRepository.save(permisoGlobal);
    }

    @DisplayName("Test para guardar un permiso")
    @Test
    public void testGuardarPermiso(){
        //given
        given(permisoRepository.findByNombre(permisoGlobal.getNombre()))
                .willReturn(Optional.empty());

        given(permisoRepository.save(permisoGlobal)).willReturn(permisoGlobal);

        //when
        Permiso permisoGuardado = permisoService.save(permisoGlobal);

        //then
        assertThat(permisoGuardado).isNotNull();

    }

    @DisplayName("Test para lanzar excepción al guardar un rol existente")
    @Test
    public void testGuardarPermisoExistente() {
        // given
        given(permisoRepository.findByNombre(permisoGlobal.getNombre())).willReturn(Optional.of(permisoGlobal));

        // when/then
        org.junit.jupiter.api.Assertions.assertThrows(PermisoExistenteException.class, () -> {
            permisoService.save(permisoGlobal);
        });
    }

    @DisplayName("Test para listar permisos")
    @Test
    public void testListarPermisos(){
        //given
        Permiso permiso1 = new Permiso();
        permiso1.setId(2L);
        permiso1.setNombre("EDITAR");

        given(permisoRepository.findAll()).willReturn(List.of(permisoGlobal, permiso1));


        //when
        List<Permiso> permisos = permisoService.findAll();

        //then
        assertThat(permisos).isNotNull();
        assertThat(permisos.size()).isEqualTo(2);
    }


    @DisplayName("Test para devolver una lista vacía")
    @Test
    public void testListarColeccionPermisosVacia() {
        //given
        given(permisoRepository.findAll()).willReturn(Collections.emptyList());

        //when & then
        assertThrows(EntityNotFoundException.class, () -> {
            permisoService.findAll();
        });

        verify(permisoRepository, times(1)).findAll();
    }


    @DisplayName("Test para obtener Permiso por Id")
    @Test
    public void testObtenerPermisoPorId(){
        //given
        given(permisoRepository.findById(1L)).willReturn(Optional.of(permisoGlobal));

        //when
        Permiso permisoGuardado = permisoService.findById(permisoGlobal.getId()).get();

        //then
        assertThat(permisoGuardado).isNotNull();
    }

    @DisplayName("Test para actualizar permiso")
    @Test
    public void testActualizarPermiso(){
        //given
        given(permisoRepository.findById(permisoGlobal.getId()))
                .willReturn(Optional.of(permisoGlobal));
        given(permisoRepository.save(permisoGlobal)).willReturn(permisoGlobal);

        permisoGlobal.setNombre("HACER");

        //when
        Permiso permisoActualizado = permisoService.update(permisoGlobal);

        //then
        assertThat(permisoActualizado.getNombre()).isEqualTo("HACER");
    }


    @DisplayName("Test para eliminar permiso")
    @Test
    public void testEliminarPermiso(){
        //given
        long permisoId = 1L;
        willDoNothing().given(permisoRepository).deleteById(permisoId);

        //when
        permisoService.delete(permisoId);

        //then
        verify(permisoRepository,times(1)).deleteById(permisoId);
    }

    @DisplayName("Test para obtener un Permiso por Id con excepción")
    @Test
    public void testObtenerPermisoPorIdConExcepcion() {
        //given
        given(permisoRepository.findById(1L)).willReturn(Optional.empty());

        //when & then
        assertThrows(EntityNotFoundException.class, () -> {
            permisoService.findById(1L);
        });

        verify(permisoRepository, times(1)).findById(1L);
    }



    @DisplayName("Test para listar un solo Permiso")
    @Test
    public void testListarUnSoloPermiso() {
        //given
        given(permisoRepository.findAll()).willReturn(List.of(permisoGlobal));

        //when
        List<Permiso> permisos = permisoService.findAll();

        //then
        assertThat(permisos).isNotNull();
        assertThat(permisos.size()).isEqualTo(1);
        assertThat(permisos.get(0).getNombre()).isEqualTo("CREAR");
    }


}
