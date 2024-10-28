package com.sistema.examenes.sistema_examenes_backend.repository;


import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;
import com.sistema.examenes.sistema_examenes_backend.repositorios.NegocioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

@SpringBootTest
@ActiveProfiles("test") // Usa el archivo application-test.properties
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // No reemplazar H2 por otra base de datos
public class NegocioRepositoryTest {

    @Autowired
    private NegocioRepository negocioRepository;

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
        Negocio negocio = new Negocio();
        negocio.setNombre("Negocio Dos");
        negocio.setDireccion("Calle 4");
        negocio.setDescripcion("Descripción del negocio dos");
        negocio.setTelefono("987654321");
        negocio.setFotoPerfil("foto_dos.png");

        Negocio negocioGuardado = negocioRepository.save(negocio);
        Long id = negocioGuardado.getNegocioId();

        Optional<Negocio> encontrado = negocioRepository.findById(id);
        assertThat(encontrado).isPresent();
        assertThat(encontrado.get().getNombre()).isEqualTo("Negocio Dos");
    }

    @Test
    public void testEncontrarPorNombre() {
        Negocio negocio = new Negocio();
        negocio.setNombre("Negocio Tres");
        negocio.setDireccion("Calle 7");
        negocio.setDescripcion("Descripción del negocio tres");
        negocio.setTelefono("123123123");
        negocio.setFotoPerfil("foto_tres.png");

        negocioRepository.save(negocio);

        assertThat(negocioRepository.existsByNombre("Negocio Tres")).isTrue();
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
        Negocio negocio = new Negocio();
        negocio.setNombre("Negocio Cinco");
        negocio.setDireccion("Calle 222");
        negocio.setDescripcion("Descripción del negocio cinco");
        negocio.setTelefono("321321321");
        negocio.setFotoPerfil("foto_cinco.png");

        Negocio negocioGuardado = negocioRepository.save(negocio);
        Long id = negocioGuardado.getNegocioId();

        negocioRepository.deleteById(id);
        assertThat(negocioRepository.findById(id)).isNotPresent();
    }

}
