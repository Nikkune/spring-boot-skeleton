package com.nnk.springboot.security;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class SecurityConfigTest {

    @Test
    public void testPasswordEncoder() {
        // When
        PasswordEncoder passwordEncoder = SecurityConfig.passwordEncoder();

        // Then
        assertNotNull(passwordEncoder);
        assertTrue(passwordEncoder.matches("password", passwordEncoder.encode("password")));
    }

    @Test
    public void testBCryptPasswordEncoderImplementation() {
        // When
        PasswordEncoder encoder = SecurityConfig.passwordEncoder();

        // Then
        assertTrue(encoder instanceof BCryptPasswordEncoder);
    }

    @Test
    public void testPasswordEncoderMatching() {
        // Given
        PasswordEncoder encoder = SecurityConfig.passwordEncoder();
        String rawPassword = "Password1!";

        // When
        String encodedPassword = encoder.encode(rawPassword);

        // Then
        assertTrue(encoder.matches(rawPassword, encodedPassword));
    }
}
