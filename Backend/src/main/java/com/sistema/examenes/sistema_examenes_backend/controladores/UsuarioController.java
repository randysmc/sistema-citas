package com.sistema.examenes.sistema_examenes_backend.controladores;

import com.sistema.examenes.sistema_examenes_backend.entidades.Rol;
import com.sistema.examenes.sistema_examenes_backend.entidades.Usuario;
import com.sistema.examenes.sistema_examenes_backend.entidades.UsuarioRol;
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



    @PostMapping("/")
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
