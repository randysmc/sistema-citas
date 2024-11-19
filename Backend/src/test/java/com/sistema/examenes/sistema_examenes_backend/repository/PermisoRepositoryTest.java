package com.sistema.examenes.sistema_examenes_backend.repository;

import com.sistema.examenes.sistema_examenes_backend.entidades.Permiso;
import com.sistema.examenes.sistema_examenes_backend.repositorios.PermisoRepository;
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
//@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)

public class PermisoRepositoryTest {

    @Autowired
    private PermisoRepository permisoRepository;

    private Permiso permisoGlobal;

    @BeforeEach
    public void setup(){
        permisoRepository.deleteAll();
        permisoGlobal = new Permiso();
        //permisoGlobal.setId(1L);
        permisoGlobal.setNombre("CREAR");
        permisoRepository.save(permisoGlobal);
    }

    @DisplayName("Test para guardar un permiso")
    @Test
    public void testGuardarPermiso(){
        //given --dado o condición previa
        Permiso permiso1 = new Permiso();
        //permiso1.setId(1L);
        permiso1.setNombre("EDITAR");

        //when -- acción o comportamiento
        Permiso permisoGuardado = permisoRepository.save(permiso1);

        //then -- verificar la salida
        assertThat(permisoGuardado).isNotNull();
        assertThat(permisoGuardado.getId()).isGreaterThan(0);
    }

    @DisplayName("Test para listar los permisos")
    @Test
    public void testListarPermisos(){
        //given
        Permiso permiso1 = new Permiso();
        //permiso1.setId(2L);
        permiso1.setNombre("EDITAR");


        permisoRepository.save(permisoGlobal);
        permisoRepository.save(permiso1);

        //when
        List<Permiso> listaPermisos = permisoRepository.findAll();

        //then
        assertThat(listaPermisos).isNotNull();
        assertThat(listaPermisos.size()).isEqualTo(2);

    }

    @DisplayName("Test para obtener permiso por Id")
    @Test
    public void testObtenerPermisoPorId(){
        //given
        permisoRepository.save(permisoGlobal);

        //when
        Permiso permisoBD = permisoRepository.findById(permisoGlobal.getId()).get();

        //then
        assertThat(permisoBD).isNotNull();
    }

    @DisplayName("Test para actualizar un permiso")
    @Test
    public void testActualizarPermiso(){
        //given
        permisoRepository.save(permisoGlobal);

        //when
        Permiso permisoGuardado = permisoRepository.findById(permisoGlobal.getId()).get();
        permisoGuardado.setNombre("LISTAR");
        Permiso permisoActualizado = permisoRepository.save(permisoGuardado);

        //then
        assertThat(permisoActualizado.getNombre()).isEqualTo("LISTAR");

    }

    @DisplayName("Test para eliminar permiso")
    @Test
    public void testEliminarPermiso(){
        //given
        permisoRepository.save(permisoGlobal);

        //when
        permisoRepository.deleteById(permisoGlobal.getId());
        Optional<Permiso> permisoOptional = permisoRepository.findById(permisoGlobal.getId());

        //then
        assertThat(permisoOptional).isEmpty();
    }
}
