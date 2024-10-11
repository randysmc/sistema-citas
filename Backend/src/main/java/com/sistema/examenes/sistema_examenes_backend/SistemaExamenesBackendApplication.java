package com.sistema.examenes.sistema_examenes_backend;

import com.sistema.examenes.sistema_examenes_backend.entidades.*;
import com.sistema.examenes.sistema_examenes_backend.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import java.util.HashSet;
import java.util.Set;

@SpringBootApplication
public class SistemaExamenesBackendApplication implements CommandLineRunner {

	@Autowired
	private UsuarioService usuarioService;

	@Autowired
	private BCryptPasswordEncoder bCryptPasswordEncoder;

	public static void main(String[] args) {
		SpringApplication.run(SistemaExamenesBackendApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {
		/*Usuario usuario = new Usuario();

		usuario.setNombre("Ringo");
		usuario.setApellido("sum");
		usuario.setUsername("nachito");
		usuario.setPassword(bCryptPasswordEncoder.encode("password"));
		usuario.setEmail("nacho@gmail.com");
		usuario.setTelefono("7754");
		usuario.setNit("465654");
		usuario.setCui("654");
		usuario.setPerfil("foto.png");

		Rol rol = new Rol();
		rol.setRolId(2L);
		rol.setRolNombre("CLIENTE");

		Set<UsuarioRol> usuarioRoles = new HashSet<>();
		UsuarioRol usuarioRol = new UsuarioRol();
		usuarioRol.setRol(rol);
		usuarioRol.setUsuario(usuario);
		usuarioRoles.add(usuarioRol);

		Usuario usuarioGuardado = usuarioService.guardarUsuario(usuario, usuarioRoles);
		System.out.println(usuarioGuardado.getUsername());*/

		/*Usuario usuario = new Usuario();

		usuario.setNombre("Cristal");
		usuario.setApellido("Coyoy");
		usuario.setUsername("crisjyr");
		usuario.setPassword(bCryptPasswordEncoder.encode("password"));
		usuario.setEmail("crisjyr@gmail.com");
		usuario.setTelefono("7795");
		usuario.setCui("123456");
		usuario.setNit("159");
		usuario.setPerfil("foto.png");

		Rol rol = new Rol();
		rol.setRolId(1L);
		rol.setRolNombre("ADMIN");

		Negocio negocio = new Negocio();
		negocio.setNegocioId(1L);
		negocio.setNombre("Clinica Dental JCDentist");


		Set<UsuarioRol> usuarioRoles = new HashSet<>();
		UsuarioRol usuarioRol = new UsuarioRol();
		usuarioRol.setRol(rol);
		usuarioRol.setUsuario(usuario);
		usuarioRoles.add(usuarioRol);

		Set<UsuarioNegocio> usuarioNegocios = new HashSet<>();
		UsuarioNegocio usuarioNegocio = new UsuarioNegocio();
		usuarioNegocio.setNegocio(negocio);
		usuarioNegocio.setUsuario(usuario);
		usuarioNegocios.add(usuarioNegocio);

		Usuario usuarioGuardado = usuarioService.guardarEmpleado(usuario, usuarioRoles, usuarioNegocios);
		System.out.println(usuarioGuardado.getUsername());*/
	}
}
