package com.sparta.lck_news.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class LoginRequestDtoTest {
    @Test
    public void testGettersAndSetters() {
        // Given
        String username = "testUser";
        String password = "testPassword";

        // When
        LoginRequestDto loginRequestDto = new LoginRequestDto();
        loginRequestDto.setUsername(username);
        loginRequestDto.setPassword(password);

        // Then
        assertEquals(username, loginRequestDto.getUsername());
        assertEquals(password, loginRequestDto.getPassword());
    }
}