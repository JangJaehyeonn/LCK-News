package com.sparta.lck_news.entity;

import com.sparta.lck_news.dto.ProfileRequestDto;
import com.sparta.lck_news.dto.SignupRequestDto;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

public class UserTest {

    @Test
    public void testUserConstructorAndGetters() {

        // Given (SignupRequestDto 객체를 생성하고 이름과 비밀번호를 설정, 인코딩된 비밀번호 문자열 준비)
        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUsername("testUser");
        signupRequestDto.setPassword("testPassword");
        String encodedPassword = "encodedTestPassword";

        // When (SignupRequestDto와 인코딩된 비밀번호를 사용하여 'User'객체를 생성)
        User user = new User(signupRequestDto, encodedPassword);

        // Then (User의 초기 값들을 검증, id는 null이어야 하고 나머지 필드들은 주어진 값이나 기본값 이어야 함)
        assertNull(user.getId());
        assertEquals("testUser", user.getUsername());
        assertEquals(encodedPassword, user.getPassword());
        assertEquals("not update profile", user.getName());
        assertEquals("not update profile", user.getIntro());
        assertEquals(UserStatus.ACTIVE, user.getStatus());
    }

    @Test
    public void testUpdateStatus() {
        // Given
        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUsername("testUser");
        signupRequestDto.setPassword("testPassword");
        User user = new User(signupRequestDto, "encodedTestPassword");

        // When
        user.update();

        // Then
        assertEquals(UserStatus.DEACTIVATED, user.getStatus());
    }

    @Test
    public void testUpdateProfile() {
        // Given
        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUsername("testUser");
        signupRequestDto.setPassword("testPassword");
        User user = new User(signupRequestDto, "encodedTestPassword");

        ProfileRequestDto profileRequestDto = new ProfileRequestDto();
        profileRequestDto.setName("newName");
        profileRequestDto.setEmail("newEmail");
        profileRequestDto.setIntroduction("newIntroduction");

        // When
        user.update(profileRequestDto, "newEncodedPassword", true);

        // Then
        assertEquals("newName", user.getName());
        assertEquals("newEmail", user.getEmail());
        assertEquals("newIntroduction", user.getIntro());
        assertEquals("newEncodedPassword", user.getPassword());
    }

    @Test
    public void testUpdateProfileWithoutPasswordChange() {
        // Given
        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUsername("testUser");
        signupRequestDto.setPassword("testPassword");
        User user = new User(signupRequestDto, "encodedTestPassword");

        ProfileRequestDto profileRequestDto = new ProfileRequestDto();
        profileRequestDto.setName("newName");
        profileRequestDto.setEmail("newEmail");
        profileRequestDto.setIntroduction("newIntroduction");

        // When
        user.update(profileRequestDto, "newEncodedPassword", false);

        // Then
        assertEquals("newName", user.getName());
        assertEquals("newEmail", user.getEmail());
        assertEquals("newIntroduction", user.getIntro());
        assertEquals("encodedTestPassword", user.getPassword()); // Password should not change
    }
}
