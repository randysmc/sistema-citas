package com.sistema.examenes.sistema_examenes_backend.controladores;

import com.sistema.examenes.sistema_examenes_backend.DTO.EmpleadoDTO;
import com.sistema.examenes.sistema_examenes_backend.DTO.NegocioDTO;
import com.sistema.examenes.sistema_examenes_backend.DTO.RolDTO;
import com.sistema.examenes.sistema_examenes_backend.DTO.UsuarioDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.*;
import com.sistema.examenes.sistema_examenes_backend.excepciones.UsuarioExistenteException;
import com.sistema.examenes.sistema_examenes_backend.servicios.EmpleadoService;
import com.sistema.examenes.sistema_examenes_backend.servicios.NegocioService;
import com.sistema.examenes.sistema_examenes_backend.servicios.RolService;
import com.sistema.examenes.sistema_examenes_backend.servicios.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;

@RestController
@RequestMapping("/empleados")
public class EmpleadoController {

    @Autowired
    private EmpleadoService empleadoService;

    @Autowired
    private RolService rolService;

    @Autowired
    private NegocioService negocioService;


    @GetMapping
    public List<Usuario> obtenerEmpleados() {
        return empleadoService.obtenerEmpleados();
    }

    @GetMapping("/activos")
    public ResponseEntity<List<Usuario>> listarUsuariosActivos() {
        List<Usuario> usuariosActivos = empleadoService.listarEmpleadosActivos();
        return ResponseEntity.ok(usuariosActivos);
    }

    @GetMapping("/desactivados")
    public ResponseEntity<List<Usuario>> listarUsuariosNoActivos() {
        List<Usuario> usuariosNoActivos = empleadoService.listarEmpleadosNoActivos();
        return ResponseEntity.ok(usuariosNoActivos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Usuario> obtenerEmpleadoPorId(@PathVariable Long id) {
        Usuario usuario = empleadoService.findById(id).orElse(null);
        return usuario != null ? ResponseEntity.ok(usuario) : ResponseEntity.notFound().build();
    }

    @PutMapping("/activar/{id}")
    public ResponseEntity<Usuario> activarUsuario(@PathVariable Long id) {
        Usuario usuarioActivado = empleadoService.activarEmpleado(id);
        return usuarioActivado != null ? ResponseEntity.ok(usuarioActivado) : ResponseEntity.notFound().build();
    }

    @PutMapping("/desactivar/{id}")
    public ResponseEntity<Usuario> desactivarUsuario(@PathVariable Long id) {
        Usuario usuarioDesactivado = empleadoService.desactivarEmpleado(id);
        return usuarioDesactivado != null ? ResponseEntity.ok(usuarioDesactivado) : ResponseEntity.notFound().build();
    }


    @PostMapping("/")
    public ResponseEntity<?> guardarE(@RequestBody EmpleadoDTO empleadoDTO) {
        try {
            // Convertir UsuarioDTO a la entidad Usuario
            Usuario empleado = new Usuario();
            empleado.setUsername(empleadoDTO.getUsername());
            empleado.setPassword(empleadoDTO.getPassword());  // Recuerda codificar la contraseña en el servicio
            empleado.setNombre(empleadoDTO.getNombre());
            empleado.setApellido(empleadoDTO.getApellido());
            empleado.setEmail(empleadoDTO.getEmail());
            empleado.setTelefono(empleadoDTO.getTelefono());
            empleado.setEnabled(empleadoDTO.isEnabled());
            empleado.setPerfil(empleadoDTO.getPerfil());
            empleado.setNit(empleadoDTO.getNit());
            empleado.setCui(empleadoDTO.getCui());

            // Manejar los roles
            Set<UsuarioRol> usuarioRoles = new HashSet<>();
            for (Long rolId : empleadoDTO.getRoles()) {
                UsuarioRol usuarioRol = new UsuarioRol();
                usuarioRol.setRol(rolService.findById(rolId).orElseThrow(() -> new Exception("Rol no encontrado")));
                usuarioRol.setUsuario(empleado);
                usuarioRoles.add(usuarioRol);
            }



            // Guardar el usuario usando el servicio
            Usuario nuevoEmpleado = empleadoService.guardarEmpleado(empleado, usuarioRoles);

            // Retornar respuesta exitosa
            return new ResponseEntity<>(nuevoEmpleado, HttpStatus.CREATED); // Código 201 para creación exitosa

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

    @PutMapping("/actualizar/{id}")
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
            Usuario empleadoExistente = empleadoService.findById(id)
                    .orElseThrow(() -> new Exception("Empleado no encontrado"));

            // Actualizar solo los campos que fueron enviados
            if (username != null && !username.isEmpty()) {
                empleadoExistente.setUsername(username);
            }
            if (nombre != null && !nombre.isEmpty()) {
                empleadoExistente.setNombre(nombre);
            }
            if (apellido != null && !apellido.isEmpty()) {
                empleadoExistente.setApellido(apellido);
            }
            if (telefono != null && !telefono.isEmpty()) {
                empleadoExistente.setTelefono(telefono);
            }

            // Si se subió una nueva imagen, guardarla usando el username existente del usuario
            if (perfil != null && !perfil.isEmpty()) {
                String nombreImagen = guardarImagen(perfil, empleadoExistente.getUsername());
                empleadoExistente.setPerfil(nombreImagen);
            }

            // Llamar al servicio para actualizar el usuario
            Usuario usuarioActualizado = empleadoService.actualizarEmpleado(empleadoExistente);

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


