package com.sistema.examenes.sistema_examenes_backend.controladores;

import com.sistema.examenes.sistema_examenes_backend.entidades.*;
import com.sistema.examenes.sistema_examenes_backend.configuraciones.JwtUtils;
import com.sistema.examenes.sistema_examenes_backend.repositorios.UsuarioRepository;
import com.sistema.examenes.sistema_examenes_backend.servicios.implementacion.CorreoServiceImpl;
import com.sistema.examenes.sistema_examenes_backend.servicios.implementacion.UserDetailsServiceImplementacion;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin("*")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserDetailsServiceImplementacion userDetailsService;

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private CorreoServiceImpl correoService;

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;


    private final Map<String, String> twoFactorCodes = new HashMap<>();


    @PostMapping("/generate-token")
    public ResponseEntity<?> generarToken(@RequestBody JwtRequest jwtRequest) throws Exception {
        try {
            autenticar(jwtRequest.getUsername(), jwtRequest.getPassword());
        } catch (Exception exception) {
            exception.printStackTrace();
            throw new Exception("Usuario no encontrado o credenciales inválidas");
        }

        Usuario usuario = usuarioRepository.findByUsername(jwtRequest.getUsername());
        if (usuario == null) {
            throw new Exception("Usuario no encontrado.");
        }

        if (usuario.isTfa()) {
            String email = usuario.getEmail();
            if (email == null || email.isEmpty()) {
                throw new Exception("El usuario no tiene un correo registrado para 2FA.");
            }

            String twoFactorCode = generateTwoFactorCode();
            sendTwoFactorCode(email, twoFactorCode);

            twoFactorCodes.put(jwtRequest.getUsername(), twoFactorCode);
            System.out.println("Codigo de autenticación: " + twoFactorCode);
            return ResponseEntity.ok(Collections.singletonMap("message", "Código de autenticación enviado. Por favor, valida el código."));
        } else {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(jwtRequest.getUsername());
            String token = this.jwtUtils.generateToken(userDetails);

            return ResponseEntity.ok(new JwtResponse(token));
        }
    }




    @PostMapping("/validate-token")
    public ResponseEntity<?> validateToken(@RequestBody TwoFactorRequest twoFactorRequest) throws Exception {
        String storedCode = twoFactorCodes.get(twoFactorRequest.getUsername());

        if (storedCode != null && storedCode.equals(twoFactorRequest.getTwoFactorCode())) {
            UserDetails userDetails = this.userDetailsService.loadUserByUsername(twoFactorRequest.getUsername());
            String token = this.jwtUtils.generateToken(userDetails);

            twoFactorCodes.remove(twoFactorRequest.getUsername());

            return ResponseEntity.ok(new JwtResponse(token));
        } else {
            throw new Exception("Código de autenticación inválido");
        }
    }


    @PostMapping("/forgot-password")
    public ResponseEntity<?> forgotPassword(@RequestBody ResetPasswordRequest resetPasswordRequest) {
        String email = resetPasswordRequest.getEmail();
        System.out.println("Solicitud de restablecimiento de contraseña recibida para el correo: " + email);

        email = email.trim();

        Usuario usuario = usuarioRepository.findByEmail(email);

        if (usuario == null) {
            System.out.println("Usuario no encontrado con el correo: " + email);
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado con el correo proporcionado.");
        }
        String resetCode = generateTwoFactorCode();
        twoFactorCodes.put(email, resetCode);

        // Mostrar el código en consola para verificar
        System.out.println("Código de recuperación generado: " + resetCode);

        //enviar el codigo
        sendTwoFactorCode(email, resetCode);
        // Devolver el código en la respuesta
        return ResponseEntity.ok(Collections.singletonMap("Codigo generado exitosamente", resetCode));

    }



    @PostMapping("/reset-password")
    public ResponseEntity<?> resetPassword(@RequestBody ResetPasswordRequest request) {
        String storedCode = twoFactorCodes.get(request.getEmail());

        if (storedCode == null || !storedCode.equals(request.getResetCode())) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Código de recuperación inválido o expirado.");
        }

        // Actualizar la contraseña del usuario
        Usuario usuario = usuarioRepository.findByEmail(request.getEmail());
        if (usuario == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Usuario no encontrado.");
        }

        // Asegúrate de encriptar la contraseña antes de guardarla
        usuario.setPassword(bCryptPasswordEncoder.encode(request.getNewPassword()));

        usuarioRepository.save(usuario);

        // Eliminar el código una vez usado
        twoFactorCodes.remove(request.getEmail());

        return ResponseEntity.ok(Collections.singletonMap("message", "Contraseña cambiada exitosamente."));
    }




    private String generateTwoFactorCode() {
        // Genera un código aleatorio de 6 dígitos
        return String.valueOf((int) (Math.random() * 900000) + 100000);
    }

    private void sendTwoFactorCode(String email, String twoFactorCode) {
        try {

            correoService.enviarCorreo(email, "Código de autenticación",
                    "Tu código de autenticación es: " + twoFactorCode);
            System.out.println("Código 2FA enviado al correo: " + email);
        } catch (javax.mail.MessagingException e) {
            System.err.println("Error al enviar el correo: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("No se pudo enviar el código de autenticación por correo.", e);
        }
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

}


