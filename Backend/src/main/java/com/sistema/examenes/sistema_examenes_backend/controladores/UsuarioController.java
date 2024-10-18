package com.sistema.examenes.sistema_examenes_backend.controladores;

import com.sistema.examenes.sistema_examenes_backend.DTO.RolDTO;
import com.sistema.examenes.sistema_examenes_backend.DTO.UsuarioDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Rol;
import com.sistema.examenes.sistema_examenes_backend.entidades.Usuario;
import com.sistema.examenes.sistema_examenes_backend.entidades.UsuarioNegocio;
import com.sistema.examenes.sistema_examenes_backend.entidades.UsuarioRol;
import com.sistema.examenes.sistema_examenes_backend.servicios.RolService;
import com.sistema.examenes.sistema_examenes_backend.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/usuarios/")
@CrossOrigin ("*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    @Autowired
    private RolService rolService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @PostMapping("/")
    public Usuario guardarUsuario(@RequestBody UsuarioDTO usuarioDTO) throws Exception {
        // Convertir UsuarioDTO a la entidad Usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setPassword(usuarioDTO.getPassword());  // Recuerda codificar la contraseña en el servicio
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setEnabled(usuarioDTO.isEnabled());
        usuario.setPerfil(usuarioDTO.getPerfil());
        usuario.setCui(usuarioDTO.getCui());
        usuario.setNit(usuarioDTO.getNit());

        // Manejar los roles
        Set<UsuarioRol> usuarioRoles = new HashSet<>();
        for (Long rolId : usuarioDTO.getRoles()) {
            UsuarioRol usuarioRol = new UsuarioRol();
            usuarioRol.setRol(rolService.findById(rolId).orElseThrow(() -> new Exception("Rol no encontrado")));
            usuarioRol.setUsuario(usuario);
            usuarioRoles.add(usuarioRol);
        }

        // Guardar el usuario usando el servicio
        return usuarioService.guardarUsuario(usuario, usuarioRoles);
    }


}
