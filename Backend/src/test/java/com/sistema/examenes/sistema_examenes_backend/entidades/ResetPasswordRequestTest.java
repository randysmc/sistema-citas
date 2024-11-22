package com.sistema.examenes.sistema_examenes_backend.entidades;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class ResetPasswordRequestTest {

    @Test
    void testSettersAndGetters() {
        // given
        ResetPasswordRequest resetPasswordRequest = new ResetPasswordRequest();
        String email = "test@example.com";
        String resetCode = "123456";
        String newPassword = "newSecurePassword";

        // when
        resetPasswordRequest.setEmail(email);
        resetPasswordRequest.setResetCode(resetCode);
        resetPasswordRequest.setNewPassword(newPassword);

        // then
        assertEquals(email, resetPasswordRequest.getEmail(), "Email should match the value set");
        assertEquals(resetCode, resetPasswordRequest.getResetCode(), "Reset code should match the value set");
        assertEquals(newPassword, resetPasswordRequest.getNewPassword(), "New password should match the value set");
    }
}
