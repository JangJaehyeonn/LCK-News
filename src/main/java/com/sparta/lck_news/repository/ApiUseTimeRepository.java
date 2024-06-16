package com.sparta.lck_news.repository;

import com.sparta.lck_news.entity.ApiUseTime;
import com.sparta.lck_news.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ApiUseTimeRepository extends JpaRepository<ApiUseTime, Long> {
    Optional<ApiUseTime> findByUser(User user);
}