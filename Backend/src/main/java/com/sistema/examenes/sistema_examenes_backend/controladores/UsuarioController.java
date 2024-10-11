package com.sistema.examenes.sistema_examenes_backend.controladores;

import com.sistema.examenes.sistema_examenes_backend.DTO.UsuarioDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Rol;
import com.sistema.examenes.sistema_examenes_backend.entidades.Usuario;
import com.sistema.examenes.sistema_examenes_backend.entidades.UsuarioRol;
import com.sistema.examenes.sistema_examenes_backend.servicios.RolService;
import com.sistema.examenes.sistema_examenes_backend.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.Set;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin ("*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private RolService rolService;



    /*@PostMapping("/")
    public Usuario guardarUsuario(@RequestBody Usuario usuario) throws Exception{
        usuario.setPerfil("default.png");
        usuario.setPassword(this.bCryptPasswordEncoder.encode(usuario.getPassword()));

        //usuario.setPassword(this.bCryptPasswordEncoder.encode(usuario.getPassword()));
        Set<UsuarioRol> usuarioRoles = new HashSet<>();
        Rol rol = new Rol();
        rol.setRolId(2L);
        rol.setRolNombre("NORMAL");

        UsuarioRol usuarioRol = new UsuarioRol();
        usuarioRol.setUsuario(usuario);
        usuarioRol.setRol(rol);

        usuarioRoles.add(usuarioRol);

        return usuarioService.guardarUsuario(usuario, usuarioRoles);
    }*/

    @PostMapping("/")
    public Usuario guardarUsuario(@RequestBody UsuarioDTO usuarioDTO) throws Exception {
        // Convertir UsuarioDTO a entidad Usuario
        Usuario usuario = new Usuario();
        usuario.setUsername(usuarioDTO.getUsername());
        usuario.setPassword(bCryptPasswordEncoder.encode(usuarioDTO.getPassword()));
        usuario.setNombre(usuarioDTO.getNombre());
        usuario.setApellido(usuarioDTO.getApellido());
        usuario.setEmail(usuarioDTO.getEmail());
        usuario.setTelefono(usuarioDTO.getTelefono());
        usuario.setEnabled(usuarioDTO.isEnabled());
        usuario.setPerfil(usuarioDTO.getPerfil());
        usuario.setNit(usuarioDTO.getNit());
        usuario.setCui(usuarioDTO.getCui());
        usuario.setTfa(usuarioDTO.isTfa());

        // Manejar los roles
        Set<UsuarioRol> usuarioRoles = new HashSet<>();
        for (Long rolId : usuarioDTO.getRoles()) {
            Rol rol = rolService.findById(rolId)
                    .orElseThrow(() -> new Exception("Rol no encontrado: " + rolId));

            UsuarioRol usuarioRol = new UsuarioRol();
            usuarioRol.setUsuario(usuario);
            usuarioRol.setRol(rol);
            usuarioRoles.add(usuarioRol);
        }

        // Verificar si hay roles antes de guardar
        if (usuarioRoles.isEmpty()) {
            throw new Exception("El usuario debe tener al menos un rol asignado.");
        }

        // Llamar al servicio para guardar el usuario
        return usuarioService.guardarUsuario(usuario, usuarioRoles);
    }


    //se envia un pathvariable
    @GetMapping("/{username}")
    public Usuario obtenerUsuario(@PathVariable("username") String username){
        System.out.println("Estamos obteniendo al usuario: " + username);
        Usuario usuario = usuarioService.obtenerUsuario(username);
        System.out.println("Usuario obtenido: " + usuario.getNombre());  // Verifica si el objeto Usuario está nulo o tiene datos
        return usuario;
    }



    @DeleteMapping("/{usuarioId}")
    public void eliminarUsuario(@PathVariable("usuarioId") Long usuarioId){
        usuarioService.eliminarUsuario(usuarioId);
    }
}
