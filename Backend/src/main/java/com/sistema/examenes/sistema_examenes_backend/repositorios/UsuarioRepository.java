package com.sistema.examenes.sistema_examenes_backend.repositorios;

import com.sistema.examenes.sistema_examenes_backend.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface UsuarioRepository extends JpaRepository<Usuario, Long> {

   public Usuario findByUsername(String username);

   public Usuario findByEmail(String email);

   public Usuario findByNit(String nit);

   public Usuario findByCui(String cui);

   public List<Usuario> findByEnabledTrue();

   public List<Usuario> findByEnabledFalse();

}
