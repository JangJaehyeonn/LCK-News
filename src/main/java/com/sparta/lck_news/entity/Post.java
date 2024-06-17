package com.sparta.lck_news.entity;


import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;
import java.time.LocalDateTime;


@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Post extends Timestamped{

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String content;


    public Post(String content) {
        this.content = content;
    }

    public Post(Long id, String content, LocalDateTime createdAt, LocalDateTime updatedAt) {
        super();
    }


    public void update(String content) {
        this.content = content;
    }
}
