package com.example.learning_english.service;

import com.example.learning_english.entity.Blog;
import com.example.learning_english.repository.BlogRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class BlogService {
    public final BlogRepository blogRepository;

    public BlogService(BlogRepository blogRepository) {
        this.blogRepository = blogRepository;
    }

    public Page<Blog> findAll(int page, int limit){
        PageRequest pageRequest = PageRequest.of(page,limit);
        return blogRepository.findAll(pageRequest);
    }

    public Optional<Blog> findById(int id){
        return blogRepository.findById(id);
    }

    public void delete(int id){
        blogRepository.deleteById(id);
    }

    public Blog save(Blog blog){
        return blogRepository.save(blog);
    }

}
