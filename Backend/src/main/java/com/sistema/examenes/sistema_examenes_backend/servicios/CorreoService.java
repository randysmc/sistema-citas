package com.sistema.examenes.sistema_examenes_backend.servicios;

import javax.mail.MessagingException;

public interface CorreoService {
    void enviarCorreo(String destinatario, String asunto, String cuerpo) throws MessagingException;

    void enviarCodigoValidacion(String correo, String codigo) throws MessagingException;

    void enviarRecuperacionContraseña(String correo, String codigo) throws MessagingException;

    void enviarCodigo2FA(String correo, String codigo) throws MessagingException;  // Método para el código 2FA


}
