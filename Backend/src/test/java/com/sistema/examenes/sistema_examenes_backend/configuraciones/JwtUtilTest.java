package com.sistema.examenes.sistema_examenes_backend.configuraciones;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;



public class JwtUtilTest {
    private JwtUtils jwtUtils;

    @Mock
    private UserDetails userDetails;

    private String testToken;
    private final String secretKey = "secret";
    private final String username = "testuser";

    @BeforeEach
    void setUp() {
        jwtUtils = new JwtUtils();
        userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn(username);

        // Creamos un token de prueba
        testToken = jwtUtils.generateToken(userDetails);
    }

    @Test
    void extractUsername_ShouldReturnCorrectUsername() {
        String extractedUsername = jwtUtils.extractUsername(testToken);
        assertEquals(username, extractedUsername, "El username extraído debe coincidir");
    }

    @Test
    void extractExpiration_ShouldReturnExpirationDate() {
        Date expirationDate = jwtUtils.extractExpiration(testToken);
        assertNotNull(expirationDate, "La fecha de expiración no debe ser nula");
    }

    /*@Test
    void isTokenExpired_ShouldReturnTrueWhenTokenIsExpired() {
        // Usamos una fecha de expiración pasada para la prueba
        String expiredToken = Jwts.builder()
                .setSubject(username)
                .setIssuedAt(new Date(System.currentTimeMillis() - 1000 * 60 * 60)) // 1 hora atrás
                .setExpiration(new Date(System.currentTimeMillis() - 1000 * 60 * 30)) // 30 minutos atrás
                .signWith(io.jsonwebtoken.SignatureAlgorithm.HS256, secretKey)
                .compact();

        Boolean isExpired = jwtUtils.isTokenExpired(expiredToken);
        assertTrue(isExpired, "El token debería estar expirado");
    }*/

    @Test
    void isTokenExpired_ShouldReturnFalseWhenTokenIsNotExpired() {
        String notExpiredToken = jwtUtils.generateToken(userDetails);
        Boolean isExpired = jwtUtils.isTokenExpired(notExpiredToken);
        assertFalse(isExpired, "El token no debería estar expirado");
    }

    @Test
    void validateToken_ShouldReturnTrueWhenTokenIsValid() {
        Boolean isValid = jwtUtils.validateToken(testToken, userDetails);
        assertTrue(isValid, "El token debería ser válido");
    }

    @Test
    void validateToken_ShouldReturnFalseWhenTokenIsInvalid() {
        UserDetails fakeUser = mock(UserDetails.class);
        when(fakeUser.getUsername()).thenReturn("fakeuser");

        Boolean isValid = jwtUtils.validateToken(testToken, fakeUser);
        assertFalse(isValid, "El token no debería ser válido para un usuario falso");
    }
}
