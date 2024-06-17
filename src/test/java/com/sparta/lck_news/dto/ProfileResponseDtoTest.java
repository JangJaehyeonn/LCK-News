package com.sparta.lck_news.dto;

import com.sparta.lck_news.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class ProfileResponseDtoTest {

    @Test
    public void testConstructorWithUser() {
        // Given
        String username = "TestUser";
        String name = "Robbie";
        String email = "Robbie@naver.com";
        String introduction = "소개";
        User user = new User();
        user.setUsername(username);
        user.setName(name);
        user.setEmail(email);
        user.setIntro(introduction);

        // When
        ProfileResponseDto dto = new ProfileResponseDto(user);

        // Then
        assertNotNull(dto);
        assertEquals(username, dto.getUsername());
        assertEquals(name, dto.getName());
        assertEquals(email, dto.getEmail());
        assertEquals(introduction, dto.getIntroduction());
    }
}
