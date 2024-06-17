package com.sparta.lck_news.service;

import com.sparta.lck_news.dto.SignupRequestDto;
import com.sparta.lck_news.entity.User;
import com.sparta.lck_news.exception.CommonException;
import com.sparta.lck_news.exception.ErrorStatus;
import com.sparta.lck_news.jwt.JwtUtil;
import com.sparta.lck_news.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class UserServiceTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtUtil jwtUtil;

    @InjectMocks
    private UserService userService;

    private SignupRequestDto signupRequestDto;

    @BeforeEach
    public void setUp() {
        signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUsername("User01");
        signupRequestDto.setPassword("Pass123456!");
    }

    @Test
    public void testSignup_Success() {
        // Given
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());
        when(passwordEncoder.encode(any(String.class))).thenReturn("encodedPassword");

        // When
        userService.signup(signupRequestDto);

        // Then
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testSignup_DuplicateUser() {
        // Given
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(new User()));

        // When / Then
        CommonException exception = assertThrows(CommonException.class, () -> {
            userService.signup(signupRequestDto);
        });

        assertEquals(ErrorStatus.DUPLICATE_USER.getStatus(), exception.getStatus());
    }

    @Test
    public void testSignup_InvalidUsername() {
        // Given
        signupRequestDto.setUsername("short");

        // When / Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.signup(signupRequestDto);
        });

        assertEquals("대소문자 포함 영문 + 숫자, 10자 이상 20자 이하로 다시 입력해주세요", exception.getMessage());
    }

    @Test
    public void testSignup_InvalidPassword() {
        // Given
        signupRequestDto.setPassword("weakpass");

        // When / Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.signup(signupRequestDto);
        });

        assertEquals("대소문자 포함 영문 + 숫자 + 특수문자 최소 1글자씩 포함, 최소 10글자 이상으로 다시 입력해주세요", exception.getMessage());
    }

    @Test
    public void testLogout_Success() {
        // Given
        User user = new User();
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));

        // When
        userService.logout("User01");

        // Then
        verify(userRepository, times(1)).save(any(User.class));
    }

    @Test
    public void testLogout_UserNotFound() {
        // Given
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

        // When / Then
        CommonException exception = assertThrows(CommonException.class, () -> {
            userService.logout("InvalidUser");
        });

        assertEquals(ErrorStatus.ID_NOT_FOUND.getStatus(), exception.getStatus());
    }

    @Test
    public void testIsValidUsername_Valid() {
        // Given
        String validUsername = "User01";

        // When / Then
        assertDoesNotThrow(() -> userService.isValidUsername(validUsername));
    }

    @Test
    public void testIsValidUsername_Invalid() {
        // Given
        String invalidUsername = "short";

        // When / Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.isValidUsername(invalidUsername);
        });

        assertEquals("대소문자 포함 영문 + 숫자, 10자 이상 20자 이하로 다시 입력해주세요", exception.getMessage());
    }

    @Test
    public void testIsValidPassword_Valid() {
        // Given
        String validPassword = "Pass123456!";

        // When / Then
        assertDoesNotThrow(() -> userService.isValidPassword(validPassword));
    }

    @Test
    public void testIsValidPassword_Invalid() {
        // Given
        String invalidPassword = "weakpass";

        // When / Then
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            userService.isValidPassword(invalidPassword);
        });

        assertEquals("대소문자 포함 영문 + 숫자 + 특수문자 최소 1글자씩 포함, 최소 10글자 이상으로 다시 입력해주세요", exception.getMessage());
    }
}
