package com.sistema.examenes.sistema_examenes_backend.controladores;

//import com.sistema.examenes.sistema_examenes_backend.DTO.RolDTO;
import com.sistema.examenes.sistema_examenes_backend.DTO.UsuarioDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.*;
import com.sistema.examenes.sistema_examenes_backend.excepciones.UsuarioExistenteException;
import com.sistema.examenes.sistema_examenes_backend.servicios.RolService;
import com.sistema.examenes.sistema_examenes_backend.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/usuarios")
@CrossOrigin ("*")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;


    @Autowired
    private RolService rolService;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    @GetMapping
    public ResponseEntity<List<Usuario>> listarUsuarios() {
        List<Usuario> usuarios = usuarioService.obtenerUsuarios();
        return ResponseEntity.ok(usuarios);
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Usuario>> listarUsuariosActivos() {
        List<Usuario> usuariosActivos = usuarioService.listarUsuariosActivos();
        return ResponseEntity.ok(usuariosActivos);
    }

    @GetMapping("/desactivados")
    public ResponseEntity<List<Usuario>> listarUsuariosNoActivos() {
        List<Usuario> usuariosNoActivos = usuarioService.listarUsuariosNoActivos();
        return ResponseEntity.ok(usuariosNoActivos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerUsuarioPorId(@PathVariable Long id) {
        Usuario usuario = usuarioService.findById(id).orElse(null);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }

    @PutMapping("/activar/{id}")
    public ResponseEntity<Usuario> activarUsuario(@PathVariable Long id) {
        Usuario usuarioActivado = usuarioService.activarUsuario(id);
        return usuarioActivado != null ? ResponseEntity.ok(usuarioActivado) : ResponseEntity.notFound().build();
    }

    @PutMapping("/desactivar/{id}")
    public ResponseEntity<Usuario> desactivarUsuario(@PathVariable Long id) {
        Usuario usuarioDesactivado = usuarioService.desactivarUsuario(id);
        return usuarioDesactivado != null ? ResponseEntity.ok(usuarioDesactivado) : ResponseEntity.notFound().build();
    }


    @PostMapping("/")
    public ResponseEntity<?> guardarUsuario(@RequestBody UsuarioDTO usuarioDTO) {
        try {
            // Convertir UsuarioDTO a la entidad Usuario
            Usuario usuario = new Usuario();
            usuario.setUsername(usuarioDTO.getUsername());
            usuario.setPassword(usuarioDTO.getPassword());
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
                usuarioRol.setRol(rolService.findById(rolId)
                        .orElseThrow(() -> new Exception("Rol no encontrado")));
                usuarioRol.setUsuario(usuario);
                usuarioRoles.add(usuarioRol);
            }

            // Guardar el usuario usando el servicio
            Usuario nuevoUsuario = usuarioService.guardarUsuario(usuario, usuarioRoles);

            // Retornar respuesta exitosa
            return new ResponseEntity<>(nuevoUsuario, HttpStatus.CREATED); // Código 201 para creación exitosa

        } catch (UsuarioExistenteException ex) {
            // Retornar respuesta específica para el error de usuario existente
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(ex.getMessage(), "Asegúrate de que el nombre de usuario, email, CUI o NIT no estén duplicados."));

        } catch (Exception ex) {
            // Retornar respuesta genérica para otros errores
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error inesperado", ex.getMessage()));
        }
    }




    @PutMapping("/{id}")
    public ResponseEntity<Usuario> actualizarUsuario(
            @PathVariable Long id,
            @RequestPart(value = "username", required = false) String username,
            @RequestPart(value = "nombre", required = false) String nombre,
            @RequestPart(value = "apellido", required = false) String apellido,
            @RequestPart(value = "telefono", required = false) String telefono,
            @RequestPart(value = "perfil", required = false) MultipartFile perfil
    ) {
        try {
            // Buscar el usuario por ID
            Usuario usuarioExistente = usuarioService.findById(id)
                    .orElseThrow(() -> new Exception("Usuario no encontrado"));

            // Actualizar solo los campos que fueron enviados
            if (username != null && !username.isEmpty()) {
                usuarioExistente.setUsername(username);
            }
            if (nombre != null && !nombre.isEmpty()) {
                usuarioExistente.setNombre(nombre);
            }
            if (apellido != null && !apellido.isEmpty()) {
                usuarioExistente.setApellido(apellido);
            }
            if (telefono != null && !telefono.isEmpty()) {
                usuarioExistente.setTelefono(telefono);
            }

            // Si se subió una nueva imagen, guardarla usando el username existente del usuario
            if (perfil != null && !perfil.isEmpty()) {
                String nombreImagen = guardarImagen(perfil, usuarioExistente.getUsername());
                usuarioExistente.setPerfil(nombreImagen);
            }

            // Llamar al servicio para actualizar el usuario
            Usuario usuarioActualizado = usuarioService.actualizarUsuario(usuarioExistente);

            // Retornar respuesta exitosa
            return new ResponseEntity<>(usuarioActualizado, HttpStatus.OK); // Código 200 para éxito

        } catch (Exception e) {
            // Retornar respuesta con error
            return new ResponseEntity<>(null, HttpStatus.NOT_FOUND); // Código 404 para usuario no encontrado
        }
    }





    private String guardarImagen(MultipartFile file, String nombreNegocio) throws IOException {
        String nombreArchivo = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // Define la ruta dentro de static
        String rutaDirectorio = "src/main/resources/static/uploads/usuario/";
        String rutaArchivo = Paths.get(rutaDirectorio, nombreArchivo).toString();

        // Crear el directorio si no existe
        File directorio = new File(rutaDirectorio);
        if (!directorio.exists()) {
            directorio.mkdirs();  // Crear el directorio si no existe
        }

        // Guardar la imagen en la ruta especificada
        byte[] bytesImg = file.getBytes();
        Path rutaCompleta = Paths.get(rutaArchivo);
        Files.write(rutaCompleta, bytesImg);

        // Retornar la ruta relativa para guardar en la base de datos
        return "/uploads/usuario/" + nombreArchivo;
    }



}
