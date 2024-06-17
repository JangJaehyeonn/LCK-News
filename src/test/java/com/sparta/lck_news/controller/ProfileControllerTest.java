package com.sparta.lck_news.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.lck_news.dto.DeactivateRequestDto;
import com.sparta.lck_news.dto.ProfileRequestDto;
import com.sparta.lck_news.dto.ProfileResponseDto;
import com.sparta.lck_news.entity.User;
import com.sparta.lck_news.security.UserDetailsImpl;
import com.sparta.lck_news.service.ProfileService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;

@WebMvcTest(ProfileController.class)
public class ProfileControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private ProfileService profileService;

    private UserDetailsImpl userDetails;

    @BeforeEach
    public void setUp() {
        User user = new User();
        user.setUsername("testUser");
        userDetails = new UserDetailsImpl(user);
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testGetProfile() throws Exception {
        // Given
        ProfileResponseDto responseDto = new ProfileResponseDto();
        responseDto.setUsername("testUser");
        when(profileService.getProfile(any(User.class))).thenReturn(responseDto);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/users/profile")
                        .principal(() -> userDetails.getUsername())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.username").value("testUser"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testEditProfile() throws Exception {
        // Given
        ProfileRequestDto requestDto = new ProfileRequestDto();
        requestDto.setName("Updated Name");

        ProfileResponseDto responseDto = new ProfileResponseDto();
        responseDto.setUsername("testUser");
        responseDto.setName("Updated Name");

        when(profileService.editProfile(any(ProfileRequestDto.class), any(User.class))).thenReturn(responseDto);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/users/profile/edit")
                        .content(objectMapper.writeValueAsString(requestDto))
                        .principal(() -> userDetails.getUsername())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.name").value("Updated Name"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER")
    public void testDeactivateUser() throws Exception {
        // Given
        DeactivateRequestDto requestDto = new DeactivateRequestDto();
        doNothing().when(profileService).deactivateUser(any(DeactivateRequestDto.class), any(User.class));

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.patch("/api/users/profile/deactivate")
                        .content(objectMapper.writeValueAsString(requestDto))
                        .principal(() -> userDetails.getUsername())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk());
    }
}
