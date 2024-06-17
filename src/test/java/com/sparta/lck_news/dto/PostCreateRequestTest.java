package com.sparta.lck_news.dto;

import com.sparta.lck_news.entity.Post;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class PostCreateRequestTest {

    @Test
    public void testToEntity() {
        // Given
        String content = "Test content";
        PostCreateRequest request = new PostCreateRequest();
        request.setContent(content);

        // When
        Post post = request.toEntity();

        // Then
        assertNotNull(post);
        assertEquals(content, post.getContent());
    }
}
