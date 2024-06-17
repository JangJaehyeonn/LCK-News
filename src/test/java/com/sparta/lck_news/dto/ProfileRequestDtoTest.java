package com.sparta.lck_news.dto;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProfileRequestDtoTest {

    @Test
    public void testNoArgsConstructor() {
        // When
        ProfileRequestDto dto = new ProfileRequestDto();

        // Then
        assertNotNull(dto);
        assertEquals(null, dto.getIntroduction());
        assertEquals(null, dto.getName());
        assertEquals(null, dto.getEmail());
        assertEquals(null, dto.getPassword());
        assertEquals(null, dto.getNewPassword());
        assertEquals(null, dto.getChangeChecked());
    }

    @Test
    public void testFieldsWithArgsConstructor() {
        // Given
        String introduction = "Introduction";
        String name = "robbie";
        String email = "robbie@naver.com";
        String password = "currentPassword";
        Boolean changeChecked = true;
        String newPassword = "newPassword";

        // When
        ProfileRequestDto dto = new ProfileRequestDto(introduction, name, email, password, changeChecked, newPassword);

        // Then
        assertNotNull(dto);
        assertEquals(introduction, dto.getIntroduction());
        assertEquals(name, dto.getName());
        assertEquals(email, dto.getEmail());
        assertEquals(password, dto.getPassword());
        assertEquals(changeChecked, dto.getChangeChecked());
        assertEquals(newPassword, dto.getNewPassword());
    }
}
