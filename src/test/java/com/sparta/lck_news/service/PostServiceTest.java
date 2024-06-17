package com.sparta.lck_news.service;

import com.sparta.lck_news.dto.PostCreateRequest;
import com.sparta.lck_news.dto.PostResponse;
import com.sparta.lck_news.entity.Post;
import com.sparta.lck_news.exception.CommonException;
import com.sparta.lck_news.exception.ErrorStatus;
import com.sparta.lck_news.repository.PostRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class PostServiceTest {

    @Mock
    private PostRepository postRepository;

    @InjectMocks
    private PostService postService;

    private Post post;

    @BeforeEach
    public void setUp() {
        post = new Post("Test content");
    }

    @Test
    public void testGetAllPost() {
        // Given
        List<Post> posts = Arrays.asList(post);
        when(postRepository.findAll()).thenReturn(posts);

        // When
        List<Post> result = postService.getAllPost();

        // Then
        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("Test content", result.get(0).getContent());
    }

    @Test
    public void testGetPostById() {
        // Given
        when(postRepository.findById(any(Long.class))).thenReturn(Optional.of(post));

        // When
        PostResponse result = postService.getPostById(1L);

        // Then
        assertNotNull(result);
        assertEquals("Test content", result.getContent());
    }

    @Test
    public void testCreatePost() {
        // Given
        PostCreateRequest request = new PostCreateRequest();
        request.setContent("New content");
        when(postRepository.save(any(Post.class))).thenReturn(post);

        // When
        Post result = postService.createPost(request);

        // Then
        assertNotNull(result);
        assertEquals("Test content", result.getContent());
    }

    @Test
    public void testUpdatePost() {
        // Given
        PostCreateRequest request = new PostCreateRequest();
        request.setContent("Updated content");
        when(postRepository.findById(any(Long.class))).thenReturn(Optional.of(post));
        when(postRepository.save(any(Post.class))).thenReturn(post);

        // When
        Post result = postService.updatePost(1L, request);

        // Then
        assertNotNull(result);
        assertEquals("Updated content", result.getContent());
    }

    @Test
    public void testDeletePost() {
        // Given
        doNothing().when(postRepository).deleteById(any(Long.class));

        // When
        postService.deletePost(1L);

        // Then
        verify(postRepository, times(1)).deleteById(any(Long.class));
    }

    @Test
    public void testFindPostById_NotFound() {
        // Given
        when(postRepository.findById(any(Long.class))).thenReturn(Optional.empty());

        // When / Then
        CommonException exception = assertThrows(CommonException.class, () -> {
            postService.findPostById(1L);
        });

        assertEquals(ErrorStatus.POST_ID_NOT_FOUND.getStatus(), exception.getStatus());
    }
}
