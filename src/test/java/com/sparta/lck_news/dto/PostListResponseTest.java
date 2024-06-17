package com.sparta.lck_news.dto;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PostListResponseTest {

    @Test
    public void testConstructorAndGetters() {
        // Given
        String name = "Robbie";
        String content = "Test content";
        LocalDateTime createdAt = LocalDateTime.of(2024, 6, 16, 12, 0);
        LocalDateTime updatedAt = LocalDateTime.of(2024, 6, 16, 13, 0);

        // When
        PostListResponse response = new PostListResponse(name, content, createdAt, updatedAt);

        // Then
        assertNotNull(response);
        assertEquals(name, response.getName());
        assertEquals(content, response.getContent());
        assertEquals(createdAt, response.getCreatedAt());
        assertEquals(updatedAt, response.getUpdatedAt());
    }

    @Test
    public void testNoArgsConstructor() {
        // When
        PostListResponse response = new PostListResponse();

        // Then
        assertNotNull(response);
        // Since no setters are provided, fields should be null or default values
        assertEquals(null, response.getName());
        assertEquals(null, response.getContent());
        assertEquals(null, response.getCreatedAt());
        assertEquals(null, response.getUpdatedAt());
    }

    @Test
    public void testAllArgsConstructor() {
        // Given
        String name = "Jane Doe";
        String content = "Another test content";
        LocalDateTime createdAt = LocalDateTime.of(2024, 6, 16, 10, 0);
        LocalDateTime updatedAt = LocalDateTime.of(2024, 6, 16, 11, 0);

        // When
        PostListResponse response = new PostListResponse(name, content, createdAt, updatedAt);

        // Then
        assertNotNull(response);
        assertEquals(name, response.getName());
        assertEquals(content, response.getContent());
        assertEquals(createdAt, response.getCreatedAt());
        assertEquals(updatedAt, response.getUpdatedAt());
    }
}
