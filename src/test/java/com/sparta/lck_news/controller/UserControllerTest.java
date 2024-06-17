package com.sparta.lck_news.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.lck_news.dto.SignupRequestDto;
import com.sparta.lck_news.service.UserService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doNothing;

@WebMvcTest(UserController.class)   // UserController에 대한 WebMvcTest 설정
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc; // MockMvc 객체 주입

    @Autowired
    private ObjectMapper objectMapper; // ObjectMapper 객체 주입

    @MockBean
    private UserService userService; // MockBean으로 UserService 객체 주입

    @Test
    public void testSignup() throws Exception {

        // Given (테스트에 사용할 SignupRequestDto 생성)
        SignupRequestDto signupRequestDto = new SignupRequestDto();
        signupRequestDto.setUsername("testUser");
        signupRequestDto.setPassword("testPassword");

        // Mock 설정 (userService.signup 메서드 호출 시 아무 일도 하지 않도록 설정)
        doNothing().when(userService).signup(any(SignupRequestDto.class));

        // When/Then (회원가입 API 호출 및 검증)
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/signup")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(signupRequestDto)))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("회원가입 성공"));
    }

    @Test
    @WithMockUser(username = "testUser", roles = "USER") // Mock 유저 설정
    public void testLogout() throws Exception {
        // Given ( Mock 설정 - userService.logout 메서드 호출 시 아무 일도 하지 않도록 설정)
        doNothing().when(userService).logout(any(String.class));

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/users/logout")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.content().string("로그아웃 성공"));
    }
}
