package com.sparta.lck_news.dto;

import com.sparta.lck_news.entity.Post;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PostResponseTest {

    @Test
    public void testPostResponseConstructor() {
        // Given
        Long id = 1L;
        String content = "Test content";
        LocalDateTime createdAt = LocalDateTime.of(2024, 6, 16, 12, 0);
        LocalDateTime updatedAt = LocalDateTime.of(2024, 6, 16, 13, 0);

        Post post = new Post(id, content, createdAt, updatedAt);

        // When
        PostResponse postResponse = new PostResponse(post);

        // Then
        assertEquals(id, postResponse.getId());
        assertEquals(content, postResponse.getContent());
        assertEquals(createdAt, postResponse.getCreatedAt());
        assertNotNull(postResponse.getUpdatedAt());
    }

    @Test
    public void testBuilder() {
        // Given
        Long id = 1L;
        String content = "Test content";
        LocalDateTime createdAt = LocalDateTime.of(2024, 6, 16, 12, 0);
        LocalDateTime updatedAt = LocalDateTime.of(2024, 6, 16, 13, 0);

        // When
        PostResponse postResponse = PostResponse.builder()
                .id(id)
                .content(content)
                .createdAt(createdAt)
                .updatedAt(updatedAt)
                .build();

        // Then
        assertEquals(id, postResponse.getId());
        assertEquals(content, postResponse.getContent());
        assertEquals(createdAt, postResponse.getCreatedAt());
        assertEquals(updatedAt, postResponse.getUpdatedAt());
    }
}
