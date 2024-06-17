package com.sparta.lck_news.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.sparta.lck_news.dto.PostCreateRequest;
import com.sparta.lck_news.dto.PostResponse;
import com.sparta.lck_news.entity.Post;
import com.sparta.lck_news.service.PostService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@WebMvcTest(PostController.class)
public class PostControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private PostService postService;

    @Test
    public void testGetAllPosts() throws Exception {
        // Given
        List<Post> posts = new ArrayList<>();
        posts.add(new Post(1L, "Test content 1"));
        posts.add(new Post(2L, "Test content 2"));
        when(postService.getAllPost()).thenReturn(posts);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts")
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.size()").value(posts.size()));
    }

    @Test
    public void testGetPostById() throws Exception {
        // Given
        Long postId = 1L;
        PostResponse postResponse = new PostResponse(postId, "Test content", null, null);
        when(postService.getPostById(postId)).thenReturn(postResponse);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").value(postId));
    }

    @Test
    public void testGetPostByIdNotFound() throws Exception {
        // Given
        Long postId = 999L; // Non-existent ID
        when(postService.getPostById(postId)).thenReturn(null);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.get("/api/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNotFound());
    }

    @Test
    public void testCreatePost() throws Exception {
        // Given
        PostCreateRequest requestDto = new PostCreateRequest("Test content");
        Post createdPost = new Post(1L, requestDto.getContent());
        when(postService.createPost(any(PostCreateRequest.class))).thenReturn(createdPost);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.post("/api/posts")
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isCreated())
                .andExpect(MockMvcResultMatchers.jsonPath("$.id").exists());
    }

    @Test
    public void testUpdatePost() throws Exception {
        // Given
        Long postId = 1L;
        PostCreateRequest requestDto = new PostCreateRequest("Updated content");
        Post updatedPost = new Post(postId, requestDto.getContent());
        when(postService.updatePost(postId, requestDto)).thenReturn(updatedPost);

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.put("/api/posts/{id}", postId)
                        .content(objectMapper.writeValueAsString(requestDto))
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
                .andExpect(MockMvcResultMatchers.jsonPath("$.content").value(requestDto.getContent()));
    }

    @Test
    public void testDeletePost() throws Exception {
        // Given
        Long postId = 1L;

        // When/Then
        mockMvc.perform(MockMvcRequestBuilders.delete("/api/posts/{id}", postId)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isNoContent());
    }
}
