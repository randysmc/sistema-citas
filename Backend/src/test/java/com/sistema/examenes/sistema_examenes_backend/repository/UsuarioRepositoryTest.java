package com.sistema.examenes.sistema_examenes_backend.repository;


import com.sistema.examenes.sistema_examenes_backend.entidades.Usuario;
import com.sistema.examenes.sistema_examenes_backend.repositorios.UsuarioRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test") // Indica que se use application-test.properties
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE) // No reemplazar H2 por otra base de datos
public class UsuarioRepositoryTest {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Test
    public void testGuardarUsuario() {
        Usuario usuario = new Usuario();
        usuario.setNombre("Ringo");
        usuario.setApellido("sum");
        usuario.setUsername("nachito");
        usuario.setPassword("password"); // Si usas codificación, ajústalo
        usuario.setEmail("nacho@gmail.com");
        usuario.setTelefono("7754");
        usuario.setNit("465654");
        usuario.setCui("654");
        usuario.setPerfil("foto.png");

        Usuario usuarioGuardado = usuarioRepository.save(usuario);

        assertThat(usuarioGuardado.getUsername()).isEqualTo("nachito");
    }
}
