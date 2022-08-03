package com.example.learning_english.service;

import com.example.learning_english.entity.Answer;
import com.example.learning_english.entity.Blog;
import org.springframework.data.domain.Page;

import java.util.Optional;

public interface BaseService<T> {
    Page<T> findAll(int page, int limit);
    Optional<T> findById(int id);
    void delete(int id);
    T save(T data);
}