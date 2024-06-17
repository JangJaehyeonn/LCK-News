package com.sparta.lck_news.service;

import com.sparta.lck_news.dto.DeactivateRequestDto;
import com.sparta.lck_news.dto.ProfileRequestDto;
import com.sparta.lck_news.dto.ProfileResponseDto;
import com.sparta.lck_news.entity.User;
import com.sparta.lck_news.entity.UserStatus;
import com.sparta.lck_news.exception.CommonException;
import com.sparta.lck_news.exception.ErrorStatus;
import com.sparta.lck_news.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static com.sparta.lck_news.entity.UserStatus.DEACTIVATED;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class ProfileServiceTest {

    @Mock
    private UserService userService;

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private ProfileService profileService;

    private User user;

    @BeforeEach
    public void setUp() {
        user = new User();
        user.setUsername("testUser");
        user.setPassword("encodedPassword");
        user.setStatus(UserStatus.ACTIVE);
    }

    @Test
    public void testGetProfile() {
        // Given
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));

        // When
        ProfileResponseDto responseDto = profileService.getProfile(user);

        // Then
        assertNotNull(responseDto);
        assertEquals("testUser", responseDto.getUsername());
    }

    @Test
    public void testGetProfile_UserDeactivated() {
        // Given
        user.setStatus(DEACTIVATED);
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));

        // When / Then
        CommonException exception = assertThrows(CommonException.class, () -> {
            profileService.getProfile(user);
        });

        assertEquals(ErrorStatus.ACCOUNT_DISABLED.getStatus(), exception.getStatus());
    }

    @Test
    public void testEditProfile() {
        // Given
        ProfileRequestDto requestDto = new ProfileRequestDto();
        requestDto.setPassword("currentPassword");
        requestDto.setNewPassword("newPassword");
        requestDto.setChangeChecked(true);
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(true);
        when(passwordEncoder.encode(any(String.class))).thenReturn("newEncodedPassword");

        // When
        ProfileResponseDto responseDto = profileService.editProfile(requestDto, user);

        // Then
        assertNotNull(responseDto);
        verify(passwordEncoder, times(1)).encode("newPassword");
    }

    @Test
    public void testEditProfile_InvalidCurrentPassword() {
        // Given
        ProfileRequestDto requestDto = new ProfileRequestDto();
        requestDto.setPassword("wrongPassword");
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(false);

        // When / Then
        CommonException exception = assertThrows(CommonException.class, () -> {
            profileService.editProfile(requestDto, user);
        });

        assertEquals(ErrorStatus.PASSWORD_MISMATCH.getStatus(), exception.getStatus());
    }

    @Test
    public void testDeactivateUser() {
        // Given
        DeactivateRequestDto requestDto = new DeactivateRequestDto();
        requestDto.setPassword("currentPassword");
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(true);

        // When
        profileService.deactivateUser(requestDto, user);

        // Then
        assertEquals(DEACTIVATED, user.getStatus());
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void testDeactivateUser_InvalidCurrentPassword() {
        // Given
        DeactivateRequestDto requestDto = new DeactivateRequestDto();
        requestDto.setPassword("wrongPassword");
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.of(user));
        when(passwordEncoder.matches(any(String.class), any(String.class))).thenReturn(false);

        // When / Then
        CommonException exception = assertThrows(CommonException.class, () -> {
            profileService.deactivateUser(requestDto, user);
        });

        assertEquals(ErrorStatus.PASSWORD_MISMATCH.getStatus(), exception.getStatus());
    }

    @Test
    public void testFindUserByUsername_NotFound() {
        // Given
        when(userRepository.findByUsername(any(String.class))).thenReturn(Optional.empty());

        // When / Then
        CommonException exception = assertThrows(CommonException.class, () -> {
            profileService.getProfile(user);
        });

        assertEquals(ErrorStatus.ID_NOT_FOUND.getStatus(), exception.getStatus());
    }
}
