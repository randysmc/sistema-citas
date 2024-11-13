package com.sistema.examenes.sistema_examenes_backend.servicios.implementacion;


import com.sistema.examenes.sistema_examenes_backend.servicios.CorreoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class CorreoServiceImpl implements CorreoService {

    @Autowired
    private JavaMailSender javaMailSender;

    @Override
    public void enviarCorreo(String destinatario, String asunto, String cuerpo) throws MessagingException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);

        helper.setFrom("tu_correo@gmail.com");
        helper.setTo(destinatario);
        helper.setSubject(asunto);
        helper.setText(cuerpo, true); // true para enviar HTML

        javaMailSender.send(mimeMessage);
    }

    @Override
    public void enviarCodigoValidacion(String correo, String codigo) throws MessagingException {
        String asunto = "Código de validación";
        String cuerpo = "<h1>Tu código de validación es: " + codigo + "</h1>";

        enviarCorreo(correo, asunto, cuerpo);
    }

    @Override
    public void enviarRecuperacionContraseña(String correo, String codigo) throws MessagingException {
        String asunto = "Recuperación de Contraseña";
        String cuerpo = "<h1>Tu código de recuperación es: " + codigo + "</h1>";

        enviarCorreo(correo, asunto, cuerpo);
    }

    @Override
    public void enviarCodigo2FA(String correo, String codigo) throws MessagingException {
        String asunto = "Código de autenticación 2FA";
        String cuerpo = "<h1>Tu código de autenticación es: " + codigo + "</h1>";

        enviarCorreo(correo, asunto, cuerpo);
    }
}
