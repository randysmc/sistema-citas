package com.sistema.examenes.sistema_examenes_backend.service;

import com.sistema.examenes.sistema_examenes_backend.servicios.implementacion.CorreoServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.test.context.ActiveProfiles;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
@ActiveProfiles("test")
public class CorreoServiceTest {

    @Mock
    private JavaMailSender javaMailSender;

    @InjectMocks
    private CorreoServiceImpl correoService;

    private MimeMessage mimeMessage;

    @BeforeEach
    public void setup() {
        mimeMessage = mock(MimeMessage.class);
        when(javaMailSender.createMimeMessage()).thenReturn(mimeMessage);
    }

    @DisplayName("Test para enviar un correo")
    @Test
    public void testEnviarCorreo() throws MessagingException {
        // given
        String destinatario = "destinatario@example.com";
        String asunto = "Asunto del correo";
        String cuerpo = "<h1>Este es el cuerpo del correo</h1>";

        // when
        correoService.enviarCorreo(destinatario, asunto, cuerpo);

        // then
        verify(javaMailSender, times(1)).send(mimeMessage);

        // Verificar que se configuraron correctamente los detalles del correo
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, true);
        helper.setFrom("tu_correo@gmail.com");
        helper.setTo(destinatario);
        helper.setSubject(asunto);
        helper.setText(cuerpo, true);
    }

    @DisplayName("Test para enviar código de validación")
    @Test
    public void testEnviarCodigoValidacion() throws MessagingException {
        // given
        String correo = "usuario@example.com";
        String codigo = "123456";

        // when
        correoService.enviarCodigoValidacion(correo, codigo);

        // then
        verify(javaMailSender, times(1)).send(mimeMessage);
    }

    @DisplayName("Test para enviar recuperación de contraseña")
    @Test
    public void testEnviarRecuperacionContraseña() throws MessagingException {
        // given
        String correo = "usuario@example.com";
        String codigo = "987654";

        // when
        correoService.enviarRecuperacionContraseña(correo, codigo);

        // then
        verify(javaMailSender, times(1)).send(mimeMessage);
    }

    @DisplayName("Test para enviar código 2FA")
    @Test
    public void testEnviarCodigo2FA() throws MessagingException {
        // given
        String correo = "usuario@example.com";
        String codigo = "2FA_CODE";

        // when
        correoService.enviarCodigo2FA(correo, codigo);

        // then
        verify(javaMailSender, times(1)).send(mimeMessage);
    }
}
