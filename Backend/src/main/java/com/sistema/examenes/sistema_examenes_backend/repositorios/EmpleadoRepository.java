package com.sistema.examenes.sistema_examenes_backend.repositorios;

import com.sistema.examenes.sistema_examenes_backend.entidades.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;

public interface EmpleadoRepository extends JpaRepository<Usuario, Long> {

    public Usuario findByUsername(String username);
}
