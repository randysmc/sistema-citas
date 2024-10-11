package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;

import com.sistema.examenes.sistema_examenes_backend.entidades.Usuario;
import com.sistema.examenes.sistema_examenes_backend.entidades.UsuarioNegocio;
import com.sistema.examenes.sistema_examenes_backend.entidades.UsuarioRol;
import com.sistema.examenes.sistema_examenes_backend.repositorios.NegocioRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.RolRepository;
import com.sistema.examenes.sistema_examenes_backend.repositorios.UsuarioRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
//Estereotipos
public class UsuarioServiceImplementacion implements UsuarioService {
    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private RolRepository rolRepository;

    @Autowired
    private NegocioRepository negocioRepository;

    @Override
    public Usuario guardarUsuario(Usuario usuario, Set<UsuarioRol> usuarioRoles) throws Exception {
        Usuario usuarioLocal = usuarioRepository.findByUsername(usuario.getUsername());
        if(usuarioLocal != null){
            System.out.println("El usuario ya existe");
            throw new Exception("El usuario ya existe");
        }
        else{
            for(UsuarioRol usuarioRol:usuarioRoles){
                rolRepository.save(usuarioRol.getRol());
            }

            usuario.getUsuarioRoles().addAll(usuarioRoles);
            usuarioLocal = usuarioRepository.save(usuario);
        }
        return usuarioLocal;
    }

    @Override
    public Usuario guardarEmpleado(Usuario usuario, Set<UsuarioRol> usuarioRoles, Set<UsuarioNegocio> usuarioNegocios) throws Exception {
        Usuario usuarioEmpleado = usuarioRepository.findByUsername(usuario.getUsername());

        if (usuarioEmpleado != null) {
            System.out.println("Usuario empleado ya existente");
            throw new Exception("El usuario ya existe");
        } else {
            // Guardar roles asociados
            for (UsuarioRol usuarioRol : usuarioRoles) {
                rolRepository.save(usuarioRol.getRol());
            }

            // Guardar negocios asociados
            for (UsuarioNegocio usuarioNegocio : usuarioNegocios) {
                negocioRepository.save(usuarioNegocio.getNegocio());
            }

            // Asociar roles y negocios al usuario
            usuario.getUsuarioRoles().addAll(usuarioRoles);
            usuario.getUsuarioNegocios().addAll(usuarioNegocios);

            // Guardar usuario en el repositorio
            usuarioEmpleado = usuarioRepository.save(usuario);  // Guardar y asignar a usuarioEmpleado
        }

        return usuarioEmpleado;  // Devolver el usuario guardado
    }

    @Override
    public Usuario obtenerUsuario(String username){
        return usuarioRepository.findByUsername(username);
    }

    @Override
    public void eliminarUsuario(Long usuarioId){
        usuarioRepository.deleteById(usuarioId);
    }
}
