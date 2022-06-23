package com.example.learning_english.service;

import com.example.learning_english.entity.Course;
import com.example.learning_english.entity.search.SearchRequest;
import com.example.learning_english.repository.CourseRepository;
import com.example.learning_english.specifications.SearchSpecification;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CourseService {

    @Autowired
    public CourseRepository courseRepository;

    public Page<Course> findAll(int page, int limit) {
        PageRequest pageRequest = PageRequest.of(page, limit);
        return courseRepository.findAll(pageRequest);
    }

    public Course save(Course course) {
        return courseRepository.save(course);
    }

    public Optional<Course> findById(int id) {
        return courseRepository.findById(id);
    }

    public void delete(int id) {
        courseRepository.deleteById(id);
    }

    public Page<Course> search(SearchRequest searchRequest){
        SearchSpecification<Course> specification = new SearchSpecification<>(searchRequest);
        PageRequest pageRequest = PageRequest.of(searchRequest.getPage(), searchRequest.getLimit());
        return courseRepository.findAll(specification, pageRequest);
    }
}
