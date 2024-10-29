package com.sistema.examenes.sistema_examenes_backend.repository;


import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;
import com.sistema.examenes.sistema_examenes_backend.repositorios.NegocioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test") // Usa el archivo application-test.properties
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // No reemplazar H2 por otra base de datos
@Transactional
public class NegocioRepositoryTest {

    @Autowired
    private NegocioRepository negocioRepository;

    private Negocio negocioGlobal;

    @BeforeEach
    public void setUp() {
        negocioGlobal = new Negocio();
        negocioGlobal.setNombre("Negocio Test");
        negocioGlobal.setDireccion("Calle 123");
        negocioGlobal.setDescripcion("Descripción del negocio de prueba");
        negocioGlobal.setTelefono("123456789");
        negocioGlobal.setFotoPerfil("foto_test.png");
        negocioGlobal.setEmail("test@negocio.com");
        negocioGlobal.setSlogan("El mejor negocio");

        // Guardar el negocio global antes de cada prueba
        negocioRepository.save(negocioGlobal);
    }



    @Test
    public void testGuardarNegocio() {
        Negocio negocio = new Negocio();
        negocio.setNombre("Negocio Uno");
        negocio.setDireccion("Calle 1");
        negocio.setDescripcion("Descripción del negocio uno");
        negocio.setTelefono("123456789");
        negocio.setFotoPerfil("foto.png");

        Negocio negocioGuardado = negocioRepository.save(negocio);

        assertThat(negocioGuardado.getNombre()).isEqualTo("Negocio Uno");
    }

    @Test
    public void testEncontrarPorId() {
        Long id = negocioGlobal.getNegocioId();

        Optional<Negocio> encontrado = negocioRepository.findById(id);
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNombre()).isEqualTo("Negocio Test");
    }

    @Test
    public void testEncontrarPorNombre() {
        assertThat(negocioRepository.existsByNombre("Negocio Test")).isTrue();
    }


    @Test
    public void testNegocioExistente() {
        Negocio negocio = new Negocio();
        negocio.setNombre("Negocio Cuatro");
        negocio.setDireccion("Calle 000");
        negocio.setDescripcion("Descripción del negocio cuatro");
        negocio.setTelefono("456456456");
        negocio.setFotoPerfil("foto_cuatro.png");

        negocioRepository.save(negocio);

        // Intentar guardar un negocio con el mismo nombre
        Negocio negocioDuplicado = new Negocio();
        negocioDuplicado.setNombre("Negocio Cuatro");
        negocioDuplicado.setDireccion("Calle 111");
        negocioDuplicado.setDescripcion("Descripción duplicada");
        negocioDuplicado.setTelefono("999999999");
        negocioDuplicado.setFotoPerfil("foto_cuatro_duplicada.png");

        // Esperamos que esto lance una excepción
        assertThatThrownBy(() -> negocioRepository.save(negocioDuplicado))
                .isInstanceOf(DataIntegrityViolationException.class);
    }

    @Test
    public void testEliminarNegocio() {
        Long id = negocioGlobal.getNegocioId();

        negocioRepository.deleteById(id);
        assertThat(negocioRepository.findById(id)).isNotPresent();
    }


    @Test
    public void testActualizarNegocio() {
        Long id = negocioGlobal.getNegocioId();

        // Actualizar el negocio
        negocioGlobal.setNombre("Negocio Test Actualizado");
        negocioRepository.save(negocioGlobal);

        Optional<Negocio> negocioActualizado = negocioRepository.findById(id);
        assertThat(negocioActualizado).isPresent();
        assertThat(negocioActualizado.get().getNombre()).isEqualTo("Negocio Test Actualizado");
    }

    @Test
    public void testGuardarNegocioConNombreDuplicado() {
        // Intentar guardar un negocio con un nombre duplicado
        Negocio negocioDuplicado = new Negocio();
        negocioDuplicado.setNombre("Negocio Test"); // Mismo nombre que el negocio global
        negocioDuplicado.setDireccion("Calle Duplicada");
        negocioDuplicado.setDescripcion("Descripción duplicada");
        negocioDuplicado.setTelefono("987654321");
        negocioDuplicado.setFotoPerfil("foto_duplicado.png");

        // Este test espera que el negocio duplicado no se guarde
        assertThat(negocioRepository.existsByNombre(negocioDuplicado.getNombre())).isTrue();
    }




}
