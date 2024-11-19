package com.sistema.examenes.sistema_examenes_backend.repository;

import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;
import com.sistema.examenes.sistema_examenes_backend.repositorios.NegocioRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
@Transactional
public class NegocioRepositoryTest {

    @Autowired
    private NegocioRepository negocioRepository;

    private Negocio negocioGlobal;

    @BeforeEach
    public void setUp() {
        negocioGlobal = new Negocio();
        negocioGlobal.setNombre("Tienda de Ropa");
        negocioGlobal.setDireccion("Calle 123, Ciudad");
        negocioGlobal.setDescripcion("Venta de ropa para todas las edades");
        negocioGlobal.setTelefono("123456789");
        negocioGlobal.setFotoPerfil("foto.png");
        negocioGlobal.setEmail("contacto@tiendaropa.com");
        negocioGlobal.setSlogan("¡Vístete con estilo!");
        negocioGlobal.setCitasAleatorias(true);

        negocioRepository.save(negocioGlobal);
    }

    @Test
    public void testGuardarNegocio() {
        // given
        Negocio negocio = new Negocio();
        negocio.setNombre("Librería Central");
        negocio.setDireccion("Avenida 456, Ciudad");
        negocio.setDescripcion("Venta de libros y material escolar");
        negocio.setTelefono("987654321");
        negocio.setFotoPerfil("libreria.png");
        negocio.setEmail("info@libreriacentral.com");
        negocio.setSlogan("Un libro para cada ocasión");
        negocio.setCitasAleatorias(false);

        // when
        Negocio negocioGuardado = negocioRepository.save(negocio);

        // then
        assertThat(negocioGuardado).isNotNull();
        assertThat(negocioGuardado.getNegocioId()).isGreaterThan(0);
    }

    @Test
    public void testListarNegocios() {
        //given
        Negocio negocio1 = new Negocio();
        negocio1.setNombre("Barberia SJ");
        negocio1.setDireccion("Avenida 456, Quetgo");
        negocio1.setDescripcion("Cortamos el pelo");
        negocio1.setTelefono("98765");
        negocio1.setFotoPerfil("barberia.png");
        negocio1.setEmail("barbs@gmail.com");
        negocio1.setSlogan("Un pelo para cada ocasión");
        negocio1.setCitasAleatorias(false);

        negocioRepository.save(negocioGlobal);
        negocioRepository.save(negocio1);

        // when
        List<Negocio> listaNegocios = negocioRepository.findAll();

        // then
        assertThat(listaNegocios).isNotNull();
        assertThat(listaNegocios.size()).isEqualTo(2);
    }

    @Test
    public void testObtenerNegocioPorId() {
        //given
        negocioRepository.save(negocioGlobal);

        // when
        Optional<Negocio> negocioOptional = negocioRepository.findById(negocioGlobal.getNegocioId());

        // then
        assertThat(negocioOptional).isPresent();
        assertThat(negocioOptional.get().getNombre()).isEqualTo("Tienda de Ropa");
    }

    @Test
    public void testExistePorNombre() {
        //given
        negocioRepository.save(negocioGlobal);

        // when
        boolean existe = negocioRepository.existsByNombre(negocioGlobal.getNombre());

        // then
        assertThat(existe).isTrue();
    }


    @Test
    public void testActualizarNegocio() {
        //given
        negocioRepository.save(negocioGlobal);

        // when
        Negocio negocioGuardado = negocioRepository.findById(negocioGlobal.getNegocioId()).get();
        negocioGuardado.setNombre("Tienda de Calzado");
        negocioGuardado.setSlogan("Pasos con estilo");
        Negocio negocioActualizado = negocioRepository.save(negocioGuardado);

        // then
        assertThat(negocioActualizado.getNombre()).isEqualTo("Tienda de Calzado");
        assertThat(negocioActualizado.getSlogan()).isEqualTo("Pasos con estilo");
    }

    @Test
    public void testEliminarNegocio() {
        //given
        negocioRepository.save(negocioGlobal);

        // when
        negocioRepository.deleteById(negocioGlobal.getNegocioId());
        Optional<Negocio> negocioOptional = negocioRepository.findById(negocioGlobal.getNegocioId());

        // then
        assertThat(negocioOptional).isEmpty();
    }

    @Test
    public void testNegocioConCitasAleatoriasTrue() {
        //given
        negocioRepository.save(negocioGlobal);

        // when
        Optional<Negocio> negocioConCitasAleatorias = negocioRepository.findByCitasAleatoriasTrue();

        // then
        assertThat(negocioConCitasAleatorias).isPresent();
        assertThat(negocioConCitasAleatorias.get().isCitasAleatorias()).isTrue();
        assertThat(negocioConCitasAleatorias.get().getNombre()).isEqualTo("Tienda de Ropa");
    }

    @Test
    public void testNegocioConCitasAleatoriasFalse() {

        // given
        negocioGlobal.setCitasAleatorias(false);
        negocioRepository.save(negocioGlobal);

        // when
        Optional<Negocio> negocioConCitasAleatorias = negocioRepository.findByCitasAleatoriasTrue();

        // then
        assertThat(negocioConCitasAleatorias).isEmpty();
    }
}
