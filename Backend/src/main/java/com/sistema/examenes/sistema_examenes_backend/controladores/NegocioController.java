package com.sistema.examenes.sistema_examenes_backend.controladores;

import com.sistema.examenes.sistema_examenes_backend.DTO.NegocioDTO;
import com.sistema.examenes.sistema_examenes_backend.DTO.RecursoDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.ErrorResponse;
import com.sistema.examenes.sistema_examenes_backend.entidades.Negocio;
import com.sistema.examenes.sistema_examenes_backend.entidades.Recurso;
import com.sistema.examenes.sistema_examenes_backend.entidades.Usuario;
import com.sistema.examenes.sistema_examenes_backend.excepciones.NegocioExistenteException;
import com.sistema.examenes.sistema_examenes_backend.servicios.NegocioService;
import com.sistema.examenes.sistema_examenes_backend.servicios.RecursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.net.MalformedURLException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RestController
@RequestMapping("/negocios")
@CrossOrigin("*")

public class NegocioController {

    @Autowired
    private NegocioService negocioService;

    @Autowired
    private RecursoService recursoService;

    @GetMapping
    public List<Negocio> obtenerNegocios(){
        return negocioService.obtenerNegocios();
    }


    @GetMapping("/{id}")
    public ResponseEntity<?> obtenerUsuarioPorId(@PathVariable Long id) {
        Optional<Negocio> negocioOptional = negocioService.findById(id);

        if (negocioOptional.isPresent()) {
            return ResponseEntity.ok(negocioOptional.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Negocio no encontrado", "No hay negocios con el ID proporcionado: " + id));
        }
    }


    @PostMapping("/")
    public ResponseEntity<?> crearNegocio(
            @RequestPart("nombre") String nombre,
            @RequestPart("direccion") String direccion,
            @RequestPart("descripcion") String descripcion,
            @RequestPart("telefono") String telefono,
            @RequestPart("email") String email,
            @RequestPart("slogan") String slogan,
            @RequestPart("fotoPerfil") MultipartFile fotoPerfil) {

        try {
            // Validar que todos los campos estén presentes y no vacíos
            if (nombre.isEmpty() || direccion.isEmpty() || descripcion.isEmpty() || telefono.isEmpty() || fotoPerfil.isEmpty()) {
                return new ResponseEntity<>("Todos los campos son obligatorios.", HttpStatus.BAD_REQUEST);
            }

            // Crear un nuevo negocio con los campos recibidos
            Negocio nuevoNegocio = new Negocio();
            nuevoNegocio.setNombre(nombre);
            nuevoNegocio.setDireccion(direccion);
            nuevoNegocio.setDescripcion(descripcion);
            nuevoNegocio.setTelefono(telefono);
            nuevoNegocio.setEmail(email);
            nuevoNegocio.setSlogan(slogan);

            // Guardar la imagen y asignar la ruta al negocio
            String rutaImagen = guardarImagen(fotoPerfil, nombre);  // Llama a la nueva función mejorada
            nuevoNegocio.setFotoPerfil(rutaImagen);

            // Guardar el negocio en la base de datos
            Negocio negocioGuardado = negocioService.guardarNegocio(nuevoNegocio);

            return new ResponseEntity<>(negocioGuardado, HttpStatus.CREATED);

        } catch (NegocioExistenteException ex) {
            // Responder con estado 400 (BAD REQUEST) si el negocio ya existe
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(ex.getMessage(), "El nombre del negocio está duplicado"));
        } catch (Exception ex) {
            // Responder con estado 500 (INTERNAL SERVER ERROR) para cualquier otro error
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error inesperado", ex.getMessage()));
        }
    }


    @PutMapping("/{id}")
    public ResponseEntity<?> actualizarNegocio(
            @PathVariable Long id,
            @RequestPart(required = false) String nombre,
            @RequestPart(required = false) String direccion,
            @RequestPart(required = false) String descripcion,
            @RequestPart(required = false) String telefono,
            @RequestPart(required = false) String email,
            @RequestPart(required = false) String slogan,
            @RequestPart(required = false) MultipartFile fotoPerfil) {

        try {
            // Obtener el negocio existente
            Negocio negocioExistente = negocioService.findById(id)
                    .orElseThrow(() -> new RuntimeException("Negocio no encontrado con ID: " + id));

            // Actualizar solo los campos que vienen en la solicitud
            if (nombre != null && !nombre.isEmpty()) {
                negocioExistente.setNombre(nombre);
            }
            if (direccion != null && !direccion.isEmpty()) {
                negocioExistente.setDireccion(direccion);
            }
            if (descripcion != null && !descripcion.isEmpty()) {
                negocioExistente.setDescripcion(descripcion);
            }
            if (telefono != null && !telefono.isEmpty()) {
                negocioExistente.setTelefono(telefono);
            }
            if (email != null && !email.isEmpty()) {
                negocioExistente.setEmail(email);
            }
            if (slogan != null && !slogan.isEmpty()) {
                negocioExistente.setSlogan(slogan);
            }

            // Manejo de la imagen
            if (fotoPerfil != null && !fotoPerfil.isEmpty()) {
                // Eliminar la imagen anterior si existe
                if (negocioExistente.getFotoPerfil() != null && !negocioExistente.getFotoPerfil().isEmpty()) {
                    eliminarImagen(negocioExistente.getFotoPerfil());
                }

                // Guardar la nueva imagen y asignar la ruta al negocio
                String rutaImagen = guardarImagen(fotoPerfil, negocioExistente.getNombre());
                negocioExistente.setFotoPerfil(rutaImagen);
            }

            // Delegar la actualización al servicio
            Negocio negocioActualizado = negocioService.actualizarNegocio(negocioExistente);

            // Responder con el negocio actualizado
            return ResponseEntity.ok(negocioActualizado);

        } catch (IllegalArgumentException ex) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new ErrorResponse(ex.getMessage(), "El negocio no se pudo actualizar."));
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ErrorResponse("Negocio no encontrado.", "No se encontró un negocio con el ID proporcionado."));
        } catch (Exception ex) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new ErrorResponse("Error inesperado", ex.getMessage()));
        }
    }



    // Eliminar un negocio por ID
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> eliminarNegocio(@PathVariable Long id) {
        negocioService.eliminarNegocio(id);
        return ResponseEntity.noContent().build();
    }

    private String guardarImagen(MultipartFile file, String nombreNegocio) throws IOException {
        String nombreArchivo = UUID.randomUUID().toString() + "_" + file.getOriginalFilename();

        // Define la ruta dentro de static
        String rutaDirectorio = "src/main/resources/static/uploads/negocio/";
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
        return "/uploads/negocio/" + nombreArchivo;
    }



    private void eliminarImagen(String rutaImagen) {
        try {
            Path rutaCompleta = Paths.get(rutaImagen.startsWith("/") ? "uploads" + rutaImagen : "uploads/" + rutaImagen);

            // Verifica si el archivo existe antes de intentar eliminarlo
            if (Files.exists(rutaCompleta)) {
                Files.delete(rutaCompleta);
            } else {
                System.out.println("La imagen no existe: " + rutaCompleta.toString());
            }
        } catch (IOException e) {
            System.err.println("No se pudo eliminar la imagen: " + e.getMessage());
        }
    }




}
