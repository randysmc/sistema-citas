package com.sistema.examenes.sistema_examenes_backend.entidades;

import org.junit.jupiter.api.Test;
import org.springframework.test.context.ActiveProfiles;

import static org.junit.jupiter.api.Assertions.*;

@ActiveProfiles("test")
class JwtRequestTest {

    @Test
    void testEmptyConstructor() {
        // Arrange & Act
        JwtRequest jwtRequest = new JwtRequest();

        // Assert
        assertNull(jwtRequest.getUsername(), "Username should be null by default");
        assertNull(jwtRequest.getPassword(), "Password should be null by default");
    }

    @Test
    void testParameterizedConstructor() {
        // Arrange
        String username = "testUser";
        String password = "testPassword";

        // Act
        JwtRequest jwtRequest = new JwtRequest(username, password);

        // Assert
        assertEquals(username, jwtRequest.getUsername(), "Username should match the value passed to constructor");
        assertEquals(password, jwtRequest.getPassword(), "Password should match the value passed to constructor");
    }

    @Test
    void testSettersAndGetters() {
        // Arrange
        JwtRequest jwtRequest = new JwtRequest();
        String username = "newUser";
        String password = "newPassword";

        // Act
        jwtRequest.setUsername(username);
        jwtRequest.setPassword(password);

        // Assert
        assertEquals(username, jwtRequest.getUsername(), "Username should match the value set");
        assertEquals(password, jwtRequest.getPassword(), "Password should match the value set");
    }
}
