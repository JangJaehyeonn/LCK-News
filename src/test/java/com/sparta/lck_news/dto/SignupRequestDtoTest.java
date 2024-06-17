package com.sparta.lck_news.dto;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.Validation;
import jakarta.validation.Validator;
import jakarta.validation.ValidatorFactory;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class SignupRequestDtoTest {

    private Validator validator;

    @BeforeEach
    public void setUp() {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        validator = factory.getValidator();
    }

    @Test
    public void testSignupRequestDtoValidation() {
        // Given
        SignupRequestDto dto = new SignupRequestDto();
        dto.setUsername("testUser");
        dto.setPassword("testPassword");

        // When
        Set<ConstraintViolation<SignupRequestDto>> violations = validator.validate(dto);

        // Then
        assertTrue(violations.isEmpty(), "Validation should pass with correct input");

        // Additional test case: Test for username being blank
        dto.setUsername("");
        violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Username should not be blank");
        assertEquals(1, violations.size(), "There should be exactly 1 violation for username");

        // Additional test case: Test for password being blank
        dto.setUsername("testUser"); // Reset username
        dto.setPassword("");
        violations = validator.validate(dto);
        assertFalse(violations.isEmpty(), "Password should not be blank");
        assertEquals(1, violations.size(), "There should be exactly 1 violation for password");
    }
}
