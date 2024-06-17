package com.sparta.lck_news.entity;

import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class PostTest {

    @Test
    public void testConsAndGetters(){  //Constructor and getter

        //Given (테스트에 사용할 문자열 준비)
        String content = "테스트 내용";

        //When ( 객체 생성)
        Post post = new Post(content);

        //Then (Post 객체의 값이 null인지 content가 주어진 값과 동일한지 검증)
        assertNull(post.getId());
        assertEquals(content, post.getContent());
    }

    @Test
    public void testUpdateMethod(){

        //Given
        String initialContent = "Initial content";
        String updatedContent = "Updated content";
        Post post = new Post(initialContent);

        //When
        post.update(updatedContent);

        //Then
        assertEquals(updatedContent, post.getContent());
    }

}