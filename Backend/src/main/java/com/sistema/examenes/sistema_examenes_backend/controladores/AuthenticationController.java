package com.sistema.examenes.sistema_examenes_backend.controladores;

import com.sistema.examenes.sistema_examenes_backend.DTO.EmpleadoDTO;
import com.sistema.examenes.sistema_examenes_backend.DTO.UsuarioResponseDTO;
import com.sistema.examenes.sistema_examenes_backend.entidades.Usuario;
import com.sistema.examenes.sistema_examenes_backend.entidades.JwtRequest; // Asegúrate de importar JwtRequest
import com.sistema.examenes.sistema_examenes_backend.entidades.JwtResponse; // Asegúrate de importar JwtResponse
import com.sistema.examenes.sistema_examenes_backend.entidades.TwoFactorRequest; // Importa la clase TwoFactorRequest
import com.sistema.examenes.sistema_examenes_backend.configuraciones.JwtUtils;
import com.sistema.examenes.sistema_examenes_backend.servicios.implementacion.UserDetailsServiceImplementacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImplementacion userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    // Mapa para almacenar códigos 2FA temporales (usar base de datos en producción)
    private final Map<String, String> twoFactorCodes = new HashMap<>();

    @PostMapping("/generate-token")
    public ResponseEntity<?> generarToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            autenticar(jwtRequest.getUsername(), jwtRequest.getPassword());
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new Exception("Usuario no encontrado");
        }

        // Generar y enviar el código 2FA
        String twoFactorCode = generateTwoFactorCode();
        sendTwoFactorCode(jwtRequest.getUsername(), twoFactorCode);

        // Almacenar el código en el mapa (temporal)
        twoFactorCodes.put(jwtRequest.getUsername(), twoFactorCode);

        return ResponseEntity.ok(Collections.singletonMap("message", "Código de autenticación enviado"));
    }

    @PostMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestBody TwoFactorRequest twoFactorRequest) throws Exception {
        String storedCode = twoFactorCodes.get(twoFactorRequest.getUsername());

        if (storedCode != null && storedCode.equals(twoFactorRequest.getTwoFactorCode())) {
            // El código es válido, generar el token JWT
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(twoFactorRequest.getUsername());
            String token = this.jwtUtils.generateToken(userDetails);

            // Eliminar el código 2FA después de usarlo
            twoFactorCodes.remove(twoFactorRequest.getUsername());

            return ResponseEntity.ok(new JwtResponse(token));
        } else {
            throw new Exception("Código de autenticación inválido");
        }
    }

    private String generateTwoFactorCode() {
        // Genera un código aleatorio de 6 dígitos
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }

    private void sendTwoFactorCode(String username, String twoFactorCode) {
        // Lógica para enviar el código, puedes usar un servicio de correo
        System.out.println("Código 2FA para " + username + ": " + twoFactorCode);
    }

    private void autenticar(String username, String password) throws Exception {
        try {
            authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(username, password));
        } catch (DisabledException exception) {
            throw new Exception("USUARIO DESHABILITADO " + exception.getMessage());
        } catch (BadCredentialsException e) {
            throw new Exception("Credenciales invalidas " + e.getMessage());
        }
    }


    @GetMapping("/actual-usuario")
    public Usuario obtenerUsuarioActual(Principal principal) {
        return (Usuario) this.userDetailsService.loadUserByUsername(principal.getName());
    }



    /*@GetMapping("/actual-usuario")
    public ResponseEntity<?> obtenerUsuarioActual(Principal principal) {
        Usuario usuario = (Usuario) this.userDetailsService.loadUserByUsername(principal.getName());

        // Crear el nuevo DTO para la respuesta
        UsuarioResponseDTO responseDTO = new UsuarioResponseDTO();
        responseDTO.setUsername(usuario.getUsername());
        responseDTO.setNombre(usuario.getNombre());
        responseDTO.setApellido(usuario.getApellido());
        responseDTO.setEmail(usuario.getEmail());
        responseDTO.setTelefono(usuario.getTelefono());
        responseDTO.setEnabled(usuario.isEnabled());
        responseDTO.setPerfil(usuario.getPerfil());

        // Convertir los roles a una lista de nombres
        Set<String> roles = usuario.getUsuarioRoles().stream()
                .map(usuarioRol -> usuarioRol.getRol().getRolNombre())  // Nombres de los roles
                .collect(Collectors.toSet());
        responseDTO.setRoles(roles);  // Asignar nombres de roles

        // Convertir los negocios a una lista de nombres
        Set<String> negocios = usuario.getUsuarioNegocios().stream()
                .map(usuarioNegocio -> usuarioNegocio.getNegocio().getNombre())  // Nombres de los negocios
                .collect(Collectors.toSet());
        responseDTO.setNegocios(negocios);  // Asignar nombres de negocios

        return ResponseEntity.ok(responseDTO);
    }*/

}


