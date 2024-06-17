package com.sparta.lck_news.dto;


import com.sparta.lck_news.entity.Post;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@NoArgsConstructor
public class PostCreateRequest {
    private String content;

    public PostCreateRequest(String testContent) {
    }

    public Post toEntity() {
        return new Post(content);
    }
}
